package org.freedom.boot.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.freedom.boot.bean.Book;
import org.freedom.boot.bean.BookExample;
import org.freedom.boot.bean.CollectExample;
import org.freedom.boot.bean.CollectExample.Criteria;
import org.freedom.boot.dao.BookMapper;
import org.freedom.boot.dao.CollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames="book")
public class BookService {

	@Autowired
	BookMapper bookMapper;

	@Autowired
	CollectMapper collectMapper;

	/**
	 * 查询书本集合
	 * 
	 * @return
	 */
	 
	@Cacheable()
	public List<Book> getBookList() {
		System.out.println("执行查询书本集合");
		BookExample bookExample=new BookExample();
		bookExample.setOrderByClause("book_id");
		return bookMapper.selectByExampleWithType(bookExample);
	}

	/**
	 * 返回某本书，还要带有自己是否收藏的信息
	 * 
	 * @param bookId
	 * @param userId
	 * @return
	 */
	@Cacheable(key="#bookId+#userId") 
	public BookWithCollect getBook(Integer bookId, Integer userId) {
		System.out.println("执行查询");
		
		//查询收藏表，是否有该用户收集过该书
		CollectExample collectExample = new CollectExample();
		Criteria criteria = collectExample.createCriteria();
		criteria.andBookIdEqualTo(bookId);
		criteria.andUserIdEqualTo(userId);

		//是否收藏过
		Integer ifCollect = collectMapper.selectByExample(collectExample).isEmpty() ? 0 : 1;
		BookWithCollect bookWithCollect = new BookWithCollect(ifCollect, bookMapper.selectByPrimaryKey(bookId));

		return bookWithCollect;
	}
	
	@Cacheable(key="#id")
	public Book getDetailBook(Integer id)
	{
		System.out.println("执行查询");
		return bookMapper.selectByPrimaryKey(id);
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
