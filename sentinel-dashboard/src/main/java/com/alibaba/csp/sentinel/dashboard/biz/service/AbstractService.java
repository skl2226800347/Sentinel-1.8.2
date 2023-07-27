package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.biz.manager.*;
import com.alibaba.csp.sentinel.dashboard.biz.mapper.*;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import java.util.List;

public class AbstractService {

    public static final int DEFAULT_PAGE_SIZE=2;

    @Autowired
    @Qualifier("authorityRuleZookeeperPublisher")
    protected DynamicRulePublisher<List<AuthorityRule>> authorityRuleZookeeperPublisher;

    @Resource
    protected AuthorityRuleMapperExt authorityRuleMapperExt;
    @Resource
    protected AuthorityRuleQueryManager authorityRuleQueryManager;


    @Resource
    protected DegradeRuleQueryManager degradeRuleQueryManager;
    @Resource
    protected DegradeRuleMapperExt degradeRuleMapperExt;
    @Autowired
    @Qualifier("degradeRuleZookeeperPublisher")
    protected DynamicRulePublisher<List<DegradeRule>> degradeRuleZookeeperPublisher;

    @Resource
    protected FlowRuleQueryManager flowRuleQueryManager;
    @Resource
    protected FlowRuleMapperExt flowRuleMapperExt;
    @Autowired
    @Qualifier("flowRuleZookeeperPublisher")
    protected DynamicRulePublisher<List<FlowRule>> flowRuleZookeeperPublisher;


    @Resource
    protected ParamFlowRuleMapperExt paramFlowRuleMapperExt;
    @Resource
    protected ParamFlowRuleQueryManager paramFlowRuleQueryManager;
    @Autowired
    @Qualifier("paramFlowRuleZookeeperPublisher")
    protected DynamicRulePublisher<List<ParamFlowRule>> paramFlowRuleZookeeperPublisher;



    @Resource
    protected SystemRuleQueryManager systemRuleQueryManager;
    @Resource
    protected SystemRuleMapperExt systemRuleMapperExt;
    @Autowired
    @Qualifier("systemRuleZookeeperPublisher")
    protected DynamicRulePublisher<List<SystemRule>> systemRuleZookeeperPublisher;

    @Resource
    protected AppMapperExt appMapperExt;
    @Resource
    protected AppQueryManager appQueryManager;
    @Resource
    protected UserManager userManager;


}
