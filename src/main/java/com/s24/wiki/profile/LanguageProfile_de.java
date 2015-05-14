package com.s24.wiki.profile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.s24.wiki.ILanguageProfile;

public class LanguageProfile_de implements ILanguageProfile {

	@Override
	public boolean isDictionaryPage(String text, String title) {		
		String t = title.trim();
	    return text.contains("{{Wortart|Substantiv|Deutsch}}") && t.length() > 3
	    && !t.contains(":") && !t.contains("-")
	    && !t.contains(" ");
	}

	@Override
	public boolean isValidGrammarPage(String wikiText, String title) {
		Pattern pattern = Pattern.compile("\\{\\{Überarbeiten\\|[^\\}]*" + "Übersicht");
		Matcher matcher = pattern.matcher(wikiText);
		return !matcher.find();
	}
	
	@Override
	public boolean isValidSubwordPage(String wikiText, String title) {
		Pattern pattern = Pattern.compile("\\{\\{Überarbeiten\\|[^\\}]*" + "Unterbegriffe");
		Matcher matcher = pattern.matcher(wikiText);
		return !matcher.find();
	}
	
	@Override
	public Iterator<String> getSubWordsIterator(String wikiText, String title) {

		List<String> words = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\\{\\{Unterbegriffe\\}\\}\n(:.*\n)+");
		Matcher matcher = pattern.matcher(wikiText);
			  
		if (matcher.find()) {	
			Pattern wordpattern = Pattern.compile("\\[\\[(\\p{Upper}[\\p{L}]+)\\]\\]");
			Matcher wordmatcher = wordpattern.matcher(matcher.group());
	
			int end = 0;
	
			while (wordmatcher.find(end)) {
				String item = wordmatcher.group(1).toLowerCase();
				words.add(item);
				end = wordmatcher.end();
			}
		}
		
		return words.iterator();
	}

	@Override
	public Iterator<String> getGrammarIterator(String wikiText, String title) {
		
		List<String> words = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile("\\{\\{.*\\n(\\|.*\\n){8}");
		Matcher matcher = pattern.matcher(wikiText);
       
		if (matcher.find()) {
			
			Pattern wordpattern = Pattern.compile("\\|[NGDA][a-zA-Z\\s]+=.*?\\s(\\p{Upper}[\\p{L}]+)");
			Matcher wordmatcher = wordpattern.matcher(matcher.group());
			int end = 0;

			while (wordmatcher.find(end)) {
				String item = wordmatcher.group(1).toLowerCase();
				words.add(item);
				end = wordmatcher.end();
			}       
		}
				
		return words.iterator();		
	}

	@Override
	public String getLanguageCode() {
		return "de";
	}	
}
