package edu.canisius.data;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import edu.canisius.data.Data;
import edu.canisius.data.DataController;
import edu.canisius.linear.PLSequence;
import edu.canisius.search.*;

/** This class is merely a testing class for the Google
 *  Search Project. It involves multiple JUnit tests.
 *  
 *  @author Sean Wagner
 *  @author Emily Terranova
 *
 */
public class GoogleTests 
{
	/** This method is only called after every other test has been completed. 
	 *  The purpose is so that the original DataController and Data are
	 *  scratched, and new ones are created.
	 */
	@After
	public void tearDown() {
		Data.getInstance().tearDown();
		DataController.getInstance().tearDown();
	}
	
	/** This method tests to make sure that the Dictionary, when
	 *  initialized, is empty. We do this by testing the size
	 *  of the Dictionary. 
	 */
	@Test
	public void emptyMap()
	{
		Data d = Data.getInstance();
		assertEquals(0,d.getData().size());
	}
	
	/** This method tests the add function of our project.
	 *  In this test, we are just adding one WebPage and
	 *  its contents to our dictionary. We then test to
	 *  see if the WebPage's contents are contained within
	 *  the Dictionary by searching the Dictionary for WebPage's
	 *  containing the URL given.
	 *  
	 *  @throws IOException throws an IOException if the URL does not exist
	 */
	@Test
	public void addOneURLToDict() throws IOException
	{
		String url = "http://www.canisius.edu";
		DataController dc = DataController.getInstance();
		dc.add(url);
		Data d = Data.getInstance();
		assertTrue(d.getData().size() > 1);
		assertEquals("http://www.canisius.edu",d.getData().get("canisius").removeFirst().getURL());
	}
	
	/** This method tests the add function of our project.
	 *  In this test, we are just adding three WebPagse and
	 *  their contents to our dictionary. We then test to
	 *  see if the WebPages' contents are contained within
	 *  the Dictionary by searching the Dictionary for WebPages'
	 *  containing the URL given. We also test to see if
	 *  the words in the Dictionary are associated with more
	 *  than one URL, as they should be.
	 *  
	 *  @throws IOException throws an IOException if the URL does not exist
	 */
	@Test
	public void addManyURLsToDict() throws IOException
	{
		String url = "http://www.goaliemonkey.com";
		String url2 = "http://www.msmathletics.org";
		String url3 = "http://www.canisius.edu";
		
		DataController dc = DataController.getInstance();
		
		dc.add(url);
		dc.add(url2);
		dc.add(url3);
		
		Data d = Data.getInstance();
		
		assertTrue(d.getData().size() > 3);
		
		assertEquals("http://www.canisius.edu",d.getData().get("canisius").get(0).getURL());
		assertEquals("http://www.goaliemonkey.com", d.getData().get("hockey").get(0).getURL());
		assertEquals("http://www.msmathletics.org",d.getData().get("lacrosse").get(0).getURL());
		assertEquals("http://www.msmathletics.org",d.getData().get("hockey").get(1).getURL());
		
	}
	
	/** This method tests to make sure that invalid websites are
	 *  not added to the Dictionary. It is expected that
	 *  an IOException will be thrown.
	 *  
	 *  @throws IOException throws an exception if the website does not exist
	 */
	@Test (expected = IOException.class)
	public void invalidAdd() throws IOException
	{
		String url = "http://www.fnhdbbd.com";
		DataController dc = DataController.getInstance();
		
		dc.add(url);
	}
	
	/** This method tests to make sure that the words
	 *  exist in the Dictionary by testing to
	 *  see if the URLs they contain are equal
	 *  to the URLs searched.
	 *  
	 *  @throws IOException
	 */
	@Test
	public void listFunction() throws IOException
	{
		String url = "http://www.canisius.edu";
		
		DataController dc = DataController.getInstance();
		
		dc.add(url);
		
		PLSequence<WebPage> pl = dc.list("canisius");
		PLSequence<WebPage> pl2 = dc.list("college");
		
		assertEquals(url,pl.getFirst().getURL());
		assertEquals(url,pl2.getFirst().getURL());
		
	}
	/** This method tests to make sure that any words
	 *  not found in the list are not in the Dictionary.
	 *  Hence, the PLSequence returned by calling the 
	 *  list() method is null.
	 *  
	 *  @throws IOException throws an exception if the website does not exist
	 */
	@Test
	public void invalidList() throws IOException
	{
		String url = "http://www.canisius.edu";
		
		DataController dc = DataController.getInstance();
		
		dc.add(url);
		
		PLSequence<WebPage> pl = dc.list("toy");
		PLSequence<WebPage> pl2 = dc.list("lard");
		
		assertNull(pl);
		assertNull(pl2);
	}
	
	/** This method tests to see if the find function of our
	 *  project functions properly. It does this by comparing
	 *  the counts of the words obtained from one website,
	 *  making sure it is the same as what it should be.
	 *  It also makes sure that the words are associated to
	 *  the same website, since we are only adding one
	 *  website.
	 *  
	 *  @throws IOException throws an exception if the website does not exist
	 */
	@Test // this method lists the items contained in the dictionary along with the number of occurrences
	public void findFunction() throws IOException
	{
		String url = "http://www.canisius.edu";
		
		DataController dc = DataController.getInstance();
		
		dc.add(url);
		
		PLSequence<WebPage> pl = dc.find("canisius");
		
		assertEquals(url,pl.getFirst().getURL());
		assertEquals(10,pl.getFirst().getCount());
	}
	
	/** This method tests to see if the remove function of our
	 *  project functions properly. It adds many websites to
	 *  the Dictionary, then removes any searches that
	 *  contained one specific website. The result should be the
	 *  same list, but with no instance of the removed website.
	 *  
	 *  @throws IOException throws an exception if the website does not exist
	 */
	@Test // this method tests the removal of pages from the dictionary
	public void removeFromDict() throws IOException
	{
		
	}
	
	/** This method tests to make sure that invalid websites are
	 *  not removed from the Dictionary. It is expected that
	 *  an IOException will be thrown.
	 *  
	 *  @throws IOException throws an exception if the website does not exist
	 */
	@Test
	public void invalidRemove() throws IOException
	{
		
	}
}
