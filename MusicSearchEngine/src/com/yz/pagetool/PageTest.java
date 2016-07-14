package com.yz.pagetool;

import org.junit.Test;

public class PageTest {

	@Test
	public void test() {
		Page page = new Page(8, 29, 6);
		for (int i = 1; i <=page.getTotalPage(); i++) {
			page.setCurrentPage(i);
			System.out.println(page);
		}
	}

}
