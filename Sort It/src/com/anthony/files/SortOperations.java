package com.anthony.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public final class SortOperations {

	private static final String[] VIDEO_EXTENSIONS =  {"mkv", "avi"};
	private static final String[] AUDIO_EXTENSIONS =  {"mp3", "flac"}; 
	
	public static String[] returnVideoExtensions() {
		return VIDEO_EXTENSIONS;
	}
	
	public static File findVideoFiles(String path) throws FileNotFoundException {
		
		File returnFile = null;
		Collection<File> foundFiles = FileUtils.listFiles(new File(path), VIDEO_EXTENSIONS, true);
		
		// No File Found
		if (foundFiles.size()==0) {
			throw new FileNotFoundException("No video files could be found.");
		}
		
		Iterator<File> iterator = foundFiles.iterator();
		
		while (iterator.hasNext()) {
			File foundFile = iterator.next();
			// Retrieve the largest file
			if (returnFile == null || foundFile.length() > returnFile.length())	{
				returnFile = foundFile;
			}
		}
		
		System.out.println("Found File - " + returnFile.getName() + " \nSize: " + (FileUtils.sizeOf(returnFile) / FileUtils.ONE_MB) + " MB");
		//estimatedName = guestimateNameVideo();
		
		return returnFile;
		
	}
	
	public static String guestimateNameVideo(String filename)
	{
		String extension = FilenameUtils.getExtension(filename);
		
		String definition;
		
		if (StringUtils.contains(filename, "720")) {
			definition = "720p";
		} else if (StringUtils.contains(filename, "1080")) {
			definition = "1080p";
		} else {
			return "";
		}
		
		filename = StringUtils.replaceChars(filename, '.', ' ');
		filename = StringUtils.substringBefore(filename, definition);
		filename = filename.trim();
		
		filename = filename + " - " + definition + "." + extension;
		
		System.out.println(filename);
		
		return filename;
	}
	
	public static String sizeParser(double size) {
		
		// raw sizes for the file lengths
		DecimalFormat twoDigits = new DecimalFormat("#.##");
		String parsedSize;
		
		size = (size / FileUtils.ONE_MB);	// Conversion to megabytes
		
		if (size > 1024) { // Is it over a GB?
			size = (size / 1024);
			parsedSize = Double.valueOf(twoDigits.format(size)) + " GB";
		} else {
			parsedSize = Double.valueOf(twoDigits.format(size)) + " MB";
		}
		
		return parsedSize;
		
	}
	
	
	
}
