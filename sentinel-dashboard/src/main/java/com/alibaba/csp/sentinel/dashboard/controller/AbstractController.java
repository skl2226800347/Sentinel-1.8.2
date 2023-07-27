package com.alibaba.csp.sentinel.dashboard.controller;
import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

public abstract class AbstractController {
    @Autowired
    protected AuthService<HttpServletRequest> authService;
    @Autowired
    protected AuthorityRuleConfigService authorityRuleConfigService;
    @Autowired
    protected AuthorityRuleQueryService authorityRuleQueryService;

    @Autowired
    protected DegradeRuleConfigService degradeRuleConfigService;
    @Autowired
    protected DegradeRuleQueryService degradeRuleQueryService;

    @Autowired
    protected FlowRuleConfigService flowRuleConfigService;
    @Autowired
    protected FlowRuleQueryService flowRuleQueryService;

    @Autowired
    protected ParamFlowRuleConfigService paramFlowRuleConfigService;
    @Autowired
    protected ParamFlowRuleQueryService paramFlowRuleQueryService;

    @Autowired
    protected SystemRuleConfigService systemRuleConfigService;
    @Autowired
    protected SystemRuleQueryService systemRuleQueryService;


    @Autowired
    protected AppQueryService appQueryService;
    @Autowired
    protected AppConfigService appConfigService;


}
