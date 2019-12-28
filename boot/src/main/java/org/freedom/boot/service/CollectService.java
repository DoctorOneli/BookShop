package org.freedom.boot.service;

import java.util.Date;
import java.util.List;

import org.freedom.boot.bean.Collect;
import org.freedom.boot.bean.CollectExample;
import org.freedom.boot.bean.ShopCart;
import org.freedom.boot.dao.CollectMapper;
import org.freedom.boot.dao.ShopCartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectService {

	@Autowired
	CollectMapper collectMapper;
	
	@Autowired
	ShopCartMapper shopCartMapper;

	/**
	 * 根据用户id查询收藏列表
	 * 
	 * @param id
	 * @return
	 */
	public List<Collect> getCollects(Integer id) {
		CollectExample example = new CollectExample();
		CollectExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(id);
		return collectMapper.selectByExample(example);
	}

	/**
	 * 根据书本id，用户id添加收藏
	 * @param bookId
	 * @param userId
	 * @return
	 */
	public int addCollect(Integer bookId,Integer userId) {
		
		CollectExample example = new CollectExample();
		CollectExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andBookIdEqualTo(bookId);
		//避免重复添加收藏
		if(collectMapper.selectByExample(example)!=null)
		{
			return 1;
		}
		else {
			Collect collect = new Collect();
			collect.setBookId(bookId);
			collect.setUserId(userId);
			collect.setCreateTime(new Date());
			return collectMapper.insertSelective(collect);
		}
	
	}
		
	/**
	 * 根据收藏数组，用户id，从收藏夹选择物品加入购物车
	 * 
	 * @param id
	 * @return
	 */
	public int addToCart(List<Integer> list,Integer userId) {
		int result = 0;
		for (Integer bookId : list) {
			ShopCart shopCart=new ShopCart();
			shopCart.setBookId(bookId);
			shopCart.setUserId(userId);
			shopCart.setCreateTime(new Date());
			shopCart.setCount(1);
			result+=shopCartMapper.insertSelective(shopCart);
		}
		return result;
	}
	
	/**
	 * 接根据用户id删除地址
	 * 
	 * @param id
	 * @return
	 */
	public int deleteAllCollect(Integer id) {
		CollectExample example = new CollectExample();
		CollectExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(id);
		return collectMapper.deleteByExample(example);
	}
	
	/**
	 * 根据收藏id删除地址
	 * 
	 * @param id
	 * @return
	 */
	public int deleteSomeCollect(List<Integer> list) {
		int result = 0;
		for (Integer id : list) {
			result+=collectMapper.deleteByPrimaryKey(id);
		}
		return result;
	}

}
