package com.yuyuedao.yydwechat.mapper;

import com.yuyuedao.yydwechat.entity.W_p_gzhf;
import com.yuyuedao.yydwechat.entity.W_p_key;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MsgRspMapper {

    W_p_gzhf getByMr(String type);

}
