package com.vuck.m_dao;

import com.vuck.entity.Match;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Mybatis 实现方式
 * @author liyabin
 * @date 2017/12/29
 */
public interface MBDao
{
    @Select("select * from match_all t where t.match_Name =#{matchName}")
    @Results(value = {
        @Result(column = "match_Name", property = "matchName"),
        @Result(column = "match_Time", property = "matchTime"),
        @Result(column = "match_state", property = "state"),
        @Result(column = "home_Team", property = "homeTeam"),
        @Result(column = "match_result", property = "result"),
        @Result(column = "guest_Team", property = "guestTeam"),
        @Result(column = "match_centre", property = "centre")
    }
    )
    List<Match> getMatchList(String match_Name);
}