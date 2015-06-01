package com.karta;

import java.util.ArrayList;

import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karta.listadapter.ListMenuAdapter;
import com.karta.model.MenuModel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Maps extends Activity implements OnMapReadyCallback,ISideNavigationCallback {
	private SideNavigationView sideNavigationView;
	
	ListView list;
	ListMenuAdapter adapter;
	EditText editsearch;
	ArrayList<MenuModel> arraylist = new ArrayList<MenuModel>();
    EditText inputSearch;	
    LocationManager mLocationManager;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
				
		setContentView(R.layout.layout_with_header);
	    RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
	    getLayoutInflater().inflate(R.layout.maps, container);
	    
	    TextView Title = (TextView) findViewById(R.id.title_bar);	    
	    TextView SubTitle = (TextView) findViewById(R.id.sub_title_bar);	 
	    
	    inputSearch = (EditText) findViewById(R.id.keyword_search);
	    
	    Title.setText("Places");
	    SubTitle.setText("Wasington, D.C.");

	    sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
        sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
        sideNavigationView.setMenuClickCallback(this);
		        
    	((ImageView) findViewById(R.id.main_menu)).setOnClickListener(btnClick);
		        
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);	
        mapFragment.getMapAsync(this);
        
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        
        inputSearch.addTextChangedListener(new TextWatcher() {	            
	        @Override
		    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	        	String text = String.valueOf(cs);
			} 

	        @Override
		    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		        // TODO Auto-generated method stub		         
		    }
		     
		    @Override
		    public void afterTextChanged(Editable arg0) {
		        // TODO Auto-generated method stub                          
		    }	
        });
    }
    
    @Override
    public void onMapReady(GoogleMap map) {
        LatLng marker1 = new LatLng(38.931137, -77.049585);
        LatLng marker2 = new LatLng(39.01137, -77.349585);
        LatLng marker3 = new LatLng(38.731137, -77.1149585);

        //map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker1, 9));
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        
        map.addMarker(new MarkerOptions()
                .title("Ledo's Pizza")
                .snippet("The most popular pizza in U.S.")
                .position(marker1));                

        map.addMarker(new MarkerOptions()
	        .title("Ledo's Pizza")
	        .snippet("The most popular pizza in U.S.")
		    .position(marker2));                
	
        map.addMarker(new MarkerOptions()
	        .title("Ledo's Pizza")
	        .snippet("The most popular pizza in U.S.")
	        .position(marker3));                

    }  
    
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
        }
    };
    
    @Override
    public void onBackPressed() {
        // hide menu if it shown
        if (sideNavigationView.isShown()) {
            sideNavigationView.hideMenu();
        } else {
            super.onBackPressed();
        }
    }

    private void logoutFunction(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        Editor editor = pref.edit();

        editor.putBoolean("isLogin", false); 
    	editor.remove("username");
    	 
    	editor.commit(); // commit changes
    	
        Intent i = new Intent(Maps.this, Login.class);
        startActivity(i);          
        finish();
	} 
	
    private View.OnClickListener btnClick = new OnClickListener() {
		
        @Override
		public void onClick(View v) {
                switch(v.getId()){
                    case R.id.main_menu:{                            
                    	sideNavigationView.toggleMenu();
                    	
                    	break;
                    }
                }	                
	        }
	};
	
    @Override
    public void onSideNavigationItemClick(int itemId) {
    	Intent i;
    	switch (itemId) {
            case R.id.side_navigation_menu_item1:
                i = new Intent(this, Home.class);
                startActivity(i);          

                break;

            case R.id.side_navigation_menu_item2:
                i = new Intent(this, Search.class);
                startActivity(i);          

                break;

            case R.id.side_navigation_menu_item3:
                i = new Intent(this, Maps.class);
                startActivity(i);          

                break;

            case R.id.side_navigation_menu_item4:
                i = new Intent(this, Maps.class);
                startActivity(i);          

                break;

            case R.id.side_navigation_menu_item5:
            	logoutFunction();
                break;

            default:
                return;
        }
        finish();
    }        
            
}
