package com.musiccrux.discogs_xml_consumption.operations;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.musiccrux.discogs_xml_consumption.models.Artist;
import com.musiccrux.discogs_xml_consumption.repositories.ArtistRepository;

/**
 * Reads from the Artist XML source file and adds the artist from
 * that file into the Artist database. 
 * 
 * NOTES: 
 * 		1)	The Artist XML file should have "<?xml version="1.0" encoding="URF-8"?>" at the top
 * 
 * @author Taiwo Oyekanmi
 *
 */
@Component
public class CreateArtistDB implements CommandLineRunner {

	@Autowired
	private ArtistRepository ArtistRepository;

	@Override
	public void run(String... arg0) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		
		String artistXmlFileString = arg0[0].toString();
		
		// "C:\\Users\\tooyeka\\Downloads\\discogs_20170301_artists.xml\\discogs_20170301_artists.xml"
		ArtistXmlFileReader xmlFileReader = new ArtistXmlFileReader(artistXmlFileString);

		while (xmlFileReader.hasNext()) {

			String artistXmlString = xmlFileReader.next();

			JSONObject xmlJsonObj = XML.toJSONObject(artistXmlString);
			JSONObject artistJson = xmlJsonObj.getJSONObject("artist");

			Artist artist = mapper.readValue(artistJson.toString(), Artist.class);
			ArtistRepository.save(artist);
			System.out.println(artist.toString());
		}
	}

	/**
	 * Helper class which reads the Artist XML file and provides useful iteration functionality 
	 * 
	 * @author Taiwo Oyekanmi
	 *
	 */
	private class ArtistXmlFileReader {

		private XMLStreamReader xmlStreamReader;
		private Transformer xmlTransformer;

		/**
		 * 
		 * @param filePath
		 * @throws FileNotFoundException
		 * @throws XMLStreamException
		 * @throws FactoryConfigurationError
		 * @throws TransformerConfigurationException
		 * @throws TransformerFactoryConfigurationError
		 */
		public ArtistXmlFileReader(String filePath) throws FileNotFoundException, XMLStreamException,
				FactoryConfigurationError, TransformerConfigurationException, TransformerFactoryConfigurationError {

			xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(new FileReader(filePath));
			xmlTransformer = TransformerFactory.newInstance().newTransformer();
			xmlStreamReader.nextTag(); // Advance to statements element
		}

		/**
		 * Iterates over the XML file
		 * 
		 * @return false if done or true if there is more to read
		 * 		
		 * @throws XMLStreamException
		 */
		public boolean hasNext() throws XMLStreamException {
			return xmlStreamReader.nextTag() == XMLStreamConstants.START_ELEMENT;
		}

		/**
		 * Obtains the XML String of an artist node from position of the XMLStreamReader
		 * @return
		 * @throws TransformerException
		 */
		public String next() throws TransformerException {
			return getStringFromStream(xmlStreamReader);
		}

		/**
		 * Transforms the xml stream being read and returns the string.
		 * 
		 * @param xmlStreamReader
		 * @return
		 * @throws TransformerException
		 */
		private String getStringFromStream(XMLStreamReader xmlStreamReader) throws TransformerException {

			StAXSource stAXSource = new StAXSource(xmlStreamReader);
			StringWriter stringWriter = new StringWriter();

			this.xmlTransformer.transform(stAXSource, new StreamResult(stringWriter));
			return stringWriter.toString();
		}

	}

}
