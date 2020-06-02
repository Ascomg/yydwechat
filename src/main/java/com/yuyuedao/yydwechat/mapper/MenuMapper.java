package com.yuyuedao.yydwechat.mapper;

import com.yuyuedao.yydwechat.entity.Fans;
import com.yuyuedao.yydwechat.entity.Menu;
import com.yuyuedao.yydwechat.entity.W_p_menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {

    List<Menu> selectAll();

    List<Menu> selectFirst();

    String showMenu(String menuId);

    String showPage(@Param("pId") String pId,@Param("type") String type);

    Integer addMenu(List<W_p_menu> menu);

    List<Fans> getCount(@Param("accountId") String accountId);

    List<W_p_menu> getMenu(@Param("sid") Integer sid);


    List<W_p_menu> sameMenu();

    W_p_menu getMenuByKey(@Param("key") String key);

    int deleteMenu();

}
