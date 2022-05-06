package com.baidu.fsg.uid.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName uid-generator
 * @Author Laiyw
 * @CreateTime 2022/5/6 11:56
 * @Description TODO
 */
@ConfigurationProperties(prefix = "uid-generator")
@Configuration
public class UidGeneratorProperties {

    private String token;
    private int timeBits;
    private int workerBits;
    private int seqBits;
    private String epochStr;
    private int boostPower;
    private int paddingFactor;
    private long scheduleInterval;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTimeBits() {
        return timeBits;
    }

    public void setTimeBits(int timeBits) {
        this.timeBits = timeBits;
    }

    public int getWorkerBits() {
        return workerBits;
    }

    public void setWorkerBits(int workerBits) {
        this.workerBits = workerBits;
    }

    public int getSeqBits() {
        return seqBits;
    }

    public void setSeqBits(int seqBits) {
        this.seqBits = seqBits;
    }

    public String getEpochStr() {
        return epochStr;
    }

    public void setEpochStr(String epochStr) {
        this.epochStr = epochStr;
    }

    public int getBoostPower() {
        return boostPower;
    }

    public void setBoostPower(int boostPower) {
        this.boostPower = boostPower;
    }

    public int getPaddingFactor() {
        return paddingFactor;
    }

    public void setPaddingFactor(int paddingFactor) {
        this.paddingFactor = paddingFactor;
    }

    public long getScheduleInterval() {
        return scheduleInterval;
    }

    public void setScheduleInterval(long scheduleInterval) {
        this.scheduleInterval = scheduleInterval;
    }
}
