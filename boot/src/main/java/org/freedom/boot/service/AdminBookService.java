package org.freedom.boot.service;

import java.util.List;
import java.util.regex.Pattern;

import org.freedom.boot.bean.Book;
import org.freedom.boot.bean.BookExample;
import org.freedom.boot.bean.BookType;
import org.freedom.boot.bean.BookTypeExample;
import org.freedom.boot.bean.BookWithBLOBs;
import org.freedom.boot.bean.BookExample.Criteria;
import org.freedom.boot.dao.BookMapper;
import org.freedom.boot.dao.BookTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminBookService {

	@Autowired
	BookMapper bookMapper;

	@Autowired
	BookTypeMapper bookTypeMapper;

	/**
	 * 查询书本集合(可根据类型查询)
	 * 
	 * @return
	 */
	public List<Book> getBookList(Integer typeId,Integer publicState) {

		BookExample bookExample = new BookExample();
		Criteria criteria = bookExample.createCriteria();

		if (typeId != 0) {
			criteria.andBookTypeIdEqualTo(typeId);
		}
		
		if(publicState!=-1)
		{
			criteria.andIfPublicEqualTo(publicState);
		}
		

		bookExample.setOrderByClause("book_count DESC");
		return bookMapper.selectByExampleWithType(bookExample);

	}

	/**
	 * 根据书本编号查询书
	 * 
	 * @param typeId
	 * @param condition
	 * @return
	 */
	public List<Book> getBookListById(String condition) {

		BookExample bookExample = new BookExample();
		Criteria criteriaBookId = bookExample.createCriteria();

		if (condition != "") {
			criteriaBookId.andBookIdEqualTo(Integer.valueOf(condition));
		}

		bookExample.setOrderByClause("book_count DESC");
		bookExample.or(criteriaBookId);
		return bookMapper.selectByExampleWithType(bookExample);

	}

	/**
	 * 根据书本名字模糊查询书
	 * 
	 * @param typeId
	 * @param condition
	 * @return
	 */
	public List<Book> getBookListByName(String condition) {

		BookExample bookExample = new BookExample();
		Criteria criteriaBookName = bookExample.createCriteria();
		if (condition != "") {
			criteriaBookName.andBookNameLike("%" + condition + "%");
		}

		bookExample.setOrderByClause("book_count DESC");
		bookExample.or(criteriaBookName);
		return bookMapper.selectByExampleWithType(bookExample);

	}

	/**
	 * 查询书本类型集合
	 * 
	 * @return
	 */
	public List<BookType> getBookTypeList() {
		BookTypeExample bookTypeExample = new BookTypeExample();
		org.freedom.boot.bean.BookTypeExample.Criteria criteria = bookTypeExample.createCriteria();
		criteria.andTypeNameNotEqualTo("全部");
		return bookTypeMapper.selectByExample(bookTypeExample);

	}

	/**
	 * 添加书本
	 * 
	 * @param bookWithBLOBs
	 * @return
	 */
	public int addBook(BookWithBLOBs bookWithBLOBs) {
		BookWithBLOBs book = bookWithBLOBs;
		bookMapper.insertSelective(book);
		int bookId = book.getBookId().intValue();
		return bookId;
	}

	/**
	 * 根据主键查询书本
	 * 
	 * @param bookWithBLOBs
	 * @return
	 */
	public BookWithBLOBs getBook(Integer bookId) {
		return bookMapper.selectByPrimaryKey(bookId);
	}

	/**
	 * 根据主键更新书本
	 * 
	 * @param bookWithBLOBs
	 * @return
	 */
	public int updateBook(BookWithBLOBs bookWithBLOBs) {
		return bookMapper.updateByPrimaryKeySelective(bookWithBLOBs);
	}

	/**
	 * 根据主键删除书本
	 * 
	 * @param bookId
	 * @return
	 */
	public int deleteBook(Integer bookId) {
		return bookMapper.deleteByPrimaryKey(bookId);
	}

	/**
	 * 根据主键更新书本类型
	 * 
	 * @param bookWithBLOBs
	 * @return
	 */
	public int updateBookType(BookType bookType) {
		return bookTypeMapper.updateByPrimaryKey(bookType);
	}

	/**
	 * 根据主键删除书本类型
	 * 
	 * @param bookId
	 * @return
	 */
	public int deleteBookType(Integer bookTypeId) {
		return bookTypeMapper.deleteByPrimaryKey(bookTypeId);
	}

	/**
	 * 添加书本类型
	 * 
	 * @param bookWithBLOBs
	 * @return
	 */
	public int addBookType(BookType type) {
		return bookTypeMapper.insertSelective(type);

	}

	/**
	 * 更新发布状态
	 * @param id
	 * @param publicState
	 * @return
	 */
	public int updateBookPublic(Integer id,Boolean publicState) {
		BookWithBLOBs book=bookMapper.selectByPrimaryKey(id);
		book.setIfPublic(publicState);
		
		if(bookMapper.updateByPrimaryKeySelective(book)==1)
		{
			return 1;
		}
		else {
			return 0;
		}
	
	}

}
