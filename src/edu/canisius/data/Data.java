package edu.canisius.data;
import java.util.HashMap;
import edu.canisius.linear.PLSequence;
import edu.canisius.search.WebPage;

/**
 * 
 * @author Emily Terranova
 * @author Sean Wagner
 *
 */
public class Data 
{
	private static Data data;
	private HashMap<String, PLSequence<WebPage>> words;
	
	/**
	 * 
	 */
	private Data() 
	{
		words = new HashMap<String,PLSequence<WebPage>>();
	}
	
	/**
	 * 
	 * @return
	 */
	protected static Data getInstance() 
	{
		if (data==null) 
		{
			data = new Data();
		}
		
		return data;
	}
	
	/**
	 * 
	 * @return
	 */
	protected HashMap<String,PLSequence<WebPage>> getData() 
	{
		return words;
	}
	
	//for j unit testing only
	protected void tearDown() {
		data = null;
		words = null;
	}
}
