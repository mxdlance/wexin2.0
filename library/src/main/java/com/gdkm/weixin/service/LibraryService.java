package com.gdkm.weixin.service;

import org.springframework.data.domain.Page;

import com.gdkm.weixin.domain.Book;
import com.gdkm.weixin.domain.DebitList;

public interface LibraryService {
	public Page<Book> seach(String keyword,int pageNumber);

	public void add(String id, DebitList debitList);

	public void remove(String id, DebitList debitList);
}
