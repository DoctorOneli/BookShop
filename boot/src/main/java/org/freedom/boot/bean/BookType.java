package org.freedom.boot.bean;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class BookType implements Serializable {
    private Integer bookTypeId;

    @NotBlank(message="不能为空")
    private String typeName;

    public Integer getBookTypeId() {
        return bookTypeId;
    }

    public void setBookTypeId(Integer bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }
}