package com.s24.wiki.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.s24.wiki.PropertyManager;

public class WikiTextUtils {
		
	private static final Logger log = LoggerFactory.getLogger(WikiTextUtils.class);
		
	public static String formatToken(String s) {
		s = s.trim();
		s = PropertyManager.isLowercaseEnabled()? s.toLowerCase() : s;
		return s;
	}
	
	/**
	 * extract a section from a wikitext
	 * @param startMarkup
	 * markup strategy to detect the session start
	 * @param endMarkup
	 * markup strategy to detect the session end
	 * @return
	 */
	public static String extractWikiSection(String wikiText, IExtractMarkup startMarkup, IExtractMarkup endMarkup) {
		
		String section = "";
		if (startMarkup == null || endMarkup == null) {
			throw new IllegalArgumentException("markups cannot be null!");
		}
		
		if (wikiText == null || wikiText.length() <= 1) {
			throw new IllegalArgumentException("wikitext null or too small: " + wikiText);
		}
			
		int startSection = 0;
		if (startMarkup.isRegex()) {
			Matcher matcher = Pattern.compile(startMarkup.getMarkup()).matcher(wikiText);
			if (matcher.find()) {
				startSection = matcher.end();
			}
		}
		else {
			String mk = startMarkup.getMarkup();
			startSection = wikiText.indexOf(mk);
			startSection = (startSection > 0)? startSection + mk.length() : startSection;			
		}
						
		if (startSection > 0 && startSection < wikiText.length()) {			
			String tmpSection = wikiText.substring(startSection);
			
			// test ending with another section start tag
			int endSection = 0;
			if (endMarkup.isRegex()) {
				Matcher matcher = Pattern.compile(endMarkup.getMarkup()).matcher(tmpSection);
				if (matcher.find()) {
					endSection = matcher.end() + startSection;
				}
			}
			else {
				String mk = endMarkup.getMarkup();
				endSection = wikiText.indexOf(mk, startSection);
			}
				
			// if ending of the section not identified try different separation token
			// 1) test for double newline
			endSection = (endSection == -1)? wikiText.indexOf("\n\n", startSection) : endSection;
			
			// 2) test ending with the end of the page
			endSection = (endSection == -1)? wikiText.length() - 1 : endSection;
			
			// extract the section to be returned
			section = wikiText.substring(startSection, endSection);		
		}
		
		return section;
	}
}
