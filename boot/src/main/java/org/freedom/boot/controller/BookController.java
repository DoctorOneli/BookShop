package org.freedom.boot.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.freedom.boot.bean.Book;
import org.freedom.boot.bean.EvaluateExample;
import org.freedom.boot.bean.Msg;
import org.freedom.boot.bean.EvaluateExample.Criteria;
import org.freedom.boot.service.AdminEvaluateService;
import org.freedom.boot.service.BookService;
import org.freedom.boot.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/client")
public class BookController {

	@Autowired
	BookService bookService;

	@Autowired
	EvaluateService evaluateService;

	/**
	 * 返回书本集合，分页查询（类型id如果是0表示返回全部）
	 * 
	 * @param pageIndex
	 * @param typeId
	 * @return
	 */
	@GetMapping("/booklist")
	public Msg getBookList(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
			@RequestParam("type_id") Integer typeId) {
		PageHelper.startPage(pageIndex, 10);
		List<Book> bookList = bookService.getBookList(typeId);
		PageInfo pageInfo = new PageInfo(bookList);
		return Msg.success().add("book", pageInfo);

	}

	/**
	 * 返回某本书的信息，还要包括自己是否收藏过
	 * 
	 * @param book_id
	 * @param user_id
	 * @return
	 */
	@GetMapping("/book")
	public Msg getBook(Integer book_id, Integer user_id) {
		return Msg.success().add("book", bookService.getBook(book_id, user_id));
	}

	/**
	 * 根据书本id返回评价列表
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/evaluate")
	public Msg getEvaluateList(Integer id) {
		return Msg.success().add("list", evaluateService.getEvaluateList(id));
	}

	/**
	 * 根据用户id，评价id，评论内容等对评价进行评论
	 * 
	 * @param orderItemId
	 * @param userId
	 * @param content
	 * @param time
	 * @param evaluateId
	 * @return
	 * @throws ParseException
	 */
	public Msg addEvaluateReply(@RequestParam("order_item_id")Integer orderItemId, @RequestParam("user_id")Integer userId, String content, String time, @RequestParam("evaluate_id")Integer evaluateId)
			throws ParseException {
		Date date = DateUtils.parseDate(time, "yyyy-MM-dd HH:mm:ss");
		if (evaluateService.addEvaluateReply(orderItemId,evaluateId, userId, content, date) >= 0) {
			return Msg.success();
		} else {
			return Msg.fail();
		}

	}

	/**
	 * 点赞评论
	 * @param userId
	 * @param evaluateId
	 * @return
	 */
	public Msg evaluateLike(@RequestParam("user_id")Integer userId,  @RequestParam("evaluate_id")Integer evaluateId)  {
		 
		if (evaluateService.evaluateLike(userId, evaluateId) >= 0) {
			return Msg.success();
		} else {
			return Msg.fail();
		}

	}
	
	/**
	 * 删除评价的评论
	 * 
	 * @param id
	 * @return
	 */
	public Msg deleteEvaluateReply(Integer id) {

		if (evaluateService.deleteEvaluateReply(id) >= 0) {
			return Msg.success();
		} else {
			return Msg.fail();
		}

	}

	/**
	 * 删除评价
	 * 
	 * @param id
	 * @return
	 */
	public Msg deleteEvaluate(Integer id) {
		if (evaluateService.deleteEvaluate(id) >= 0) {
			return Msg.success();
		} else {
			return Msg.fail();
		}

	}

}
