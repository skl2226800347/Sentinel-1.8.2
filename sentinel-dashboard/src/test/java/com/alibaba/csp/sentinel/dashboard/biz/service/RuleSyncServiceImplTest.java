package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.biz.AbstractTestCase;
import org.junit.Test;

import javax.annotation.Resource;

public class RuleSyncServiceImplTest extends AbstractTestCase {
    @Resource
    RuleSyncService ruleSyncService;
    @Test
    public void updateConfigCenterRuleFromDbBatch(){
        ruleSyncService.updateConfigCenterRuleFromDbBatch();
    }
}
