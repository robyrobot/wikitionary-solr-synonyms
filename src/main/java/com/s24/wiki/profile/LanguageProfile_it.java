package com.s24.wiki.profile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.s24.wiki.ILanguageProfile;
import com.s24.wiki.utils.IExtractMarkup;
import com.s24.wiki.utils.WikiTextUtils;

public class LanguageProfile_it implements ILanguageProfile {

	private static final Logger log = LoggerFactory.getLogger(LanguageProfile_it.class);
	
	private static final Pattern badChar = Pattern.compile("[\\*#\\|,-]+");
	private static final Pattern patternSubWord = Pattern.compile("\\[\\[([^\\]\\]]+)\\]\\]");
	private static final Pattern plurals = Pattern.compile("\\{\\{Linkp\\|(.+)\\}\\}");
	private static final Pattern itSection = Pattern.compile("\\{\\{.+\\|(?:it)?\\}\\}");
	
	@Override
	public boolean isDictionaryPage(String wikiText, String title) {		
		String t = title.trim();
	    return wikiText.contains("== {{-it-}} ==") && t.length() > 3
	    && !t.contains(":") && !t.contains("-")
	    && !t.contains(" ");
	}

	@Override
	public boolean isValidSubwordPage(String wikiText, String title) {
		Matcher matcher = itSection.matcher(wikiText);
		return matcher.find();
	}
	
	@Override
	public boolean isValidGrammarPage(String wikiText, String title) {
		Matcher matcher = itSection.matcher(wikiText);
		return matcher.find();
	}
						
	@Override
	public Iterator<String> getGrammarIterator(String wikiText, String title) {		
		List<String> words = new ArrayList<String>();
		
		// extract italian section
		String itSection = WikiTextUtils.extractWikiSection(wikiText, new IExtractMarkup() {
			
			@Override
			public boolean isRegex() {
				return true;
			}
			
			@Override
			public String getMarkup() {
				return "\\{\\{.+\\|it\\}\\}";
			}
		}, new IExtractMarkup() {
			
			@Override
			public boolean isRegex() {
				return false;
			}
			
			@Override
			public String getMarkup() {
				return "{{-";
			}
		} );
		
		// search for plurals (if any)
		if (!itSection.isEmpty()) {
			Matcher wordmatcher = plurals.matcher(itSection);				
			int end = 0;			
			while (wordmatcher.find(end)) {
				String item = WikiTextUtils.formatToken(wordmatcher.group(1));
				if (item != null && !item.isEmpty() && !badChar.matcher(item).find() && !item.equalsIgnoreCase("inserisci qui voce al plurale")) {
					words.add(item);
				}
				
				end = wordmatcher.end();		
			}
		}
		
		return words.iterator();
	}

	@Override
	public Iterator<String> getSubWordsIterator(String wikiText, String title) {
		
		List<String> words = new ArrayList<String>();		
				
		// extract synonym region	
		String itSection = WikiTextUtils.extractWikiSection(wikiText, new IExtractMarkup() {
			
			@Override
			public boolean isRegex() {
				return true;
			}
			
			@Override
			public String getMarkup() {
				return "\\{\\{.+\\|it\\}\\}";
			}
		}, new IExtractMarkup() {
			
			@Override
			public boolean isRegex() {
				return false;
			}
			
			@Override
			public String getMarkup() {
				return "==";
			}
		});
						
		if (!itSection.trim().isEmpty()) {
			String synSection = WikiTextUtils.extractWikiSection(itSection, new IExtractMarkup() {
				
				@Override
				public boolean isRegex() {
					return false;
				}
				
				@Override
				public String getMarkup() {
					return "{{-sin-}}";
				}
			}, new IExtractMarkup() {
				
				@Override
				public boolean isRegex() {
					return false;
				}
				
				@Override
				public String getMarkup() {
					return "{{-";
				}
			});
				
			if (!synSection.trim().isEmpty()) {
				Matcher wordmatcher = patternSubWord.matcher(synSection);				
				int end = 0;			
				while (wordmatcher.find(end)) {
					String item = WikiTextUtils.formatToken(wordmatcher.group(1));
					if (item != null && !item.isEmpty() && !badChar.matcher(item).find()) {
						words.add(item);
					}
					end = wordmatcher.end();
				}
			}
		}
	
		return words.iterator();
	}

	@Override
	public String getLanguageCode() {
		return "it";
	}
		
}
