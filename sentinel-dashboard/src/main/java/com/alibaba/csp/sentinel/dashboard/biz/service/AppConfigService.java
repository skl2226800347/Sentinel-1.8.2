package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.vo.result.AppSaveResultVO;
import com.alibaba.csp.sentinel.dashboard.discovery.AppInfo;
import com.alibaba.csp.sentinel.dashboard.domain.Result;

public interface AppConfigService {
    Result<AppSaveResultVO> createApp(AppInfo appInfo, AuthService.AuthUser authUser);

    Result<AppSaveResultVO> updateApp(AppInfo appInfo, AuthService.AuthUser authUser);

    Result<AppSaveResultVO> saveApp(AppInfo appInfo, AuthService.AuthUser authUser);

    Result<AppSaveResultVO> deleteApp(Long id);

}
