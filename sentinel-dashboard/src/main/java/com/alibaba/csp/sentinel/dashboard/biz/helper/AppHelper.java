package com.alibaba.csp.sentinel.dashboard.biz.helper;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.bean.SpringBeanUtil;
import com.alibaba.csp.sentinel.dashboard.biz.constants.CommonConstant;
import com.alibaba.csp.sentinel.dashboard.biz.entity.App;
import com.alibaba.csp.sentinel.dashboard.biz.vo.param.AppSaveParamVO;
import com.alibaba.csp.sentinel.dashboard.discovery.AppInfo;
import com.alibaba.csp.sentinel.dashboard.discovery.AppManagement;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.enums.ResultCodeEnum;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class AppHelper {

    public static final Map<String,AppInfo> convertAppInfoMap(List<AppInfo> appInfoList){
        Map<String,AppInfo> appInfoMap = Maps.newHashMap();
        if(CollectionUtils.isEmpty(appInfoList)){
            return appInfoMap;
        }
        appInfoList.stream().forEach(a->{
            appInfoMap.putIfAbsent(a.getApp(),a);
        });
        return appInfoMap;
    }

    public static final App convertInsertApp(AppInfo appInfo, AuthService.AuthUser authUser){
        App insertApp = new App();
        insertApp.setApp(appInfo.getApp());
        insertApp.setCreator(authUser.getLoginName());
        insertApp.setOperId(authUser.getLoginName());

        Date curDate = new Date();
        insertApp.setGmtCreate(curDate);
        insertApp.setGmtModified(curDate);

        //
        AppManagement appManagement =SpringBeanUtil.getBean(AppManagement.class);
        AppInfo memoryAppInfo =appManagement.getDetailApp(appInfo.getApp());
        if(memoryAppInfo != null){
            insertApp.setAppInfo(JSONObject.toJSONString(memoryAppInfo));
        }else{
            insertApp.setAppInfo(JSONObject.toJSONString(appInfo));
        }
        return insertApp;
    }

    public static final App convertUpdateApp(AppInfo appInfo, AuthService.AuthUser authUser){
        App updateApp = new App();
        updateApp.setId(appInfo.getId());
        updateApp.setOperId(authUser.getLoginName());
        Date curDate = new Date();
        updateApp.setGmtModified(curDate);

        AppManagement appManagement =SpringBeanUtil.getBean(AppManagement.class);
        AppInfo memoryAppInfo =appManagement.getDetailApp(appInfo.getApp());
        if(memoryAppInfo != null){
            updateApp.setAppInfo(JSONObject.toJSONString(memoryAppInfo));
        }else{
            updateApp.setAppInfo(JSONObject.toJSONString(appInfo));
        }

        return updateApp;
    }

    public static final Result checkCreateAppParam(AppInfo appInfo, AuthService.AuthUser authUser){
        if(appInfo == null){
            return Result.ofFail(ResultCodeEnum.DATA_ERROR);
        }
        if(StringUtil.isEmpty(appInfo.getApp())){
            return Result.ofFail(ResultCodeEnum.APP_IS_NULL);
        }
        if(authUser == null){
            Result.ofFail(ResultCodeEnum.NO_LOGIN);
        }
        return null;
    }

    public static final Result checkSaveAppParam(AppInfo appInfo, AuthService.AuthUser authUser){
        return checkUpdateAppParam(appInfo,authUser);
    }
    public static final Result checkUpdateAppParam(AppInfo appInfo, AuthService.AuthUser authUser){
        if(appInfo == null){
            return Result.ofFail(ResultCodeEnum.DATA_ERROR);
        }
        if(appInfo.getId() == null){
            return Result.ofFail(ResultCodeEnum.ID_IS_NULL);
        }
        if(StringUtil.isEmpty(appInfo.getApp())){
            return Result.ofFail(ResultCodeEnum.APP_IS_NULL);
        }
        if(authUser == null){
            Result.ofFail(ResultCodeEnum.NO_LOGIN);
        }
        return null;
    }
}
