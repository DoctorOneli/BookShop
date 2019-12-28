package org.freedom.boot.controller;

import java.util.List;

import org.freedom.boot.bean.Address;
import org.freedom.boot.bean.Msg;
import org.freedom.boot.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class AddressController {


	@Autowired
	AddressService addressService;
	
	/**
	 * 根据用户id查询地址
	 * @param id
	 * @return
	 */
	@GetMapping("/address/{id}")
	public Msg getAddresses(@PathVariable("id")Integer id)
	{
		return Msg.success().add("data", addressService.getAddresses(id));
	}
	
	/**
	 * 根据地址id查询具体地址
	 * @param id
	 * @return
	 */
	@GetMapping("/findaddress/{id}")
	public Msg getAddressDetail(@PathVariable("id")Integer id)
	{
		return Msg.success().add("data", addressService.getAddressDetail(id));
	}
	 
	/**
	 * 接收address添加
	 * @param id
	 * @return
	 */
	@PostMapping("/addaddress")
	public Msg addAddress(@RequestBody Address address)
	{
		if (addressService.addAddress(address) == 1) {
			return Msg.success();
		} else {
			return Msg.fail();
		}
		 
	}
	
	/**
	 * 接收address更新(根据address_id）
	 * @param id
	 * @return
	 */
	@PostMapping("/updateaddress")
	public Msg updateAddress(@RequestBody Address address)
	{
		if (addressService.updateAddress(address)>=0) {
			return Msg.success();
		} else {
			return Msg.fail();
		}
		 
	}
	/**
	 * 接根据id删除地址
	 * @param id
	 * @return
	 */
	@PostMapping("/deleteaddress")
	public Msg deleteAddress(Integer id)
	{
		if (addressService.deleteAddress(id)==1) {
			return Msg.success();
		} else {
			return Msg.fail();
		}
		 
	}
}
