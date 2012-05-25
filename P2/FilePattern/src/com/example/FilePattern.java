package com.example;

import java.io.File;
import java.io.FileFilter;

/** Filters file names using command-line wildcards.
 * <P>
 * <tt>*</tt> matches any number of characters.
 * <tt>?</tt> matches exactly one characters.
 * <P>
 * <b>Example:</b> 
 * <tt>*.txt</tt> matches all text files;  
 * <tt>stoya??.jpg</tt> matches all images that start
 * with <i>stoya</i> and got two more characters. 
 * 
 * @author Adrianus Kleemans & Rafael Breu
 *
 */

public class FilePattern implements FileFilter {
	
	private String fileFilterName;
	
	public FilePattern (String fileFilterNameImput){
		fileFilterName = fileFilterNameImput;		
	}
	
	public boolean accept(File pathname){
		if (fileFilterName.equals(pathname.getName()))
			return true;
		else if (fileFilterName.equals("*"))
			return true;
		else
			return false;
	}
	
	
}
