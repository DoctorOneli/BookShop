package org.freedom.boot.controller;

import java.util.List;

import org.freedom.boot.bean.Msg;
import org.freedom.boot.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class CollectController {
	
	@Autowired
	CollectService collectService;
	
	/**
	 * 根据用户id查询收藏列表
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/collect")
	public Msg getCollects(@RequestParam("user_id")Integer id) {
		 
		return Msg.success().add("data", collectService.getCollects(id));
	}

	/**
	 * 根据书本id，用户id添加收藏
	 * @param bookId
	 * @param userId
	 * @return
	 */
	@PostMapping("/collectadd")
	public Msg addCollect(Integer bookId,Integer userId) {
		if(collectService.addCollect(bookId, userId)==1)
		{
			return Msg.success();
		}
		else {
			return Msg.fail();
		}
		
	}
		
	/**
	 * 根据收藏数组，用户id，从收藏夹选择物品加入购物车
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/removetocart")
	public Msg addToCart(@RequestParam("collects")List<Integer> list,@RequestParam("user_id")Integer userId) {
		if(collectService.addToCart(list, userId)>=0)
		{
			return Msg.success();
		}
		else {
			return Msg.fail();
		}
	}
	
	/**
	 * 接根据用户id删除地址
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/collectdeleteall")
	public Msg deleteAllCollect(Integer user_id) {
		if(collectService.deleteAllCollect(user_id)>=0)
		{
			return Msg.success();
		}
		else {
			return Msg.fail();
		}
	}
	
	/**
	 * 根据收藏id数组删除地址
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/collectdelete")
	public Msg deleteSomeCollect(List<Integer> list) {
		if(collectService.deleteSomeCollect(list)>=0)
		{
			return Msg.success();
		}
		else {
			return Msg.fail();
		}
	}

	
}
