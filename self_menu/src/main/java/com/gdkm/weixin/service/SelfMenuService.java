package com.gdkm.weixin.service;

import com.gdkm.weixin.domian.SelfMenu;

public interface SelfMenuService {

	SelfMenu getMenu();

	void save(SelfMenu selfMenu);

}
