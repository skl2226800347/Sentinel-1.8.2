package com.alibaba.csp.sentinel.dashboard.biz.manager;

import com.alibaba.csp.sentinel.dashboard.biz.mapper.FlowRuleMapperExt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FlowRuleConfigManager {
    @Resource
    FlowRuleMapperExt flowRuleMapperExt;
}
