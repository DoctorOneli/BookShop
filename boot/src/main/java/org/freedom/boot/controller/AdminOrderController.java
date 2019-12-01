package org.freedom.boot.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.freedom.boot.bean.Msg;
import org.freedom.boot.bean.Order;
import org.freedom.boot.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RequestMapping("/adminorder")
@RestController
public class AdminOrderController {

	@Autowired
	AdminOrderService adminOrderService;

	/**
	 * 返回管理端的订单(分页)
	 * 
	 * @param pageIndex
	 * @return
	 */
	@GetMapping("/order")
	public Msg getOrderList(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
			@RequestParam("stateId") Integer stateId, @RequestParam("date") String date,
			@RequestParam("condition") String condition) {

		PageHelper.startPage(pageIndex, 10);
		Date result = null;

		try {
			if (date != "")
				result = DateUtils.parseDate(date, "yyyy-MM-dd");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PageInfo pageInfo = new PageInfo(adminOrderService.getOrderList(stateId, result, condition));
		return Msg.success().add("order", pageInfo);

	}
	
	/**
	 * 根据订单id返回订单详情
	 * @param orderId
	 * @return
	 */
	@GetMapping("/order/{id}")
	public Msg getOrderByOrderId(@PathVariable("id") Integer orderId)
	{
		return Msg.success().add("order", adminOrderService.getOrderByOrderId(orderId)); 
	}
	
	/**
	 * 更新订单状态
	 * @param order
	 * @return
	 */
	@PutMapping("/order")
	public Msg updateOrderState(@RequestBody Order order)
	{
		if(adminOrderService.updateOrderState(order)==1)
		{
			return Msg.success().add("result", "更新成功");
		}
		else {
			return Msg.fail();
		}
	}
	
	

}
