package com.s24.wiki;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrSynonyms {

   private static final String stem = PropertyManager.getOutSutemmFilename();
   private static final String subword = PropertyManager.getOutSubwordsFilename();
   private static final Logger log = LoggerFactory.getLogger(SolrSynonyms.class);
      
   /**
    * retrieve the right instance of the language helper
    * @param lang
    * @return
    */
   private static ILanguageProfile getLanguageHelperClass(String name) {	   
	   ILanguageProfile instance = null;	   
	   Class<?> clazz;
	   try {
		   clazz = Class.forName(name);
	   } 
	   catch (ClassNotFoundException e1) {
		   throw new RuntimeException(String.format("Language helper '%s' class not found in classpath",name), e1);
	   }
	   
	   if (clazz != null) {
		   try {
			   instance = (ILanguageProfile) clazz.newInstance();
		   } 
		   catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("error instantiating class: " + clazz.getName(), e);
		   }
	   }
	   
	   return instance;
   }
   
   /**
    * @param args
    */
   public static void main(String[] args) {

	  // instantiate the helper based on the language code provider
	   if (args.length <= 1 || args[1].isEmpty()) {
		   throw new RuntimeException("missing language code");
	   }
	   
	   String dump = args[0];
	   ILanguageProfile profile = getLanguageHelperClass(args[1]);
	   
      WikiParser wp = new WikiParser(profile);

      final File stemout = new File(profile.getLanguageCode() + "." + stem);
      final File subwordout = new File(profile.getLanguageCode() + "." +subword);

      final Map<String, Pair<List<String>, List<String>>> subwortmap = new TreeMap<String, Pair<List<String>, List<String>>>();
      final Map<String, String> stemmap = new TreeMap<String, String>();

      wp.addParser(new SubwordPageParser(new PageParserCallback() {

         @Override
         public void callback(List<String> left, List<String> right) {

        	 try {
        		 if (left.size() < right.size()) {
        			 subwortmap.put(left.get(0), Pair.of(left, right));
        		 }
        	 }
        	 catch (Exception e) {
            	log.debug("left = " + left);
            	log.debug("right = " + right);
            	log.error("error in subword callback", e);            	
            }
         }
      }, profile));

      wp.addParser(new GrammarPageParser(new PageParserCallback() {

         @Override
         public void callback(List<String> left, List<String> right) {

        	 try {
        		 if (left.size() > right.size()) {
        			 stemmap.put(StringUtils.join(left, ","), StringUtils.join(right, ","));
        		 }
        	 }
        	 catch (Exception e) {
         		log.debug("left = " + left);
         		log.debug("right = " + right);
         		log.error("error in grammar callback", e);
         	}
         }
      }, profile));

      wp.parse(dump);

      try {

         SortedSet<String> keys = new TreeSet<String>(subwortmap.keySet());

         FileUtils.writeStringToFile(subwordout, "");

         for (String key : keys) {
            Pair<List<String>, List<String>> pair = subwortmap.get(key);
            List<String> right = pair.getRight();

            SortedSet<String> newright = new TreeSet<String>(right);

            // if desired recurse subwords to integrate synonyms
            if (PropertyManager.isTransitiveEnabled()) {
	            Iterator<String> iterator = right.iterator();
	            while (iterator.hasNext()) {
	               // is subword a generic word ?
	               String subword = iterator.next();
	
	               if (keys.contains(subword)) {
	                  Pair<List<String>, List<String>> subpair = subwortmap.get(subword);
	                  newright.addAll(subpair.getRight());
	               }
	            }
            }

            FileUtils.writeStringToFile(subwordout,
                  StringUtils.join(pair.getLeft(), ",") + " => " + StringUtils.join(newright, ",") + "\n", "utf-8",
                  true);
         }
      } catch (IOException e) {
    	  log.error("error writing file: " + subwordout.getAbsolutePath(), e);
      }

      try {

         SortedSet<String> keys = new TreeSet<String>(stemmap.keySet());

         FileUtils.writeStringToFile(stemout, "");

         for (String key : keys) {
            FileUtils.writeStringToFile(stemout, key + " => " + stemmap.get(key) + "\n", "utf-8", true);
         }

      } catch (IOException e) {
         log.error("error writing file: " + stemout.getAbsolutePath(), e);
      }

   }

}
