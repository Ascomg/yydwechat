package com.yuyuedao.yydwechat.service.serviceImpl;

import com.yuyuedao.yydwechat.entity.*;
import com.yuyuedao.yydwechat.mapper.MenuMapper;
import com.yuyuedao.yydwechat.mapper.generator.WPMenuMapper;
import com.yuyuedao.yydwechat.service.MenuService;
import com.yuyuedao.yydwechat.util.*;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl extends PublicMethod implements MenuService {
    @Resource
    private MenuMapper menuMapper;

    @Resource
    private WPMenuMapper wpMenuMapper;


    @Override
    public List<Menu> getMenu() {
        List<Menu> tree =  menuMapper.selectFirst();//返回所有父级
        List<Menu> allTree =  menuMapper.selectAll();//返回所有数据

        for (Menu list : tree) {
            list.setMenuList(menuList(allTree,list.getMenuId()));
        }
        return tree;
    }

    @Override
    public String getShowMenu(String menuId) {
        return menuMapper.showMenu(menuId);
    }

    @Override
    public String getShowPage(String menuId, String type) {
        return menuMapper.showPage(menuId,type);
    }

    @Override
    public Integer addMenu(List<W_p_menu> menuList) {
        menuMapper.deleteMenu();
        return menuMapper.addMenu(menuList);
    }

    @Override
    public List<Fans>getCount(String accountId) {

        return menuMapper.getCount(getDataBySession(IUserConstants.accountid));
    }

    @Override
    public List<W_p_menu> getWPMenu() {
        List<W_p_menu> menuList=menuMapper.getMenu(null);
        for(W_p_menu f:menuList){
            List<W_p_menu> children=menuMapper.getMenu(f.getSid());
            List<W_p_menu> ctemp=new ArrayList<W_p_menu>();
            f.setSub_button(ctemp);
            if(children.size()>0){
                f.setSub_button(children);
            }
        }
        return menuList;
    }

    @Override
    public Integer sameMenu() throws ParseException {
            List<W_p_menu> menuList=menuMapper.sameMenu();
            MenuButton menu = new MenuButton();
            if(menuList.size()==0){
                return -1;
            }else{

                Button firstArr[] = new Button[menuList.size()];
                for (int a = 0; a < menuList.size(); a++) {
                    List<W_p_menu> childList=menuMapper.getMenu(menuList.get(a).getSid());
                    if (childList.size() == 0) {
                        if("view".equals(menuList.get(a).getType())){
                            ViewButton viewButton = new ViewButton();
                            viewButton.setName(menuList.get(a).getMenuname());
                            viewButton.setType(menuList.get(a).getType());
                            viewButton.setUrl(menuList.get(a).getUrl());
                            firstArr[a] = viewButton;
                        }else if("click".equals(menuList.get(a).getType())){
                            CommonButton cb = new CommonButton();
                            cb.setKey(menuList.get(a).getMenukey());
                            cb.setName(menuList.get(a).getMenuname());
                            cb.setType(menuList.get(a).getType());
                            firstArr[a] = cb;
                        }else if("miniprogram".equals(menuList.get(a).getType())){
                            System.out.println("miniprogram");
                            MiniButton mb=new MiniButton();
                            mb.setAppid("wx831d39893a82e547");
                            mb.setName(menuList.get(a).getMenuname());
                            mb.setUrl("https://www.baidu.com");
                            mb.setPagepath(menuList.get(a).getUrl());
                            mb.setType(menuList.get(a).getType());
                            firstArr[a] = mb;
                        }
                    } else {
                        ComplexButton complexButton = new ComplexButton();
                        complexButton.setName(menuList.get(a).getMenuname());

                        Button[] secondARR = new Button[childList.size()];
                        for (int i = 0; i < childList.size(); i++) {
                            W_p_menu children = childList.get(i);
                            String type = children.getType();
                            if ("view".equals(type)) {
                                ViewButton viewButton = new ViewButton();
                                viewButton.setName(children.getMenuname());
                                viewButton.setType(children.getType());
                                viewButton.setUrl(children.getUrl());
                                secondARR[i] = viewButton;

                            } else if ("click".equals(type)) {
                                CommonButton cb1 = new CommonButton();
                                cb1.setName(children.getMenuname());
                                cb1.setType(children.getType());
                                cb1.setKey(children.getMenukey());
                                secondARR[i] = cb1;
                            }
                        }
                        complexButton.setSub_button(secondARR);
                        firstArr[a] = complexButton;
                    }
                }
                menu.setButton(firstArr);
                JSONObject jsonMenu = JSONObject.fromObject(menu);
                Token token= CommonUtil.getToken(WxConstants.appid,WxConstants.appsecret);
                String accessToken=token.getAccessToken();
                if(accessToken==null){
                    return -2;
                }else{
                    String url = WxUtil.menu_create_url.replace("ACCESS_TOKEN",accessToken);
                    JSONObject jsonObject= new JSONObject();
                    try {
                        jsonObject = WxUtil.httpRequest(url, "POST", jsonMenu.toString());
                        if(jsonObject!=null){
                            if (0 == jsonObject.getInt("errcode")) {
                                return 1;
                                //rmap.put("status", true);
                                //rmap.put("message", "同步菜单成功！");
                            }else {
                                return -3;
                                //rmap.put("status", false);
                                //rmap.put("message", "同步菜单失败！错误码为："+jsonObject.getInt("errcode")+"错误信息为："+jsonObject.getString("errmsg")+"") ;
                            }
                        }else{
                            return -4;
                            //rmap.put("status", false);
                            //rmap.put("message", "同步菜单失败！同步自定义菜单URL地址不正确");
                        }
                    } catch (Exception e) {
                        return -5;
                        //rmap.put("status", false);
                        //rmap.put("message", "同步菜单失败！");
                    }finally{
                        //log.log(menu_id, "", "同步菜单");
                    }
                }

            }



    }


    private static List<Menu> menuList(List<Menu> list, String menu_pid) {
        List<Menu> newList = new ArrayList<>();
        for (Menu menu : list) {
            if (menu_pid.equals(menu.getMenuModule()) ) {
                List<Menu> tempList = menuList(list, menu.getMenuId());
                menu.setMenuList(tempList);
                newList.add(menu);
            }
        }
        return newList;
    }

}
