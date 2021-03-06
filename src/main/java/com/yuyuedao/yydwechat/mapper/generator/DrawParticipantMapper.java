package com.yuyuedao.yydwechat.mapper.generator;

import com.yuyuedao.yydwechat.entity.DrawParticipant;
import com.yuyuedao.yydwechat.entity.DrawParticipantExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DrawParticipantMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table draw_participant
     *
     * @mbg.generated
     */
    long countByExample(DrawParticipantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table draw_participant
     *
     * @mbg.generated
     */
    int deleteByExample(DrawParticipantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table draw_participant
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer sid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table draw_participant
     *
     * @mbg.generated
     */
    int insert(DrawParticipant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table draw_participant
     *
     * @mbg.generated
     */
    int insertSelective(DrawParticipant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table draw_participant
     *
     * @mbg.generated
     */
    List<DrawParticipant> selectByExample(DrawParticipantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table draw_participant
     *
     * @mbg.generated
     */
    DrawParticipant selectByPrimaryKey(Integer sid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table draw_participant
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") DrawParticipant record, @Param("example") DrawParticipantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table draw_participant
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") DrawParticipant record, @Param("example") DrawParticipantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table draw_participant
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DrawParticipant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table draw_participant
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DrawParticipant record);
}