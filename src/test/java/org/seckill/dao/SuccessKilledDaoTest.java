package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by Luohong on 2016/5/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {

    @Resource
    SuccessKilledDao successKilledDao;
    @Test
    public void testInsertSucKilled() throws Exception {
        long id = 1000L;
        long phone = 15811112222L;
        int insertCount = successKilledDao.insertSucKilled(id, phone);
        System.out.println("insertCount: " + insertCount);
        assertEquals(1, insertCount);
    }

    @Test
    public void testQueryByIdWithSeckill() throws Exception {
        long id = 1000L;
        long phone = 15811112222L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println(successKilled);
        if (successKilled != null) {
            System.out.println(successKilled.getSeckill());
        }
    }
}