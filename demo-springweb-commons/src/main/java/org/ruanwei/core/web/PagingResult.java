package org.ruanwei.core.web;

import java.util.List;


/**
 * Created by zhongxianyao.
 * Time 2018/5/24
 * Desc 文件描述
 */
public class PagingResult<T> extends Result<List<T>> {

    private int curPage = 1;

    private int pageSize = 10;

    private long count;


    public PagingResult() {
    }


    public PagingResult(int code, String message) {
        super(code, message);
    }


    public PagingResult(Page page, long count, List<T> list) {
        this.setSuccess(true);
        this.curPage = page.getCurPage();
        this.pageSize = page.getPageSize();
        this.count = count;
        this.setData(list);
    }


    public PagingResult(int curPage, int pageSize, long count, List<T> list) {
        this.setSuccess(true);
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.count = count;
        this.setData(list);
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


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append(", curPage=").append(curPage);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", count=").append(count);
        return sb.toString();
    }


    public static PagingResultBuilder bulider() {
        return new PagingResultBuilder();
    }


    public static class PagingResultBuilder<T> extends ResultBuilder<List<T>> {

        protected PagingResult<T> result;


        public PagingResultBuilder() {
            result = new PagingResult<>();
        }


        public PagingResultBuilder list(List<T> data) {
            result.setData(data);
            return this;
        }


        public PagingResultBuilder count(long count) {
            result.setCount(count);
            return this;
        }


        public PagingResultBuilder page(Page page) {
            result.setCurPage(page.getCurPage());
            result.setPageSize(page.getPageSize());
            return this;
        }


        public PagingResultBuilder curPage(int curPage) {
            result.setCurPage(curPage);
            return this;
        }


        public PagingResultBuilder pageSize(int pageSize) {
            result.setPageSize(pageSize);
            return this;
        }


        public PagingResult<T> build() {
            return result;
        }
    }
}
