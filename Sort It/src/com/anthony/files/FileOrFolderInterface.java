package com.anthony.files;

public abstract class FileOrFolderInterface {

	protected String nameOfEntity;
	protected String fullPath;
	protected String parsedSizeOfEntity;
	protected long realSizeOfEntity;
	
	protected boolean isEmpty;
	protected boolean toDelete;
	
	// Get and Set methods
	
	public void nameOfEntity(String name) {
		nameOfEntity = name;
	}

	public String nameOfEntity() {
		return nameOfEntity;
	}

	public String parsedSizeOfEntity() {
		return parsedSizeOfEntity;
	}

	public void realSizeOfEntity(long size) {
		realSizeOfEntity = size;
	}

	public long realSizeOfEntity() {
		return realSizeOfEntity;
	}

	public void toDelete(boolean delete) {
		toDelete = delete;
	}

	public boolean toDelete() {
		return toDelete;
	}
	
	public void fullPath(String path) {
		fullPath = path;
	}

	public String fullpath() {
		return fullPath;
	}
	
	public abstract String movedName();
	
	public abstract void parsedSizeOfEntity(String size);
	
	public abstract void isEmpty(boolean empty);
	public abstract boolean isEmpty();
	
	public abstract boolean canBeOpened();
	
	// methods
	
	public abstract boolean isFolder();
	public abstract boolean isFile();
	
	@Override
	public String toString() {
		return nameOfEntity();
	}
	
}
