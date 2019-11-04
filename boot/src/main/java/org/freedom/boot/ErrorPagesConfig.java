package org.freedom.boot;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 自定义错误返回页面
 * @author oneli
 *
 */
@Component
public class ErrorPagesConfig implements ErrorPageRegistrar{
	  @Override
	    public void registerErrorPages(ErrorPageRegistry registry) {
	        ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/book/error.html");
	        registry.addErrorPages(error403Page);
	    }
 
}
