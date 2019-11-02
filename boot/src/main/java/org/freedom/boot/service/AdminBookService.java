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
	 * 查询书本集合
	 * 
	 * @return
	 */
	public List<Book> getBookList(Integer typeId, String condition) {

		boolean ifFindByBookId=true;
		BookExample bookExample = new BookExample();
		Criteria criteriaBookId = bookExample.createCriteria();
		Criteria criteriaBookName = bookExample.createCriteria();
		if (condition != "") {
			Pattern pattern = Pattern.compile("[0-9]*");
			if (pattern.matcher(condition).matches()) {
				criteriaBookId.andBookIdEqualTo(Integer.valueOf(condition));
				criteriaBookName.andBookNameLike("%" + condition + "%");
			} else {
				criteriaBookName.andBookNameLike("%" + condition + "%");
				ifFindByBookId=false;
			}
				 
		}

		if (typeId != 0) {
			if(ifFindByBookId)
			{
				criteriaBookId.andBookTypeIdEqualTo(typeId);
			}
		
			criteriaBookName.andBookTypeIdEqualTo(typeId);
		}

		bookExample.setOrderByClause("book_count DESC");
		bookExample.or(criteriaBookId);
		bookExample.or(criteriaBookName);
		return bookMapper.selectByExampleWithType(bookExample);

	}

	/**
	 * 查询书本类型集合
	 * 
	 * @return
	 */
	public List<BookType> getBookTypeList() {
        BookTypeExample bookTypeExample=new BookTypeExample();
        org.freedom.boot.bean.BookTypeExample.Criteria criteria=bookTypeExample.createCriteria();
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
	
	

}
