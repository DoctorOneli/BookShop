package org.freedom.boot;

import java.util.Date;

import org.freedom.boot.bean.Msg;
import org.freedom.boot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestController {

	@RequestMapping("/test")
	public ModelAndView test(Model model)
	{
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}
	 
	@RequestMapping("/register")
	public ModelAndView Register(@ModelAttribute("form") User user) {  // user:视图层传给控制层的表单对象；model：控制层返回给视图层的对象
        // 在 model 中添加一个名为 "user" 的 user 对象
		System.out.println(user.getUsername());
		
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("success");
		return modelAndView;
}

	
	 
}
