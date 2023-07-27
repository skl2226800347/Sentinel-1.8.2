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
import com.alibaba.csp.sentinel.dashboard.biz.helper.AppHelper;
import com.alibaba.csp.sentinel.dashboard.biz.vo.result.AppSaveResultVO;
import com.alibaba.csp.sentinel.dashboard.controller.AbstractController;
import com.alibaba.csp.sentinel.dashboard.discovery.AppInfo;
import com.alibaba.csp.sentinel.dashboard.discovery.AppManagement;
import com.alibaba.csp.sentinel.dashboard.discovery.MachineInfo;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.domain.vo.MachineInfoVo;
import com.alibaba.csp.sentinel.dashboard.enums.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Eric Zhao
 * @since 0.2.1
 */
@RestController
@RequestMapping(value = "/v2/app")
public class AppV2Controller extends AbstractController {
    private final Logger logger = LoggerFactory.getLogger(AppV2Controller.class);
    @Autowired
    private AppManagement appManagement;

    @GetMapping("/briefinfos.json")
    @AuthAction(AuthService.PrivilegeType.READ_RULE)
    public Result<List<AppInfo>> queryAppInfos(HttpServletRequest request) {
        //List<AppInfo> list = new ArrayList<>(appManagement.getBriefApps());
        AuthService.AuthUser authUser = authService.getAuthUser(request);
        List<AppInfo> list =appQueryService.getAppInfoList(authUser);
        Collections.sort(list, Comparator.comparing(AppInfo::getApp));
        return Result.ofSuccess(list);
    }

    @GetMapping("/getAllAppList")
    @AuthAction(AuthService.PrivilegeType.READ_RULE)
    public Result<List<AppInfo>> getAllAppList(HttpServletRequest request) {
        //List<AppInfo> list = new ArrayList<>(appManagement.getBriefApps());
        AuthService.AuthUser authUser = authService.getAuthUser(request);
        List<AppInfo> list =appQueryService.getAppInfoList(authUser);
        Collections.sort(list, Comparator.comparing(AppInfo::getApp));
        return Result.ofSuccess(list);
    }


    @RequestMapping("/add.json")
    @AuthAction(AuthService.PrivilegeType.WRITE_RULE)
    public Result<AppSaveResultVO> add(AppInfo appInfo, HttpServletRequest request) {
        AuthService.AuthUser authUser = authService.getAuthUser(request);
        Result<AppSaveResultVO> result = AppHelper.checkCreateAppParam(appInfo,authUser);
        if(result != null){
            return result;
        }
        result =appConfigService.createApp(appInfo,authUser);
        if(!result.isSuccess()){
            return result;
        }
        appConfigService.updateApp(appInfo,authUser);
        return result;
    }

    @RequestMapping("/update.json")
    @AuthAction(AuthService.PrivilegeType.WRITE_RULE)
    public Result<AppSaveResultVO> update(AppInfo appInfo, HttpServletRequest request) {
        AuthService.AuthUser authUser = authService.getAuthUser(request);
        Result<AppSaveResultVO> result = AppHelper.checkUpdateAppParam(appInfo,authUser);
        if(result != null){
            return result;
        }
        appConfigService.updateApp(appInfo,authUser);
        AppSaveResultVO appUpdateResultVO = new AppSaveResultVO();
        return Result.ofSuccess(appUpdateResultVO);
    }

    @RequestMapping("/delete.json")
    @AuthAction(AuthService.PrivilegeType.WRITE_RULE)
    public Result<AppSaveResultVO> delete(Long id, HttpServletRequest request) {
        AuthService.AuthUser authUser = authService.getAuthUser(request);
        if(authUser == null){
            return Result.ofFail(ResultCodeEnum.NO_LOGIN);
        }
        if(id == null){
            return Result.ofFail(ResultCodeEnum.ID_IS_NULL);
        }
        appConfigService.deleteApp(id);
        AppSaveResultVO appUpdateResultVO = new AppSaveResultVO();
        return Result.ofSuccess(appUpdateResultVO);
    }

    @GetMapping(value = "/machines.json")
    @AuthAction(AuthService.PrivilegeType.READ_RULE)
    public Result<List<MachineInfoVo>> getMachinesByApp(String app) {
        AppInfo appInfo = appManagement.getDetailApp(app);
        if (appInfo == null) {
            appInfo = appQueryService.getAppInfoByApp(app);
            if(appInfo == null) {
                return Result.ofSuccess(null);
            }
        }
        List<MachineInfo> list = new ArrayList<>(appInfo.getMachines());
        Collections.sort(list, Comparator.comparing(MachineInfo::getApp).thenComparing(MachineInfo::getIp).thenComparingInt(MachineInfo::getPort));
        return Result.ofSuccess(MachineInfoVo.fromMachineInfoList(list));
    }

    @RequestMapping(value = "/machine/remove.json")
    public Result<String> removeMachineById(
            @PathVariable("app") String app,
            @RequestParam(name = "ip") String ip,
            @RequestParam(name = "port") int port,HttpServletRequest request) {
        AuthService.AuthUser authUser=authService.getAuthUser(request);
        if(authUser == null){
            return Result.ofFail(ResultCodeEnum.NO_LOGIN);
        }
        AppInfo appInfo = appManagement.getDetailApp(app);
        if(appInfo != null){
            if (appManagement.removeMachine(app, ip, port)) {
                appInfo = appManagement.getDetailApp(app);
                Result<AppSaveResultVO> saveResult = appConfigService.saveApp(appInfo,authUser);
                if(saveResult.isSuccess()){
                    return Result.ofSuccessMsg("success");
                }else{
                    return Result.ofFail(saveResult.getCode(),saveResult.getMsg());
                }
            } else {
                return Result.ofFail(1, "remove failed");
            }
        }else{
            appInfo =appQueryService.getAppInfoByApp(app);
            if (appInfo == null) {
                return Result.ofSuccess(null);
            }
            appInfo.removeMachine(ip,port);
            Result<AppSaveResultVO> saveResult = appConfigService.saveApp(appInfo,authUser);
            if(saveResult.isSuccess()){
                return Result.ofSuccessMsg("success");
            }else{
                return Result.ofFail(saveResult.getCode(),saveResult.getMsg());
            }
        }
    }
}
