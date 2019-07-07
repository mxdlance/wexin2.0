package com.gdkm.weixin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import com.gdkm.weixin.domain.DebitList;
import com.gdkm.weixin.service.LibraryService;

@Controller
@RequestMapping("/library/debit")
//表示哪些属性放进Session里面
@SessionAttributes({"debitList"})
public class DebitController {
	@Autowired
	private LibraryService libraryService;

	
	@RequestMapping
	public String add(@RequestParam("id") String id,
			//用WebRequest可以实现跨平台性 比HttpServletRequest好 是Spring mvc封装的 使用方法类似 
			WebRequest request) {
		//获取Session里面debitList的对象 
		
		DebitList debitList=(DebitList)request.getAttribute("debitList", WebRequest.SCOPE_SESSION);
		
		if(debitList==null) {
			
			debitList=new DebitList();
			request.setAttribute("debitList", debitList, WebRequest.SCOPE_SESSION);
		}
		libraryService.add(id,debitList);
		return "redirect:/library/debit/list";

	}
	@RequestMapping("list")
	public String list() {
		return "/WEB-INF/views/library/debit/list.jsp";
	}
	
	@RequestMapping("remove/{id}")
	public String remove(@PathVariable ("id")String id,
			//表示获取session中名为debitList的对象
			@SessionAttribute(name="debitList") DebitList debitList) {
		
		this.libraryService.remove(id,debitList);
		return "redirect:/library/debit/list";
	}
}
