package com.example.gus;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;



import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity{
	   private GoogleMap mMap;
	   private final LatLng NEWHOME = new LatLng(32.010,34.777);
	  private final LatLng METULA = new LatLng(32.003,34.788);
	  private final LatLng BEGIN = new LatLng(32.005,34.784);
	  private Marker marker;
	  private Hashtable<String, String> markers;
	  private static final String LOG_TAG = "ExampleApp";	    
	  
	  String BASE_URL = "http://ufo-project.appspot.com/";
  		double lat =32.03217;
  		double lon =34.79936;
  		String getUrl = BASE_URL + String.format("get_stations?distance=0.1&latitude=%f&longitude=%f", lat, lon);
	  	myJson jsn = new myJson();
    
	private void setUpMapIfNeeded(final List<LatLng> positions) {
		String value = "";
		value = jsn.getJson(getUrl);

		if (mMap == null){
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

			markers = new Hashtable<String, String>();
			
		      mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
		      mMap.addMarker(new MarkerOptions().position(new LatLng(15, 3)));
		      mMap.addMarker(new MarkerOptions().position(new LatLng(14.99, 3.01)));
		}
			if (mMap != null) {
				mMap.setMyLocationEnabled(true);
				
				mMap.addMarker(new MarkerOptions()
				.position(NEWHOME)
				.title("Gus station 1")
				.snippet("paz")
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
				
				mMap.addMarker(new MarkerOptions()
				.position(METULA)
				.title("Gus station 2")
				.snippet("sonol")
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
				
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
		            public View getInfoContents(Marker arg0) {
		 
		                // Getting view from the layout file info_window_layout
		                View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);
		 
		                // Getting the position from the marker
		                LatLng latLng = arg0.getPosition();
		 
		                // Getting reference to the TextView to set latitude
		                TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
		 
		                // Getting reference to the TextView to set longitude
		                TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
		                ImageView cmpVal = (ImageView) v.findViewById(R.id.cmpImage);
		                
		                String markTitle = arg0.getTitle();
		                
		                // Setting the latitude
		                tvLat.setText(markTitle);
		                
		                String cmp = arg0.getSnippet();
		                
		                if (cmp.equalsIgnoreCase("delek")){
		                	cmpVal.setImageResource(R.drawable.delek);
		                }else if (cmp.equalsIgnoreCase("paz")){	
		                	cmpVal.setImageResource(R.drawable.paz);
		                }else if (cmp.equalsIgnoreCase("sonol")){
		                	cmpVal.setImageResource(R.drawable.sonol);
		                }
		                
		                // Setting the longitude
		                tvLng.setText("");
		 
		                // Returning the view containing InfoWindow contents
		                return v;
		 
		            }
		        });
		        

	        	mMap.setOnCameraChangeListener(new OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {
                            int count = 0;
                            Projection projection = mMap.getProjection();
                            LatLngBounds bounds = projection.getVisibleRegion().latLngBounds;
                            for (int i = positions.size() - 1; i >= 0; i--) {
                                    LatLng position = positions.get(i);
                                    if (bounds.contains(position)) {
                                    	
                                 
                                    		mMap.addMarker(new MarkerOptions().position(position).title("Gus station 3")
                                    				.snippet("delek").visible(cameraPosition.zoom>=6));
                                 
                                            positions.remove(i);
                                            count++;
                                    }
                            }
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
        
        final List<LatLng> positions = new ArrayList<LatLng>();
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
                LatLng position = new LatLng((r.nextDouble() - 0.5) * 170.0, (r.nextDouble() - 0.5) * 360.0);
                positions.add(position);
        }
        
   
        
        setUpMapIfNeeded(positions);
 
        
    }
    
    /*
       

        public static List<Club> getNearestClubs(double lat, double lon) {
            // YOUR URL GOES HERE
           

            List<Club> ret = new ArrayList<Club>();

           
            try {
                response = httpClient.execute(getMethod);

                

                // CONVERT RESPONSE STRING TO JSON ARRAY
                JSONArray ja = new JSONArray(result);

                // ITERATE THROUGH AND RETRIEVE CLUB FIELDS
                int n = ja.length();
                for (int i = 0; i < n; i++) {
                    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
                    JSONObject jo = ja.getJSONObject(i);

                    // RETRIEVE EACH JSON OBJECT'S FIELDS
                    long id = jo.getLong("id");
                    String name = jo.getString("name");
                    String address = jo.getString("address");
                    String country = jo.getString("country");
                    String zip = jo.getString("zip");
                    double clat = jo.getDouble("lat");
                    double clon = jo.getDouble("lon");
                    String url = jo.getString("url");
                    String number = jo.getString("number");

                    // CONVERT DATA FIELDS TO CLUB OBJECT
                    Club c = new Club(id, name, address, country, zip, clat, clon, url, number);
                    ret.add(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // RETURN LIST OF CLUBS
            return ret;
        }

    }
   */
    
/**ADD to insert gas station from Json
     * @return 
     * @throws JSONException */
    /*
    protected void retrieveAndAddCities() throws IOException, JSONException {
    	String BASE_URL = "http://ufo-project.appspot.com/";
    	double lat =32.03217;
    	double lon =34.79936;
    	String getUrl = BASE_URL + String.format("get_stations?distance=0.1&latitude=%f&longitude=%f", lat, lon);
    	//getUrl = "http://www.flickr.com/services/rest/?method=flickr.test.echo&format=json&api_key=f9a8c211dd97025c2c222503be4e46a8";
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpResponse response = null;
        HttpGet getMethod = new HttpGet(getUrl);
    	final StringBuilder json = new StringBuilder();
        try {
        	response = httpClient.execute(getMethod);
        	// CONVERT RESPONSE TO STRING
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        }catch (IOException e){
            Log.e(LOG_TAG+ "1", e.getMessage());
        }
        */
        /*// Create markers for the city data.
        // Must run this on the UI thread since it's a UI operation.
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    createMarkersFromJson(json.toString());
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error processing JSON", e);
                }
            }
        });*/
   // }
    
   /* void createMarkersFromJson(String json) throws JSONException {
        // De-serialize the JSON string into an array of city objects
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < 1/*jsonArray.length()*///; i++) {
            // Create a marker for each city in the JSON data.
           // JSONObject jsonObj = jsonArray.getJSONObject(i);
           // mMap.addMarker(new MarkerOptions()
         //       .title(jsonObj.getString("COMPANY"))
        //        .snippet("paz")
       //         .position(BEGIN
                      /*  jsonObj.getJSONArray("LOCATION").getDouble(0),
                        jsonObj.getJSONArray("latlng").getDouble(1)*/
           //      )
        //    );
       // }
    }
  

	
    



