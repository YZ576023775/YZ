package com.yz.pagetool;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.yz.lucene.LuceneDao;

public class PageListTest {

	@Test
	public void test() throws Exception {
		LuceneDao luceneDao = new LuceneDao();
		List<Map<String, String>> list =  luceneDao.findByPhraseWithMap("徐佳莹", 0, 50);
		System.out.println(list.size());
		Page page = new Page(8, list.size(), 6);
		int totalPage = page.getTotalPage();
		System.out.println(totalPage);
		for (int i = 1; i <=totalPage; i++) {
			page.setCurrentPage(i);
			int firstResult  = page.getFirst();
			int maxResult  = page.getCount();
			List<Map<String, String>> datas = luceneDao.findByPhraseWithMap("徐佳莹", firstResult, maxResult);
			PageBean<Map<String, String>> pageBean = new PageBean<Map<String, String>>();
			pageBean.setDatas(datas);
			pageBean.setPage(page);
		/*	System.out.println("当前页:	"+pageBean.getPage());
			System.out.print("当前页数据:	");
			for (Object object : datas) {
				System.out.println(object);
			}*/
			System.out.println(pageBean);
		}
			
		}

}
