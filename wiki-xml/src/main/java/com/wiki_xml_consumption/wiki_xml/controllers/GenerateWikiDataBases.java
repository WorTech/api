package com.wiki_xml_consumption.wiki_xml.controllers;

import java.io.FileReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.wiki_xml_consumption.wiki_xml.models.Artist;
import com.wiki_xml_consumption.wiki_xml.repositories.WikiArtistRepository;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/*
 * This will parse the 60+GB Wiki dump to create a much smaller xml file
 * It's contents will be the InfoBoxes for each artist inside the Music-Crux DB
 * 
 * WARNING:
 * Due to the large amount of entities the XMLStreamReader has to process the following argument needs to be added to the 
 * VM argument section under run configurations to avoid the "JAXP00010004" error
 * "-Djdk.xml.totalEntitySizeLimit=0"
 */
@Component
@Order(value = 1)
public class GenerateWikiDataBases implements CommandLineRunner {

	private static final int MYTHREADS = 100;
	ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
	
	@Autowired
	private WikiArtistRepository WArtistRepository;

//	//private WikiBandRepository WBandRepository;

	
	/*
	 * Parses the XML document for the Infoboxes and maps them to JSON objects
	 * Only Infoboxes that have "{{Infobox Musical Artist" currently
	 */
	@Override
	public void run(String... arg0) throws Exception {		

		try{
			//Input the path of the enwiki-latest-pages-articles.xml document below
			String filePath = "C:\\Users\\eclouhi\\Downloads\\enwiki-latest-pages-articles.xml";
			Reader fileReader = new FileReader(filePath);
			
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();			
			XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(fileReader);			
			
			while(reader.hasNext()) {
				int xmlEvent = reader.next();
				Artist artist = new Artist();		
				if(xmlEvent == XMLStreamConstants.START_ELEMENT) {					
					if("text".equalsIgnoreCase(reader.getLocalName())) {												

						String parsedString = parseInfoBoxeMusicalArtist(streamToString(reader));						
						if(StringUtils.isNotEmpty(parsedString)) {
							
//							//Will need to do a check for Band or Artist here prior to calling parseArtistNameFromString
//							parseArtistNameFromString(parsedString, artist);
//							
//							if(artist.getName() != null) {
//								parseArtistBirthNameFromString(parsedString, artist);
//								parseArtistPlaceOfBirth(parsedString, artist);
//								parseInstruments(parsedString, artist);								
//								/* The date of birth fields for the latest-page wiki xml is too inconsistent for valid parsing
//								parseArtistDOBFromString(parsedString, artist);
//								*/
//								WArtistRepository.save(artist);
//								System.out.println(artist.toString());
//							}	
							
							executor.execute(() -> { 								
								multithreadedParsing(parsedString, artist);
								//System.out.println(Thread.currentThread().getId());
							}); 
						}
					}
				}
				 reader.close();				 
			}
			executor.shutdownNow();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Function to be called to run concurrent multithreading for each parsing function
	 * All parsing functions to be called in this function
	 */
	private void multithreadedParsing(String stream, Artist artist) {
		parseArtistNameFromString(stream, artist);		
		if(artist.getName() != null) {
		
			Future futrue1 = executor.submit(() -> {
					parseArtistBirthNameFromString(stream, artist);
					});
			Future futrue2 = executor.submit(() -> {
					parseArtistPlaceOfBirth(stream, artist);	
					});
			Future futrue3 = executor.submit(() -> {
					parseInstruments(stream, artist);	
					});
				/* The date of birth fields for the latest-page wiki xml is too inconsistent for valid parsing
				parseArtistDOBFromString(parsedString, artist);
				*/
				try {//All threads futrue1 -> furture3 MUST finish before the artist is written into the Collection
					if((futrue1.get() == null) && (futrue2.get() == null) && (futrue3.get() == null)) {
						WArtistRepository.save(artist);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}			
		}
		
	}
	
	
	private String streamToString(XMLStreamReader xmlr) throws TransformerConfigurationException,
	    TransformerFactoryConfigurationError, TransformerException{
	    Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    StringWriter stringWriter = new StringWriter();
	    transformer.transform(new StAXSource(xmlr), new StreamResult(stringWriter));
	    return stringWriter.toString();
	}	
	/*For Bands the field below is inside the Musical Artist Infobox
	 * background          = group_or_band
	 * 
	 * For Artists the Field below is inside the Musical Artist Infobox
	 *  background          = solo_singer
	 *  OR
	 *  background          = non_vocal_instrumentalist
	 *  Artist my vary greatly, therefore the check will be mainly to sort out the Bands
	 * 
	 */
	private String parseInfoBoxeMusicalArtist(String stream) {
		return StringUtils.substringBetween(stream, "{{Infobox musical artist", "}}");
	}
	
	/*Problems:
	 *  
	 * Possible stringbuilder instead of string
	 */
	private void  parseArtistNameFromString(String stream, Artist artist) {	
		String parseName = StringUtils.substringBetween(stream, "name", "\n");	
		String parseEquals = StringUtils.remove(parseName, "=");
		String parseLeftSpaces = StringUtils.strip(parseEquals);
		if( StringUtils.isNotEmpty(parseLeftSpaces) && parseLeftSpaces.matches("^[ A-Za-z]+$")) {					
			if(StringUtils.isNotEmpty(parseLeftSpaces)) { 
				artist.setName(parseLeftSpaces);				
			}
		}
	}
	
	private void  parseArtistBirthNameFromString(String stream, Artist artist) {		
		String parseName = StringUtils.substringBetween(stream, "birth_name", "\n");
		String parseEquals = StringUtils.remove(parseName, "=");
		String parseLeftSpaces = StringUtils.strip(parseEquals);
		if(StringUtils.isNotEmpty(parseLeftSpaces)) { 
			artist.setBirthName(parseLeftSpaces);
		}
	}
	
	//Currently not working due to formating problems
	private void  parseArtistDOBFromString(String stream, Artist artist) {	
		String parseDate = StringUtils.substringBetween(stream, "birth_date", "\n");
		if(StringUtils.isNotEmpty(parseDate)) {
			String parseDigits = StringUtils.substringBetween(parseDate, "|", "|" );
			//String parseDigits = parseDate.replaceAll("[^0-9]",  "");
			//int parseDigits =  Integer.parseInt(parseDate);
			//System.out.println(parseDate);
		}	
	}
	
	/*
	 * St. Louis|St. Louis, Missouri
		Miami, Florida
		San Francisco|San Francisco, California
		
		Determine what's a state, city, and country
		Needs more work
	 */
	private void parseArtistPlaceOfBirth(String stream, Artist artist) {
		String parsePlaceOfBirth = StringUtils.substringBetween(stream, "birth_place", "\n");
		String parsedLocation = StringUtils.substringBetween(parsePlaceOfBirth, "[[", "]]");		
		if(StringUtils.isNotEmpty(StringUtils.strip(parsedLocation))) { 
			//System.out.println(parsedLocation);
			artist.setPlaceOfBirth(StringUtils.strip(parsedLocation));
		}
	}
	
	/*Finds what ever sequence is being searched for in a string and replaces it with another Char/String
	 * Created custom function as "replaceAll" is very difficult to use in CASE Insensitive matters
	*/
	private String findAndReplaceAll(String pattern, String replaceWith, String inputString) {
		    Pattern patterns = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
		    Matcher matcher = patterns.matcher(inputString);
		    return matcher.replaceAll(replaceWith);		
	}
	
	/*Problems to fix: (Some problems can possibly fixed post Artist creation)
	 *remove blank indices, indices with a single char 
	 *[kora during a performance at Afrofest  in Toronto, Ontario]
	 *[s]
	 *caseInsensitive regex patterns seperated by |
	 *[+ Human Vocals]
	 *[, author]
	 *[s Vocals, ] (or other various characters that follow after a "s "
	 *remove "rock"
	 */
	private void parseInstruments(String stream, Artist artist) {
		if(StringUtils.containsIgnoreCase("flatlist", StringUtils.substringBetween(stream, "instrument", "\n"))) {
			String parsedInstrumentField = StringUtils.substringBetween(stream, "instrument", "\\}");	
			if(StringUtils.isNotEmpty(parsedInstrumentField)) {	
				//System.out.println(parsedInstrumentField);
			}
		}else {
			String parsedInstrumentField = StringUtils.substringBetween(stream, "instrument", "\n");	
			if(StringUtils.isNotEmpty(parsedInstrumentField) && !StringUtils.contains(parsedInstrumentField, "\\.")) {	
				String parsedString = parsedInstrumentField.replaceAll("\\[|\\]|\\=|\\{|\\|", "")							
							.replaceAll("[0-9]", "").replaceAll("instrument", "").replaceFirst("alist", "").replaceAll("\\(.*\\)","").replaceAll("(?i)Keyboard keyboards|(?i)MusickeyboardKeyboards", "Keyboard")
							.replaceAll("(?i)sampler sampler", "Sampler").replaceAll("(?i)Bass guitar|(?i)Bass guitarBass|(?i)bass", "Bass Guitar");					
					String parsedArray = StringUtils.remove(StringUtils.replaceIgnoreCase(StringUtils
							.trimToNull(findAndReplaceAll("voiceVocals|VocalsVocals|singerVocals|Singingvocals|Lead VocalsistVocals|lead VocalsVocals|singer|singing|vocals|Vocal|Voice", "Vocals", parsedString)), "vocalss", "Vocals"), "al ");
					if(StringUtils.isNotBlank(parsedArray) && StringUtils.containsNone(parsedArray, "\\&|\\:|\\;|\\.")) {	
						//System.out.println(Arrays.asList(StringUtils.removeAll(StringUtils.removeAll(parsedArray, "(?i)human"), "\\(|\\)")));
						List<String> array = Arrays.asList(StringUtils.removeAll(StringUtils.removeAll(parsedArray, "(?i)human"), "\\(|\\)"));						
						//System.out.println(array.toString());
						artist.setInstruments(array);
						//System.out.println(artist.toString());
											
					}				
			}
			
		}
	}

}
