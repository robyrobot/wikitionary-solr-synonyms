package com.s24.wiki;

/**
 * manager for system and environments properties 
 * @author Roby
 *
 */
public class PropertyManager {

	// properties
	public static final String WORDS_TRANSITIVE = "wk2s.words.transitive";
	public static final String WORDS_LOWERCASE = "wk2s.words.lowercase";
	public static final String OUT_SUBWORD_FILE = "wk2s.subwordFile";
	public static final String OUT_STEM_FILE = "wk2s.stemmFile";	
	
	private static String toEnvFormat(String s) {
		return s.toUpperCase().replace('.', '_');
	}
	
	public static boolean isTransitiveEnabled() {
		String b = System.getProperty(WORDS_TRANSITIVE, System.getenv(toEnvFormat(WORDS_TRANSITIVE)));
		return (b != null)? Boolean.parseBoolean(b) : false;
	}
	
	public static boolean isLowercaseEnabled() {
		String b = System.getProperty(WORDS_LOWERCASE, System.getenv(toEnvFormat(WORDS_LOWERCASE)));
		return (b != null)? Boolean.parseBoolean(b) : true;
	}

	public static String getOutSubwordsFilename() {
		String s = System.getProperty(OUT_SUBWORD_FILE, System.getenv(toEnvFormat(OUT_SUBWORD_FILE)));
		return (s != null && !s.isEmpty())? s : "subword.txt"; 
	}
	
	public static String getOutSutemmFilename() {
		String s = System.getProperty(OUT_STEM_FILE, System.getenv(toEnvFormat(OUT_STEM_FILE)));
		return (s != null && !s.isEmpty())? s : "stem.txt"; 
	}
}
