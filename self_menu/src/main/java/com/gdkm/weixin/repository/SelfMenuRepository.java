package com.gdkm.weixin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gdkm.weixin.domian.SelfMenu;

@Repository
public interface SelfMenuRepository extends JpaRepository<SelfMenu, String>{

}
