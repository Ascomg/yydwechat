package com.yuyuedao.yydwechat.entity;

import java.util.List;

public class PosterA {
    private String sName;
    private Integer sid;
    private List<PosterQuestionDetails>  posterQuestionDetails;

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public List<PosterQuestionDetails> getPosterQuestionDetails() {
        return posterQuestionDetails;
    }

    public void setPosterQuestionDetails(List<PosterQuestionDetails> posterQuestionDetails) {
        this.posterQuestionDetails = posterQuestionDetails;
    }
}
