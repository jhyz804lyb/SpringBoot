package com.vuck.action;

import com.vuck.annotations.*;
import com.vuck.common.Cost;
import com.vuck.entity.*;
import com.vuck.m_dao.MBDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexAction
{
    @Autowired
    MBDao dao;

    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        request.setAttribute("name","张三");
        return "index";
    }
    @RequestMapping("/index2")
    @Page
    public String getList(@Find List<Match> result)
    {
        return "index";
    }

    @RequestMapping("/index3")
    @Page
    public String getListDao(@Find(entityClass =Match.class) List<MatchDao> result)
    {
        return Cost.COMMON_PAGE;
    }

    @RequestMapping(value = "excelFile")
    @ExportExcel(titleList = {"比赛名称", "比赛时间", "状态", "主队", "客队", "结果"}
            , fieldList = {"matchName", "matchTime", "state", "homeTeam", "guestTeam",
            "result"}, fileName = "框架导出测试", cellWidth = {5, 5, 5, 5, 5, 5})
    public List<Match> exportFile(@Find List<Match> matchs)
    {
        return matchs;
    }


    @RequestMapping(value = "excelFile2")
    @ExportExcel(titleList = {"比赛名称", "比赛时间", "状态", "主队", "客队", "结果"}
            , fieldList = {"matchName", "matchTime", "state", "homeTeam", "guestTeam",
            "result"}, fileName = "框架导出测试", cellWidth = {5, 5, 5, 5, 5, 5})
    public List<Match> exportFile2(@Find(invokeClass=MBDao.class,invokeMethod = "getMatchList",searchKey = {"matchName"})List<Match> matchs )
    {
        return matchs;
    }
}
