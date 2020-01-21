package com.ucar.datalink.biz.cron.http;

import com.ucar.datalink.biz.cron.QuartzManager;
import com.ucar.datalink.biz.service.JobService;
import com.ucar.datalink.biz.utils.DataLinkFactory;
import com.ucar.datalink.biz.utils.HttpUtils;
import com.ucar.datalink.domain.job.JobExecutionInfo;
import com.ucar.datalink.domain.job.JobExecutionState;
import com.ucar.datalink.util.ConfigReadUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;

/**
 * Created by yang.wang09 on 2018-07-31 14:31.
 */
public class HttpCronCheck implements Job {

    private static final Logger logger = LoggerFactory.getLogger(HttpCronCheck.class);

    private static final String REST_URL_PREFIX = ConfigReadUtil.getString("datax.rest.url.prefix");
    private static final String URL = REST_URL_PREFIX +"/jobService/state?EXECUTE_ID={0}&JOB_ID_SIGNAL={1}";

    private JobService jobService = DataLinkFactory.getObject(JobService.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String job_name = context.getTrigger().getJobKey().getName();
        logger.info("execute -> "+job_name);
        HttpQuartzJob job = (HttpQuartzJob)context.getJobDetail().getJobDataMap().get(job_name);
        long executionId = job.getExecuteId();
        JobExecutionInfo executionInfo = jobService.getJobExecutionById(executionId);

        //如果成功，自己删除自己
        if(JobExecutionState.SUCCEEDED.equalsIgnoreCase(executionInfo.getState())) {
            QuartzManager.getInstance().deleteCheckJob(job);
            return;
        }
        //如果不是失败状态也删除这个任务
        if( !JobExecutionState.FAILED.equalsIgnoreCase(executionInfo.getState()) ) {
            QuartzManager.getInstance().deleteCheckJob(job);
            return;
        }

//创建一个重试的 定时任务，然后再自己删除自己
QuartzManager.getInstance().deleteCheckJob(job);
        HttpQuartzJob retryJob = HttpCronUtil.cloneWithExecuteId(job, executionId);




//            System.out.println(this.getClass().getName()+"    "+job_name);
        }

        }