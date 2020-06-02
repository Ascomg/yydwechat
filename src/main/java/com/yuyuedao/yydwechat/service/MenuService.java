package com.yuyuedao.yydwechat.service;

import com.yuyuedao.yydwechat.entity.Fans;
import com.yuyuedao.yydwechat.entity.Menu;
import com.yuyuedao.yydwechat.entity.WPMenu;
import com.yuyuedao.yydwechat.entity.W_p_menu;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;


public interface MenuService {


    List<Menu> getMenu();

    String getShowMenu(String menuId);

    String getShowPage(String menuId,String type);

    Integer addMenu(List<W_p_menu> menuList);

    List<Fans> getCount(String accountId);

    List<W_p_menu> getWPMenu();

    Integer sameMenu() throws ParseException;




}
