package com.yuyuedao.yydwechat.controller;

import com.yuyuedao.yydwechat.entity.WPNewsdetails;
import com.yuyuedao.yydwechat.entity.WPNewsdetailsExample;
import com.yuyuedao.yydwechat.mapper.generator.WPNewsdetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {
    @Resource
    WPNewsdetailsMapper wpNewsdetailsMapper;
    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("test1")
    public void test1() {
        WPNewsdetailsExample wpNewsdetailsExample = new WPNewsdetailsExample();
//        wpNewsdetailsExample.createCriteria().andAccountidEqualTo("1");
        List<WPNewsdetails> wpNewsdetails = wpNewsdetailsMapper.selectByExample(wpNewsdetailsExample);
        wpNewsdetailsExample.clear();
        wpNewsdetails.forEach(System.out::print);
        logger.info(wpNewsdetails.get(0).getContent());
    }
}
