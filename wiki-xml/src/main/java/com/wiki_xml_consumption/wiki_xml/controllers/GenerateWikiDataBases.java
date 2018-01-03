package com.wiki_xml_consumption.wiki_xml.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageRequestDto;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.musiccrux.discogs_xml_consumption.operations.CreateArtistDB.ArtistXmlFileReader;
import com.wiki_xml_consumption.wiki_xml.models.Artist;
import com.wiki_xml_consumption.wiki_xml.repositories.MusicCruxArtistRepository;
import com.wiki_xml_consumption.wiki_xml.repositories.WikiArtistRepository;

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

//	@Autowired
//	private MusicCruxArtistRepository MCArtistRepository;
//	private WikiArtistRepository WArtistRepository;
//	//private MusicCruxBandRepository MCBandRepository;
//	//private WikiBandRepository WBandRepository;
//	

	
	

	
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
						//System.out.println("Start Element: " +reader.getLocalName());					
						

						String parsedString = parseInfoBoxeMusicalArtist(streamToString(reader));
						if(StringUtils.isNotEmpty(parsedString)) {
							
							parseArtistNameFromString(parsedString, artist);
							
							if(artist.getName() != null) {
								parseArtistBirthNameFromString(parsedString, artist);
								parseArtistPlaceOfBirth(parsedString, artist);
								parseInstruments(parsedString, artist);
								//System.out.println(parsedString);
								/* The date of birth fields for the latest-page wiki xml is too inconsistent for valid parsing
								parseArtistDOBFromString(parsedString, artist);
								*/
								
								System.out.println(artist.toString());
							}
							
							
							
							
						}
					}

				}
//				 if (xmlEvent == XMLStreamConstants.END_ELEMENT) {
//					 if("text".equalsIgnoreCase(reader.getLocalName())) {
//						System.out.println("End Element: "+reader.getLocalName());
//					 }
//				}
				 reader.close();
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
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
	 * 
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
			System.out.println(parseDate);
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
			System.out.println(parsedLocation);
			artist.setPlaceOfBirth(StringUtils.strip(parsedLocation));
		}
	}
	
	private String findAndReplaceAll(String pattern, String replaceWith, String inputString) {
		    Pattern patterns = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
		    Matcher matcher = patterns.matcher(inputString);
		    return matcher.replaceAll(replaceWith);		
	}
	
	/*Problems to fix:
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
				System.out.println(parsedInstrumentField);
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
						artist.setInstruments(array);
						System.out.println(artist.toString());
											
					}				
			}
			
		}
	}

}
