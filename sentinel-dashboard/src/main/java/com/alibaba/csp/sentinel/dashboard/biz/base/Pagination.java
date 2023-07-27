package com.alibaba.csp.sentinel.dashboard.biz.base;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页Page
 * @author  skl
 */
public class Pagination<T> implements Serializable {
    private static final long serialVersionUID = -3509205611665142708L;
    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     *  每页查询数量
     */
    private Integer pageSize;

    /**
     *  总记录数
     */
    private Integer totalCount;

    /**
     * 查询数据结果列表
     */
    private List<T> list;

    private static final Integer DEFAULT_TOTOAL_ITEM = 0;
    /**
     * 默认每页记录数
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 默认当前页
     */
    public static final Integer DEFAULT_CURRENT_PAGE = 1;
    /**
     * 1
     */
    public static final Integer ONE=1;
    /**
     * page count默认值
     */
    public static final Integer DEFAULT_PAGE_COUNT=0;

    /**
     * 构造函数
     */
    public Pagination() {
        init(null, null);
    }

    /**
     * 构造方法
     *
     * @param currentPage
     *            页码索引下标
     *
     */
    public Pagination(Integer currentPage) {
        init(currentPage, null);
    }

    /**
     * 构造方法
     *
     * @param currentPage
     *            页码索引下标
     * @param pageSize
     *            每页查询数量
     */
    public Pagination(Integer currentPage, Integer pageSize) {
        init(currentPage, pageSize);
    }

    /**
     * 初始化分页器
     *
     * @param page
     * @param rows
     *
     */
    private void init(Integer page, Integer rows) {
        this.currentPage = (page == null || page.intValue() < ONE.intValue()) ? DEFAULT_CURRENT_PAGE : page;
        this.pageSize = (rows == null || rows.intValue() < ONE.intValue()) ? DEFAULT_PAGE_SIZE : rows;
        this.list = new ArrayList<T>();
    }

    /**
     * 设置总记录数，并计算总页数
     *
     * @param totalCount
     *
     */
    public void setTotalCount(Integer totalCount) {
        if(totalCount == null || totalCount.intValue()<DEFAULT_TOTOAL_ITEM.intValue()){
            return;
        }
        this.totalCount = totalCount;
        int rows=getPageSize();
        int tempTotalCount=totalCount.intValue();
        int pageCount = tempTotalCount % rows == 0 ? tempTotalCount / rows : tempTotalCount / rows + 1;
        int page=getCurrentPage();
        if (pageCount != 0 && page > pageCount) {
            setCurrentPage(pageCount);
        }
    }

    public Integer getCurrentPage() {
        if(currentPage == null || currentPage.intValue()<0){
            return DEFAULT_CURRENT_PAGE;
        }
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage=currentPage;
    }


    public Integer getPageSize() {
        if(pageSize ==null || pageSize.intValue()<0){
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize=pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public Integer getPageCount() {
        if(this.totalCount == null || this.totalCount.intValue()<DEFAULT_TOTOAL_ITEM.intValue()){
            return DEFAULT_PAGE_COUNT;
        }
        int totalItem=this.totalCount.intValue();
        int rows=getPageSize();
        int pageCount = totalItem % rows == 0 ? totalItem / rows : totalItem / rows + 1;
        return pageCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
