package org.seckill.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * Created by Luohong on 2016/5/28.
 */
@Controller
@RequestMapping("/seckill")//url:/模块/资源/{id}/细分
public class SeckillController {

    private Log log = LogFactory.getLog(SeckillController.class);

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("list");
        List<Seckill> seckills = seckillService.getSeckillList();
        mv.addObject("list", seckills);
        return mv;
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable("seckillId") Long seckillId){
        ModelAndView mv = new ModelAndView();
        if (seckillId == null){
            mv.setViewName("redirect:/seckill/list");
        }
        Seckill seckill = seckillService.getById(seckillId.longValue());
        if (seckill == null){
            mv.setViewName("forward:/seckill/list");
        }
        mv.addObject("seckill", seckill);
        mv.setViewName("detail");
        return mv;
    }

    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST,
    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(exposer, true);
        }catch (Exception e){
            result  = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return  result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId, @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long killPhone){
        SeckillExecution seckillExecution;
        if (killPhone == null){
            return new SeckillResult<SeckillExecution>(false, SeckillStatEnum.NOT_LOGIN.getStateInfo());
        }
        try {
            seckillExecution = seckillService.executeSeckill(seckillId, killPhone, md5);
            return new SeckillResult<SeckillExecution>(seckillExecution, true);
        }catch (RepeatKillException e){
            seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(seckillExecution, true);
        }catch (SeckillCloseException e){
            seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckillExecution>(seckillExecution, true);
        }catch (Exception e){
            log.error(e.getMessage());
            seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(seckillExecution, true);
        }
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> now(){
        Date nowTime = new Date();
        return  new SeckillResult<Long>(nowTime.getTime(), true);
    }

}
