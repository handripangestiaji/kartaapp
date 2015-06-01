package com.karta;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.karta.listadapter.ListCategoryAdapter;
import com.karta.model.CategoryModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Home extends Activity implements ISideNavigationCallback{

    private SideNavigationView sideNavigationView;

    Context mContext;
	ListView list;	
	EditText editsearch;
	private ArrayList<CategoryModel> arraylist = new ArrayList<CategoryModel>();
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContext = this;

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.layout_with_header);
	    RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
	    getLayoutInflater().inflate(R.layout.home, container);
	    
	    ImageView LogoOnHeader = (ImageView) findViewById(R.id.logo_on_menu);	 
	    TextView Title = (TextView) findViewById(R.id.title_bar);	    
	    TextView SubTitle = (TextView) findViewById(R.id.sub_title_bar);	 
	     
	    LogoOnHeader.setVisibility(View.VISIBLE);
	    Title.setVisibility(View.GONE);
	    SubTitle.setVisibility(View.GONE);
	    
        sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
        sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
        sideNavigationView.setMenuClickCallback(this);
	    		
	    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String username = pref.getString("kartaUsername", null);
        Boolean isLogin = pref.getBoolean("isLogin", true);
        
        try
        {
	        if( (!isLogin) || username.equals("") ){
	        	logoutFunction();
	        }                
	        
	        if(username.equals("anynomous")){
	            Editor editor = pref.edit();
	
	            editor.putBoolean("isLogin", false); 
	        	editor.remove("username");
	        	 
	        	editor.commit(); // commit changes        	
	        }
        }catch(Exception ex){ logoutFunction(); }
        
    	((ImageView) findViewById(R.id.main_menu)).setOnClickListener(btnClick);
    	    	
		RequestParams params = new RequestParams();
		AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://karta.dreamcube.co.id/v1/a-p-i-karta/categories.json", params ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {                		
                        JSONArray obj = new JSONArray(response);
                        
                      	for (int i = 0; i < obj.length(); i++) 
                		{
                          	JSONObject Category = new JSONObject(obj.getString(i));
                      		
                          	JSONObject tempImage = Category.getJSONObject("image_url");
                          	String url_image = "http://karta.dreamcube.co.id" + tempImage.getString("path") + tempImage.getString("filename"); 
                          	
                          	CategoryModel temp = new CategoryModel();
                   			temp.setId(Category.getInt("id"));
                			temp.setName(Category.getString("name"));
                   			temp.setImage(url_image);
                   			arraylist.add(temp);                   			
                		}
                      	
	                  	list = (ListView) findViewById(R.id.list_category);
	                  	ListCategoryAdapter adapter = new ListCategoryAdapter(mContext, arraylist);
	               		list.setAdapter(adapter);                      	
                } 
                catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } 
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } 
                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });		
		    	
	}	
    	
	private void logoutFunction(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        Editor editor = pref.edit();

        editor.putBoolean("isLogin", false); 
    	editor.remove("username");
    	 
    	editor.commit(); // commit changes
    	
        Intent i = new Intent(Home.this, Login.class);
        startActivity(i);          
        finish();
	}
	
    @Override
    public void onBackPressed() {
        // hide menu if it shown
        if (sideNavigationView.isShown()) {
            sideNavigationView.hideMenu();
        } else {
        	moveTaskToBack(true);
            super.onBackPressed();
        }
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
