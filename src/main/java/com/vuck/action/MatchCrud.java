package com.vuck.action;

import com.vuck.annotations.Find;
import com.vuck.entity.Match;
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
    public List<Match> getList(@Find List<Match> result)
    {
       return result;
    }
}
