package com.gdkm.weixin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdkm.weixin.domain.Book;
import com.gdkm.weixin.service.LibraryService;

@Controller
@RequestMapping("/library")
public class IndexController {
	@Autowired
	private LibraryService libraryService;



	@RequestMapping

	public String index(
			// required 表示是否必须 默认是true 没有参数传入null
			@RequestParam(value = "keword", required = false) String keyword, //
			// Spring data 封装的分页是从0开始的
			@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber, //
			Model model) {
		Page<Book> page = this.libraryService.seach(keyword, pageNumber);
		model.addAttribute("page", page);

		return "/WEB-INF/views/library/index.jsp";

	}

}
