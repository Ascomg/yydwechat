package com.yuyuedao.yydwechat.service.serviceImpl;

import com.yuyuedao.yydwechat.mapper.SelectMapper;
import com.yuyuedao.yydwechat.service.SelectService;
import com.yuyuedao.yydwechat.util.SelectItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SelectServiceImpl implements SelectService {
    @Resource
    private SelectMapper selectMapper;

    @Override
    public List<SelectItem> getUrlList() {

        return selectMapper.getUrlList();
    }

    @Override
    public List<SelectItem> getKeyList() {
        return selectMapper.getKeyList();
    }

    @Override
    public List<SelectItem> getQuestion() {
        return selectMapper.getQuestion();
    }

    @Override
    public List<SelectItem> getPoster() {
        return selectMapper.getPoster();
    }

    @Override
    public List<SelectItem> getPosterActivity() {
        return selectMapper.getPosterActivity();
    }

    @Override
    public List<SelectItem> getDrawActivity() {
        return null;
    }


}
