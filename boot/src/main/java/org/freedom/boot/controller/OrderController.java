package org.freedom.boot.controller;

import java.util.List;

import org.freedom.boot.bean.AddEvaluate;
import org.freedom.boot.bean.Msg;
import org.freedom.boot.bean.OrderItem;
import org.freedom.boot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class OrderController {

	@Autowired
	OrderService orderService;

	/**
	 * 根据user_id获取订单列表
	 * 
	 * @param user_id
	 * @return
	 */
	@GetMapping("/orderlist")
	public Msg listOrder(Integer user_id) {
		return Msg.success().add("order", orderService.getOrderListByUser(user_id));
	}

	/**
	 * 创建订单请求
	 * 
	 * @param order
	 * @param user_id
	 * @param address_id
	 * @param remark
	 * @return
	 */
	@PostMapping("/order")
	public Msg createOrder(@RequestBody List<OrderItem> order, Integer user_id, Integer address_id, String remark) {

		orderService.createOrder(order, user_id, address_id, remark);
		return Msg.success();
	}

	/**
	 * 删除订单（实则是修改订单flag为1）
	 * 
	 * @param orderId
	 * @return
	 */

	@PutMapping("/orderdelete")
	public Msg deleteOrder(Integer order_id) {
		orderService.deleteOrder(order_id);
		return Msg.success();
	}

	/**
	 * 取消订单
	 * 
	 * @param order_id
	 * @param detail
	 * @return
	 */
	@PutMapping("/ordercancel")
	public Msg updateOrderToCancel(Integer order_id, String detail) {
		orderService.updateOrderToCancel(order_id, 5, detail);
		return Msg.success();
	}

	/**
	 * 修改订单状态（比如付款后修改状态成待发货，确认收货后改成待评价等）
	 * 
	 * @param order_id
	 * @param order_state_id
	 * @return
	 */
	@PutMapping("/orderupdate")
	public Msg updateOrderState(Integer order_id, Integer order_state_id) {
		orderService.updateOrderState(order_id, order_state_id);
		return Msg.success();
	}

	/**
	 * 修改订单地址
	 * 
	 * @param order_id
	 * @param address_id
	 * @return
	 */
	@PutMapping("/orderaddress")
	public Msg updateOrderAddress(Integer order_id, Integer address_id) {
		orderService.updateOrderAddress(order_id, address_id);
		return Msg.success();
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
	@PostMapping("/evaluateorder")
	public Msg addEvaluate(@RequestParam("user_id") Integer userId, @RequestBody List<AddEvaluate> list,
			@RequestParam("if_anonymous") Integer flag, Integer order_id) {
		if (orderService.addEvaluate(userId, list, flag, userId) >= 0) {
			return Msg.success();
		} else {
			return Msg.fail();
		}
	}
}
