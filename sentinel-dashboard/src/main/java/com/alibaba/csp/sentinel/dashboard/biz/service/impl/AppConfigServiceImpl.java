package com.alibaba.csp.sentinel.dashboard.biz.service.impl;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.constants.CommonConstant;
import com.alibaba.csp.sentinel.dashboard.biz.entity.App;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.biz.helper.AppHelper;
import com.alibaba.csp.sentinel.dashboard.biz.service.AbstractService;
import com.alibaba.csp.sentinel.dashboard.biz.service.AppConfigService;
import com.alibaba.csp.sentinel.dashboard.biz.vo.param.AppSaveParamVO;
import com.alibaba.csp.sentinel.dashboard.biz.vo.result.AppSaveResultVO;
import com.alibaba.csp.sentinel.dashboard.discovery.AppInfo;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.enums.ResultCodeEnum;
import org.springframework.stereotype.Service;

@Service
public class AppConfigServiceImpl extends AbstractService implements AppConfigService {
    @Override
    public Result<AppSaveResultVO> createApp(AppInfo appInfo, AuthService.AuthUser authUser) {
        Result result = AppHelper.checkCreateAppParam(appInfo,authUser);
        if(result != null){
            return result;
        }
        App insertApp = AppHelper.convertInsertApp(appInfo,authUser);
        App dbApp = appQueryManager.getAppByApp(appInfo.getApp());
        if(dbApp == null){
            int updateRows =appMapperExt.insertSelective(insertApp);
            if(updateRows<=CommonConstant.ZERO){
                throw new DashboardBizException("更新失败");
            }
            appInfo.setId(insertApp.getId());
            AppSaveResultVO appAddResultVO = new AppSaveResultVO();
            appAddResultVO.setId(insertApp.getId());
            return Result.ofSuccess(appAddResultVO);
        }else{
            appInfo.setId(dbApp.getId());
            return updateApp(appInfo,authUser);
        }
    }

    @Override
    public Result<AppSaveResultVO> updateApp(AppInfo appInfo, AuthService.AuthUser authUser) {
        Result result = AppHelper.checkUpdateAppParam(appInfo,authUser);
        if(result != null){
            return result;
        }
        App updateApp = AppHelper.convertUpdateApp(appInfo,authUser);
        int updateRows =appMapperExt.updateByPrimaryKeySelective(updateApp);

        if(updateRows<=CommonConstant.ZERO){
            throw new DashboardBizException("更新失败");
        }
        AppSaveResultVO appSaveResultVO = new AppSaveResultVO();
        appSaveResultVO.setId(appSaveResultVO.getId());
        return Result.ofSuccess(appSaveResultVO);
    }

    @Override
    public Result<AppSaveResultVO> saveApp(AppInfo appInfo, AuthService.AuthUser authUser) {
        Result result = AppHelper.checkSaveAppParam(appInfo,authUser);
        if(result != null){
            return result;
        }
        if(appInfo.getId() == null){
            result = createApp(appInfo,authUser);
        }else{
            result = updateApp(appInfo,authUser);
        }
        return result;
    }

    @Override
    public Result<AppSaveResultVO> deleteApp(Long id) {
        int updateRows =appMapperExt.deleteByPrimaryKey(id);
        if(updateRows<=CommonConstant.ZERO){
            throw new DashboardBizException("删除失败");
        }
        AppSaveResultVO appSaveResultVO = new AppSaveResultVO();
        appSaveResultVO.setId(id);
        return Result.ofSuccess(appSaveResultVO);
    }
}
