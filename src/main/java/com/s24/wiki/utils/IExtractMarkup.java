package com.s24.wiki.utils;

public interface IExtractMarkup {

	/**
	 * return true if the declared markup is a regular expression pattern
	 * @return
	 */
	public boolean isRegex();
	
	/**
	 * returns the markup
	 * @return
	 */
	public String getMarkup();
}
