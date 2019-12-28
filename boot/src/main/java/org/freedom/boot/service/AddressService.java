package org.freedom.boot.service;

import java.util.List;

import org.freedom.boot.bean.Address;
import org.freedom.boot.bean.AddressExample;
import org.freedom.boot.bean.AddressExample.Criteria;
import org.freedom.boot.dao.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

	@Autowired
	AddressMapper addressMapper;
	/**
	 * 根据用户id查询地址
	 * @param id
	 * @return
	 */
	public List<Address> getAddresses(Integer id)
	{
		AddressExample addressExample=new AddressExample();
		Criteria criteria=addressExample.createCriteria();
		criteria.andUserIdEqualTo(id);
		return addressMapper.selectByExample(addressExample);
	}
	
	/**
	 * 根据地址id查询具体地址
	 * @param id
	 * @return
	 */
	public Address getAddressDetail(Integer id)
	{
		return addressMapper.selectByPrimaryKey(id);
	}
	 
	/**
	 * 接收address添加
	 * @param id
	 * @return
	 */
	public int addAddress(Address address)
	{
		 Address newAddress=address;
		 return addressMapper.insertSelective(newAddress);
	}
	
	/**
	 * 接收address更新(根据address_id）
	 * @param id
	 * @return
	 */
	public int updateAddress(Address address)
	{
		 return addressMapper.updateByPrimaryKeySelective(address);
	}
	
	/**
	 * 接根据id删除地址
	 * @param id
	 * @return
	 */
	public int deleteAddress(Integer id)
	{
		 return addressMapper.deleteByPrimaryKey(id);
	}
	
}
