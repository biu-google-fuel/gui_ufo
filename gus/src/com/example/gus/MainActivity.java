package com.example.gus;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;


import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity{
	public int country_mode = 1;	// 1 = Israel , 2 = USA 
	
	   private GoogleMap mMap;
	 
	  public HashMap<Marker,MarkInfo> markers = new HashMap<Marker,MarkInfo>();
	  public HashMap<String,Integer> cmpImg = new HashMap<String,Integer>();
	  private static final String LOG_TAG = "ExampleApp";	    
	  public Button statBtn;
	  public Button mode_button;;
	  private Button loc1_button;
	  private Button loc2_button;
	  private Button loc3_button;
	  private Button list_button;
	  
	  public Location lastLoc ;
	  StringBuilder json = new StringBuilder();
	  public boolean firstTime = true;
	  
	//  String BASE_URL = "http://ufo-project.appspot.com/";
  		double lat =32.03217;
  		double lon =34.79936;
//  		String getUrl = BASE_URL + String.format("get_stations?distance=0.1&latitude=%f&longitude=%f", lat, lon);

		

		
    
	private void setUpMapIfNeeded() {
		
		if (mMap == null){
			
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			
		}
			if (mMap != null) {
				mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				/////////
				
              
		         /*  mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

		               @Override
		               public void onMyLocationChange(Location arg0) {
		                // TODO Auto-generated method stub
		            	   lastLoc = arg0;
		            	   if (firstTime){
		            		   firstTime = false;
		            		  CameraPosition cmraPosition = new CameraPosition.Builder()
		                      .target(new LatLng(lastLoc.getLatitude(), lastLoc.getLongitude()))      // Sets the center of the map to location user
		                      .zoom(19)                   // Sets the zoom
		                      .bearing(60)                // Sets the orientation of the camera to east
		                      .tilt(80)                   // Sets the tilt of the camera to 30 degrees
		                      .build();                   // Creates a CameraPosition from the builder
		                  mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cmraPosition));
		               }
		               }
		              });*/
		           

		                
				////////
				
				
				/*mMap.addMarker(new MarkerOptions()
				.position(BEGIN)
				.title("Gus station 3")
				.snippet("delek")
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));*/
			}
				// Setting a custom info window adapter for the google map
		        mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
		 
		            // Use default InfoWindow frame
		            @Override
		            public View getInfoWindow(Marker arg0) {
		                return null;
		            }
		 
		            // Defines the contents of the InfoWindow
		            @Override
		            public View getInfoContents(Marker mark) {
		 
		                // Getting view from the layout file info_window_layout
		                View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);
		                MarkInfo info=markers.get(mark);
		 
		                // Getting reference to the TextView to set latitude
		                TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
		 
		                // Getting reference to the TextView to set longitude
		                TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
		                ImageView cmpVal = (ImageView) v.findViewById(R.id.cmpImage);
		                
		                String markTitle = mark.getTitle();
		                String cmp ="";
		                // Setting the latitude
		                tvLat.setText(markTitle);
		                if (info !=null){
		                tvLng.setText(info.petrol95 + "$");
		                 cmp = info.cmpName;
		                }
		                
		                if (cmp.equalsIgnoreCase("דלק")){
		                	cmpVal.setImageResource(R.drawable.delek);
		                }else if (cmp.equalsIgnoreCase("פז")){	
		                	cmpVal.setImageResource(R.drawable.paz);
		                }else if (cmp.equalsIgnoreCase("סונול")){
		                	cmpVal.setImageResource(R.drawable.sonol);
		                }else{
		                	cmpVal.setImageResource(R.drawable.unknown);
		                }
		           
		                // Returning the view containing InfoWindow contents
		                return v;
		 
		            }
		        });
		        

	        	mMap.setOnCameraChangeListener(new OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {
                           /* int count = 0;
                            Projection projection = mMap.getProjection();
                            final LatLngBounds bounds = projection.getVisibleRegion().latLngBounds;
                            */
                    
                           
                           /* for (int i = positions.size() - 1; i >= 0; i--) {
                                    LatLng position = positions.get(i);
                                    if (bounds.contains(position)) {
                                    	
                                 
                                    		mMap.addMarker(new MarkerOptions().position(position).title("Gus station 3")
                                    				.snippet("delek").visible(cameraPosition.zoom>=6));
                                 
                                            positions.remove(i);
                                            count++;
                                    }
                            }*/
                    }
	        	});
		        
		        // Adding and showing marker while touching the GoogleMap
		        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
		        	@Override
		            public boolean onMarkerClick(Marker arg0) {
		                // Clears any existing markers from the GoogleMap
		            	//mMap.clear();
		 
		                // Creating an instance of MarkerOptions to set position
		               // MarkerOptions markerOptions = new MarkerOptions();
		 
		                // Setting position on the MarkerOptions
		               // markerOptions.position(arg0);
		 
		                // Animating to the currently touched position
		                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
		                //mMap.animateCamera(CameraUpdateFactory.zoomTo(TRIM_MEMORY_COMPLETE));
		                // Adding marker on the GoogleMap
		                //Marker marker = mMap.addMarker(markerOptions);
		 
		                // Showing InfoWindow on the GoogleMap
		                arg0.showInfoWindow();
		                return true;
		 
		            }
		        	
					
		        });
		        
				//myLoc = mMap.getMyLocation();
				//mMap.addCircle(new CircleOptions().fillColor(color.common_signin_btn_light_text_disabled).center(new LatLng(myLoc.getLatitude(),myLoc.getLongitude())));
			}
		
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setModeAndLocations();
        setUpMapIfNeeded();

 
        
    }
    
    
private void setModeAndLocations() {
	this.mode_button = (Button)this.findViewById(R.id.button2);
	this.loc1_button = (Button)this.findViewById(R.id.loc1);
	this.loc2_button = (Button)this.findViewById(R.id.loc2);
	this.loc3_button = (Button)this.findViewById(R.id.loc3);
	this.list_button = (Button)this.findViewById(R.id.stnList);
	final Context context = this;
	
	
	this.list_button.setOnClickListener(new OnClickListener() {
	      @Override
	      public void onClick(View v) {
			    Intent intent = new Intent(context,StationsListActivity.class);
			    intent.putExtra("JSON",json.toString());
			    intent.putExtra("MODE",country_mode);
                          startActivity(intent);  
	      }
	    });
	
	 this.loc1_button.setOnClickListener(new OnClickListener() {
	      @Override
	      public void onClick(View v) {
	    	  mMap.clear();
	    	  if (country_mode==1){
	    		  lat =31.99217;
	    	  	  lon =34.79936;
	    	  }else{
	    		  lat=40.714555;
	    		  lon=-74.006588;
	    	  }
	    	  mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title("My Location"));
    		  CameraPosition cmraPosition = new CameraPosition.Builder()
              .target(new LatLng(lat,lon))      // Sets the center of the map to location user
              .zoom(19)                   // Sets the zoom
              .bearing(60)                // Sets the orientation of the camera to east
              .tilt(80)                   // Sets the tilt of the camera to 30 degrees
              .build();                   // Creates a CameraPosition from the builder
    		  mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cmraPosition));
	    	  
	      }
	    });
	 
	 this.loc2_button.setOnClickListener(new OnClickListener() {
	      @Override
	      public void onClick(View v) {
	    	  mMap.clear();
	    	  if (country_mode==1){
	    		  lat =32.084972;
	    	  	  lon =34.887396;
	    	  }else{
	    		  lat =38.909458;
	    	  	  lon =-77.042656;
	    	  }
	    	  mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title("My Location"));
    		  CameraPosition cmraPosition = new CameraPosition.Builder()
              .target(new LatLng(lat,lon))      // Sets the center of the map to location user
              .zoom(19)                   // Sets the zoom
              .bearing(60)                // Sets the orientation of the camera to east
              .tilt(80)                   // Sets the tilt of the camera to 30 degrees
              .build();                   // Creates a CameraPosition from the builder
    		  mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cmraPosition));
	    	  
	      }
	    });
	 
	 this.loc3_button.setOnClickListener(new OnClickListener() {
	      @Override
	      public void onClick(View v) {
	    	  mMap.clear();
	    	  if (country_mode==1){
	    		  lat =32.830086;
	    	  	  lon =34.974954;
	    	  }else{
	    		  lat =40.650522;
	    	  	  lon =-73.951635;
	    	  }
	    	  mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title("My Location"));
    		  CameraPosition cmraPosition = new CameraPosition.Builder()
              .target(new LatLng(lat,lon))      // Sets the center of the map to location user
              .zoom(19)                   // Sets the zoom
              .bearing(60)                // Sets the orientation of the camera to east
              .tilt(80)                   // Sets the tilt of the camera to 30 degrees
              .build();                   // Creates a CameraPosition from the builder
    		  mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cmraPosition));
	    	  
	      }
	    });
	 
	 this.mode_button.setOnClickListener(new OnClickListener() {
	      @Override
	      public void onClick(View v) {
	    	  
	    	  if(country_mode==1){
	    		  mode_button.setText("US mode");
	    		  loc1_button.setText("NYC");
	    		  loc2_button.setText("Washington");
	    		  loc3_button.setText("Brooklyn");
	    		  country_mode=2;
	    	  }else{
	    		  mode_button.setText("Israel mode");
	    		  loc1_button.setText("Holon");
	    		  loc2_button.setText("Petah-Tikva");
	    		  loc3_button.setText("Haifa");
	    		  country_mode=1;
	    	  }
	      }
	    });
	 
    this.statBtn = (Button)this.findViewById(R.id.button1);
    this.statBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
    	  new Thread(new Runnable() {
              public void run() {
                  try {
                		 retrieveAndAddCities(); 
                  } catch (IOException e) {
                      Log.e(LOG_TAG, "Cannot retrive cities", e);
                      return;
                  }
              }
          }).start();
      }
    });
    
	}


/**ADD to insert gas station from Json
 * @param location 
     * @return 
     * @throws JSONException */
    
    protected void retrieveAndAddCities() throws IOException {
    	String getUrl = getUrlFormatByMode();
    	
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpResponse response = null;
        HttpGet getMethod = new HttpGet(getUrl);
    	
        try {
        	response = httpClient.execute(getMethod);
        	// CONVERT RESPONSE TO STRING
            String result = EntityUtils.toString(response.getEntity());
            json = new StringBuilder();
            json.append(result);
        }catch (IOException e){
            Log.e(LOG_TAG+ "1", e.getMessage());
        }
        
        // Create markers for the city data.
        // Must run this on the UI thread since it's a UI operation.
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    createMarkersFromJson(json.toString());
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error processing JSON", e);
                }
            }
        });
    }
    
   private String getUrlFormatByMode() {
	   String getUrl;
	   if (country_mode==1){
		   getUrl = "http://ufo-project.appspot.com/" + String.format("get_stations?distance=0.1&latitude=%f&longitude=%f", lat, lon);
	   }
	   else{
		   getUrl = "http://api.mygasfeed.com/stations/radius/" +String.format("%f/%f/5/reg/price/xencxarzp7.json", lat, lon);
	   }
	   return getUrl;
}


void createMarkersFromJson(String json) throws JSONException {
	double lat;
	double lng;
	String petrol95 ;
	String cmpName;
	// De-serialize the JSON string into an array of city objects
	JSONArray jsonArray =null;
		if (country_mode==1){
			 jsonArray = new JSONArray(json);
		}else{
			JSONObject jsonObj2 = new JSONObject(json);
			jsonArray = jsonObj2.getJSONArray("stations");	
		}
        
        
        for (int i = 0; i < jsonArray.length(); i++) {
            // Create a marker for each city in the JSON data.
        	
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            if (country_mode==1){ //Israel mode
            	 lat = Double.parseDouble(jsonObj.getJSONObject("LOCATION").getString("latitude"));
            	 lng = Double.parseDouble(jsonObj.getJSONObject("LOCATION").getString("longitude"));
            	 petrol95 = jsonObj.getString("PETROL95");
            	 cmpName = jsonObj.getString("COMPANY");
            }
            else{
            	  lat = Double.parseDouble(jsonObj.getString("lat"));
                  lng = Double.parseDouble(jsonObj.getString("lng"));
                  petrol95 = jsonObj.getString("price");
                  cmpName = jsonObj.getString("station");
            }
         
           Marker newMarker =  mMap.addMarker(new MarkerOptions().title(cmpName).position(new LatLng(lat, lng)));
           MarkInfo newinfo = new MarkInfo(cmpName, lat, lng, petrol95);
            markers.put(newMarker,newinfo);
       }
    }
}
  

	
    



