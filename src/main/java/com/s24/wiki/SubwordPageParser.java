package com.s24.wiki;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.s24.wiki.utils.WikiTextUtils;

import edu.jhu.nlp.wikipedia.WikiPage;

public class SubwordPageParser extends PageParser {

   public SubwordPageParser(PageParserCallback cb, ILanguageProfile helper) {
      super(cb, helper);
   }

   @Override
   public void parse(WikiPage page) {

      if (isValidPage(page)) {

    	 String title = WikiTextUtils.formatToken(page.getTitle());
         List<String> right = new ArrayList<String>();
         final List<String> left = new ArrayList<String>();

         PageParser grammar = new GrammarPageParser(new PageParserCallback() {

            @Override
            public void callback(List<String> l, List<String> r) {
               left.addAll(l);
            }
         }, getLanguageHelper());

         grammar.parse(page);
         right.addAll(left);
                           
         Iterator<String> words = getLanguageHelper().getSubWordsIterator(page.getWikiText(), title);
         while (words.hasNext()) {
        	 String w = words.next();
             if (!right.contains(w)) {
                 right.add(w);
              }
         }
         
         callback.callback(left, right);
      }
   }

//   @Override
//   protected String getName() {
//      return getLanguageHelper().getSubWordPageName(); // "Unterbegriffe";
//   }

}
