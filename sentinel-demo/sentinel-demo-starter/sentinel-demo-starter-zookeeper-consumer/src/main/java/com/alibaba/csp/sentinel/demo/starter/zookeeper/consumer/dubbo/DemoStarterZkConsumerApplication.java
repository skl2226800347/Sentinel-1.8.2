/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.demo.starter.zookeeper.consumer.dubbo;

import com.alibaba.csp.sentinel.adapter.dubbo.config.DubboAdapterGlobalConfig;
import com.alibaba.csp.sentinel.demo.cluster.dubbo.api.FooService;
import com.alibaba.csp.sentinel.demo.starter.zookeeper.consumer.dubbo.consumer.FooServiceConsumer;
import com.alibaba.csp.sentinel.init.InitExecutor;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.SentinelRpcException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Please add the following VM arguments:
 * <pre>
 * -Djava.net.preferIPv4Stack=true
 * -Dcsp.sentinel.dashboard.server=localhost:9090
 * -Dcsp.sentinel.api.port=8721
 * -Dproject.name=dubbo-consumer-demo
 * </pre>
 *
 * @author shikelei
 */
@SpringBootApplication(scanBasePackages = "com.alibaba.csp.sentinel")
public class DemoStarterZkConsumerApplication {
    private static final String INTERFACE_RES_KEY = FooService.class.getName();
    private static final String RES_KEY = INTERFACE_RES_KEY + ":sayHello(java.lang.String)";

    public static void main(String[] args) throws Exception{
        ConfigurableApplicationContext context = SpringApplication.run(DemoStarterZkConsumerApplication.class, args);
        InitExecutor.doInit();
        System.out.println("启动成功");
        call(context);

    }
    protected static void call(ConfigurableApplicationContext context)throws Exception{
        FooServiceConsumer service = context.getBean(FooServiceConsumer.class);
        for (int i = 0; i < 15; i++) {
            try {
                String message = service.sayHello("Eric");
                System.out.println("Success: " + message);
            } catch (SentinelRpcException ex) {
                System.out.println("Blocked");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // method flowcontrol
        Thread.sleep(1000);
        initFlowRule(20, true);
        for (int i = 0; i < 10; i++) {
            try {
                String message = service.sayHello("Eric");
                System.out.println("Success: " + message);
            } catch (SentinelRpcException ex) {
                System.out.println("Blocked");
                System.out.println("fallback:" + service.doAnother());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // fallback to result
        Thread.sleep(1000);
        registryCustomFallback();

        for (int i = 0; i < 10; i++) {
            try {
                String message = service.sayHello("Eric");
                System.out.println("Result: " + message);
            } catch (SentinelRpcException ex) {
                System.out.println("Blocked");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // fallback to exception
        Thread.sleep(1000);
        registryCustomFallbackForCustomException();

        for (int i = 0; i < 10; i++) {
            try {
                String message = service.sayHello("Eric");
                System.out.println("Result: " + message);
            } catch (SentinelRpcException ex) {
                System.out.println("Blocked");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        Thread.sleep(1000);
        registryCustomFallbackWhenFallbackError();
        for (int i = 0; i < 10; i++) {
            try {
                String message = service.sayHello("Eric");
                System.out.println("Result: " + message);
            } catch (SentinelRpcException ex) {
                System.out.println("Blocked");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void initFlowRule(int interfaceFlowLimit, boolean method) {
        FlowRule flowRule = new FlowRule(INTERFACE_RES_KEY)
                .setCount(interfaceFlowLimit)
                .setGrade(RuleConstant.FLOW_GRADE_QPS);
        List<FlowRule> list = new ArrayList<>();
        if (method) {
            FlowRule flowRule1 = new FlowRule(RES_KEY)
                    .setCount(5)
                    .setGrade(RuleConstant.FLOW_GRADE_QPS);
            list.add(flowRule1);
        }
        list.add(flowRule);
        FlowRuleManager.loadRules(list);
    }

    public static void registryCustomFallback() {
        DubboAdapterGlobalConfig.setConsumerFallback(
                (invoker, invocation, ex) -> AsyncRpcResult.newDefaultAsyncResult("fallback", invocation));

    }
    public static void registryCustomFallbackForCustomException() {
        DubboAdapterGlobalConfig.setConsumerFallback(
                (invoker, invocation, ex) -> AsyncRpcResult.newDefaultAsyncResult(new RuntimeException("fallback"), invocation));
    }

    public static void registryCustomFallbackWhenFallbackError() {
        DubboAdapterGlobalConfig.setConsumerFallback(
                (invoker, invocation, ex) -> {
                    throw new RuntimeException("fallback");
                });
    }

}
