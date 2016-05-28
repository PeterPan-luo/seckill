package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Luohong on 2016/5/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    @Autowired
    private SeckillService seckillService;
    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> allSeckills = seckillService.getSeckillList();
        for(Seckill seckill : allSeckills){
            System.out.println(seckill);
        }
    }

    @Test
    public void testGetById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        assertNotNull(seckill);
        System.out.println(seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        assertNotNull(exposer);
        System.out.println(exposer);
    }

    @Test
    public void testExecuteSeckill() throws Exception {
        long id = 1000;
        String md5 = "9b8082b22ded08718a4255e9f482a80c";
        long usrerphone = 11111;
        SeckillExecution seckillExecution = seckillService.executeSeckill(id, usrerphone, md5);
        assertNotNull(seckillExecution);
        System.out.println(seckillExecution);
    }
}