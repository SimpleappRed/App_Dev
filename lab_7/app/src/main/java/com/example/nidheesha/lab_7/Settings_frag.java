package com.example.nidheesha.lab_7;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Settings_frag extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
        PreferenceScreen ps = getPreferenceScreen();
        SharedPreferences sp = ps.getSharedPreferences();
        int i;
        int count = ps.getPreferenceCount();
        for(i=0;i<count;i++){
            Preference p = ps.getPreference(i);
            if(!(p instanceof CheckBoxPreference)){
                String val = sp.getString(p.getKey(),"");
                setPreference(val,p);
            }
        }

    }
    private  void setPreference(String val,Preference p){
        if(p instanceof ListPreference){
            ListPreference lp = (ListPreference) p;
            int prefindex = lp.findIndexOfValue(val);
            if(prefindex>=0){
                lp.setSummary(lp.getEntries()[prefindex]);
            }
        }
        else if(p instanceof EditTextPreference){
            p.setSummary(val);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            Preference p = findPreference(s);
            if(p!=null){
                if(!(p instanceof CheckBoxPreference)){
                    String val = sharedPreferences.getString(p.getKey(),"");
                    setPreference(val,p);
                }
            }
    }
}
