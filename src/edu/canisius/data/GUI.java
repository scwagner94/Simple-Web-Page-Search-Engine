package edu.canisius.data;

import edu.canisius.linear.PLSequence;
import edu.canisius.search.WebPage;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.EventQueue;
import java.awt.Font;

import java.io.IOException;
import java.util.Iterator;

/** This class creates and initializes the GUI. From there, the GUI
 *  accesses the DataController class, which accesses the Dictionary
 *  based on the commands that the user inputs through the interface.
 *  This GUI has 5 different features: add, list, find, remove, and
 *  quit.
 *  
 *  Add takes a valid URL from the user, gets the text content of the
 *  page, and stores each word in the dictionary. The dictionary keeps
 *  track of which words are from the webpages, and the number of
 *  occurrences of the word in said webpage.
 *  
 *  List takes a word from the user's input and searches for that word
 *  in the dictionary. If the word is in the dictionary (ie the search
 *  engine), then a new frame with a text area will appear with a list
 *  of webpages that have that word on their page. If the word is not 
 *  in the dictionary, then a message will appear, saying that it does
 *  not exist in the search engine.
 *  
 *  Find takes a word from the user's input and searches for that word
 *  in the dictionary. If the word is in the dictionary (ie the search
 *  engine), then a new frame with a text area will appear with a list
 *  of webpages that have that word on their page and a number, which
 *  is the number of occurrences of that word on that webpage. If the 
 *  word is not in the dictionary, then a message will appear, saying 
 *  that it does not exist in the search engine.
 *  
 *  Remove will take a URL as input from the user. If the URL is valid
 *  and has been searched for originally, then every word that the URL
 *  contained that is in the list will be removed from the dictionary,
 *  so long as another webpage does not also have that word. If the
 *  URL is not in the list, nothing will occur. If the URL is invalid,
 *  nothing will also occur.
 *  
 *  Quit exits the GUI, thus killing the program.
 *  
 *  @author Sean Wagner
 *  @author Emily Terranova
 *
 */
public class GUI 
{

	private JFrame frame;
	private JFrame textFrame;
	private JTextArea textArea;

	/** The main method just launches the GUI.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					GUI window = new GUI();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		// creates the setting of the main User Interface
		frame = new JFrame();
		frame.setSize(500, 275);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// initializes the text area used for the list and find functions
		intializeTextArea();
		
		JButton btnAdd = new JButton("Add"); // creates the add button
		btnAdd.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) // adds the URL and its text to a dictionary used as the search engine
			{
				String url = JOptionPane.showInputDialog(null, "Please enter a URL");
				DataController dc = DataController.getInstance();
				if(url!=null) 
				{
					// checks for a valid URL
					if(url.startsWith("www.")) 
					{
						url = "http://" + url;
					}
					else if(!url.startsWith("http://www."))
					{
						url = "http://www." + url;
					}
					
					try 
					{
						dc.add(url);
						JOptionPane.showMessageDialog(null, "Added to list.");
					} 
					catch (IOException e1) 
					{
						JOptionPane.showMessageDialog(null, "Invalid URL");
					}
				}
				
			}
		});
		btnAdd.setBounds(6, 34, 117, 29);
		frame.getContentPane().add(btnAdd);
		
		JButton btnList = new JButton("List"); // creates the List button
		btnList.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String s = JOptionPane.showInputDialog(null,"Please enter a word to search for");
				if (s!=null) 
				{
					DataController dc = DataController.getInstance();
					PLSequence<WebPage> plS = dc.list(s);
					
					if(plS == null) // if the word is not contained in the dictionary
					{
						JOptionPane.showMessageDialog(null, "The word \"" + s + "\" is not contained in the search engine.");
					}
					else
					{
						Iterator<WebPage> it = plS.iterator();
						while(it.hasNext()) // takes each URL associated with the word and appends it to a JTextArea
						{
							WebPage wp = it.next();
							String str = wp.getURL();
							textArea.append(str + "\n");
						}
						textFrame.setVisible(true);
					}
				}
			}
		});
		btnList.setBounds(6, 75, 117, 29);
		frame.getContentPane().add(btnList);
		
		JButton btnFind = new JButton("Find"); // creates the Find button
		btnFind.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String s = JOptionPane.showInputDialog(null,"Please enter a word to search for");
				if (s!=null) 
				{
					DataController dc = DataController.getInstance();
					PLSequence<WebPage> plS = dc.find(s);
					
					if(plS == null) // if the word is not contained in the dictionary
					{
						JOptionPane.showMessageDialog(null, "The word \"" + s + "\" is not contained in the search engine.");
					}
					else
					{
						Iterator<WebPage> it = plS.iterator();
						while(it.hasNext())  // takes each URL and count associated with the word and appends it to a JTextArea
						{
							WebPage wp = it.next();
							String str = wp.getURL() + " - " + wp.getCount();
							textArea.append(str + "\n");
						}
						textFrame.setVisible(true);
					}
				}
			}
		});
		btnFind.setBounds(6, 116, 117, 29);
		frame.getContentPane().add(btnFind);
		
		JButton btnRemove = new JButton("Remove"); // creates the remove button
		btnRemove.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String url = JOptionPane.showInputDialog(null, "Please enter a URL");
				if(url!=null) 
				{
					// checks for a valid URL
					if(url.startsWith("www."))
					{
						url = "http://" + url;
					}
					else if(!url.startsWith("http://www."))
					{
						url = "http://www." + url;
					}
					DataController dc = DataController.getInstance();
					dc.remove2(url);
					JOptionPane.showMessageDialog(null, "Removed from List.");
				}
				
			}
		});
		btnRemove.setBounds(6, 157, 117, 29);
		frame.getContentPane().add(btnRemove);
		
		JButton btnQuit = new JButton("Quit"); // creates the Quit button
		btnQuit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				System.exit(0); // kills the program
			}
		});
		btnQuit.setBounds(6, 198, 117, 29);
		frame.getContentPane().add(btnQuit);
		
		JLabel lblGoogleSearchEngine = new JLabel("Google Search Engine");
		lblGoogleSearchEngine.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblGoogleSearchEngine.setHorizontalAlignment(SwingConstants.CENTER);
		lblGoogleSearchEngine.setBounds(6, 6, 488, 21);
		frame.getContentPane().add(lblGoogleSearchEngine);
		
		JLabel lblAddsAWeb = new JLabel("Adds a Web Page to your searches.");
		lblAddsAWeb.setVerticalAlignment(SwingConstants.TOP);
		lblAddsAWeb.setBounds(135, 39, 234, 35);
		frame.getContentPane().add(lblAddsAWeb);
		
		JLabel lblListTheSearches = new JLabel("List the pages on which a word occurs.");
		lblListTheSearches.setVerticalAlignment(SwingConstants.TOP);
		lblListTheSearches.setBounds(135, 80, 359, 35);
		frame.getContentPane().add(lblListTheSearches);
		
		JLabel lblFindingTheWords = new JLabel("Finds the word and prints the number of occurances.");
		lblFindingTheWords.setBounds(135, 121, 359, 16);
		frame.getContentPane().add(lblFindingTheWords);
		
		JLabel lblRemovesTheWeb = new JLabel("Removes the Web Page from your list of indexed pages.");
		lblRemovesTheWeb.setBounds(135, 162, 359, 16);
		frame.getContentPane().add(lblRemovesTheWeb);
		
		JLabel lblQuitsAndRemoves = new JLabel("Quits and removes all data.");
		lblQuitsAndRemoves.setBounds(135, 203, 234, 16);
		frame.getContentPane().add(lblQuitsAndRemoves);
	}
	
	/** This method initializes the JTextArea
	 *  used for the List and Find methods of
	 *  the GUI. This JFrame does implement
	 *  a WindowListener, which allows for
	 *  certain actions to occur before the frame
	 *  closes. The frame also includes a ScrollPane,
	 *  in case the list gets too large to fit on
	 *  the screen.
	 */
	public void intializeTextArea()
	{
		textFrame = new JFrame();
		textFrame.setSize(300,300);
		textFrame.setLocationRelativeTo(null);
		textFrame.setLayout(null);
		textFrame.getContentPane().setLayout(null);
		
		textArea = new JTextArea();
		textArea.setSize(300,300);
		textArea.setEditable(false);
		
		JScrollPane scroll = new JScrollPane (textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setSize(300,300);
	    textFrame.getContentPane().add(scroll);
		
		textFrame.addWindowListener(new java.awt.event.WindowAdapter() 
		{
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) 
		    {
		        textArea.setText(null);
		    }
		});
	}
}
