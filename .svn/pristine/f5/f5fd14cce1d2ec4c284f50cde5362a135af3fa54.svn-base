package com.hy.demo.handler;

import com.hy.demo.service.ProfileService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 *  @ClassName TestJobHandler
 *  description:
 *  yao create 2023年06月13日
 *  version: 1.0
 */
@Component
public class TestJobHandler {
    private final static Logger log = LoggerFactory.getLogger(TestJobHandler.class);
    @Autowired
    private ProfileService profileService;
    @XxlJob("testJob1")
    public void testJob1() throws InterruptedException {
        XxlJobHelper.log("不带返回值：XXL-JOB, Hello World.");
        for (int i = 0; i < 5; i++) {
            XxlJobHelper.log("beat at:" + i);
            TimeUnit.SECONDS.sleep(1);
            log.info("test01-------{}",i);
            System.out.println("test01----------------"+i);
        }

    }

    /**
     * 2、简单任务示例（Bean模式）带成功或失败返回值
     */
    @XxlJob("testJob2")
    public ReturnT<String> testJob2() throws Exception {

        XxlJobHelper.log("带返回值：XXL-JOB, Hello World.");
        for (int i = 0; i < 5; i++) {
            XxlJobHelper.log("beat at:" + i);
            TimeUnit.SECONDS.sleep(1);
            log.info("test02-------{}",i);
            System.out.println("test02----------------"+i);
        }
        return ReturnT.SUCCESS;
    }
    @XxlJob("testJob3")
    public void testJob3(){
        int count = profileService.count();
        XxlJobHelper.log("文件数据库总个数:"+count);
        log.info("文件数据库总个数:{}",count);
    }
}
