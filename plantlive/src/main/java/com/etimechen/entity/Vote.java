package com.etimechen.entity;

import java.util.Date;

public class Vote {

    private Integer id;
    
    private Boolean voteyesorno;
    
    private Long voteip;

    private Date votedate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getVoteyesorno() {
		return voteyesorno;
	}

	public void setVoteyesorno(Boolean voteyesorno) {
		this.voteyesorno = voteyesorno;
	}

	public Long getVoteip() {
        return voteip;
    }

    public void setVoteip(Long voteip) {
        this.voteip = voteip;
    }

    public Date getVotedate() {
        return votedate;
    }

    public void setVotedate(Date votedate) {
        this.votedate = votedate;
    }
}