package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import components.FilePanel;
import components.MenuBar;
import components.SearchPanel;
import components.DisplayPanel;
import model.Item;

/**
 * ManualGUI is the GUI for the HomeManual app.
 * 
 * @author Andrew Lim, Anthony Nguyen, Darryl James, Tyke Sykes
 * @version 2 May 2020
 */
public class ManualGUI extends JFrame {
	
    /**default serial id */
	private static final long serialVersionUID = 1L;

	// constants to capture screen dimensions
    /** A ToolKit. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    
    /** The Dimension of the screen. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    
    
	private final static String TITLE = "Homeowner's Manual";
	

	/**
	 * The GUI JFrame.
	 */
	ManualGUI() {
		super(TITLE);
		
		setJMenuBar(new MenuBar(this));
		initGUI();
	}
	
	/**
	 * Reads all the Items from the input file
	 * so they can be displayed on 
	 * the application.
	 * 
	 * @author Darryl James
	 * @return A List of test items to search for
	 */
	private ArrayList<Item> getItems() {
		ArrayList<Item> allItems = new ArrayList<Item>();
		
		try(BufferedReader br = new BufferedReader(new FileReader("files/testItemFile.txt"))) {
			String line = br.readLine();
			
			while(line != null) {
				String[] temp = line.split(", ");
				String name = temp[0];
				String file = temp[1];
				String[] tags = temp[2].split(" ");
				
				Item A = new Item(name, new File(file));
				for (String tag : tags) A.addTag(tag);
				allItems.add(A);
				
				line = br.readLine();
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return allItems;	
	}
	
	/**
	 * Initializes the GUI with all its components and panels. 
	 * @author Anthony
	 */
	private void initGUI() {
		
		// Test Item Stuff
		ArrayList<Item> allItems = getItems();
		// Test Item Stuff 
		
		setLayout(new BorderLayout());
		
		//main display for the manual
		final DisplayPanel displayPanel = new DisplayPanel();

		final FilePanel filePanel = new FilePanel();
		filePanel.setLayout(new BoxLayout(filePanel, WIDTH));

		// Makes the Scroll Bar appear and resizes its width
		final JScrollPane scrollPane = new JScrollPane(filePanel);	
        final JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setPreferredSize(new Dimension(12, 12));

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setVerticalScrollBar(scrollBar);
        
		final SearchPanel searchPanel = new SearchPanel(filePanel, displayPanel);
		searchPanel.setSize(this.getWidth() / 3 , this.getHeight() / 8);
		searchPanel.attachList(allItems);
					
		//TODO: ADD FILE SYSTEM
		//left file display system
		final JPanel westPanel = new JPanel(new BorderLayout());
		westPanel.add(searchPanel, BorderLayout.NORTH);		
		westPanel.add(scrollPane);
				
		final JPanel masterPanel = new JPanel(new BorderLayout());
		masterPanel.add(displayPanel, BorderLayout.CENTER);
		masterPanel.add(westPanel, BorderLayout.WEST);

		add(masterPanel);
		pack();
   	 	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize((int) (SCREEN_SIZE.width / 2), (int) Math.round(SCREEN_SIZE.height * 0.75));
   	 	setVisible(true);
	}
	

}
