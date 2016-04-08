package edu.canisius.search;

/** 
 *  
 *  @author Sean Wagner
 *  @author Emily Terranova
 */
public class WebPage 
{
	private String url;
	private int count;
	
	public WebPage(String x) 
	{
		url = x;
		count = 1;
	}
	public WebPage(String x, int y) 
	{
		url = x;
		count = y;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getURL() 
	{ 
		return url; 
	}
	
	/**
	 * 
	 * @return
	 */
	public int getCount() 
	{ 
		return count;
	}
	
	/**
	 * 
	 * @param x
	 */
	public void setCount(int x) 
	{
		count = x;
	}
	
	/**
	 * 
	 * @param x
	 */
	public void setURL(String x) 
	{ 
		url = x;
	}
	
	/**
	 * 
	 */
	public void incrementCount() 
	{
		count++;
	}
}
