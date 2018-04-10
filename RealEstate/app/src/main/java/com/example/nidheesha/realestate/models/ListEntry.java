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


package com.example.nidheesha.realestate.models;

import java.util.List;

public class ListEntry 
{
	public int id;
	public String title;
	public String subtitle;
	public String description;
	public String info;
	public String imageUrl;
	public String imageBgUrl;
	public String imageThumbUrl;
    
	public String details;
	public String summary;
	public String extraInfo;
	public String price;
    
	public String name;
	public String address;
//	public String reviews;
    
	public float latitude;
	public float longitude;
    
	public int rating;
	public int featured;
	public String telNo;
	public String checkInTime;
	public String date;
	public String annotationDescription;
	public String zipCode;
	public String zipCodeMin;
	public String zipCodeMax;
	
	public String noOfRooms;
	public String bathrooms;
	public String apartmentType;
    
	public int tag;
	
	public int residImageUrl;
	public int residImageBgUrl;
	public int residImageThumbUrl;
	
	public List<ImageEntry> imageList;
	public List<ReviewEntry> reviewList;
	
	public int selected;
	public String phoneNo;
	public String email;
	
	public int apartmentId;
	
	public String reviews;
}
