package com.anthony;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.anthony.files.FileItem;
import com.anthony.files.FileOrFolderInterface;
import com.anthony.files.FolderItem;
import com.anthony.files.SortOperations;

public class FileManager {

	private String folderLocation;

	private HashMap<String, FileOrFolderInterface> itemsFound;
	
	private double totalSize;
	private int totalFiles;
	
	public FileManager(String location) {
		folderLocation = location;
		itemsFound = new HashMap<String, FileOrFolderInterface>();
	}
	
	public FileManager() {
		itemsFound = new HashMap<String, FileOrFolderInterface>();
	}
	
	/**
	 * Set method for the folderLocation String
	 * @param newLocation String to set the folderLocation to a new location.
	 */
	public void folderLocation(String newLocation) {
		folderLocation = newLocation;
	}
	
	/**
	 * Get method for the folderLocation String
	 * @return Returns the folderLocation String
	 */
	public String folderLocation() {
		return folderLocation;
	}
	
	public int searchFolder() {
		
		try {
		
			totalSize = 0;
				
			File folderLoc = new File(folderLocation);
			File[] fileList = folderLoc.listFiles();
			
			System.out.println("Found "+ fileList.length +" files.. Processing.");
			

			
			// work out the folder sizes for each of the folders.
			for (int j = 0; fileList.length > j; j++) {
				
				long rawSize;
				String parsedSize;
				
				if (fileList[j].isDirectory()) {
					rawSize = FileUtils.sizeOfDirectory(fileList[j]);
				} else {	
					rawSize = fileList[j].length();
				}
				
				parsedSize =  SortOperations.sizeParser(rawSize);
				createFileOrFolder(fileList[j].getName(), parsedSize, rawSize);
				
				totalSize += rawSize;
		}
			
		totalFiles = itemsFound.size();
	
		} catch (NullPointerException error)
		{
			System.out.println("The location \""+ folderLocation() + "\" doesn't exist.");
			
			StackTraceElement stackTrace[] = Thread.currentThread().getStackTrace();
			
			for (int i = 0; i > stackTrace.length; i++)
			{
				System.out.println(stackTrace[i].toString());
			}
			return 0;
		}
		
		return 1;
		
	}
	

	
	/**
	 * Used to create the arrays used by the TableView
	 * @return an Object array containing the sizes and folders for the 
	 */
	public Object[][] generateData()
	{
		int i = 0;
		
		HashMap<String, FileOrFolderInterface> list = itemsFound();
		Object[][] tableData = new Object[list.size()][3];
		
		for (FileOrFolderInterface item : list.values()) {
			
			tableData[i][0] = item;
			tableData[i][1] = item.parsedSizeOfEntity();
			
			if (item.isEmpty()) tableData[i][2] = new String("Empty");
			
			i++;
		}
		return tableData;
	}
	
	private void createFileOrFolder(String name, String parsedSize, long realSize) {
		
		HashMap<String, FileOrFolderInterface> list = itemsFound();
		
		FileOrFolderInterface foundItem;
		
		// check that the last char is a backslash
		if (folderLocation.charAt(folderLocation.length()-1) != IOUtils.DIR_SEPARATOR)  {
			folderLocation = folderLocation + IOUtils.DIR_SEPARATOR;
		}
		
		File found = new File(folderLocation+name);
		
		if (found.isDirectory()) foundItem = new FolderItem();
		else foundItem = new FileItem();
		
		foundItem.nameOfEntity(name);
		foundItem.fullPath(folderLocation+name);
		foundItem.parsedSizeOfEntity(parsedSize);
		foundItem.realSizeOfEntity(realSize);
		
		list.put(name, foundItem);
		
	}
	
	private HashMap<String, FileOrFolderInterface> itemsFound() {
		return itemsFound;
	}
	
	public double totalSize()
	{
		return totalSize;
	}
	
	public int totalFiles()
	{
		return totalFiles;
	}
}
