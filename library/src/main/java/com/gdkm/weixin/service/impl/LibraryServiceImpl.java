package com.gdkm.weixin.service.impl;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gdkm.weixin.domain.Book;
import com.gdkm.weixin.domain.DebitList;
import com.gdkm.weixin.repository.BookRepository;
import com.gdkm.weixin.service.LibraryService;

@Service
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	BookRepository bookRepository;

	@Override
	public Page<Book> seach(String keyword, int pageNumber) {
		// 封装分页条件 Spring Data 提供
		Pageable pageable = PageRequest.of(pageNumber, 10);

		// 利用关键字进行搜索 得到Page对象
		Page<Book> page;
		if (StringUtils.isEmpty(keyword)) {
			// 没有关键字就查看全部数据
			// Disabled表示用户实体对象里面的disabled字段作为查询条件
			// Fales表示查询 disabled=fales
			// sql =select * from Book where disabled = false
			// 只有返回Page对象的方法 都得提供Pageable参数
			page = this.bookRepository.findByDisabledFalse(pageable);

		} else {
			// 有关键字
			// sql =select * from Book where disabled = false and name like '%'+keyword+'%'
			page = this.bookRepository.findByDisabledFalseAndNameContaining(keyword, pageable);

		}

		return page;
	}

	@Override
	public void add(String id, DebitList debitList) {
		// TODO Auto-generated method stub
		if (debitList.getBooks() == null) {
			debitList.setBooks(new LinkedList<>());
		}
		// 1-检查列表内是否又id相同的书
		boolean flag = false;
		for (Book b : debitList.getBooks()) {
			if (b.getId().equals(id)) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			this.bookRepository.findById(id).ifPresent((Book book) -> {
				debitList.getBooks().add(book);
			});
			// 2-根据id查图书
			// 3-加入列表
		}
	}

	@Override
	public void remove(String id, DebitList debitList) {
		// TODO Auto-generated method stub
		debitList.getBooks()
				// 把集合转换成（stream）流对象 后可以用流式编程
				.stream()
				// 找到需要删除的图书
				.filter((Book book) -> {
					return book.getId().equals(id);
				})
				.findFirst()
				.ifPresent((Book book) -> {
					debitList.getBooks().remove(book);
				});
		;
	}

}
