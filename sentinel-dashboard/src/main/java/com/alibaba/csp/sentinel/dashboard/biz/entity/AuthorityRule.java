package com.alibaba.csp.sentinel.dashboard.biz.entity;

import java.io.Serializable;
import java.util.Date;

public class AuthorityRule implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority_rule.id
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority_rule.app
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private String app;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority_rule.resource
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private String resource;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority_rule.limit_app
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private String limitApp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority_rule.strategy
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private Integer strategy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority_rule.rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private String rule;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority_rule.authority_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private String authorityRule;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority_rule.oper_id
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private String operId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority_rule.creator
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority_rule.gmt_create
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority_rule.gmt_modified
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private Date gmtModified;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table authority_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority_rule.id
     *
     * @return the value of authority_rule.id
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority_rule.id
     *
     * @param id the value for authority_rule.id
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority_rule.app
     *
     * @return the value of authority_rule.app
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public String getApp() {
        return app;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority_rule.app
     *
     * @param app the value for authority_rule.app
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setApp(String app) {
        this.app = app == null ? null : app.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority_rule.resource
     *
     * @return the value of authority_rule.resource
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public String getResource() {
        return resource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority_rule.resource
     *
     * @param resource the value for authority_rule.resource
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setResource(String resource) {
        this.resource = resource == null ? null : resource.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority_rule.limit_app
     *
     * @return the value of authority_rule.limit_app
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public String getLimitApp() {
        return limitApp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority_rule.limit_app
     *
     * @param limitApp the value for authority_rule.limit_app
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setLimitApp(String limitApp) {
        this.limitApp = limitApp == null ? null : limitApp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority_rule.strategy
     *
     * @return the value of authority_rule.strategy
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public Integer getStrategy() {
        return strategy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority_rule.strategy
     *
     * @param strategy the value for authority_rule.strategy
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setStrategy(Integer strategy) {
        this.strategy = strategy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority_rule.rule
     *
     * @return the value of authority_rule.rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public String getRule() {
        return rule;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority_rule.rule
     *
     * @param rule the value for authority_rule.rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setRule(String rule) {
        this.rule = rule == null ? null : rule.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority_rule.authority_rule
     *
     * @return the value of authority_rule.authority_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public String getAuthorityRule() {
        return authorityRule;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority_rule.authority_rule
     *
     * @param authorityRule the value for authority_rule.authority_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setAuthorityRule(String authorityRule) {
        this.authorityRule = authorityRule == null ? null : authorityRule.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority_rule.oper_id
     *
     * @return the value of authority_rule.oper_id
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public String getOperId() {
        return operId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority_rule.oper_id
     *
     * @param operId the value for authority_rule.oper_id
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority_rule.creator
     *
     * @return the value of authority_rule.creator
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority_rule.creator
     *
     * @param creator the value for authority_rule.creator
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority_rule.gmt_create
     *
     * @return the value of authority_rule.gmt_create
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority_rule.gmt_create
     *
     * @param gmtCreate the value for authority_rule.gmt_create
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority_rule.gmt_modified
     *
     * @return the value of authority_rule.gmt_modified
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority_rule.gmt_modified
     *
     * @param gmtModified the value for authority_rule.gmt_modified
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}