package com.yz.pagetool;

import java.util.List;

public class PageBean<T> {
	private List<T> datas;
	
	private Page page;
	
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	@Override
	public String toString() {
		return "PageBean [datas=" + datas + ", page=" + page + "]";
	}

}
