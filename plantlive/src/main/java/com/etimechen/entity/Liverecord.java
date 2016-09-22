package com.etimechen.entity;

import java.util.Date;

public class Liverecord {

    private Integer id;

    private Integer voteyes;

    private Integer voteno;

    private Short voteresult;

    private Short isexecute;

    private Date recorddatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVoteyes() {
        return voteyes;
    }

    public void setVoteyes(Integer voteyes) {
        this.voteyes = voteyes;
    }

    public Integer getVoteno() {
        return voteno;
    }

    public void setVoteno(Integer voteno) {
        this.voteno = voteno;
    }

    public Short getVoteresult() {
        return voteresult;
    }

    public void setVoteresult(Short voteresult) {
        this.voteresult = voteresult;
    }

    public Short getIsexecute() {
        return isexecute;
    }

    public void setIsexecute(Short isexecute) {
        this.isexecute = isexecute;
    }

    public Date getRecorddatetime() {
        return recorddatetime;
    }

    public void setRecorddatetime(Date recorddatetime) {
        this.recorddatetime = recorddatetime;
    }
}