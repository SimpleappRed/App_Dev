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


package com.example.nidheesha.realestate.helper.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.nidheesha.realestate.models.ListEntry;

import java.util.List;

public class CustomAsyncTask extends AsyncTask<Void, Void, String>
{
	public ProgressDialog dialog;
	public Activity activity;
	public List<ListEntry> entryList;
	public List<ListEntry> entryListExtra;
	public List<ListEntry> entryListExtraInfo;
	public List<ListEntry> entryListExtra1;
	public List<ListEntry> entryListExtra2;
	public List<ListEntry> entryListExtra3;
	public List<ListEntry> entryListExtra4;
	public List<ListEntry> entryListExtra5;
	
	public int tag = 0;
	public int listTag = 0;
	
	OnAsyncTaskListener mCallback;
	
	public interface OnAsyncTaskListener 
    {
        public void doAsyncTaskInBackground(CustomAsyncTask customAsyncTask);
        
        public void onAsyncTaskProgressUpdate(CustomAsyncTask customAsyncTask);
        
        public void onAsyncTaskPostExecute(CustomAsyncTask customAsyncTask);
        
        public void onAsyncTaskPreExecute(CustomAsyncTask customAsyncTask);
    }
	
	public void setAsyncTaskListener(OnAsyncTaskListener listener)
	{
		try 
		{
            mCallback = (OnAsyncTaskListener) listener;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(this.toString() + " must implement OnAsyncTaskListener	");
        }
	}
	
	public CustomAsyncTask(Activity activity)
	{
		this.activity = activity;
	}
	
	public void startAsyncTask()
	{
		this.execute();
	}
	
	@Override
	protected String doInBackground(Void... params)
	{
		mCallback.doAsyncTaskInBackground(this);
		return "";
	}
	
	@Override
	protected void onPostExecute(String result)
	{
		// execution of result of Long time consuming operation. parse json data
		dialog.dismiss();
		mCallback.onAsyncTaskPostExecute(this);
	}

	@Override
	protected void onPreExecute() 
	{
		// Things to be done before execution of long running operation. For example showing ProgessDialog
		dialog = ProgressDialog.show(activity, "", "Loading. Please wait...", true);
		mCallback.onAsyncTaskPreExecute(this);
		dialog.dismiss();
	}

	@Override
	protected void onProgressUpdate(Void... values)
	{
		//dialog.dismiss();
		// Things to be done while execution of long running operation is in progress. For example updating ProgessDialog
		mCallback.onAsyncTaskProgressUpdate(this);
	}
		
}
