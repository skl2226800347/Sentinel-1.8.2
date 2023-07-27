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
package com.alibaba.csp.sentinel.demo.starter.zookeeper.provider.dubbo;

import com.alibaba.csp.sentinel.init.InitExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Provider demo for Apache Dubbo 2.7.x or above. Please add the following VM arguments:
 * <pre>
 * -Djava.net.preferIPv4Stack=true
 * -Dcsp.sentinel.api.port=8720
 * -Dcsp.sentinel.dashboard.server=localhost:9090
 * -Dproject.name=dubbo-provider-demo
 * </pre>
 *
 * @author skl
 */
@SpringBootApplication(scanBasePackages = "com.alibaba.csp.sentinel")
public class DemoStarterZkProviderApplication {

    public static void main(String[] args) {
        System.out.println("ddd");
        System.setProperty("csp.sentinel.dashboard.server","http://localhost:9090");
        System.setProperty("project.name","sentinel-demo-starter-zookeeper-provider");


        SpringApplication.run(DemoStarterZkProviderApplication.class, args);
        InitExecutor.doInit();
        System.out.println("启动成功");
    }
}