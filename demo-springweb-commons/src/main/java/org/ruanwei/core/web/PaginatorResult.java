package org.ruanwei.core.web;

import java.util.List;


/**
 * Created by zhongxianyao.
 * Time 2018/5/24
 * Desc 文件描述
 */
public class PaginatorResult<T> extends BaseResult {
    private int curPage = 1;
    private int pageSize = 10;
    private long count;
    private List<T> list;

    public PaginatorResult() {}

    public PaginatorResult(Page page, long count, List<T> list) {
        this.setSuccess(true);
        this.curPage = page.getCurPage();
        this.pageSize = page.getPageSize();
        this.count = count;
        this.list = list;
    }

    public PaginatorResult(int curPage, int pageSize, long count, List<T> list) {
        this.setSuccess(true);
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.count = count;
        this.list = list;
    }


    public int getCurPage() {
        return curPage;
    }


    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }


    public int getPageSize() {
        return pageSize;
    }


    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public long getCount() {
        return count;
    }


    public void setCount(long count) {
        this.count = count;
    }


    public List<T> getList() {
        return list;
    }


    public void setList(List<T> list) {
        this.list = list;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("success=").append(isSuccess());
        sb.append(", code=").append(getCode());
        sb.append(", message='").append(getMessage());
        sb.append(", curPage=").append(curPage);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", count=").append(count);
        sb.append(", data=").append(list);
        sb.append('}');
        return sb.toString();
    }
}
