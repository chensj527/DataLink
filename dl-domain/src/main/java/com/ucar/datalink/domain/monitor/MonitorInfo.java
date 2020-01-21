package com.ucar.datalink.domain.monitor;

import com.ucar.datalink.domain.Storable;
import com.ucar.datalink.domain.alarm.StrategyConfig;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by csf on 17/4/26.
 */
@Alias("monitor")
public class MonitorInfo implements Serializable, Storable {

    private Long id;

    private Long resourceId;

    private String resourceName;

    private Integer isEffective;

    private Integer threshold;

    private Long intervalTime;

    private String receivePeople;

    private Integer monitorType;

    private Integer monitorCat;

    private Date createTime;

    private Date modifyTime;

    private String monitorRange;

    /**
     * 是否发送电话报警
     */
    private Boolean isPhone = false;
    /**
     * 是否发送短信报警
     */
    private Boolean isSMS = false;
    /**
     * 是否发送钉钉报警
     */
    private Boolean isDingD = false;


    private String otherConfig = "";


    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(Integer monitorType) {
        this.monitorType = monitorType;
    }

    public Integer getMonitorCat() {
        return monitorCat;
    }

    public void setMonitorCat(Integer monitorCat) {
        this.monitorCat = monitorCat;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Long intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getReceivePeople() {
        return receivePeople;
    }

    public void setReceivePeople(String receivePeople) {
        this.receivePeople = receivePeople;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getMonitorRange() {
        return monitorRange;
    }

    public void setMonitorRange(String monitorRange) {
        this.monitorRange = monitorRange;
    }

    public Boolean isPhone() {
        return isPhone;
    }

    public void setPhone(Boolean phone) {
        isPhone = phone;
    }

    public Boolean isSMS() {
        return isSMS;
    }

    public void setSMS(Boolean SMS) {
        isSMS = SMS;
    }

    public Boolean isDingD() {
        return isDingD;
    }

    public void setDingD(Boolean dingD) {
        isDingD = dingD;
    }

    public String getOtherConfig() {
        return otherConfig;
    }

    public void setOtherConfig(String otherConfig) {
        this.otherConfig = otherConfig;
    }

    @Override
    public String toString() {
        return "MonitorInfo{" +
                "id=" + id +
                ", resourceId=" + resourceId +
                ", resourceName='" + resourceName + '\'' +
                ", isEffective=" + isEffective +
                ", createTime=" + createTime +
                '}';
    }

}