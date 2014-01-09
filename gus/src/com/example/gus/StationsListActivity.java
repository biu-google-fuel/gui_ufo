package com.example.gus;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
 
public class StationsListActivity extends Activity {
	 ListView listView ;
	Button button;
	int country_mode;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		Bundle extras = getIntent().getExtras();
		if(extras !=null) {
		    String json = extras.getString("JSON");
		    country_mode = extras.getInt("MODE"); 
		    buildListPage(json);
		}
		
	}

	private void buildListPage(String json) {
		listView = (ListView) findViewById(R.id.listView1);
		String[] values = {"Sorry,no gas stations!"};
		
		try {
			values = createValuesFromJson(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

// Define a new Adapter
// First parameter - Context
// Second parameter - Layout for the row
// Third parameter - ID of the TextView to which the data is written
// Forth - the Array of data

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);


		// Assign adapter to ListView
		listView.setAdapter(adapter); 
	}
 
	
	String[] createValuesFromJson(String json) throws JSONException {
		String petrol95 ;
		String cmpName;
		String distance;
		
		
		// De-serialize the JSON string into an array of city objects
		JSONArray jsonArray =null;
			if (country_mode==1){
				 jsonArray = new JSONArray(json);
			}else{
				JSONObject jsonObj2 = new JSONObject(json);
				jsonArray = jsonObj2.getJSONArray("stations");	
			}
	        
			String[] myStringArray = new String[jsonArray.length()];
			myStringArray[0]= "Sorry, no gus Stations!";
	        for (int i = 0; i < jsonArray.length(); i++) {
	            // Create a marker for each city in the JSON data.
	        	
	            JSONObject jsonObj = jsonArray.getJSONObject(i);
	            if (country_mode==1){ //Israel mode
	            	 petrol95 = jsonObj.getString("PETROL95");
	            	 distance = "0.5";
	            	 cmpName = jsonObj.getString("COMPANY");
	            }
	            else{
	                  distance = jsonObj.getString("distance");
	                  petrol95 = jsonObj.getString("price");
	                  cmpName = jsonObj.getString("station");
	            }
	       
	            myStringArray[i]= cmpName + " | " +"Price:"+ petrol95+", Distance: "+distance +"!";
	       }
	        return myStringArray;
	    }
	
}