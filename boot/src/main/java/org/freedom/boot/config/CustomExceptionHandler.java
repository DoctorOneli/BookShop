package org.freedom.boot.config;

import java.io.IOException;

import org.freedom.boot.bean.Msg;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
/**
 * 全局异常处理配置，比如上传文件大小
 * @author oneli
 *
 */

@RestControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public Msg uploadException(MaxUploadSizeExceededException e)throws IOException{
		
		return Msg.fail().add("result", "上传文件大小超出1MB!");
		
	}
	
}
