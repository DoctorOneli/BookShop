package org.freedom.boot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilesConfig {

	/**
	 * 单文件上传最大内存
	 */
	@Value("${upload.multipart.maxFileSize}")
	private String maxFileSize;

	/**
	 * 多文件上传最大内存
	 */
	@Value("${upload.multipart.maxRequestSize}")
	private String maxRequestSize;

	/**
	 * 文件上传临时目录
	 */
	@Value("${upload.path.temporary}")
	private String temporaryPath;

	/**
	 * 文件上传正式目录
	 */
	@Value("${upload.path.formal}")
	private String formalPath;

	public String getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(String maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public String getMaxRequestSize() {
		return maxRequestSize;
	}

	public void setMaxRequestSize(String maxRequestSize) {
		this.maxRequestSize = maxRequestSize;
	}

	public String getTemporaryPath() {
		return temporaryPath;
	}

	public void setTemporaryPath(String temporaryPath) {
		this.temporaryPath = temporaryPath;
	}

	public String getFormalPath() {
		return formalPath;
	}

	public void setFormalPath(String formalPath) {
		this.formalPath = formalPath;
	}

}