package com.s24.wiki;
import edu.jhu.nlp.wikipedia.WikiPage;

public abstract class PageParser {

   PageParserCallback callback;
   ILanguageProfile langHlp;
   public PageParser(PageParserCallback cb, ILanguageProfile helper) {
      callback = cb;
      langHlp = helper;
   }

   abstract public void parse(WikiPage page);

   final protected ILanguageProfile getLanguageHelper() {
	   return langHlp;
   }
   
   protected boolean isValidGrammarPage(WikiPage page) {
	   return langHlp.isValidGrammarPage(page.getWikiText(), page.getTitle());
   }
   
   protected boolean isValidPage(WikiPage page) {
	  return langHlp.isValidSubwordPage(page.getWikiText(), page.getTitle()); 
	  
   }

}
