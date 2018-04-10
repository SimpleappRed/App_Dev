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

import com.example.nidheesha.realestate.models.ImageEntry;
import com.example.nidheesha.realestate.models.ListEntry;
import com.example.nidheesha.realestate.models.ReviewEntry;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XMLHandler extends DefaultHandler {
	  
	private List<ListEntry> listNodes = new ArrayList<ListEntry>();
	
	private List<ImageEntry> imageList = null;
	
	private List<ReviewEntry> reviewList = null;
	  
	private ListEntry xmlNode = null;
	
	private ImageEntry xmlNodeImage = null;
	
	private ReviewEntry xmlNodeReview = null;
	
	private String tempVal;
	
	
	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes attrib) throws SAXException
	{
		super.startElement(namespaceURI, localName, qName, attrib);
		
		tempVal = "";
		if(localName.equals("entry"))
		{
			xmlNode = new ListEntry();
		}
		if(localName.equals("images"))
		{
			imageList = new ArrayList<ImageEntry>();
		}
		if(localName.equals("image"))
		{
			xmlNodeImage = new ImageEntry();
		}
		
		if(localName.equals("reviews"))
		{
			reviewList = new ArrayList<ReviewEntry>();
		}
		
		if(localName.equals("review"))
		{
			xmlNodeReview = new ReviewEntry();
		}
		
	}
	
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		
		tempVal = new String(ch, start, length);
	}

	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		
		if(localName.equals("entry"))
		{
			listNodes.add(xmlNode);
			xmlNode = null;
		}
		else if( qName.equalsIgnoreCase("id") )
		{
//			String currency = theAtts.getValue("currency");
//			String rate = theAtts.getValue( "rate");
//			listNodes.add( new CurrencyData( currency, rate));
			
			if(tempVal.length() > 0)
				if(xmlNode != null)
				xmlNode.id = Integer.parseInt(tempVal);
		}
		else if( qName.equalsIgnoreCase("title") )
		{if(xmlNode !=null)
			xmlNode.title = tempVal;
        }
		else if( qName.equalsIgnoreCase("subtitle") )
		{if (xmlNode!=null)
			xmlNode.subtitle = tempVal;
        }
		else if( qName.equalsIgnoreCase("description") )
		{if (xmlNode!=null)
			xmlNode.description = tempVal;
        }
		else if( qName.equalsIgnoreCase("info") )
		{if (xmlNode!=null)
			xmlNode.info = tempVal;
        }
		else if( qName.equalsIgnoreCase("imageUrl") )
		{if (xmlNode!=null)
			xmlNode.imageUrl = tempVal;
        }
		else if( qName.equalsIgnoreCase("imageBgUrl") )
		{if (xmlNode!=null)
			xmlNode.imageBgUrl = tempVal;
        }
		else if( qName.equalsIgnoreCase("imageThumbUrl") )
		{if (xmlNode!=null)
			xmlNode.imageThumbUrl = tempVal;
        }
		else if( qName.equalsIgnoreCase("details") )
		{if (xmlNode!=null)
			xmlNode.details = tempVal;
        }
		else if( qName.equalsIgnoreCase("summary") )
		{if (xmlNode!=null)
			xmlNode.summary = tempVal;
        }
		else if( qName.equalsIgnoreCase("extraInfo") )
		{if (xmlNode!=null)
			xmlNode.extraInfo = tempVal;
        }
		else if( qName.equalsIgnoreCase("price") )
		{if (xmlNode!=null)
			xmlNode.price = tempVal;
        }
		else if( qName.equalsIgnoreCase("name") )
		{if (xmlNode!=null)
			xmlNode.name = tempVal;
        }
		else if( qName.equalsIgnoreCase("address") )
		{if (xmlNode!=null)
			xmlNode.address = tempVal;
        }
		else if( qName.equalsIgnoreCase("latitude") )
		{if (xmlNode!=null)
			if(tempVal.length() > 0)
				xmlNode.latitude = Float.parseFloat(tempVal);
        }
		else if( qName.equalsIgnoreCase("longitude") )
		{if (xmlNode!=null)
			if(tempVal.length() > 0)
				xmlNode.longitude = Float.parseFloat(tempVal);
        }
		else if( qName.equalsIgnoreCase("rating") && tempVal.length() > 0 )
		{if (xmlNode!=null)
			if(tempVal.length() > 0)
				xmlNode.rating = Integer.parseInt(tempVal);
        }
		else if( qName.equalsIgnoreCase("featured")  && tempVal.length() > 0)
		{if (xmlNode!=null)
			if(tempVal.length() > 0)
				xmlNode.featured = Integer.parseInt(tempVal);
        }
		else if( qName.equalsIgnoreCase("telNo") )
		{if (xmlNode!=null)
			xmlNode.telNo = tempVal;
        }
		else if( qName.equalsIgnoreCase("checkInTime") )
		{if (xmlNode!=null)
			xmlNode.checkInTime = tempVal;
        }
		else if( qName.equalsIgnoreCase("date") )
		{if (xmlNode!=null)
			xmlNode.date = tempVal;
        }
		else if( qName.equalsIgnoreCase("annotationDescription") )
		{if (xmlNode!=null)
			xmlNode.annotationDescription = tempVal;
        }
		else if( qName.equalsIgnoreCase("zipCode") )
		{if (xmlNode!=null)
			if(tempVal.length() > 0)
				xmlNode.zipCode = tempVal;
			else
				xmlNode.zipCode = "0";
        }
		else if( qName.equalsIgnoreCase("zipCodeMin") )
		{if (xmlNode!=null)
			if(tempVal.length() > 0)
				xmlNode.zipCodeMin = tempVal;
			else
				xmlNode.zipCodeMin = "0";
        }
		else if( qName.equalsIgnoreCase("zipCodeMax") )
		{if (xmlNode!=null)
			if(tempVal.length() > 0)
				xmlNode.zipCodeMax = tempVal;
			else
				xmlNode.zipCodeMax = "0";
        }
		else if( qName.equalsIgnoreCase("noOfRooms") )
		{if (xmlNode!=null)
			xmlNode.noOfRooms = tempVal;
        }
		else if( qName.equalsIgnoreCase("bathrooms") )
		{if (xmlNode!=null)
			xmlNode.bathrooms = tempVal;
        }
		else if( qName.equalsIgnoreCase("apartmentType") )
		{if (xmlNode!=null)
			xmlNode.apartmentType = tempVal;
        }
		else if( qName.equalsIgnoreCase("selected") && tempVal.length() > 0)
		{if (xmlNode!=null)
			xmlNode.selected = Integer.parseInt(tempVal);
        }
		else if( qName.equalsIgnoreCase("email") )
		{if (xmlNode!=null)
			xmlNode.email = tempVal;
        }
		else if( qName.equalsIgnoreCase("phoneNo") )
		{if (xmlNode!=null)
			xmlNode.phoneNo = tempVal;
        }
		else if( qName.equalsIgnoreCase("apartmentId") && tempVal.length() > 0 )
		{if (xmlNode!=null)
			xmlNode.apartmentId = Integer.parseInt(tempVal);
        }
		else if( qName.equalsIgnoreCase("reviews") )
		{if (xmlNode!=null)
			xmlNode.reviews = tempVal;
        }

		// -----------
		else if( qName.equalsIgnoreCase("photoCoverUrl") )
		{if (xmlNode!=null)
			xmlNodeImage.photoCoverUrl = tempVal;
        }
		else if( qName.equalsIgnoreCase("photoImageUrl") )
		{if (xmlNode!=null)
			xmlNodeImage.photoImageUrl = tempVal;
        }
		else if( qName.equalsIgnoreCase("photoImageThumbUrl") )
		{if (xmlNode!=null)
			xmlNodeImage.photoImageThumbUrl = tempVal;
        }
		 
		else if(localName.equals("image"))
		{
			imageList.add(xmlNodeImage);
			if (xmlNode!=null)
				xmlNodeImage = null;
		}
		
		else if(localName.equals("images"))
		{if (xmlNode!=null)
			xmlNode.imageList =  imageList;
			imageList = null;
		}
		
		// -----------
		else if( qName.equalsIgnoreCase("reviewName") )
		{
			xmlNodeReview.reviewName = tempVal;
        }
		else if( qName.equalsIgnoreCase("reviewImageUrl") )
		{
			xmlNodeReview.reviewImageUrl = tempVal;
        }
		
		else if( qName.equalsIgnoreCase("reviewImageThumbUrl") )
		{
			xmlNodeReview.reviewImageThumbUrl = tempVal;
        }
		else if( qName.equalsIgnoreCase("comment") )
		{
			xmlNodeReview.comment = tempVal;
        }
		else if( qName.equalsIgnoreCase("commentDate") )
		{
			xmlNodeReview.commentDate = tempVal;
        }
		
		else if(localName.equals("review"))
		{
			reviewList.add(xmlNodeReview);
			xmlNodeReview = null;
		}
		
		else if(localName.equals("reviews"))
		{
			xmlNode.reviewList = reviewList;
			reviewList = null;
		}
	}
	
	public List<ListEntry> getXMLList()
	{
		return listNodes;
	} 

}
