package com.s24.wiki.test;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.security.MessageDigest;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.s24.wiki.PropertyManager;
import com.s24.wiki.SolrSynonyms;

public class TestSolrSynonym {

	private String getMD5FromFile(String filename) throws Throwable {
		byte[] content = IOUtils.toByteArray(new FileReader(filename));
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(content);
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		
		return sb.toString();
	}
	
	@Test
	public void testSolrSynonymBuildDe() throws Throwable {
		// test considering the md5 of the German version files
		String md5expected_subword = "f9be91ac4d0c889bde6959cb4c0749db";	
		String md5expected_stemm = "18789b4136319bc7b8322a1e5863cef6";
		System.setProperty(PropertyManager.WORDS_TRANSITIVE, "true");
		SolrSynonyms.main(new String[]{"/wiki_dump/dewiktionary-20150407-pages-meta-current.xml", "com.s24.wiki.profile.LanguageProfile_de"});
		
		assertEquals(md5expected_subword, getMD5FromFile("de.subword.txt"));
		assertEquals(md5expected_stemm, getMD5FromFile("de.stem.txt"));		
	}
	
	@Test
	public void testSolrSynonymBuildIt_non_transitive() throws Throwable {
		String md5expected_subword = "e63f31ec03e49349f9ecc97c22a4bafb";	
		String md5expected_stemm = "94ab17a0b5e3f83358ae8e7bdd810b45";
		System.setProperty(PropertyManager.WORDS_TRANSITIVE, "false");

		SolrSynonyms.main(new String[]{"/wiki_dump/itwiktionary-20150331-pages-meta-current.xml", "com.s24.wiki.profile.LanguageProfile_it"});
		
		assertEquals(md5expected_subword, getMD5FromFile("it.subword.txt"));
		assertEquals(md5expected_stemm, getMD5FromFile("it.stem.txt"));
	}
	
	@Test
	public void testSolrSynonymBuildIt_transitive() throws Throwable {
		String md5expected_subword = "e63f31ec03e49349f9ecc97c22a4bafb";	
		String md5expected_stemm = "94ab17a0b5e3f83358ae8e7bdd810b45";
		System.setProperty(PropertyManager.WORDS_TRANSITIVE, "true");

		SolrSynonyms.main(new String[]{"/wiki_dump/itwiktionary-20150331-pages-meta-current.xml", "com.s24.wiki.profile.LanguageProfile_it"});
		
		assertEquals(md5expected_subword, getMD5FromFile("it.subword.txt"));
		assertEquals(md5expected_stemm, getMD5FromFile("it.stem.txt"));		

	}

}
