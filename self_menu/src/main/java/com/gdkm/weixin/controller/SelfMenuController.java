package com.gdkm.weixin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gdkm.weixin.domian.SelfMenu;
import com.gdkm.weixin.service.SelfMenuService;

@Controller
@RequestMapping("/menu")
public class SelfMenuController {
	
	@Autowired
	private SelfMenuService menuService;
	
	
	// 显示一个菜单修改的页面
	@GetMapping//=@RequestMapping(Method=Get)
	public ModelAndView index() {
		// 使用JSP必须修改pom.xml文件，增加JSP解析器的依赖，默认不支持JSP。
		// JSP在加入依赖以后，需要把文件放在/src/main/resources/META-INF/resources目录下才有效！
		// /META-INF/resources是Java EE的规范，每个不同模块的JSP文件放入此目录，可以当做是普通WEB应用的WebContent文件夹。
		return new ModelAndView("/WEB-INF/views/menu/index.jsp");

	}

	@GetMapping(produces = "application/json") //表明对外提供JSON数据
	@ResponseBody //返回的对象，就是响应体
	public SelfMenu data() {
		return menuService.getMenu();

	}
	
	@PostMapping
	@ResponseBody
	// @RequestBody的作用：把整个请求体转换为Java对象
	public String save(@RequestBody SelfMenu selfMenu) {
		this.menuService.save(selfMenu);
		return "保存成功";
	}
}
