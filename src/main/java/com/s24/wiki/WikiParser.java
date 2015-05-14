package com.s24.wiki;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.jhu.nlp.wikipedia.PageCallbackHandler;
import edu.jhu.nlp.wikipedia.WikiPage;
import edu.jhu.nlp.wikipedia.WikiXMLParser;
import edu.jhu.nlp.wikipedia.WikiXMLParserFactory;

public class WikiParser {

   List<PageParser> parser;
   ILanguageProfile helper;

   public WikiParser(ILanguageProfile hlp) {
      parser = new ArrayList<PageParser>();
      helper = hlp;
   }

   public void parse(String filename) {
      try {
         WikiXMLParser wxsp = WikiXMLParserFactory.getSAXParser(filename);
         wxsp.setPageCallback(new PageCallbackHandler() {
            public void process(WikiPage page) {
               if (!page.isSpecialPage() && !page.isRedirect() && helper.isDictionaryPage(page.getWikiText(), page.getTitle())) {
                  Iterator<PageParser> iter = parser.iterator();

                  while (iter.hasNext()) {
                     iter.next().parse(page);
                  }

               }

            }
         });

         wxsp.parse();

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void addParser(PageParser p) {
      parser.add(p);
   }
}
