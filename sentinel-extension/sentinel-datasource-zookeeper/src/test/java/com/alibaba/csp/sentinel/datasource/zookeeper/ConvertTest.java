package com.alibaba.csp.sentinel.datasource.zookeeper;

import org.junit.Test;

public class ConvertTest {
    @Test
    public void toObjectV2(){
        String json="{\"type\":\"flow\",\"data\":\"[{\\\"clusterConfig\\\":{\\\"acquireRefuseStrategy\\\":0,\\\"clientOfflineTime\\\":2000,\\\"fallbackToLocalWhenFail\\\":true,\\\"resourceTimeout\\\":2000,\\\"resourceTimeoutStrategy\\\":0,\\\"sampleCount\\\":10,\\\"strategy\\\":0,\\\"thresholdType\\\":0,\\\"windowIntervalMs\\\":1000},\\\"clusterMode\\\":false,\\\"controlBehavior\\\":0,\\\"count\\\":666.0,\\\"grade\\\":1,\\\"limitApp\\\":\\\"default\\\",\\\"maxQueueingTimeMs\\\":500,\\\"resource\\\":\\\"com.alibaba.csp.sentinel.demo.apache.zookeeper.dubbo.FooService:sayHello(java.lang.String)\\\",\\\"strategy\\\":0,\\\"warmUpPeriodSec\\\":10}]\"}";

    }
}
