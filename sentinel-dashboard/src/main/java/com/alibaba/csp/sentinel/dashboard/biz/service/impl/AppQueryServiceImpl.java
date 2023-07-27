package com.alibaba.csp.sentinel.dashboard.biz.service.impl;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.base.Pagination;
import com.alibaba.csp.sentinel.dashboard.biz.bean.SpringBeanUtil;
import com.alibaba.csp.sentinel.dashboard.biz.constants.CommonConstant;
import com.alibaba.csp.sentinel.dashboard.biz.domain.query.AppPaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.App;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.biz.helper.AppHelper;
import com.alibaba.csp.sentinel.dashboard.biz.service.AbstractService;
import com.alibaba.csp.sentinel.dashboard.biz.service.AppConfigService;
import com.alibaba.csp.sentinel.dashboard.biz.service.AppQueryService;
import com.alibaba.csp.sentinel.dashboard.discovery.AppInfo;
import com.alibaba.csp.sentinel.dashboard.discovery.AppManagement;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AppQueryServiceImpl extends AbstractService implements AppQueryService {
    private static final Logger LOG = LoggerFactory.getLogger(AppQueryServiceImpl.class);
    @Autowired
    AppConfigService appConfigService;
    @Override
    public Pagination<App> queryAppListWithPaging(AppPaginationQuery appPaginationQuery) {
        if(appPaginationQuery == null){
            throw new DashboardBizException("入参为null");
        }
        Pagination<App> pagination =appQueryManager.queryAppListWithPaging(appPaginationQuery);
        return pagination;
    }

    @Override
    public List<AppInfo> getAppInfoList(AuthService.AuthUser authUser) {
        //查询数据库
        List<App> appList =appQueryManager.getAllAppList();
        if(CollectionUtils.isEmpty(appList)){
            appList = Lists.newArrayList();
        }
        List<AppInfo> dbAppInfoList = Lists.newArrayList();
        appList.stream().forEach(app->{
            AppInfo appInfo =JSONObject.parseObject(app.getAppInfo(),AppInfo.class);
            if(appInfo.getId() == null){
                appInfo.setId(app.getId());
                appConfigService.updateApp(appInfo,authUser);
            }
            dbAppInfoList.add(appInfo);
        });
        Map<String,AppInfo> dbAppInfoMap = AppHelper.convertAppInfoMap(dbAppInfoList);
        //从内存中获取
        AppManagement appManagement=SpringBeanUtil.getBean(AppManagement.class);
        Set<AppInfo> memoryAppInfoSet = appManagement.getBriefApps();
        if(CollectionUtils.isEmpty(memoryAppInfoSet)){
            memoryAppInfoSet = Sets.newHashSet();
        }
        //合成
        List<AppInfo> appInfoList = Lists.newArrayList(dbAppInfoList);
        for(AppInfo appInfo : memoryAppInfoSet){
            AppInfo dbAppInfo =dbAppInfoMap.get(appInfo.getApp());
            if(dbAppInfo == null){
                appConfigService.createApp(appInfo,authUser);
                appInfoList.add(appInfo);
            }
        }
        return appInfoList;
    }

    @Override
    public AppInfo getAppInfoByApp(String app) {
        if(StringUtils.isEmpty(app)){
            return null;
        }
        App dbApp = appQueryManager.getAppByApp(app);
        AppInfo appInfo = JSONObject.parseObject(dbApp.getAppInfo(),new TypeReference<AppInfo>(){});
        return appInfo;
    }
}
