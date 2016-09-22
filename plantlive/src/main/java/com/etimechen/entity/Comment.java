package com.etimechen.entity;

import java.util.Date;

public class Comment {

    private Integer id;

    private Integer commentip;

    private Date commentdatetime;

    private String commentcontent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommentip() {
        return commentip;
    }

    public void setCommentip(Integer commentip) {
        this.commentip = commentip;
    }

    public Date getCommentdatetime() {
        return commentdatetime;
    }

    public void setCommentdatetime(Date commentdatetime) {
        this.commentdatetime = commentdatetime;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }
}