package org.freedom.boot.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Book implements Serializable {
	private Integer bookId;

	@NotBlank(message = "名称不能为空")
	private String bookName;

	@Min(0)
	private BigDecimal bookPrice;

	private Integer bookTypeId;

	@Min(0)
	private Integer bookCount;

	@NotBlank(message = "作者不能为空")
	private String bookAuthor;

	private Integer shopId;

	@NotBlank(message = "不能为空")
	private String bookCover;

	private BookType bookType;

	private Boolean ifPublic;

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Book(Integer bookId, String bookName, BigDecimal bookPrice, Integer bookTypeId, Integer bookCount,
			String bookAuthor, Integer shopId, String bookCover) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.bookPrice = bookPrice;
		this.bookTypeId = bookTypeId;
		this.bookCount = bookCount;
		this.bookAuthor = bookAuthor;
		this.shopId = shopId;
		this.bookCover = bookCover;
	}

	public Boolean getIfPublic() {
		return ifPublic;
	}

	public void setIfPublic(Boolean ifPublic) {
		this.ifPublic = ifPublic;
	}

	public BookType getBookType() {
		return bookType;
	}

	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName == null ? null : bookName.trim();
	}

	public BigDecimal getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(BigDecimal bookPrice) {
		this.bookPrice = bookPrice;
	}

	public Integer getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(Integer bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

	public Integer getBookCount() {
		return bookCount;
	}

	public void setBookCount(Integer bookCount) {
		this.bookCount = bookCount;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor == null ? null : bookAuthor.trim();
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getBookCover() {
		return bookCover;
	}

	public void setBookCover(String bookCover) {
		this.bookCover = bookCover == null ? null : bookCover.trim();
	}
}