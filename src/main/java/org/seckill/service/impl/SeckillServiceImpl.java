package org.seckill.service.impl;

import com.google.common.base.Strings;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Luohong on 2016/5/28.
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    private Log log = LogFactory.getLog(SeckillServiceImpl.class);

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;
    //md5盐值字符串,用于混淆md5
    private final String slat = "asdfasd2341242@#$@#$%$%%#@$%#@%^%^";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,100);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = getById(seckillId);
        if (seckill == null)
            return new Exposer(false, seckillId);
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date now = new Date();
        if (now.getTime() > endTime.getTime() || now.getTime() < startTime.getTime()){
            return new Exposer(false, seckillId, now.getTime(), startTime.getTime(), endTime.getTime());
        }
        //转化特定字符串的过程,不可逆
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Transactional
    @Override
    /**
     * 使用注解控制事务的优点:
     * 1.开发团队达成一致约定,明确标注事务方法的编程风格.
     * 2.保证事务方法的执行时间尽可能短,不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部.
     * 3.不是所有的方法都需要事务.如一些查询的service.只有一条修改操作的service.
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, SeckillCloseException,
    RepeatKillException{
        if (Strings.isNullOrEmpty(md5) || !md5.equals(getMD5(seckillId))){
            throw new SeckillException(SeckillStatEnum.DATA_REWRITE.getStateInfo());
        }
        //执行秒杀逻辑:1.减库存.2.记录购买行为
        Date nowTime = new Date();
        try {
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0){
                throw new SeckillCloseException(SeckillStatEnum.END.getStateInfo());
            }else {
                //记录购买行为
                int insertCount = successKilledDao.insertSucKilled(seckillId, userPhone);
                if (insertCount <= 0){
                    //重复秒杀
                    throw new RepeatKillException(SeckillStatEnum.REPEAT_KILL.getStateInfo());
                }else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        }catch (SeckillCloseException e){
            throw e;
        }catch (RepeatKillException e){
            throw e;
        }catch (Exception e){
            log.error(e.getMessage());
            //所有的编译期异常转化为运行期异常,spring的声明式事务做rollback
            throw new SeckillException("seckill inner error: " + e.getMessage());
        }
    }

    private  String getMD5(long seckillId){
        String base = String.valueOf(seckillId) + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        log.info("_________________md5: " + md5);
        return  md5;
    }
}
