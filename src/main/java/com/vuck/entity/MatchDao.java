package com.vuck.entity;

import com.vuck.annotations.ListField;

import javax.persistence.Column;
import java.util.Date;

public class MatchDao {
    @ListField(showName = "比赛名称",orderId = 1)
    private String matchName;
    @ListField(showName = "比赛时间",orderId = 6)
    private Date matchTime;
    @ListField(showName = "比赛状态",orderId = 5)
    private String state;
    @ListField(showName = "主队名称",orderId = 2)
    private String homeTeam;
    @ListField(showName = "比赛结果",orderId = 4)
    private String result;
    @ListField(showName = "客队每次",orderId = 3)
    private String guestTeam;

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public Date getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getGuestTeam() {
        return guestTeam;
    }

    public void setGuestTeam(String guestTeam) {
        this.guestTeam = guestTeam;
    }
}
