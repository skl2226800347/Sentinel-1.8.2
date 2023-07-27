package com.alibaba.csp.sentinel.dashboard.biz.base;

import java.io.Serializable;

public class BaseQuery<T> implements Serializable {
    /**
     * 默认第一页值
     */
    private static final Integer DEFAULT_CURRENT_PAGE = 1;
    /**
     * 默认页大小
     */
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 当前页
     */
    private Integer currentPage =DEFAULT_CURRENT_PAGE;
    /**
     * 每页查询数量
     */
    private Integer pageSize =DEFAULT_PAGE_SIZE;

    /**
     * 偏移量
     */
    private Integer offset;

    private String groupByClause;

    private String orderByClause;


    public Integer getCurrentPage() {
        if(currentPage ==null || currentPage<=0){
            return DEFAULT_CURRENT_PAGE;
        }
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        if(this.pageSize == null || this.pageSize<=0){
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        int tempCurrentPage=getCurrentPage();
        int tempPageSize=getPageSize();
        this.offset=(tempCurrentPage-1)*tempPageSize;
        return this.offset;
    }

    public String getGroupByClause() {
        return groupByClause;
    }

    public void setGroupByClause(String groupByClause) {
        this.groupByClause = groupByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }
}
