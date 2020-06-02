package com.yuyuedao.yydwechat.mapper.generator;

import com.yuyuedao.yydwechat.entity.WPFans;
import com.yuyuedao.yydwechat.entity.WPFansExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WPFansMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table w_p_fans
     *
     * @mbg.generated
     */
    long countByExample(WPFansExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table w_p_fans
     *
     * @mbg.generated
     */
    int deleteByExample(WPFansExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table w_p_fans
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer sid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table w_p_fans
     *
     * @mbg.generated
     */
    int insert(WPFans record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table w_p_fans
     *
     * @mbg.generated
     */
    int insertSelective(WPFans record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table w_p_fans
     *
     * @mbg.generated
     */
    List<WPFans> selectByExample(WPFansExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table w_p_fans
     *
     * @mbg.generated
     */
    WPFans selectByPrimaryKey(Integer sid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table w_p_fans
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") WPFans record, @Param("example") WPFansExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table w_p_fans
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") WPFans record, @Param("example") WPFansExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table w_p_fans
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(WPFans record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table w_p_fans
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(WPFans record);
}