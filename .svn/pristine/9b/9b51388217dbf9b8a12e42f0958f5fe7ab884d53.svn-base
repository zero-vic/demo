package com.hy.demo.common;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
/**
 *
 * @Description 分页数据封装类
 * @Author yao
 * @Date 2023/5/26 17:18
 **/
public class CommonPage<T> {
    // 每页个数
    private long pageSize;

    // 当前页
    private long pageNum;

    // 总页数
    private long totalPage;

    // 总个数
    private long total;

    //数据记录
    private List<T> items;

    public CommonPage() {
    }

    public CommonPage(IPage<T> page){
            this.pageSize = page.getSize();
            this.pageNum = page.getCurrent();
            this.totalPage = page.getPages();
            this.total = page.getTotal();
            this.items = page.getRecords();

    }
    /**
     *
     *  支持自定义列表
     *
     **/
    public CommonPage(IPage<T> page ,List<T> items){
        this.pageSize = page.getSize();
        this.pageNum = page.getCurrent();
        this.totalPage = page.getPages();
        this.total = page.getTotal();
        this.items = items;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}