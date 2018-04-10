// ================================================================================================
//
// ApartmentPro - RealEstate Android App
// Designed By: UI Designs www.uidesigns.us
// Android Project and ApartmentPro code-base is licensed to __PHILIPPINE GLOBAL OUTSOURCING__
// which allows them to publish and sell this app without attribution and royalty.
//
// However, you are not allowed to resell or redistribute it.
// You can modify this project to fit into your or your clients' project.
// Although support is included, this product provided as is. We are not legally liable for
// any misuse or damage caused by these files directly or indirectly.
//
// If you have questions or implementation issues, please don't hesitate to contact us
// at philippineoutsourcing@gmail.com.
//
// This file is exclusively distributed in the Envato Marketplaces.
// Additional license information is available in their website.
//
// Copyright 2013 UIDesigns (www.uidesigns.us). All Rights Reserved.
//
// ================================================================================================


package com.example.nidheesha.realestate.components.xmlparser;

import android.app.Activity;

import com.example.nidheesha.realestate.models.ListEntry;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLRequest 
{
	private String urlM;
	
	public XMLRequest(String theUrl)
	{
		urlM = theUrl;
	}

	public List<ListEntry> obtainXMLList() throws ParserConfigurationException, SAXException, IOException
	{
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser parser = parserFactory.newSAXParser();
 
		XMLReader xmlReader = parser.getXMLReader();
		XMLHandler currHandler = new XMLHandler();
		xmlReader.setContentHandler(currHandler);
       
		URL url = new URL(urlM);
		xmlReader.parse(new InputSource( url.openStream()));
 
		return currHandler.getXMLList();        
	}
	
	public List<ListEntry> obtainXMLListFromLocal(Activity act) throws ParserConfigurationException, SAXException, IOException
	{
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser parser = parserFactory.newSAXParser();
 
		XMLReader xmlReader = parser.getXMLReader();
		XMLHandler currHandler = new XMLHandler();
		xmlReader.setContentHandler(currHandler);
       
		xmlReader.parse(new InputSource( act.getAssets().open(urlM)));
 
		return currHandler.getXMLList();        
	}
	
	/*
	 * For local xml call in assets folder
	 * 
	 * XMLRequest req = new XMLRequest("xml/apartments.xml");
     * List<ListEntry> entryList = req.obtainXMLListFromLocal(customAsyncTask.activity);
	 * 
	 */

}
