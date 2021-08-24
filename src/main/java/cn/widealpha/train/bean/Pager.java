package cn.widealpha.train.bean;

import java.util.List;

/**
 * 分页实体
 * @param <T>
 */
public class Pager<T> {
    private int page;//分页起始页
    private int size;//每页记录数
    private List<T> rows;//返回的记录集合
    private long total;//总记录条数

    public Pager() {
    }

    public Pager(int page, int size, List<T> rows, long total) {
        this.page = page;
        this.size = size;
        this.rows = rows;
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Pager{" +
                "page=" + page +
                ", size=" + size +
                ", rows=" + rows +
                ", total=" + total +
                '}';
    }
}