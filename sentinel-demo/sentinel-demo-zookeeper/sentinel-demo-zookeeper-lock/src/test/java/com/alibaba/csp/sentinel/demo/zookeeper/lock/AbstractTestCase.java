package com.alibaba.csp.sentinel.demo.zookeeper.lock;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoZookeeperLockApplication.class)
@SpringBootConfiguration
@Rollback(false)
public class AbstractTestCase {

}
