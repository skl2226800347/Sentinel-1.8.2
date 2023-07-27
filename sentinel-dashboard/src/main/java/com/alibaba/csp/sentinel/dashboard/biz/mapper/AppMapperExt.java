package com.alibaba.csp.sentinel.dashboard.biz.mapper;

import com.alibaba.csp.sentinel.dashboard.biz.domain.query.AppPaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.App;

import java.util.List;

public interface AppMapperExt extends AppMapper{
    /**
     * 应用分页查询接口
     * @param appPaginationQuery 入参
     * @return 结果
     */
    List<App> selectAppWithPaging(AppPaginationQuery appPaginationQuery);

    /**
     * 查询条数
     * @param appPaginationQuery 入参
     * @return 条数
     */
    Integer countAppWithPaging(AppPaginationQuery appPaginationQuery);
}