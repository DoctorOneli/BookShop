package org.freedom.boot.controller;

import java.util.List;

import org.freedom.boot.bean.Msg;
import org.freedom.boot.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ShopCartController {
	
	@Autowired
	ShopCartService shopCartService;
	
	/**
	 * 根据用户id查询购物车列表
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/cart")
	public Msg getcarts(@RequestParam("user_id")Integer id) {
		 
		return Msg.success().add("data", shopCartService.getShopCarts(id));
	}

	/**
	 * 根据书本id，用户id添加购物车
	 * @param bookId
	 * @param userId
	 * @return
	 */
	@PostMapping("/cartadd")
	public Msg addcart(Integer bookId,Integer userId) {
		if(shopCartService.addShopCart(bookId, userId)==1)
		{
			return Msg.success();
		}
		else {
			return Msg.fail();
		}
		
	}
		
	/**
	 * 根据购物车数组，用户id，从购物车夹选择物品加入收藏夹
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/removetocart")
	public Msg addToCart(@RequestParam("carts")List<Integer> list,@RequestParam("user_id")Integer userId) {
		if(shopCartService.addToCollect(list, userId)>=0)
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
	@PostMapping("/cartdeleteall")
	public Msg deleteAllCart(Integer user_id) {
		if(shopCartService.deleteAllCart(user_id)>=0)
		{
			return Msg.success();
		}
		else {
			return Msg.fail();
		}
	}
	
	/**
	 * 根据购物车id数组删除地址
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/cartdelete")
	public Msg deleteSomeCart(List<Integer> list) {
		if(shopCartService.deleteSomeCart(list)>=0)
		{
			return Msg.success();
		}
		else {
			return Msg.fail();
		}
	}

	
}
