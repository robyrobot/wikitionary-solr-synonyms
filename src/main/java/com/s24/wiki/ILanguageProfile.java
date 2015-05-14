package com.s24.wiki;

import java.util.Iterator;

/**
 * Abstract language profile for parsing different language wiki markup
 * @author Roby
 *
 */
public interface ILanguageProfile {

	/**
	 * test if the page is a valid dictionary entry (noun, verb abjective, ...)
	 * Use this test to filter for Help wiki page and special page that not contains important data
	 * @param wikiText
	 * the wikitext of the page
	 * @param title
	 * title of the page. May be useful in some cases
	 * @return
	 */
	boolean isDictionaryPage(String wikiText, String title);
	
	/**
	 * test if the page described by the wikitext provided is valid for parsing grammar markup
	 * Use this test to filter for Help wiki page and special page that not contains important data
	 * @param wikiText
	 * the wikitext of the page to be validated
	 * @param title
	 * title of the page. May be useful in some cases
	 * @return
	 * true if and only if the page is valid 
	 */
	boolean isValidGrammarPage(String wikiText, String title);
	
	/**
	 * test if the page described by the wikitext provided is valid for parsing synonym markup
	 * @param wikiText
	 * the wikitext of the page to be validated
	 * @param title
	 * title of the page. May be useful in some cases
	 * @return
	 * true if and only if the page is valid
	 */
	boolean isValidSubwordPage(String wikiText, String title);
			
	/**
	 * returns an iterator over the subwords or synonyms extracted from the input wikitext
	 * @param wikiText
	 * wiki text of the page to be parsed
	 * @param title
	 * title of the page. May be useful in some cases
	 * @return
	 * a String iterator over the extracted items
	 */
	Iterator<String> getSubWordsIterator(String wikiText, String title);
	

	/**
	 * returns an iterator over the grammar item (eg: plurals) extracted from the input wikitext
	 * @param wikiText
	 * @param wikiText
	 * wiki text of the page to be parsed
	 * @param title
	 * title of the page. May be useful in some cases
	 * @return
	 * a String iterator over the extracted items
	 */
	Iterator<String> getGrammarIterator(String wikiText, String title);
	
	/**
	 * returns the language code
	 * @return
	 */
	String getLanguageCode();
		
}
