package vell.bibi.vvip.test;

import vell.bibi.vvip.service.StringService;

public class StringTest {
	public static void main(String[] args) {
		StringService mStringService = StringService.getInstance();
		
		System.out.println(mStringService.getAdminHelpStr());
	}

}
