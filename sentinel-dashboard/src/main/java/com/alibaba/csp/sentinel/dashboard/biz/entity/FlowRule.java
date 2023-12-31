package com.alibaba.csp.sentinel.dashboard.biz.entity;

import java.io.Serializable;
import java.util.Date;

public class FlowRule implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.id
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.app
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private String app;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.limit_app
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private String limitApp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.resource
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private String resource;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.grade
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private Integer grade;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.count
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private Double count;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.strategy
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private Integer strategy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.ref_resource
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private String refResource;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.control_behavior
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private Integer controlBehavior;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.warm_up_period_sec
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private Integer warmUpPeriodSec;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.max_queueing_time_ms
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private Integer maxQueueingTimeMs;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.clusterMode
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private Byte clustermode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.cluster_config
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private String clusterConfig;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private String flowRule;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.oper_id
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private String operId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.creator
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.gmt_create
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flow_rule.gmt_modified
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private Date gmtModified;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.id
     *
     * @return the value of flow_rule.id
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.id
     *
     * @param id the value for flow_rule.id
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.app
     *
     * @return the value of flow_rule.app
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public String getApp() {
        return app;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.app
     *
     * @param app the value for flow_rule.app
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setApp(String app) {
        this.app = app == null ? null : app.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.limit_app
     *
     * @return the value of flow_rule.limit_app
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public String getLimitApp() {
        return limitApp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.limit_app
     *
     * @param limitApp the value for flow_rule.limit_app
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setLimitApp(String limitApp) {
        this.limitApp = limitApp == null ? null : limitApp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.resource
     *
     * @return the value of flow_rule.resource
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public String getResource() {
        return resource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.resource
     *
     * @param resource the value for flow_rule.resource
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setResource(String resource) {
        this.resource = resource == null ? null : resource.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.grade
     *
     * @return the value of flow_rule.grade
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.grade
     *
     * @param grade the value for flow_rule.grade
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.count
     *
     * @return the value of flow_rule.count
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public Double getCount() {
        return count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.count
     *
     * @param count the value for flow_rule.count
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setCount(Double count) {
        this.count = count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.strategy
     *
     * @return the value of flow_rule.strategy
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public Integer getStrategy() {
        return strategy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.strategy
     *
     * @param strategy the value for flow_rule.strategy
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setStrategy(Integer strategy) {
        this.strategy = strategy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.ref_resource
     *
     * @return the value of flow_rule.ref_resource
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public String getRefResource() {
        return refResource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.ref_resource
     *
     * @param refResource the value for flow_rule.ref_resource
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setRefResource(String refResource) {
        this.refResource = refResource == null ? null : refResource.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.control_behavior
     *
     * @return the value of flow_rule.control_behavior
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public Integer getControlBehavior() {
        return controlBehavior;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.control_behavior
     *
     * @param controlBehavior the value for flow_rule.control_behavior
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setControlBehavior(Integer controlBehavior) {
        this.controlBehavior = controlBehavior;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.warm_up_period_sec
     *
     * @return the value of flow_rule.warm_up_period_sec
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public Integer getWarmUpPeriodSec() {
        return warmUpPeriodSec;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.warm_up_period_sec
     *
     * @param warmUpPeriodSec the value for flow_rule.warm_up_period_sec
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setWarmUpPeriodSec(Integer warmUpPeriodSec) {
        this.warmUpPeriodSec = warmUpPeriodSec;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.max_queueing_time_ms
     *
     * @return the value of flow_rule.max_queueing_time_ms
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public Integer getMaxQueueingTimeMs() {
        return maxQueueingTimeMs;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.max_queueing_time_ms
     *
     * @param maxQueueingTimeMs the value for flow_rule.max_queueing_time_ms
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setMaxQueueingTimeMs(Integer maxQueueingTimeMs) {
        this.maxQueueingTimeMs = maxQueueingTimeMs;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.clusterMode
     *
     * @return the value of flow_rule.clusterMode
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public Byte getClustermode() {
        return clustermode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.clusterMode
     *
     * @param clustermode the value for flow_rule.clusterMode
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setClustermode(Byte clustermode) {
        this.clustermode = clustermode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.cluster_config
     *
     * @return the value of flow_rule.cluster_config
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public String getClusterConfig() {
        return clusterConfig;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.cluster_config
     *
     * @param clusterConfig the value for flow_rule.cluster_config
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setClusterConfig(String clusterConfig) {
        this.clusterConfig = clusterConfig == null ? null : clusterConfig.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.flow_rule
     *
     * @return the value of flow_rule.flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public String getFlowRule() {
        return flowRule;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.flow_rule
     *
     * @param flowRule the value for flow_rule.flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setFlowRule(String flowRule) {
        this.flowRule = flowRule == null ? null : flowRule.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.oper_id
     *
     * @return the value of flow_rule.oper_id
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public String getOperId() {
        return operId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.oper_id
     *
     * @param operId the value for flow_rule.oper_id
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.creator
     *
     * @return the value of flow_rule.creator
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.creator
     *
     * @param creator the value for flow_rule.creator
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.gmt_create
     *
     * @return the value of flow_rule.gmt_create
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.gmt_create
     *
     * @param gmtCreate the value for flow_rule.gmt_create
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flow_rule.gmt_modified
     *
     * @return the value of flow_rule.gmt_modified
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flow_rule.gmt_modified
     *
     * @param gmtModified the value for flow_rule.gmt_modified
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}