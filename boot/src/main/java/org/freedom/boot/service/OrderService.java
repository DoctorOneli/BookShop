package org.freedom.boot.service;

import java.util.Date;
import java.util.List;

import org.freedom.boot.bean.AddEvaluate;
import org.freedom.boot.bean.Book;
import org.freedom.boot.bean.Evaluate;
import org.freedom.boot.bean.Msg;
import org.freedom.boot.bean.Order;
import org.freedom.boot.bean.OrderExample;
import org.freedom.boot.bean.OrderExample.Criteria;
import org.freedom.boot.bean.OrderItem;
import org.freedom.boot.dao.BookMapper;
import org.freedom.boot.dao.EvaluateLikeMapper;
import org.freedom.boot.dao.EvaluateMapper;
import org.freedom.boot.dao.OrderItemMapper;
import org.freedom.boot.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

	@Autowired
	OrderMapper orderMapper;

	@Autowired
	OrderItemMapper orderItemMapper;

	@Autowired
	BookMapper bookMapper;

	@Autowired
	EvaluateMapper evaluateMapper;

	/**
	 * 创建订单
	 * 
	 * @param orderList
	 * @param userId
	 * @param addressId
	 * @param remark
	 */
	public void createOrder(List<OrderItem> orderList, Integer userId, Integer addressId, String remark) {

		Order order = new Order(null, new Date(), 0, userId, addressId, false, remark);
		// 获取到自增id
		int result = orderMapper.insertSelective(order);

		for (OrderItem orderItem : orderList) {
			orderItemMapper.insertSelective(orderItem);

			Book book = bookMapper.selectByPrimaryKey(orderItem.getBookId());
			book.setBookCount(book.getBookCount() - orderItem.getCount());
			bookMapper.updateByPrimaryKey(book);
		}

	}

	/**
	 * 删除订单（实则是隐藏订单，修改订单flag为1）
	 * 
	 * @param orderId
	 */
	public void deleteOrder(Integer orderId) {
		Order order = orderMapper.selectByPrimaryKey(orderId);

		// flag修改为1，表示删除状态
		order.setFlag(true);
		orderMapper.updateByPrimaryKey(order);
	}

	/**
	 * 根据用户id查询所有订单
	 * 
	 * @param userId
	 * @return
	 */
	public List<Order> getOrderListByUser(Integer userId) {

		OrderExample orderExample = new OrderExample();
		Criteria criteria = orderExample.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andFlagEqualTo(false);
		orderExample.setOrderByClause("create_time DESC");
		return orderMapper.selectByExampleWithAll(orderExample);
	}

	/**
	 * 取消订单
	 * 
	 * @param orderId
	 * @param orderStateId
	 * @param detail
	 */
	public void updateOrderToCancel(Integer orderId, Integer orderStateId, String detail) {
		Order order = orderMapper.selectByPrimaryKey(orderId);
		order.setRemark(detail);
		order.setOrderStateId(orderStateId);
		orderMapper.updateByPrimaryKeyWithBLOBs(order);
	}

	/**
	 * 修改订单状态
	 * 
	 * @param orderId
	 * @param orderStateId
	 */
	public void updateOrderState(Integer orderId, Integer orderStateId) {
		Order order = orderMapper.selectByPrimaryKey(orderId);
		order.setOrderStateId(orderStateId);
		orderMapper.updateByPrimaryKey(order);

	}

	/**
	 * 修改订单地址
	 * 
	 * @param orderId
	 * @param addressId
	 */
	public void updateOrderAddress(Integer orderId, Integer addressId) {
		Order order = orderMapper.selectByPrimaryKey(orderId);
		order.setAddressId(addressId);
		orderMapper.updateByPrimaryKey(order);

	}

	/**
	 * 查询所有订单
	 * 
	 * @param userId
	 * @return
	 */
	public List<Order> getOrderList() {

		return orderMapper.selectByExampleWithAll(null);
	}

	/**
	 * 评价订单
	 * 
	 * @param userId
	 * @param list
	 * @param flag
	 * @param orderId
	 * @return
	 */
	public int addEvaluate(Integer userId, List<AddEvaluate> list, Integer flag, Integer orderId) {
		int result = 0;
		for (AddEvaluate addEvaluate : list) {
			Evaluate evaluate = new Evaluate();
			evaluate.setUserId(userId);
			evaluate.setOrderItemId(addEvaluate.getOrder_item_id());
			evaluate.setImg(addEvaluate.getImg());
			evaluate.setContent(addEvaluate.getContent());
			evaluate.setEvaluateTime(new Date());
			evaluate.setIfAnonymous(flag);
			result += evaluateMapper.insertSelective(evaluate);
		}
		Order order = orderMapper.selectByPrimaryKey(orderId);
		order.setOrderStateId(4);
		orderMapper.updateByPrimaryKey(order);
		return result;
	}

}
