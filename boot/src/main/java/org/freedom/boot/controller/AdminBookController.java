package org.freedom.boot.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.freedom.boot.bean.Book;
import org.freedom.boot.bean.BookType;
import org.freedom.boot.bean.BookWithBLOBs;
import org.freedom.boot.bean.Evaluate;
import org.freedom.boot.bean.EvaluateSubmit;
import org.freedom.boot.bean.Msg;
import org.freedom.boot.service.AdminBookService;
import org.freedom.boot.service.AdminEvaluateService;
import org.freedom.boot.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/adminbook")
public class AdminBookController {

	@Autowired
	AdminBookService adminBookService;

	@Autowired
	AdminEvaluateService adminEvaluateService;

	@Autowired
	UploadService uploadService;

	/**
	 * 返回管理端的书本集合，分页查询
	 * 
	 * @param pageIndex
	 * @return
	 */
	@GetMapping("/book")
	public Msg getBookList(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
			@RequestParam("type_id") Integer bookTypeId, Integer publicState) {
		PageHelper.startPage(pageIndex, 10);
		List<Book> bookList = adminBookService.getBookList(bookTypeId, publicState);
		PageInfo pageInfo = new PageInfo(bookList);
		return Msg.success().add("book", pageInfo);

	}

	/**
	 * 返回管理端的书本集合(根据id查询)，分页查询
	 * 
	 * @param pageIndex
	 * @param typeId
	 * @param condition
	 * @return
	 */
	@GetMapping("/bookbyid")
	public Msg getBookListById(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
			@RequestParam("condition") String condition) {
		PageHelper.startPage(pageIndex, 10);
		List<Book> bookList = adminBookService.getBookListById(condition);
		PageInfo pageInfo = new PageInfo(bookList);
		return Msg.success().add("book", pageInfo);

	}

	/**
	 * 返回管理端的书本集合(根据名字模糊查询)，分页查询
	 * 
	 * @param pageIndex
	 * @param typeId
	 * @param condition
	 * @return
	 */
	@GetMapping("/bookbyname")
	public Msg getBookListByName(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
			@RequestParam("condition") String condition) {
		PageHelper.startPage(pageIndex, 10);
		List<Book> bookList = adminBookService.getBookListByName(condition);
		PageInfo pageInfo = new PageInfo(bookList);
		return Msg.success().add("book", pageInfo);

	}

	/**
	 * 返回管理端的书本类型集合(分页)
	 * 
	 * @param pageIndex
	 * @return
	 */
	@GetMapping("/booktype")
	public Msg getBookTypeList(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex) {

		PageHelper.startPage(pageIndex, 10);
		List<BookType> bookTypes = adminBookService.getBookTypeList();
		PageInfo pageInfo = new PageInfo(bookTypes);
		return Msg.success().add("bookType", pageInfo);

	}

	/**
	 * 返回管理端的书本类型集合
	 * 
	 * @return
	 */
	@GetMapping("/allbooktype")
	public Msg getAllBookTypeList() {

		return Msg.success().add("bookType", adminBookService.getBookTypeList());

	}

	/**
	 * 添加书本，需要校验
	 * 
	 * @param book
	 * @return
	 */
	@PostMapping("/book")
	public Msg addBook(@Valid @RequestBody BookWithBLOBs book, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			List<ObjectError> all = bindingResult.getAllErrors();
			for (ObjectError objectError : all) {
				errors.add(objectError.getDefaultMessage());
			}

			return Msg.fail().add("result", "请好好填信息，谢谢").add("data", errors);
		}

		int bookId = adminBookService.addBook(book);
		if (bookId > 0) {
			return Msg.success().add("result", bookId);
		}

		else {
			return Msg.success().fail();
		}
	}

	/**
	 * 根据主键返回书本
	 * 
	 * @param bookId
	 * @return
	 */
	@GetMapping("/book/{id}")
	public Msg getBook(@PathVariable("id") Integer bookId) {
		return Msg.success().add("book", adminBookService.getBook(bookId));
	}

	/**
	 * 根据主键删除书本
	 * 
	 * @param bookId
	 * @return
	 */
	@DeleteMapping("/book/{id}")
	public Msg deleteBook(@PathVariable("id") Integer bookId) {
		if (adminBookService.deleteBook(bookId) == 1) {
			return Msg.success().add("result", "删除成功");
		} else {
			return Msg.fail().add("result", "删除失败");
		}
	}

	/**
	 * 根据主键，书本更新
	 * 
	 * @param book
	 * @return
	 */
	@PutMapping("/book")
	public Msg updateBook(@Valid @RequestBody BookWithBLOBs book, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			List<ObjectError> all = bindingResult.getAllErrors();
			for (ObjectError objectError : all) {
				errors.add(objectError.getDefaultMessage());
			}

			return Msg.fail().add("result", "请好好填信息，谢谢").add("data", errors);
		}

		if (adminBookService.updateBook(book) == 1) {
			return Msg.success().add("result", "更新成功");
		} else {
			return Msg.fail().add("result", "更新失败");
		}
	}

	@PostMapping("/uploadimg")
	public Msg uploadFile(MultipartFile file, HttpServletRequest request) throws IOException {
		String uploadOne = uploadService.uploadOne(file, request);
		return Msg.success().add("url", uploadOne);
	}

	@PostMapping("/deleteimg")
	public Msg deleteFile(HttpServletRequest request) {
		return Msg.success().add("result", uploadService.delFileUrl(request));
	}

	@PostMapping("/deletelocateimg")
	public Msg deleteLocateFile(HttpServletRequest request) {
		return Msg.success().add("result", uploadService.delFile(request));
	}

	/**
	 * 根据主键删除书本书本类型
	 * 
	 * @param bookId
	 * @return
	 */
	@DeleteMapping("/booktype/{id}")
	public Msg deleteBookType(@PathVariable("id") Integer bookTypeId) {
		if (adminBookService.deleteBookType(bookTypeId) == 1) {
			return Msg.success().add("result", "删除成功");
		} else {
			return Msg.fail().add("result", "删除失败");
		}
	}

	/**
	 * 根据主键，更新书本类型
	 * 
	 * @param book
	 * @return
	 */
	@PutMapping("/booktype")
	public Msg updateBookType(@Valid @RequestBody BookType bookType, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			List<ObjectError> all = bindingResult.getAllErrors();
			for (ObjectError objectError : all) {
				errors.add(objectError.getDefaultMessage());
			}

			return Msg.fail().add("result", "请好好填信息，谢谢").add("data", errors);
		}

		if (adminBookService.updateBookType(bookType) == 1) {
			return Msg.success().add("result", "更新成功");
		} else {
			return Msg.fail().add("result", "更新失败");
		}
	}

	/**
	 * 添加书本类型
	 * 
	 * @param book
	 * @return
	 */
	@PostMapping("/booktype")
	public Msg addBookType(@Valid @RequestBody BookType bookType, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			List<ObjectError> all = bindingResult.getAllErrors();
			for (ObjectError objectError : all) {
				errors.add(objectError.getDefaultMessage());
			}

			return Msg.fail().add("result", "请好好填信息，谢谢").add("data", errors);
		}

		if (adminBookService.addBookType(bookType) == 1) {
			return Msg.success().add("result", "添加成功");
		} else {
			return Msg.fail().add("result", "添加失败");
		}
	}

	/**
	 * 根据书本id获取评价列表
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/evaluate")
	public Msg getEvaluateList(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex, Integer id) {
		PageHelper.startPage(pageIndex, 10);
		PageInfo pageInfo = new PageInfo(adminEvaluateService.getEvaluateList(id));
		return Msg.success().add("list", pageInfo);
	}

	/**
	 * 添加评论
	 * 
	 * @param book
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/evaluate")
	public Msg addEvaluate(@Valid @RequestBody EvaluateSubmit evaluateSubmit, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			List<ObjectError> all = bindingResult.getAllErrors();
			for (ObjectError objectError : all) {
				errors.add(objectError.getDefaultMessage());
			}

			return Msg.fail().add("result", "请好好填信息，谢谢").add("data", errors);
		}

		int Id = adminEvaluateService.addEvaluate(evaluateSubmit.getEvaluate(), evaluateSubmit.getIfHyperAdmin());
		if (Id > 0) {
			return Msg.success().add("result", Id);
		}

		else {
			return Msg.success().fail();
		}
	}

	/**
	 * 更新发布状态
	 * 
	 * @param id
	 * @param publicState
	 * @return
	 */
	@PutMapping("/bookpublic")
	public Msg updateState(Integer id, Boolean publicState) {
		if (adminBookService.updateBookPublic(id, publicState) == 1) {
			return Msg.success().add("result", "更新成功");
		} else {
			return Msg.fail().add("result", "更新失败");
		}
	}
	
	 
	@DeleteMapping("/evaluate/{id}")
	public Msg deleteEvaluate(@PathVariable("id") Integer id) {
		if (adminEvaluateService.deleteEvaluate(id)>0) {
			return Msg.success().add("result", "删除成功");
		} else {
			return Msg.fail().add("result", "删除失败");
		}
	}

}
