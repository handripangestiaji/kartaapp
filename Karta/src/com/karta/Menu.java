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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.karta.listadapter.ListMenu;
import com.karta.listadapter.ListMenuAdapter;
import com.karta.model.CategoryModel;
import com.karta.model.MenuModel;
import com.karta.model.RestaurantModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Menu extends Activity implements ISideNavigationCallback {

	private SideNavigationView sideNavigationView;
	
	Integer idCategory = 0;
	String CategoryName = "";
	
	ListMenu adapter;
	
    Context mContext;
    SwipeMenuListView list;	
	private ArrayList<MenuModel> arraylist = new ArrayList<MenuModel>();

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;

		Bundle extras = getIntent().getExtras();
	    if (extras != null)
	    {
	        idCategory = extras.getInt("IdCategory");
	        CategoryName = extras.getString("CategoryName");
	    }
	    
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.layout_with_header);
	    RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
	    getLayoutInflater().inflate(R.layout.menu, container);
	    
	    TextView Title = (TextView) findViewById(R.id.title_bar);	    
	    TextView SubTitle = (TextView) findViewById(R.id.sub_title_bar);	 
	    
	    Title.setText(CategoryName);
	    SubTitle.setText("Washington, D.C.");
	    
        sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
        sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
        sideNavigationView.setMenuClickCallback(this);
		        
    	((ImageView) findViewById(R.id.main_menu)).setOnClickListener(btnClick);
    	    			
		RequestParams params = new RequestParams();
		AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://karta.dreamcube.co.id/v1/a-p-i-karta/menulist.json?category=" + idCategory.toString() , params ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {                		
                        JSONArray obj = new JSONArray(response);
                        
                      	for (int i = 0; i < obj.length(); i++) 
                		{
                          	JSONObject Menu = new JSONObject(obj.getString(i));
                      		
                          	// set category
                            JSONArray listCategory = new JSONArray(Menu.getString("category"));
                          	CategoryModel category[] = new CategoryModel[listCategory.length()];
                          	for(int j=0 ; j<listCategory.length() ; j++)
                          	{
                              	JSONObject responeCategory = new JSONObject(listCategory.getString(j));
                          		
                          		CategoryModel tempCategory = new CategoryModel(); 
                       			tempCategory.setId(responeCategory.getInt("id"));
                    			tempCategory.setName(responeCategory.getString("name"));
                       			tempCategory.setImage(responeCategory.getString("image"));
                       			
                       			category[j] = tempCategory;
                          	}
                          	
                          	// set restaurant
                          	JSONObject responeRestaurant = new JSONObject(Menu.getString("restaurants"));
                          	RestaurantModel restaurant = new RestaurantModel();
                          	restaurant.setId(responeRestaurant.getInt("id"));
                          	restaurant.setName(responeRestaurant.getString("name"));
                          	restaurant.setDescription(responeRestaurant.getString("description"));
                          	restaurant.setAddress(responeRestaurant.getString("address"));
                          	restaurant.setLatitude(responeRestaurant.getJSONObject("location").getDouble("latitude"));
                          	restaurant.setLongitude(responeRestaurant.getJSONObject("location").getDouble("longitude"));

                          	// set ingredients
                            JSONArray listIngredients = new JSONArray(Menu.getString("ingredients"));
                          	String[] ingredients = new String[listIngredients.length()];
                          	for(int j=0 ; j<listIngredients.length() ; j++)
                          	{                       			
                       			ingredients[j] = listIngredients.getString(j);                       			
                          	}
                          	
                          	MenuModel tempMenu = new MenuModel();
                   			tempMenu.setId(Menu.getInt("id"));
                			tempMenu.setName(Menu.getString("name"));
                   			tempMenu.setCurrency(Menu.getString("currency"));
                   			tempMenu.setPrice(Menu.getDouble("price"));
                   			tempMenu.setRating(Menu.getDouble("rating"));
                   			tempMenu.setThumb_image(Menu.getString("thumb_image"));
                   			tempMenu.setDescription(Menu.getString("description"));
                   			tempMenu.setHalal(Menu.getBoolean("halal"));
                   			
                   			tempMenu.setCategory(category);
                   			tempMenu.setRestaurant(restaurant);                   			
                   			tempMenu.setIngredients(ingredients);

                   			//tempMenu.setImages();
                   			
                   			arraylist.add(tempMenu);                   			
                		}
                      	                      	
                    	adapter=new ListMenu(mContext, arraylist);
                		list = (SwipeMenuListView)findViewById(R.id.list_menu);
                		list.setAdapter(adapter);                		
                		
                		list.setOnItemClickListener(new OnItemClickListener() {
                			@Override
                			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                			Intent intent = new Intent(mContext, MenuDetail.class);
	        					intent.putExtra("IdMenu", arraylist.get(position).getId());
	        					intent.putExtra("MenuName", arraylist.get(position).getName());
	        					intent.putExtra("RestaurantName", arraylist.get(position).getRestaurant().getName());
	        					intent.putExtra("RestaurantAddress", arraylist.get(position).getRestaurant().getAddress());
	                			startActivity(intent);          
                			}
            			});

            			SwipeMenuCreator swipeMenu = new SwipeMenuCreator() {
                			@Override
                			public void create(SwipeMenu A) {
	                			SwipeMenuItem reviewItem = new SwipeMenuItem(mContext);
	                			reviewItem.setBackground(new ColorDrawable(Color.rgb(0x43, 0xA1, 0x47)));
	                			reviewItem.setWidth(dp2px(80));
	                			reviewItem.setIcon(R.drawable.star_review);
	                			reviewItem.setTitle("Review");
	                			reviewItem.setTitleSize(14);
	                			reviewItem.setTitleColor(Color.WHITE);
	                			A.addMenuItem(reviewItem);
                			}
            			};

            			list.setMenuCreator(swipeMenu);

            			list.setOnMenuItemClickListener(new OnMenuItemClickListener() {
	            			@Override
	            			public boolean onMenuItemClick(int position, SwipeMenu A, int index) {
		            			switch (index) {
		            			case 0:
		            			  Intent intent = new Intent(mContext, LeaveReview.class);
		            			  intent.putExtra("IdMenu", arraylist.get(position).getId());
		            			  intent.putExtra("MenuName", arraylist.get(position).getName());
		            			  intent.putExtra("RestaurantName", arraylist.get(position).getRestaurant().getName());
		            			  intent.putExtra("RestaurantAddress", arraylist.get(position).getRestaurant().getAddress());
		            			  startActivity(intent);          
		            			  break;
		            			}
		            			return false;
	            			}
            			});                     		
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
    
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
    	
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
    	
        Intent i = new Intent(Menu.this, Login.class);
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
