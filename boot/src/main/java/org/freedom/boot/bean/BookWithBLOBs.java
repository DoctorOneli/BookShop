package org.freedom.boot.bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BookWithBLOBs extends Book {
	@NotBlank(message="详情不能为空")
    private String bookDetail;

    private String bookImg;
    @NotBlank(message="不能为空")
    private String detailImg;

    public String getBookDetail() {
        return bookDetail;
    }

    public void setBookDetail(String bookDetail) {
        this.bookDetail = bookDetail == null ? null : bookDetail.trim();
    }

    public String getBookImg() {
        return bookImg;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg == null ? null : bookImg.trim();
    }

    public String getDetailImg() {
        return detailImg;
    }

    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg == null ? null : detailImg.trim();
    }
}