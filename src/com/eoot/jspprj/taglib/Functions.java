package com.eoot.jspprj.taglib;

public class Functions {
	public static int lastNum(double total){
		int lastPager = (int) (Math.ceil(total/20));
		return lastPager;
		
/*		int _total = (int)total;
		int last = _total/20;
		if(_total%20 > 0)
			last++ */
			
	}
}
