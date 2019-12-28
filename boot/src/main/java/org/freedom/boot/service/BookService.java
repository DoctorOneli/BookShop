package org.freedom.boot.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.freedom.boot.bean.Book;
import org.freedom.boot.bean.BookExample;
import org.freedom.boot.bean.BookType;
import org.freedom.boot.bean.Collect;
import org.freedom.boot.bean.CollectExample;
import org.freedom.boot.bean.ShopCart;
import org.freedom.boot.bean.ShopCartExample;
import org.freedom.boot.bean.CollectExample.Criteria;
import org.freedom.boot.dao.BookMapper;
import org.freedom.boot.dao.BookTypeMapper;
import org.freedom.boot.dao.CollectMapper;
import org.freedom.boot.dao.ShopCartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Data;

@Service
@CacheConfig(cacheNames = "book")
public class BookService {

	@Autowired
	BookMapper bookMapper;

	@Autowired
	CollectMapper collectMapper;

	@Autowired
	BookTypeMapper bookTypeMapper;
	
	@Autowired
	ShopCartMapper shopCartMapper;

	/**
	 * 查询书本集合（结合cache将查询结果添加到到redis作缓存） 如果书本类型是0，表示返回全部
	 * 
	 * @return
	 */

	@Cacheable()
	public List<Book> getBookList(Integer bookTypeId) {
		BookExample bookExample = new BookExample();
		if (bookTypeId == 0) {
			BookExample.Criteria criteria = bookExample.createCriteria();
			criteria.andBookTypeIdEqualTo(bookTypeId);
		}

		bookExample.setOrderByClause("book_id");
		return bookMapper.selectByExampleWithType(bookExample);
	}

	/**
	 * 返回某本书具体详情，还要带有自己是否收藏的信息
	 * 
	 * @param bookId
	 * @param userId
	 * @return
	 */
	@Cacheable(key = "#bookId+#userId")
	public BookWithCollect getBook(Integer bookId, Integer userId) {
		System.out.println("执行查询");

		// 查询收藏表，是否有该用户收集过该书
		CollectExample collectExample = new CollectExample();
		Criteria criteria = collectExample.createCriteria();
		criteria.andBookIdEqualTo(bookId);
		criteria.andUserIdEqualTo(userId);

		// 是否收藏过
		Integer ifCollect = collectMapper.selectByExample(collectExample).isEmpty() ? 0 : 1;
		BookWithCollect bookWithCollect = new BookWithCollect(ifCollect, bookMapper.selectByPrimaryKey(bookId));

		return bookWithCollect;
	}

	/**
	 * 根据关键字模糊查询书本集合
	 * 
	 * @param id
	 * @return
	 */
	@Cacheable
	public List<Book> getBookByName(String name) {
		BookExample bookExample = new BookExample();
		BookExample.Criteria criteria = bookExample.createCriteria();

		criteria.andBookNameLike("%" + name + "%");

		return bookMapper.selectByExampleWithType(bookExample);
	}

	/**
	 * 返回所有书本类型
	 * 
	 * @return
	 */
	@Cacheable
	public List<BookType> getBookTypes() {
		return bookTypeMapper.selectByExample(null);
	}
	
	/**
	 * 将书本添加到购物车
	 * @param bookId
	 * @param userId
	 * @return
	 */
	public int addToCart(Integer bookId,Integer userId)
	{
		ShopCartExample example=new ShopCartExample();
		ShopCartExample.Criteria criteria=example.createCriteria();
		
		criteria.andUserIdEqualTo(userId);
		criteria.andBookIdEqualTo(bookId);
		//取消收藏
		if(shopCartMapper.selectByExample(example)!=null)
		{
			return shopCartMapper.deleteByExample(example);
		}
		else {
			ShopCart shopCart=new ShopCart();
			shopCart.setUserId(userId);
			shopCart.setBookId(bookId);
			shopCart.setCount(1);
			shopCart.setCreateTime(new Date());
			return shopCartMapper.insertSelective(shopCart);
		}
		
	}
	
	/**
	 * 将书本添加到收藏夹
	 * @param bookId
	 * @param userId
	 * @return
	 */
	public int addToCollect(Integer bookId,Integer userId)
	{
		CollectExample example = new CollectExample();
		CollectExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andBookIdEqualTo(bookId);
		//取消收藏
		if(collectMapper.selectByExample(example)!=null)
		{
			return collectMapper.deleteByExample(example);
		}
		else {
			Collect collect = new Collect();
			collect.setBookId(bookId);
			collect.setUserId(userId);
			collect.setCreateTime(new Date());
			return collectMapper.insertSelective(collect);
		}
	}

}

class BookWithCollect implements Serializable {

	Integer ifCollect;

	Book book;

	public Integer getIfCollect() {
		return ifCollect;
	}

	public void setIfCollect(Integer ifCollect) {
		this.ifCollect = ifCollect;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BookWithCollect() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BookWithCollect(Integer ifCollect, Book book) {
		super();
		this.ifCollect = ifCollect;
		this.book = book;
	}

}
