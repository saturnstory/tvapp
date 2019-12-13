package com.proton.tvapp.util;

public class PageableUtil {
	public static int limit(String lm) {
		int limit = 10;
		try {
			limit = Integer.parseInt(lm);
		}catch (Exception exp){
			System.out.println("Limit : " + exp.getMessage());
		}
		
		if(limit <= 0)  {
			limit = 10;
		}
		
		return limit;
	}
	
	public static int page(String pg) {
		int page = 1;
		try {
			page = Integer.parseInt(pg);
		}catch(Exception exp) {
			System.out.println("Page : " + exp.getMessage());
		}
		
		if(page <= 0) {
			page = 1;
		}
		
		return page;
	}
	
	public static int skip(int page, int limit) {
		if(page <= 1) {
			return 0;
		}		
		int skip = (limit * page) - limit;
		return skip;
	}
	
	public static long totalPage(long rowCount, long limit) {
		long totalPage = (long) Math.ceil(((double) rowCount /  limit));
		return totalPage;
	}
}
