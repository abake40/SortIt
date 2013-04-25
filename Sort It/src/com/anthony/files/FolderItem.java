package com.anthony.files;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class FolderItem extends FileOrFolderInterface {
	
	protected String movedName;
	
	@Override
	public void parsedSizeOfEntity(String size) {
		parsedSizeOfEntity = size;
		
		if (size.equalsIgnoreCase("0.0 MB") || size.equalsIgnoreCase("0.01 MB")) {
			isEmpty(true);
			return;
		}
		
		boolean hasFilesOfInterest = false;
		
		Iterator<File> filesInFolder = returnFilesInFolder();
		while (filesInFolder.hasNext()) {
			File next = filesInFolder.next();
			
			if (!StringUtils.containsIgnoreCase(next.getName(), "sample")) {
				hasFilesOfInterest = true;
			}
		}
		
		if (!hasFilesOfInterest) {
			isEmpty(true);
		}
		
	}

	@Override
	public boolean isFolder() {
		return true;
	}

	@Override
	public boolean isFile() {
		return false;
	}
	
	@Override
	public void isEmpty(boolean empty) {
		isEmpty = empty;
	}

	@Override
	public boolean isEmpty() {
		return isEmpty;
	}

	@Override
	public boolean canBeOpened() {
		return true;
	}
	
	@Override
	public String movedName() {
		
		if (movedName == null) {
			movedName = SortOperations.guestimateNameVideo(nameOfEntity());
		}
		
		return movedName;
	}
	
	public Iterator<File> returnFilesInFolder() {
		
		File files = new File(fullpath());
		return FileUtils.iterateFiles(files, SortOperations.returnVideoExtensions(), true);
		
	}
	


}
