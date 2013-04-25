package com.anthony.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.anthony.files.FileOrFolderInterface;

public class FileDetailUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private Container content;
	
	private String folder;
	private String fileLocation;
	
	// field headers
	private JLabel titleFileName;
	private JLabel titleFileSize;
	private JLabel titleFileNameChange;
	
	// display fields
	
	private JLabel fileName;
	private JLabel definition;
	private JLabel fileSize;
	private JLabel fileType;
	
	private JTextField nameChangeField;
	
	private JButton exitBtn;
	private JButton deleteBtn;
	private JButton moveBtn;
	
	public FileDetailUI(FileOrFolderInterface item) {
		
		super();

		this.setTitle(item.nameOfEntity());
		this.getContentPane().setLayout(new FlowLayout());
		
		this.setLayout(null);
		this.setSize(510, 400);
		this.setVisible(true);
		this.setResizable(false);
		
		content = this.getContentPane();
		
		titleFileName = new JLabel("File Name");
		titleFileSize = new JLabel("File Size");
		titleFileNameChange = new JLabel("New File Name");
		
		fileName = new JLabel(item.nameOfEntity());
		fileSize = new JLabel(item.parsedSizeOfEntity());
		nameChangeField = new JTextField(item.movedName());
		
		titleFileName.setLocation(10, 10);
		titleFileSize.setLocation(10, 50);
		titleFileNameChange.setLocation(10, 90);
		
		fileName.setLocation(120, 10);
		fileSize.setLocation(120, 50);
		nameChangeField.setLocation(120, 90);
		
		titleFileName.setSize(100, 20);
		titleFileSize.setSize(100, 20);
		titleFileNameChange.setSize(100, 20);
		
		fileName.setSize(200, 20);
		fileSize.setSize(80, 20);
		nameChangeField.setSize(200, 20);
		
		exitBtn = new JButton("Close");
		exitBtn.addActionListener(this);
		exitBtn.setActionCommand("CLOSEWINDOW");
		exitBtn.setSize(100, 30);
		exitBtn.setLocation(10, 330);
		
		deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(this);
		deleteBtn.setActionCommand("DELETEFILE");
		deleteBtn.setSize(100, 30);
		deleteBtn.setLocation(400, 330);
		
		moveBtn = new JButton("Move");
		moveBtn.addActionListener(this);
		moveBtn.setActionCommand("MOVEFILE");
		moveBtn.setSize(100, 30);
		moveBtn.setLocation(290, 330);
		
		content.add(exitBtn);
		content.add(deleteBtn);
		content.add(moveBtn);
		
		content.add(titleFileName);
		content.add(titleFileSize);
		content.add(titleFileNameChange);
		
		content.add(fileName);
		content.add(fileSize);
		content.add(nameChangeField);
			
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if (actionCommand.equals("CLOSEWINDOW")) {
			this.dispose();
		}
	}

}
