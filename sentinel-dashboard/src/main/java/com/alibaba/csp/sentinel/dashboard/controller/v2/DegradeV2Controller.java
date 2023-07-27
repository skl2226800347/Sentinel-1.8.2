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
import com.alibaba.csp.sentinel.dashboard.biz.helper.DegradeRuleHelper;
import com.alibaba.csp.sentinel.dashboard.controller.AbstractController;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.repository.rule.RuleRepository;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Controller regarding APIs of degrade rules. Refactored since 1.8.0.
 *
 * @author Carpenter Lee
 * @author Eric Zhao
 */
@RestController
@RequestMapping("/v2/degrade")
public class DegradeV2Controller extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(DegradeV2Controller.class);
    @Autowired
    private RuleRepository<DegradeRuleEntity, Long> repository;

    @Autowired
    @Qualifier("degradeRuleZookeeperProvider")
    private DynamicRuleProvider<List<DegradeRule>> degradeRuleZookeeperProvider;

    @Autowired
    @Qualifier("degradeRuleZookeeperPublisher")
    private DynamicRulePublisher<List<DegradeRule>> degradeRuleZookeeperPublisher;


    @GetMapping("/rules.json")
    @AuthAction(PrivilegeType.READ_RULE)
    public Result<List<DegradeRuleEntity>> apiQueryMachineRules(@RequestParam String app) {
        if (StringUtil.isEmpty(app)) {
            return Result.ofFail(-1, "app can't be null or empty");
        }
        try {
            //List<DegradeRuleEntity> rules = degradeRuleZookeeperProvider.getRules(app);
            List<DegradeRuleEntity> rules = degradeRuleQueryService.getDegradeRuleEntityListByApp(app);
            rules = repository.saveAll(rules);
            return Result.ofSuccess(rules);
        } catch (Throwable throwable) {
            logger.error("queryApps error:", throwable);
            return Result.ofThrowable(-1, throwable);
        }
    }

    @PostMapping("/rule")
    @AuthAction(PrivilegeType.WRITE_RULE)
    public Result<DegradeRuleEntity> apiAddRule(@RequestBody DegradeRuleEntity entity, HttpServletRequest request) {
        AuthService.AuthUser authUser = authService.getAuthUser(request);
        Result<DegradeRuleEntity> checkResult = DegradeRuleHelper.checkEntityInternal(entity,authUser);
        if (checkResult != null) {
            logger.info("[apiAddRule] msg:{}", checkResult.getMsg());
            return checkResult;
        }
        Date date = new Date();
        entity.setGmtCreate(date);
        entity.setGmtModified(date);
        try {
            //insert db
            degradeRuleConfigService.createDegradeRule(entity,authUser);
            entity = repository.save(entity);
            //update db
            degradeRuleConfigService.updateDegradeRule(entity,authUser);
            publishRules(entity.getApp());
        } catch (Throwable t) {
            logger.error("Failed to add new degrade rule, app={}, ip={}", entity.getApp(), entity.getIp(), t);
            return Result.ofThrowable(-1, t);
        }
        return Result.ofSuccess(entity);
    }

    @PutMapping("/rule/{id}")
    @AuthAction(PrivilegeType.WRITE_RULE)
    public Result<DegradeRuleEntity> apiUpdateRule(@PathVariable("id") Long id,
                                                     @RequestBody DegradeRuleEntity entity,HttpServletRequest request) {

        AuthService.AuthUser authUser = authService.getAuthUser(request);
        Result<DegradeRuleEntity> checkResult  =DegradeRuleHelper.checkApiUpdateRule(id,authUser);
        if (checkResult != null) {
            logger.info("[apiUpdateRule] msg:{}", checkResult.getMsg());
            return checkResult;
        }
        DegradeRuleEntity oldEntity = repository.findById(id);
        if (oldEntity == null) {
            return Result.ofFail(-1, "Degrade rule does not exist, id=" + id);
        }
        entity.setApp(oldEntity.getApp());
        entity.setIp(oldEntity.getIp());
        entity.setPort(oldEntity.getPort());
        entity.setId(oldEntity.getId());
        checkResult = DegradeRuleHelper.checkEntityInternal(entity,authUser);
        if (checkResult != null) {
            return checkResult;
        }

        entity.setGmtCreate(oldEntity.getGmtCreate());
        entity.setGmtModified(new Date());
        try {
            entity = repository.save(entity);
            //update db
            degradeRuleConfigService.updateDegradeRule(entity,authUser);
            publishRules(entity.getApp());
        } catch (Throwable t) {
            logger.error("Failed to save degrade rule, id={}, rule={}", id, entity, t);
            return Result.ofThrowable(-1, t);
        }
        return Result.ofSuccess(entity);
    }

    @DeleteMapping("/rule/{id}")
    @AuthAction(PrivilegeType.DELETE_RULE)
    public Result<Long> delete(@PathVariable("id") Long id,HttpServletRequest request) {
        AuthService.AuthUser authUser = authService.getAuthUser(request);
        Result<Long> checkResult =DegradeRuleHelper.checkDelete(id,authUser);
        if (checkResult != null) {
            logger.info("[delete] msg:{}", checkResult.getMsg());
            return checkResult;
        }
        DegradeRuleEntity oldEntity = repository.findById(id);
        if (oldEntity == null) {
            return Result.ofSuccess(null);
        }
        try {
            repository.delete(id);
            //delete from db
            degradeRuleConfigService.deleteDegradeRule(id);
            publishRules(oldEntity.getApp());
        } catch (Throwable throwable) {
            logger.error("Failed to delete degrade rule, id={}", id, throwable);
            return Result.ofThrowable(-1, throwable);
        }

        return Result.ofSuccess(id);
    }






    private void publishRules(String app) throws Exception {
        List<DegradeRuleEntity> rules = repository.findAllByApp(app);
        degradeRuleZookeeperPublisher.publish(app, DegradeRuleHelper.convertDegradeRuleListByCore(rules));
    }
}
