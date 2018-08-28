package com.zcxa.share.vo;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long total;
	private List<T> rows;
	private Object extra;

	public Page() {
		this.total = 0L;
		this.extra = null;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public List<T> getRows() {
		return this.rows;
	}

	public Object getExtra() {
		return this.extra;
	}

	public void setExtra(Object extra) {
		this.extra = extra;
	}

	public long getTotal() {
		return this.total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}
