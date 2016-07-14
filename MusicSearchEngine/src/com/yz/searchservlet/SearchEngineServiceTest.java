package com.yz.searchservlet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class SearchEngineServiceTest {

	@Test
	public void test() {
		SearchEngineService searchEngineService = new SearchEngineService();
		List<String> list =Arrays.asList(new String[]{"artist","music","album","mv"});

		List<Map<String , String >> lMaps = searchEngineService.searchHighLightByParseCatagory("徐佳莹", list, 0, 2);
		for (Map<String, String> map : lMaps) {
			System.out.println(map);
		}
		
	}

}
