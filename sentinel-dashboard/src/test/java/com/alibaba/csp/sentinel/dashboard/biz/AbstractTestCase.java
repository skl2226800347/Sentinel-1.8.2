package com.alibaba.csp.sentinel.dashboard.biz;

import com.alibaba.csp.sentinel.dashboard.DashboardApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DashboardApplication.class)
@SpringBootConfiguration
@Rollback(false)
public class AbstractTestCase {

}
