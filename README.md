# wikitionary-solr-synonyms


## Why this fork ?
The original version of the code manages only german version of the wikitionary markup. Since I needed to adapt the code to parse an italian version of the wikitionary, I asked to myself << can I build up a more "generic" version ? >> The answer was << Why not ? >>. 
I Hope this fork may be useful to other people. 

Parser for wiktionary files that creates 2 files `<lang>.stem.txt` (<lang> stemming) and `<lang>.subword.txt`. <lang> will be replaced by your language.
 
E.g: it.stem.txt (for italian).

Stemming:
```
(de) pullover,pullovers,pullovern => pullover
(it) maglione,maglioni => maglione 
```

Subwords:
```
(de) pullover,pullovers,pullovern => pullover,pullovern,pullovers,strickpullover,sweatshirt,wollpullover
(it) pullover => cardigan,golf,maglione,pullover
```

## Wiktionary download

Download `dewiktionary-<DATE>-pages-meta-current.xml` (or other language) from http://dumps.wikimedia.org/backup-index.html

## Usage

### Properties and Environment variables
I added some properties that could be user as JVM system properties or environment variables. In the table below these are summarized.

JVM Property | Environment variable | Description | Defaults
------------ | ------------- | ------------- | ------------- 
wk2s.words.transitive | WK2S_WORDS_TRANSITIVE | if "true" enable transitive subwords | false
wk2s.words.lowercase | WK2S_WORDS_LOWERCASE | if "true" enable the lowercase of the words into the output files | true
wk2s.subwordFile | WK2S_SUBWORDFILE | the file path where to save the extracted subword | <lang>.subword.txt
wk2s.stemmFile | WK2S_STEMFILE | the file path where to save the extracted stemmed word | <lang>.stem.txt


	$ mvn clean package
	$ java -jar target/wikitionary-solr-synonyms-0.0.1-SNAPSHOT-jar-with-dependencies.jar [dump] [language profile]
    
Where :
- **[dump]** is the path of the dump file to be parsed (e.g: itwiktionary-20150331-pages-meta-current.xml)
- **[language profile]** is the qualified name of the class that implements the language profile

### Example (not transitive)
	
	$ java -jar target/wikitionary-solr-synonyms-0.0.1-SNAPSHOT-jar-with-dependencies.jar /tmp/itwiktionary-20150331-pages-meta-current.xml com.s24.wiki.profile.LanguageProfile_it

### Example (transitive)

	$ java -Dwk2s.words.transitive=true -jar target/wikitionary-solr-synonyms-0.0.1-SNAPSHOT-jar-with-dependencies.jar /tmp/itwiktionary-20150331-pages-meta-current.xml com.s24.wiki.profile.LanguageProfile_it





## License

This project is licensed under the [Apache License, Version 2](http://www.apache.org/licenses/LICENSE-2.0.html).

