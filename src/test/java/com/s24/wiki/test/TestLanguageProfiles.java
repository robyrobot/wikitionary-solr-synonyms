package com.s24.wiki.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.s24.wiki.profile.LanguageProfile_it;

public class TestLanguageProfiles {

	@Test
	public void testLanguageProfile_it() throws IOException {
		
		String txt = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("abbigliamento_wiki.txt"));
		LanguageProfile_it hlp = new LanguageProfile_it();
		Iterator<String> iter = hlp.getSubWordsIterator(txt, "abbigliamento");
		int i = 0;
		while (iter.hasNext()) {
			System.out.println(++i + ") " + iter.next());
		}
		
		assertEquals(18, i);
		
		iter = hlp.getGrammarIterator(txt, "abbigliamento");
		i = 0;
		while (iter.hasNext()) {
			System.out.println(++i + ") " + iter.next());
		}
		
		assertEquals(1, i);
	}
	
	@Test
	public void testLanguageProfile_it_abaceto() throws IOException {
		
		String txt = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("abaceto_wiki.txt"));
		LanguageProfile_it hlp = new LanguageProfile_it();
		Iterator<String> iter = hlp.getSubWordsIterator(txt, "abaceto");
		int i = 0;
		while (iter.hasNext()) {
			System.out.println(++i + ") " + iter.next());
		}
		
		assertEquals(0, i);
	}

}
