package org.freedom.boot.service;

import java.util.Date;
import java.util.List;

import org.freedom.boot.bean.Collect;
import org.freedom.boot.bean.CollectExample;
import org.freedom.boot.bean.Shop;
import org.freedom.boot.bean.ShopCart;
import org.freedom.boot.bean.ShopCartExample;
import org.freedom.boot.dao.CollectMapper;
import org.freedom.boot.dao.ShopCartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopCartService {

	@Autowired
	CollectMapper collectMapper;

	@Autowired
	ShopCartMapper shopCartMapper;

	/**
	 * 根据用户id查询购物车
	 * 
	 * @param id
	 * @return
	 */
	public List<ShopCart> getShopCarts(Integer id) {
		ShopCartExample example = new ShopCartExample();
		ShopCartExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(id);
		return shopCartMapper.selectByExample(example);
	}

	/**
	 * 根据书本id，用户id添加到购物车
	 * 
	 * @param bookId
	 * @param userId
	 * @return
	 */
	public int addShopCart(Integer bookId, Integer userId) {
		ShopCartExample example = new ShopCartExample();
		ShopCartExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andBookIdEqualTo(bookId);

		List<ShopCart> list = shopCartMapper.selectByExample(example);
		// 更新购物车该物品数量，原基础数量上加1
		if (list != null) {
			for (ShopCart shopCart : list) {
				shopCart.setCount(shopCart.getCount() + 1);
				shopCartMapper.updateByPrimaryKey(shopCart);
			}
			return 1;
		} else {
			ShopCart shopCart = new ShopCart();
			shopCart.setBookId(bookId);
			shopCart.setUserId(userId);
			shopCart.setCount(1);
			shopCart.setCreateTime(new Date());
			return shopCartMapper.insertSelective(shopCart);
		}

	}

	/**
	 * 根据购物车id数组，用户id，从购物车选择物品加入到收藏夹
	 * 
	 * @param id
	 * @return
	 */
	public int addToCollect(List<Integer> list, Integer userId) {
		int result = 0;
		for (Integer bookId : list) {
			Collect collect = new Collect();
			collect.setBookId(bookId);
			collect.setUserId(userId);
			collect.setCreateTime(new Date());

			result += collectMapper.insertSelective(collect);
		}
		return result;
	}

	/**
	 * 接根据用户id删除全部购物车商品
	 * 
	 * @param id
	 * @return
	 */
	public int deleteAllCart(Integer id) {
		ShopCartExample example = new ShopCartExample();
		ShopCartExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(id);
		return shopCartMapper.deleteByExample(example);
	}

	/**
	 * 根据收藏id删除部分购物车商品
	 * 
	 * @param id
	 * @return
	 */
	public int deleteSomeCart(List<Integer> list) {
		int result = 0;
		for (Integer id : list) {
			result += shopCartMapper.deleteByPrimaryKey(id);
		}
		return result;
	}

}
