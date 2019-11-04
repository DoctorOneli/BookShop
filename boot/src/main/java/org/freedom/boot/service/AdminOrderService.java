package org.freedom.boot.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.freedom.boot.bean.Order;
import org.freedom.boot.bean.OrderExample;
import org.freedom.boot.bean.User;
import org.freedom.boot.bean.UserExample;
import org.freedom.boot.bean.OrderExample.Criteria;
import org.freedom.boot.dao.OrderMapper;
import org.freedom.boot.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminOrderService {

	@Autowired
	OrderMapper orderMapper;

	@Autowired
	UserMapper userMapper;

	/**
	 * 返回订单（根据时间降序）
	 * 
	 * @return
	 */
	public List<Order> getOrderList(Integer orderStateId, Date date, String condition) {

		boolean ifFindByOrderId=true;
		OrderExample orderExample = new OrderExample();
		 
		Criteria criteriaOrderId = orderExample.createCriteria();
		Criteria criteriaUserName = orderExample.createCriteria();
		if (condition != "") {
			Pattern pattern = Pattern.compile("[0-9]*");
			if (pattern.matcher(condition).matches()) {
				criteriaOrderId.andOrderIdEqualTo(Integer.valueOf(condition));
				criteriaUserName.andUserNameLike("%" + condition + "%");
			} else {
				criteriaUserName.andUserNameLike("%" + condition + "%");
				ifFindByOrderId=false;
			}
		}

		if (date != null) {
			if(ifFindByOrderId)
			{
			criteriaOrderId.andCreateTimeGreaterThanOrEqualTo(date);
			}
			criteriaUserName.andCreateTimeGreaterThanOrEqualTo(date);
			Date date2 = date;
			Calendar c = Calendar.getInstance();
			c.setTime(date2);
			c.add(Calendar.DATE, 1);
			if(ifFindByOrderId)
			{
				criteriaOrderId.andCreateTimeLessThan(c.getTime());
			}
		
			criteriaUserName.andCreateTimeLessThan(c.getTime());

		}

		if (orderStateId != 10) {
			if (orderStateId == 3) {
				if(ifFindByOrderId)
				{
					criteriaOrderId.andOrderStateIdBetween(3, 4);
				}
				
				criteriaUserName.andOrderStateIdBetween(3, 4);

			} else {
				if(ifFindByOrderId)
				{
					criteriaOrderId.andOrderStateIdEqualTo(orderStateId);
				}
				criteriaUserName.andOrderStateIdEqualTo(orderStateId);
			}

		}
		orderExample.or(criteriaOrderId);
		orderExample.or(criteriaUserName);
		orderExample.setOrderByClause("create_time DESC");
		return orderMapper.selectByExampleWithAddress(orderExample);
	}

	/**
	 * 根据订单id查询订单
	 * 
	 * @param OrderId
	 * @return
	 */
	public Order getOrderByOrderId(Integer orderId) {
		return orderMapper.selectByPrimaryKey(orderId);
	}

	/**
	 * 更新订单
	 * @param order
	 * @return
	 */
	public int updateOrderState(Order order) {
		return orderMapper.updateByPrimaryKeySelective(order);
	}

}
