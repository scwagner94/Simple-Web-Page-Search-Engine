package edu.canisius.search;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * 
 * @author Sean Wagner
 * @author Emily Terranova
 */
public class Google 
{
  /**
   * 
   */
  public HashMap<String, Integer> processLine(Iterator<String> line, String lineText) 
  {
	  HashMap<String,Integer> lineMap = new HashMap<String,Integer>();
	  while(line.hasNext())
	  {
		  String word = line.next();
		  if(lineMap.containsKey(word)) 
		  {
			  lineMap.put(word, lineMap.get(word)+1);
		  }
		  else
		  {
			  lineMap.put(word,1);
		  }
	  }
	  return lineMap;
  }
}
