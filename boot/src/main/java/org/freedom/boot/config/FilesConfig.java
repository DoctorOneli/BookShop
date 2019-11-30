package org.freedom.boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * 静态资源配置
 * @author oneli
 *
 */
@Component
public class FilesConfig {

	/**
	 * 文件上传正式目录
	 */
	@Value("${upload.path.formal}")
	private String formalPath;
	 
	 
	public String getFormalPath() {
		return formalPath;
	}

	public void setFormalPath(String formalPath) {
		this.formalPath = formalPath;
	}

}