package edu.canisius.data;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import edu.canisius.linear.PLSequence;
import edu.canisius.search.*;
import edu.canisius.web.WebPageParser;

/** 
 *  
 *  @author Sean Wagner
 *  @author Emily Terranova
 *
 */
public class DataController 
{
	private static DataController dc;
	private Data data;
	
	//for j unit testing only
	protected void tearDown() 
	{
		dc = null;
		data = null;
	}
	
	private DataController() 
	{
		data = Data.getInstance();	
	}
	
	public static DataController getInstance() 
	{
		if(dc==null) 
		{
			dc = new DataController();
		}
		return dc;
	}
	
	/**
	 * 
	 * @param url
	 * @throws IOException
	 */
	public void add(String url) throws IOException 
	{
		HashMap<String,PLSequence<WebPage>> hm = data.getData();
		WebPageParser wpp = new WebPageParser(url);
		Iterable<String> it = wpp.getText();
		
		Google go = new Google();
		HashMap<String,Integer> map = go.processLine(it.iterator(), url);
		Iterator<Entry<String,Integer>> entries = map.entrySet().iterator();
		
		while(entries.hasNext()) 
		{
			Entry<String,Integer> e = entries.next();
			WebPage wp = new WebPage(url,e.getValue());
			if(hm.containsKey(e.getKey())) 
			{
				PLSequence<WebPage> pl = hm.get(e.getKey());
				pl.addLast(wp);
			}
			else
			{
				PLSequence<WebPage> pl = new PLSequence<WebPage>();
				pl.addLast(wp);
				hm.put(e.getKey(), pl);
			}
		}
	}
	
	/**
	 * 
	 * @param url
	 */
	public void remove(String url)
	{
		Data dc = Data.getInstance();
		HashMap<String,PLSequence<WebPage>> hm = dc.getData();
		Iterator<Entry<String,PLSequence<WebPage>>> hmEntries = hm.entrySet().iterator();
		
		while(hmEntries.hasNext())
		{
			Entry<String, PLSequence<WebPage>> e = hmEntries.next();
			System.out.println(e.getKey() + "\t" + e.getValue().getFirst().getCount());
			Iterator<WebPage> plIT = e.getValue().iterator();

			while(plIT.hasNext())
			{
				WebPage wp = plIT.next();
				if(wp.getURL().equals(url)) // && (e.getValue().size() == 1)
				{
					// remove the entire entry from the dictionary
					hm.remove(e.getKey());
				}
				else if(wp.getURL().equals(url))
				{
					// remove only the PLSequence from the dictionary
					
				}
			}
			
			
		}
		
	}
	
	public void remove2(String url)
	{
		Data dc = Data.getInstance();
		HashMap<String,PLSequence<WebPage>> hm = dc.getData();
		Iterator<Entry<String,PLSequence<WebPage>>> hmEntries = hm.entrySet().iterator();
		HashMap<String,Integer> removeWords = new HashMap<String,Integer>();
		
		while(hmEntries.hasNext())
		{
			Entry<String, PLSequence<WebPage>> e = hmEntries.next();
			System.out.println(e.getKey() + "\t" + e.getValue().getFirst().getCount());
			Iterator<WebPage> plIT = e.getValue().iterator();
			int count = 0;
			while(plIT.hasNext())
			{
				WebPage wp = plIT.next();
				if(wp.getURL().equals(url))
				{
					removeWords.put(e.getKey(),count);
				}
				count++;
			}
		}
		Iterator<Entry<String,Integer>> removeIT = removeWords.entrySet().iterator();
		while(removeIT.hasNext())
		{
			Entry<String,Integer> entry = removeIT.next();
			hm.get(entry.getKey()).remove(entry.getValue());
			if(hm.get(entry.getKey()).isEmpty()) {
				hm.remove(entry.getKey());
			}
		}
	}
	
	/**
	 * 
	 * @param searchVal
	 * @return
	 */
	public PLSequence<WebPage> find(String searchVal) 
	{
		Data dc = Data.getInstance();
		HashMap<String,PLSequence<WebPage>> hm = dc.getData();
		PLSequence<WebPage> plS = hm.get(searchVal);
		return plS;
	}
	
	/**
	 * 
	 * @param searchVal
	 * @return
	 */
	public PLSequence<WebPage> list(String searchVal) 
	{
		Data dc = Data.getInstance();
		HashMap<String,PLSequence<WebPage>> hm = dc.getData();
		PLSequence<WebPage> plS = hm.get(searchVal);
		return plS;
	}
	
}
