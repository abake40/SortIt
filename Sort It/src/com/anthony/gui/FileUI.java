package com.anthony.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.anthony.FileManager;
import com.anthony.TableController;
import com.anthony.files.FileOrFolderInterface;
import com.anthony.files.SortOperations;

public class FileUI extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FileManager manager;
	private TableController tableControl;
	
	private JFileChooser browse;
	
	private JLabel searchLabel;
	private JLabel totalLabel;
	private JButton browseFileSystem;
	private JButton searchButton;
	private JButton exitButton;
	private JButton aboutButton;
	private JTextField fileLoc;
	private JScrollPane folderData;
	private JProgressBar searchProcess;
	
	private Container content;

	
	public static void main(String[] args) {
		
		new FileUI();
	}
	
	public FileUI()
	{
		
		super();
		
		manager = new FileManager();
		
		// load proeprties
		Properties props = new Properties();
		
		try {
			props.load(new FileInputStream("config.properties"));
			System.out.println(props.getProperty("def.folder.location"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setTitle("Download File Manager");
		this.getContentPane().setLayout(new FlowLayout());
		
		searchLabel = new JLabel("Path");
		totalLabel = new JLabel("Total -");
		
		browse = new JFileChooser();
		browse.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		browse.setMultiSelectionEnabled(false);
		
		browseFileSystem = new JButton("Browse");
		browseFileSystem.setToolTipText("Browse");
		browseFileSystem.setActionCommand("BROWSE");
		browseFileSystem.addActionListener(this);
		
		searchButton = new JButton("Search");
		searchButton.setToolTipText("Search");
		searchButton.setActionCommand("SEARCH");
		searchButton.addActionListener(this);
		//searchButton.setEnabled(false); -- DEBUG
		
		aboutButton = new JButton("About");
		aboutButton.setToolTipText("About");
		aboutButton.setActionCommand("ABOUT");
		aboutButton.addActionListener(this);
		
		exitButton = new JButton("Exit");
		exitButton.setToolTipText("Exit");
		exitButton.setActionCommand("EXIT");
		exitButton.addActionListener(this);
		
		fileLoc = new JTextField();
		fileLoc.setText(props.getProperty("def.folder.location"));
		fileLoc.setToolTipText("Press the browse button to find a folder");
		
		 // formatting
		fileLoc.setLocation(100, 15);
		fileLoc.setSize(350, 30);
		browseFileSystem.setLocation(20, 50);
		browseFileSystem.setSize(100, 30);
		searchButton.setLocation(130, 50);
		searchButton.setSize(100, 30);
		searchLabel.setLocation(25, 15);
		searchLabel.setSize(80, 30);
		totalLabel.setLocation(25, 780);
		totalLabel.setSize(350, 20);
		exitButton.setSize(100, 30);
		exitButton.setLocation(240, 50);
		aboutButton.setSize(100, 30);
		aboutButton.setLocation(350, 50);
		
		totalLabel.setVisible(false);
		
		content = this.getContentPane();
		
		content.add(fileLoc);
		content.add(browseFileSystem);
		content.add(searchButton);
		content.add(searchLabel);
		content.add(totalLabel);
		content.add(exitButton);
		content.add(aboutButton);
		
		this.setLayout(null);
		this.setSize(510, 830);
		this.setVisible(true);
		this.setResizable(false);
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void actionPerformed(ActionEvent event)
	{
		String actionCommand = event.getActionCommand();
		
		if (actionCommand.equals("BROWSE")) {
			int returnVal = browse.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				fileLoc.setText(browse.getSelectedFile().getPath());
				searchButton.setEnabled(true);
			}
		}
		
		if (actionCommand.equals("SEARCH")) {
			Thread newThread = new Thread(new Runnable() {
	
				public void run() {
					
					manager.folderLocation(fileLoc.getText());
					
					if (fileLoc.getText().equals("")) {
						errorHasOccured("Please enter a directory.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					int checkError = manager.searchFolder();
					
					if (checkError == 0) {
						errorHasOccured("The location - " + fileLoc.getText() + " has caused an error, please try again.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if (folderData!=null) {
						content.remove(folderData);
						folderData = null;
					}
					
					folderData = generateTable();
					content.add(folderData);

					totalLabel.setText(SortOperations.sizeParser(manager.totalSize()) + " in total.");
					totalLabel.setVisible(true);
					
				}
			});
			
			searchProcess = new JProgressBar(0, 10);
			searchProcess.setStringPainted(true);
			searchProcess.setValue(0);
			searchProcess.setLocation(30, 480);
			content.add(searchProcess);
			searchProcess.setVisible(true);
			newThread.run();
			
		}
		
		if (actionCommand.equals("EXIT")) {
			System.exit(0);
		}
		
		if (actionCommand.equals("ABOUT")) {
			String aboutText = "Download File Manager Alpha v0.1\nAuthor: Anthony Baker\nContact: abake40@gmail.com";
			errorHasOccured(aboutText, "About", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	public JScrollPane generateTable()
	{
		
		String[] columnNames = {"Location", "Size", "Empty"};
		JTable tempTable = new JTable();
		
		@SuppressWarnings("serial")
		DefaultTableModel tableModel = new DefaultTableModel(manager.generateData(), columnNames) {
			
			@Override
			public boolean isCellEditable(int row, int column)
			{
				if (column==2) return true;
				
				return false;
			}
			
		}; 
		
		tempTable.setModel(tableModel);
		
		tableControl = new TableController(tempTable);
		tempTable.getSelectionModel().addListSelectionListener(tableControl);
		tempTable.getColumnModel().getSelectionModel().addListSelectionListener(tableControl);
		
		tempTable.setFillsViewportHeight(true);
		
		// double click event
		tempTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2) {
					JTable table = (JTable) e.getSource();
					int row = table.getSelectedRow();
					
					FileOrFolderInterface selected = (FileOrFolderInterface) table.getValueAt(row, 0);
					
					if (selected.canBeOpened()) {
						new FileDetailUI(selected);	
					}
				}					
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(tempTable);
		scrollPane.setSize(450, 690);
		scrollPane.setLocation(30, 85);
		
		tempTable.getColumnModel().getColumn(0).setPreferredWidth(290);
		tempTable.getColumnModel().getColumn(1).setPreferredWidth(90);
		tempTable.getColumnModel().getColumn(2).setPreferredWidth(70);
		
		return scrollPane;
	}
	
	public void errorHasOccured(String theString, String title, int typeOfMessage)
	{
		JOptionPane.showMessageDialog(null, theString, title, typeOfMessage, null);
	}
	
	
}
