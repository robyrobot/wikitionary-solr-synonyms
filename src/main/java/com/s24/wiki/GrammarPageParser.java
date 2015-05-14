package com.s24.wiki;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.s24.wiki.utils.WikiTextUtils;

import edu.jhu.nlp.wikipedia.WikiPage;

public class GrammarPageParser extends PageParser {

   public GrammarPageParser(PageParserCallback cb, ILanguageProfile helper) {
      super(cb, helper);
   }

   @Override
   public void parse(WikiPage page) {

      if (isValidGrammarPage(page)) {
    	  String title = WikiTextUtils.formatToken(page.getTitle());
    	  
         List<String> left = new ArrayList<String>();
         List<String> right = new ArrayList<String>();
         left.add(title);
         right.add(title);

         Iterator<String> words = getLanguageHelper().getGrammarIterator(page.getWikiText(), title);
         while (words.hasNext()) {
        	 String w = WikiTextUtils.formatToken(words.next());
             if (!left.contains(w)) {
                 left.add(w);
              }
         }
         
         callback.callback(left, right);
      }

   }

//   @Override
//   protected String getName() {
//      return getLanguageHelper().getGrammarPageName(); //"Übersicht";
//   }

}
