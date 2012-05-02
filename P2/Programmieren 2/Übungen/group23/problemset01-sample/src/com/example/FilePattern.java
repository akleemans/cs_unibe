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
 * @author omn
 *
 */
public class FilePattern implements FileFilter {

	private String pattern;

	public FilePattern(String string) {
		pattern = string;
	}

	public boolean accept(File file) {
		return match(pattern, file.getPath());
	}

	private boolean match(String glob, String fileName) {
		if (glob.isEmpty()) {
			return fileName.isEmpty();
		}
		// Non-empty glob, so handle first char, then recurse 
		if (glob.startsWith("*")) {
			return matchStar(glob.substring(1), fileName);
		}
		// First glob char is not *, so we cannot match empty string
		if (fileName.isEmpty()) {
			return false;
		}
		// Only fail if concrete chars do not match
		if ((!glob.startsWith("?")) && (glob.charAt(0) != fileName.charAt(0))) {
			return false;
		}
		// Recurse now. NB: substrings may now be empty
		return match(glob.substring(1), fileName.substring(1));
	}

	private boolean matchStar(String rest, String fileName) {
		// Be greedy and match as much as possible.
		// If the match fails, try being less greedy.
		for (int i = fileName.length(); i >= 0; i--) {
			if (match(rest, fileName.substring(i))) {
				return true;
			}
		}
		return false;
	}
    
}
