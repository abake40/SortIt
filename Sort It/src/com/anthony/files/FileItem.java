package com.anthony.files;

public class FileItem extends FileOrFolderInterface {

	@Override
	public void parsedSizeOfEntity(String size) {
		parsedSizeOfEntity = size;
	}

	@Override
	public boolean isFolder() {
		return false;
	}

	@Override
	public boolean isFile() {
		return true;
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
		return false;
	}
	
	@Override
	public String movedName() {
		return nameOfEntity();
	}

}
