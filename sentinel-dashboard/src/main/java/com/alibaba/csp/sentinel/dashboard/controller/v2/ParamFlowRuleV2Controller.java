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
package com.alibaba.csp.sentinel.dashboard.controller.v2;

import com.alibaba.csp.sentinel.dashboard.auth.AuthAction;
import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.auth.AuthService.PrivilegeType;
import com.alibaba.csp.sentinel.dashboard.biz.helper.ParamFlowRuleHelper;
import com.alibaba.csp.sentinel.dashboard.client.CommandNotFoundException;
import com.alibaba.csp.sentinel.dashboard.controller.AbstractController;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.SentinelVersion;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.discovery.AppManagement;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.repository.rule.RuleRepository;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.util.VersionUtils;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * @author Eric Zhao
 * @since 0.2.1
 */
@RestController
@RequestMapping(value = "/v2/paramFlow")
public class ParamFlowRuleV2Controller extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(ParamFlowRuleV2Controller.class);

    private final SentinelVersion version020 = new SentinelVersion().setMinorVersion(2);

    @Autowired
    private AppManagement appManagement;
    @Autowired
    private RuleRepository<ParamFlowRuleEntity, Long> repository;

    @Autowired
    @Qualifier("paramFlowRuleZookeeperProvider")
    private DynamicRuleProvider<List<ParamFlowRule>> paramFlowRuleZookeeperProvider;

    @Autowired
    @Qualifier("paramFlowRuleZookeeperPublisher")
    private DynamicRulePublisher<List<ParamFlowRule>> paramFlowRuleZookeeperPublisher;

    private boolean checkIfSupported(String app, String ip, int port) {
        try {
            return Optional.ofNullable(appManagement.getDetailApp(app))
                .flatMap(e -> e.getMachine(ip, port))
                .flatMap(m -> VersionUtils.parseVersion(m.getVersion())
                    .map(v -> v.greaterOrEqual(version020)))
                .orElse(true);
            // If error occurred or cannot retrieve machine info, return true.
        } catch (Exception ex) {
            return true;
        }
    }

    @GetMapping("/rules")
    @AuthAction(PrivilegeType.READ_RULE)
    public Result<List<ParamFlowRuleEntity>> apiQueryAllRulesForMachine(@RequestParam String app) {
        if (StringUtil.isEmpty(app)) {
            return Result.ofFail(-1, "app cannot be null or empty");
        }
       /* if (StringUtil.isEmpty(ip)) {
            return Result.ofFail(-1, "ip cannot be null or empty");
        }
        if (port == null || port <= 0) {
            return Result.ofFail(-1, "Invalid parameter: port");
        }
        if (!checkIfSupported(app, ip, port)) {
            return unsupportedVersion();
        }*/
        try {
            //List<ParamFlowRuleEntity> rules =  paramFlowRuleZookeeperProvider.getRules(app);
            List<ParamFlowRuleEntity> rules =  paramFlowRuleQueryService.getParamFlowRuleListByApp(app);
            repository.saveAll(rules);
            return Result.ofSuccess(rules);
        } /*catch (ExecutionException ex) {
            logger.error("Error when querying parameter flow rules", ex.getCause());
            if (isNotSupported(ex.getCause())) {
                return unsupportedVersion();
            } else {
                return Result.ofThrowable(-1, ex.getCause());
            }
        }*/ catch (Throwable throwable) {
            logger.error("Error when querying parameter flow rules", throwable);
            return Result.ofFail(-1, throwable.getMessage());
        }
    }



    @PostMapping("/rule")
    @AuthAction(PrivilegeType.WRITE_RULE)
    public Result<ParamFlowRuleEntity> apiAddParamFlowRule(@RequestBody ParamFlowRuleEntity entity,HttpServletRequest request) {
        AuthService.AuthUser authUser=authService.getAuthUser(request);
        Result<ParamFlowRuleEntity> checkResult = ParamFlowRuleHelper.checkEntityInternal(entity,authUser);
        if (checkResult != null) {
            return checkResult;
        }
        /*if (!checkIfSupported(entity.getApp(), entity.getIp(), entity.getPort())) {
            return unsupportedVersion();
        }*/
        entity.setId(null);
        entity.getRule().setResource(entity.getResource().trim());
        Date date = new Date();
        entity.setGmtCreate(date);
        entity.setGmtModified(date);
        try {
            //insert db
            paramFlowRuleConfigService.createParamFlowRule(entity,authUser);
            entity = repository.save(entity);
            //update db
            paramFlowRuleConfigService.updateParamFlowRule(entity,authUser);
            publishRules(entity.getApp());
            return Result.ofSuccess(entity);
        } catch (ExecutionException ex) {
            logger.error("Error when adding new parameter flow rules", ex.getCause());
            if (isNotSupported(ex.getCause())) {
                return unsupportedVersion();
            } else {
                return Result.ofThrowable(-1, ex.getCause());
            }
        } catch (Throwable throwable) {
            logger.error("Error when adding new parameter flow rules", throwable);
            return Result.ofFail(-1, throwable.getMessage());
        }
    }



    @PutMapping("/rule/{id}")
    @AuthAction(PrivilegeType.WRITE_RULE)
    public Result<ParamFlowRuleEntity> apiUpdateParamFlowRule(@PathVariable("id") Long id,
                                                              @RequestBody ParamFlowRuleEntity entity,HttpServletRequest request) {
        if (id == null || id <= 0) {
            return Result.ofFail(-1, "Invalid id");
        }
        ParamFlowRuleEntity oldEntity = repository.findById(id);
        if (oldEntity == null) {
            return Result.ofFail(-1, "id " + id + " does not exist");
        }
        AuthService.AuthUser authUser=authService.getAuthUser(request);
        Result<ParamFlowRuleEntity> checkResult = ParamFlowRuleHelper.checkEntityInternal(entity,authUser);
        if (checkResult != null) {
            return checkResult;
        }
        /*if (!checkIfSupported(entity.getApp(), entity.getIp(), entity.getPort())) {
            return unsupportedVersion();
        }*/
        entity.setId(id);
        Date date = new Date();
        entity.setGmtCreate(oldEntity.getGmtCreate());
        entity.setGmtModified(date);
        try {

            entity = repository.save(entity);
            //update db
            paramFlowRuleConfigService.updateParamFlowRule(entity,authUser);
            publishRules(entity.getApp());
            return Result.ofSuccess(entity);
        } catch (ExecutionException ex) {
            logger.error("Error when updating parameter flow rules, id=" + id, ex.getCause());
            if (isNotSupported(ex.getCause())) {
                return unsupportedVersion();
            } else {
                return Result.ofThrowable(-1, ex.getCause());
            }
        } catch (Throwable throwable) {
            logger.error("Error when updating parameter flow rules, id=" + id, throwable);
            return Result.ofFail(-1, throwable.getMessage());
        }
    }

    @DeleteMapping("/rule/{id}")
    @AuthAction(PrivilegeType.DELETE_RULE)
    public Result<Long> apiDeleteRule(@PathVariable("id") Long id) {
        if (id == null) {
            return Result.ofFail(-1, "id cannot be null");
        }
        ParamFlowRuleEntity oldEntity = repository.findById(id);
        if (oldEntity == null) {
            return Result.ofSuccess(null);
        }

        try {
            repository.delete(id);
            //delete from db
            paramFlowRuleConfigService.deleteParamFlowRuleEntity(id);
            publishRules(oldEntity.getApp());
            return Result.ofSuccess(id);
        } catch (ExecutionException ex) {
            logger.error("Error when deleting parameter flow rules", ex.getCause());
            if (isNotSupported(ex.getCause())) {
                return unsupportedVersion();
            } else {
                return Result.ofThrowable(-1, ex.getCause());
            }
        } catch (Throwable throwable) {
            logger.error("Error when deleting parameter flow rules", throwable);
            return Result.ofFail(-1, throwable.getMessage());
        }
    }

    private boolean isNotSupported(Throwable ex) {
        return ex instanceof CommandNotFoundException;
    }

    private <R> Result<R> unsupportedVersion() {
        return Result.ofFail(4041,
            "Sentinel client not supported for parameter flow control (unsupported version or dependency absent)");
    }



    private void publishRules(String app) throws Throwable{
        List<ParamFlowRuleEntity> rules = repository.findAllByApp(app);
        paramFlowRuleZookeeperPublisher.publish(app,ParamFlowRuleHelper.convertParamFlowRuleByCore(rules));
    }

}
