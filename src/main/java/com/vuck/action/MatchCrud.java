package com.vuck.action;

import com.vuck.annotations.Find;
import com.vuck.annotations.Page;
import com.vuck.entity.Match;
import com.vuck.entity.MatchDao;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author liyabin
 * @date 2017/12/27
 */
@RestController
public class MatchCrud
{
    @RequestMapping("/matchs")
    @Page
    public List<Match> getList(@Find List<Match> result)
    {
       return result;
    }
    @RequestMapping("/matchsdao")
    @Page
    public MatchDao getListDao(@Find(entityClass =Match.class) MatchDao result)
    {
        return result;
    }
}
