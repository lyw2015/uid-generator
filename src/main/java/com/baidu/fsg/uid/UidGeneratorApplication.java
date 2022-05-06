package com.baidu.fsg.uid;

import com.alibaba.druid.pool.DruidDataSource;
import com.baidu.fsg.uid.core.UidGeneratorProperties;
import com.baidu.fsg.uid.core.impl.CachedUidGenerator;
import com.baidu.fsg.uid.core.impl.DefaultUidGenerator;
import com.baidu.fsg.uid.handler.CustomRejectedPutBufferHandler;
import com.baidu.fsg.uid.handler.CustomRejectedTakeBufferHandler;
import com.baidu.fsg.uid.worker.DisposableWorkerIdAssigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @ProjectName uid-generator
 * @Author Laiyw
 * @CreateTime 2022/5/6 9:18
 * @Description TODO
 */

@SpringBootApplication
public class UidGeneratorApplication {

    @Autowired
    private UidGeneratorProperties properties;

    public static void main(String[] args) {
        SpringApplication.run(UidGeneratorApplication.class, args);
    }

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean
    public DisposableWorkerIdAssigner disposableWorkerIdAssigner() {
        return new DisposableWorkerIdAssigner();
    }

    @Bean
    public DefaultUidGenerator defaultUidGenerator() {
        DefaultUidGenerator generator = new DefaultUidGenerator();
        generator.setWorkerIdAssigner(disposableWorkerIdAssigner());
        /* 对于并发数要求不高、期望长期使用的应用, 可增加timeBits位数, 减少seqBits位数.
         例如节点采取用完即弃的WorkerIdAssigner策略, 重启频率为12次/天, 那么配置成{"workerBits":23,"timeBits":31,"seqBits":9}时, 可支持28个节点以整体并发量14400 UID/s的速度持续运行68年.

         对于节点重启频率频繁、期望长期使用的应用, 可增加workerBits和timeBits位数, 减少seqBits位数.
         例如节点采取用完即弃的WorkerIdAssigner策略, 重启频率为24*12次/天, 那么配置成{"workerBits":27,"timeBits":30,"seqBits":6}时, 可支持37个节点以整体并发量2400 UID/s的速度持续运行34年.
         */
        generator.setTimeBits(properties.getTimeBits());
        generator.setWorkerBits(properties.getWorkerBits());
        generator.setSeqBits(properties.getSeqBits());
        generator.setEpochStr(properties.getEpochStr());
        return generator;
    }

    @Bean
    public CachedUidGenerator cachedUidGenerator() {
        CachedUidGenerator generator = new CachedUidGenerator();
        generator.setWorkerIdAssigner(disposableWorkerIdAssigner());
        /* 对于并发数要求不高、期望长期使用的应用, 可增加timeBits位数, 减少seqBits位数.
         例如节点采取用完即弃的WorkerIdAssigner策略, 重启频率为12次/天, 那么配置成{"workerBits":23,"timeBits":31,"seqBits":9}时, 可支持28个节点以整体并发量14400 UID/s的速度持续运行68年.

         对于节点重启频率频繁、期望长期使用的应用, 可增加workerBits和timeBits位数, 减少seqBits位数.
         例如节点采取用完即弃的WorkerIdAssigner策略, 重启频率为24*12次/天, 那么配置成{"workerBits":27,"timeBits":30,"seqBits":6}时, 可支持37个节点以整体并发量2400 UID/s的速度持续运行34年.
         */
        generator.setTimeBits(properties.getTimeBits());
        generator.setWorkerBits(properties.getWorkerBits());
        generator.setSeqBits(properties.getSeqBits());
        generator.setEpochStr(properties.getEpochStr());
        /*
          RingBuffer size扩容参数, 可提高UID生成的吞吐量.
          默认:3， 原bufferSize=8192, 扩容后bufferSize= 8192 << 3 = 65536
         */
        generator.setBoostPower(properties.getBoostPower());
        /*
          另外一种RingBuffer填充时机, 在Schedule线程中, 周期性检查填充
          默认:不配置此项, 即不实用Schedule线程. 如需使用, 请指定Schedule线程时间间隔, 单位:秒
         */
        generator.setScheduleInterval(properties.getScheduleInterval());
        // 默认无需指定, 将丢弃Put操作, 仅日志记录. 如有特殊需求, 请实现RejectedPutBufferHandler接口(支持Lambda表达式)
        generator.setRejectedPutBufferHandler(new CustomRejectedPutBufferHandler());
        //  默认无需指定, 将记录日志, 并抛出UidGenerateException异常. 如有特殊需求, 请实现RejectedTakeBufferHandler接口(支持Lambda表达式)
        generator.setRejectedTakeBufferHandler(new CustomRejectedTakeBufferHandler());
        return generator;
    }
}
