package com.yz.searchservlet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import com.yz.lucene.LuceneDao;
import com.yz.pagetool.Constants;
import com.yz.pagetool.Page;
import com.yz.pagetool.PageBean;

public class SearchEngineService {
	
	LuceneDao luceneDao = new LuceneDao();
	
	public int findRowCountByfindIndex(String keywords) {
		List<Map<String, String>> list = null;
		try {
			list = luceneDao.findIndexWithMap(keywords, 0, 999999999);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list.size();

	}
	public int findRowCountByfindParse(String keywords) {
		int list = 0;
		try {
			list = luceneDao.findCountByPhraseWithObject(keywords, 0, 999999999);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}

	public PageBean<Map<String, String>> search(String keywords, int currentPage) {
		int rowCount = findRowCountByfindIndex(keywords);
		if (rowCount!=0) {
			Page page = new Page(Constants.PAGESIZE, rowCount, currentPage,
					Constants.PAGINATIONNUM);
			List<Map<String, String>> datas = null;
			try {
				datas = luceneDao.findIndexWithMap(keywords, page.getFirst(),
						page.getCount());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PageBean<Map<String, String>> pageBean = new PageBean<Map<String, String>>();
			pageBean.setDatas(datas);
			pageBean.setPage(page);
			System.out.println(pageBean);
			return pageBean;
		}
		return null;

	}
	public PageBean<Map<String, String>> searchByParse(String keywords, int currentPage) {
		int rowCount = findRowCountByfindParse(keywords);
		if (rowCount!=0) {
			Page page = new Page(Constants.PAGESIZE, rowCount, currentPage,
					Constants.PAGINATIONNUM);
			List<Map<String, String>> datas = null;
			try {
				datas = luceneDao.findByPhraseWithMap(keywords, page.getFirst(),
						page.getCount());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PageBean<Map<String, String>> pageBean = new PageBean<Map<String, String>>();
			pageBean.setDatas(datas);
			pageBean.setPage(page);
			System.out.println(pageBean);
			return pageBean;
		}
		return null;
		
	}

	public List<Map<String, String>> searchHighLightByParseCatagory(
			String keywords, List<String> catagory, int firstResult,
			int maxResult)  {
		List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
		for (String string : catagory) {
			List<Map<String, String>> datastmp = null;
			try {
				datastmp = luceneDao.findByPhraseCatagoryWithHighLightMap(keywords,
						string, firstResult, maxResult);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (datastmp.size() > 0) {
				for (Map<String, String> map : datastmp) {
					datas.add(map);
				}
			}
		}
		return datas;

	}
	public PageBean<Map<String, String>> searchHighLightByParse(String keywords, int currentPage) {
		int rowCount = findRowCountByfindParse(keywords);
		if (rowCount!=0) {
			Page page = new Page(Constants.PAGESIZE, rowCount, currentPage,
					Constants.PAGINATIONNUM);
			List<Map<String, String>> datas = null;
			try {
				datas = luceneDao.findByPhraseWithHighLightMap(keywords, page.getFirst(),
						page.getCount());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		/*	for (Map<String, String> map : datas) {
				System.out.println(map);
			}*/
			PageBean<Map<String, String>> pageBean = new PageBean<Map<String, String>>();
			pageBean.setDatas(datas);
			pageBean.setPage(page);
			return pageBean;
		}
		return null;
		
	}
	public List<String> otherSearchStrings(ServletContext application , String keywords){
		Map<String, Integer> allOtherCount = new HashMap<String, Integer>();
		Enumeration<String> enumeration = application.getAttributeNames();
		while (enumeration.hasMoreElements()) {
			String string = (String) enumeration.nextElement();
//			System.out.println(sessionId);
			Object singleOtherSearch=  application.getAttribute(string);
			if (singleOtherSearch instanceof Set) {
				Set<String> set = (Set<String>)singleOtherSearch;
				if (set.contains(keywords)) {
					for (String keywordsString : set) {
						Integer integer = allOtherCount.get(keywordsString);
						if (integer != null) {
							allOtherCount.put(keywordsString, integer+1);
						} else {
							allOtherCount.put(keywordsString, 1);
						}
					}
				}
			}
		}
		allOtherCount.remove(keywords);
		System.out.println("其他人还搜索：	"+allOtherCount);
//		for (Map.Entry<String, Integer> entry: allOtherCount.entrySet()) {
//			System.out.println(entry.getKey()+" : "+entry.getValue());
//		}
		List<String> otherSearch = new ArrayList<String>();
		return null;
		
	}
	
	
}
