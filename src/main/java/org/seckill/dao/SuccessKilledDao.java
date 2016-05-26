package org.seckill.dao;

import org.seckill.entity.SuccessKilled;

/**
 * Created by Luohong on 2016/5/27.
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细,可过滤重复(数据库有联合主键)
      * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSucKilled(long seckillId, long userPhone);

    /**
     * 根据ID查询SuccessKilled并携带秒杀产品对象实体
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithSeckill(long seckillId, long userPhone);
}
