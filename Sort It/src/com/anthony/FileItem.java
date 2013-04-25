package com.anthony;

import java.io.File;

public class FileItem {

	private static File file;
	
	private boolean performDelete;
	private double fileSize;
	
	public FileItem(File aFile) {
		super();
		
		file = aFile;
	}
	
	public boolean getDelete() {
		return performDelete;
	}
	
	public void setDelete(boolean delete) {
		performDelete = delete;
	}
	
	public void calcFileSize() {
		
	}
	
}
