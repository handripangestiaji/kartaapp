package com.karta;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.karta.model.CityModel;
import com.karta.model.MenuModel;
import com.karta.model.RestaurantModel;
import com.karta.model.StateModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class BusinessDetail extends Activity implements ISideNavigationCallback {

	private SideNavigationView sideNavigationView;
	
	ListMenu recomendedAdapter; 		
	ListMenu fullAdapter; 		
    SwipeMenuListView listTopRatedMenu;	
    SwipeMenuListView listFullMenu;	
   	static Context mContext;
	private RestaurantModel restaurantDetail = new RestaurantModel();
	
	Integer idRestaurant = 0;
	String restaurantName = "";
	String restaurantAddress = "";
	
	static Integer widthDevice = 0;
	static Integer heightDevice = 0;	
	static Integer maxHeightPager;
	
	static RelativeLayout relative;        								

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		heightDevice = displaymetrics.heightPixels;
		widthDevice = displaymetrics.widthPixels;
		maxHeightPager = 100;

		Bundle extras = getIntent().getExtras();
	    if (extras != null)
	    {
	        idRestaurant = extras.getInt("IdRestaurant");
	        restaurantName = extras.getString("RestaurantName");
	        restaurantAddress = extras.getString("RestaurantAddress");
	    }

	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.layout_with_header);
	    RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
	    getLayoutInflater().inflate(R.layout.detail_business, container);
	    
		relative = (RelativeLayout) findViewById(R.id.slider_frame);

	    TextView Title = (TextView) findViewById(R.id.title_bar);	    
	    TextView SubTitle = (TextView) findViewById(R.id.sub_title_bar);	 
	    
	    Title.setText(restaurantName);
	    SubTitle.setText(restaurantAddress);

	    sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
        sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
        sideNavigationView.setMenuClickCallback(this);
		        
    	((ImageView) findViewById(R.id.main_menu)).setOnClickListener(btnClick);
    	
		RequestParams params = new RequestParams();
		AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://karta.dreamcube.co.id/v1/a-p-i-karta/restaurants.json?id=" + idRestaurant.toString() , params ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {                		
                		JSONArray obj = new JSONArray(response);
                      	JSONObject Restaurant = new JSONObject(obj.getString(0));
                  		
                        JSONArray listMenu = new JSONArray(Restaurant.getString("full_menu"));
                      	MenuModel fullMenu[] = new MenuModel[listMenu.length()];
                      	for(int j=0 ; j<listMenu.length() ; j++)
                      	{
                          	JSONObject responeMenu = new JSONObject(listMenu.getString(j));
                      		
                      		MenuModel tempMenu = new MenuModel(); 
                   			tempMenu.setId(responeMenu.getInt("id"));
                			tempMenu.setName(responeMenu.getString("name"));
                			tempMenu.setCurrency(responeMenu.getString("currency"));
                			tempMenu.setPrice(responeMenu.getDouble("price"));
                			tempMenu.setRating(responeMenu.getDouble("rating"));
                			tempMenu.setHalal(responeMenu.getBoolean("halal"));
                			tempMenu.setDescription(responeMenu.getString("description"));
                   			tempMenu.setThumb_image(responeMenu.getString("thumb_image"));
                   			
                          	// set ingredients
                            JSONArray listIngredients = new JSONArray(responeMenu.getString("ingredients"));
                          	String[] ingredients = new String[listIngredients.length()];
                          	for(int k=0 ; k<listIngredients.length() ; k++)
                          	{                       			
                       			ingredients[k] = listIngredients.getString(k);                       			
                          	}
                          	tempMenu.setIngredients(ingredients);

                          	// set restaurant
                          	RestaurantModel restaurant = new RestaurantModel();
                          	restaurant.setId(Restaurant.getInt("id"));
                          	restaurant.setName(Restaurant.getString("name"));
                          	restaurant.setAddress(Restaurant.getString("address"));                          	
                          	tempMenu.setRestaurant(restaurant);
                   			
                   			fullMenu[j] = tempMenu;
                      	}
                      	
                        listMenu = new JSONArray(Restaurant.getString("recomended_menu"));
                      	MenuModel recomendedMenu[] = new MenuModel[listMenu.length()];
                      	for(int j=0 ; j<listMenu.length() ; j++)
                      	{
                          	JSONObject responeMenu = new JSONObject(listMenu.getString(j));
                      		
                      		MenuModel tempMenu = new MenuModel(); 
                   			tempMenu.setId(responeMenu.getInt("id"));
                			tempMenu.setName(responeMenu.getString("name"));
                			tempMenu.setCurrency(responeMenu.getString("currency"));
                			tempMenu.setPrice(responeMenu.getDouble("price"));
                			tempMenu.setRating(responeMenu.getDouble("rating"));
                			tempMenu.setHalal(responeMenu.getBoolean("halal"));
                			tempMenu.setDescription(responeMenu.getString("description"));
                   			tempMenu.setThumb_image(responeMenu.getString("thumb_image"));
                   			
                          	// set ingredients
                            JSONArray listIngredients = new JSONArray(responeMenu.getString("ingredients"));
                          	String[] ingredients = new String[listIngredients.length()];
                          	for(int k=0 ; k<listIngredients.length() ; k++)
                          	{                       			
                       			ingredients[k] = listIngredients.getString(k);                       			
                          	}
                          	tempMenu.setIngredients(ingredients);
                          	
                          	// set restaurant
                          	RestaurantModel restaurant = new RestaurantModel();
                          	restaurant.setId(Restaurant.getInt("id"));
                          	restaurant.setName(Restaurant.getString("name"));
                          	restaurant.setAddress(Restaurant.getString("address"));                          	
                          	tempMenu.setRestaurant(restaurant);

                          	recomendedMenu[j] = tempMenu;
                      	}
                      	
                      	// set images
                        JSONArray listImages = new JSONArray(Restaurant.getString("image_collection"));
                      	String[] images = new String[listImages.length()];
                      	for(int j=0 ; j<listImages.length() ; j++)
                      	{                       			
                   			images[j] = listImages.getString(j);
                      	}
                      	
                      	// set operation hours
                        JSONArray listOperation = new JSONArray(Restaurant.getString("operation"));
                      	String[] operations = new String[listOperation.length()];
                      	String operationString = ""; 
                      	for(int j=0 ; j<listOperation.length() ; j++)
                      	{                       			
                      		JSONObject responseOperation = new JSONObject(listOperation.getString(j));
                      		
                   			operations[j] = responseOperation.getString("day") + " " + responseOperation.getString("hour");
                   			operationString += operations[j] + "\n";
                      	}

                      	RestaurantModel tempRestaurant = new RestaurantModel();
               			tempRestaurant.setId(Restaurant.getInt("id"));
               			tempRestaurant.setName(Restaurant.getString("name"));
               			tempRestaurant.setAddress(Restaurant.getString("address"));
               			tempRestaurant.setDescription(Restaurant.getString("description"));
               			tempRestaurant.setRating(Restaurant.getDouble("rating"));          
               			tempRestaurant.setZipCode((Restaurant.getString("zip_code") == null) ? Restaurant.getString("zip_code") : "");          
               			tempRestaurant.setCity(new CityModel(0, Restaurant.getString("city"), new StateModel(0, Restaurant.getString("state"))));
               			
               			tempRestaurant.setMenu(fullMenu);
               			tempRestaurant.setMenuRecomended(recomendedMenu);
               			tempRestaurant.setProfileImage(images);
               			
               			restaurantDetail = tempRestaurant;               
               			
            			ViewPager pager = (ViewPager) findViewById(R.id.pager);
            			TextView viewRating = (TextView) findViewById(R.id.bussiness_rating);
            			TextView viewAddress = (TextView) findViewById(R.id.business_address);
            			TextView viewCity = (TextView) findViewById(R.id.business_city);
            			TextView viewOperationHours = (TextView) findViewById(R.id.operation_hour);
            			
            			viewAddress.setText(restaurantDetail.getAddress());
            			viewCity.setText(restaurantDetail.getCity().getName() + " " + restaurantDetail.getCity().getState().getName() + " " + restaurantDetail.getZipCode());
            			viewRating.setText(String.valueOf(restaurantDetail.getRating()));
            			viewOperationHours.setText(operationString);
            			
            			pager.setAdapter(new ImageAdapter(mContext, restaurantDetail.getProfileImage()));
            			pager.setCurrentItem(0);

            			PageIndicator mIndicator;
            	        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
            	        mIndicator.setViewPager(pager);
               			
               			final ArrayList<MenuModel> arraylistFullMenu = new ArrayList<MenuModel>(Arrays.asList(fullMenu));
               			final ArrayList<MenuModel> arraylistRecomendedMenu = new ArrayList<MenuModel>(Arrays.asList(recomendedMenu));
                      	                      	
                    	fullAdapter = new ListMenu(mContext, arraylistFullMenu);
                		listFullMenu = (SwipeMenuListView)findViewById(R.id.list_full_menu);
                		listFullMenu.setAdapter(fullAdapter);                		
                		
                    	recomendedAdapter = new ListMenu(mContext, arraylistRecomendedMenu);
                		listTopRatedMenu = (SwipeMenuListView)findViewById(R.id.list_top_rated_menu);
                		listTopRatedMenu.setAdapter(recomendedAdapter);                		
                		
                		listTopRatedMenu.setOnItemClickListener(new OnItemClickListener() {
                			@Override
                			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                			Intent intent = new Intent(mContext, MenuDetail.class);
	        					intent.putExtra("IdMenu", arraylistRecomendedMenu.get(position).getId());
	        					intent.putExtra("MenuName", arraylistRecomendedMenu.get(position).getName());
	        					intent.putExtra("RestaurantName", arraylistRecomendedMenu.get(position).getRestaurant().getName());
	        					intent.putExtra("RestaurantAddress", arraylistRecomendedMenu.get(position).getRestaurant().getAddress());
	                			startActivity(intent);          
                			}
            			});
                		
                		listFullMenu.setOnItemClickListener(new OnItemClickListener() {
                			@Override
                			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                			Intent intent = new Intent(mContext, MenuDetail.class);
	        					intent.putExtra("IdMenu", arraylistFullMenu.get(position).getId());
	        					intent.putExtra("MenuName", arraylistFullMenu.get(position).getName());
	        					intent.putExtra("RestaurantName", arraylistFullMenu.get(position).getRestaurant().getName());
	        					intent.putExtra("RestaurantAddress", arraylistFullMenu.get(position).getRestaurant().getAddress());
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
            			
            			listFullMenu.setMenuCreator(swipeMenu);
            			listTopRatedMenu.setMenuCreator(swipeMenu);

            			listFullMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
	            			@Override
	            			public boolean onMenuItemClick(int position, SwipeMenu A, int index) {
		            			switch (index) {
		            			case 0:
		            			  Intent intent = new Intent(mContext, LeaveReview.class);
		            			  intent.putExtra("IdMenu", arraylistFullMenu.get(position).getId());
		            			  intent.putExtra("MenuName", arraylistFullMenu.get(position).getName());
		            			  intent.putExtra("RestaurantName", arraylistFullMenu.get(position).getRestaurant().getName());
		            			  intent.putExtra("RestaurantAddress", arraylistFullMenu.get(position).getRestaurant().getAddress());
		            			  startActivity(intent);          
		            			  break;
		            			}
		            			return false;
	            			}
            			});                     		
            			
            			listTopRatedMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
	            			@Override
	            			public boolean onMenuItemClick(int position, SwipeMenu A, int index) {
		            			switch (index) {
		            			case 0:
		            			  Intent intent = new Intent(mContext, LeaveReview.class);
		            			  intent.putExtra("IdMenu", arraylistRecomendedMenu.get(position).getId());
		            			  intent.putExtra("MenuName", arraylistRecomendedMenu.get(position).getName());
		            			  intent.putExtra("RestaurantName", arraylistRecomendedMenu.get(position).getRestaurant().getName());
		            			  intent.putExtra("RestaurantAddress", arraylistRecomendedMenu.get(position).getRestaurant().getAddress());
		            			  startActivity(intent);          
		            			  break;
		            			}
		            			return false;
	            			}
            			});
            			
                   		setListViewHeightBasedOnChildren(listFullMenu);		
                   		setListViewHeightBasedOnChildren(listTopRatedMenu);		

                   		ScrollView mainScroll = (ScrollView) findViewById(R.id.main_scroll_view);
                        mainScroll.smoothScrollTo(0, 0);

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
	
	private static class ImageAdapter extends PagerAdapter {

		private static String[] IMAGE_URLS = {};

		private LayoutInflater inflater;
		private DisplayImageOptions options;

		ImageAdapter(Context context, String[] image_url) {
			inflater = LayoutInflater.from(context);
			IMAGE_URLS = image_url;

			options = new DisplayImageOptions.Builder()
					.showImageForEmptyUri(R.drawable.ic_empty)
					.showImageOnFail(R.drawable.ic_error)
					.resetViewBeforeLoading(true)
					.cacheOnDisk(true)
					.postProcessor(new BitmapProcessor() {
					    @Override
					    public Bitmap process(Bitmap bmp) {					    	
					    	int originalWidth = bmp.getWidth();
					    	int originalHeight = bmp.getHeight();
					    	float scale = 1.0f * widthDevice / originalWidth;
					    	int newHeight = (int) (scale * originalHeight);
					    	
					    	if(newHeight > maxHeightPager) {
						    	maxHeightPager =  newHeight; 
						    	relative.getLayoutParams().height = maxHeightPager;  
			        			relative.getLayoutParams().width = widthDevice;					        			
					    	}
					    						        
					        return Bitmap.createScaledBitmap(bmp, widthDevice, newHeight, false);
					    }
					})
					.bitmapConfig(Bitmap.Config.RGB_565)
					.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
					.considerExifParams(true)
					.displayer(new FadeInBitmapDisplayer(300))
					.build();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return IMAGE_URLS.length;
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.image_slider_item, view, false);
			assert imageLayout != null;
			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

			ImageLoader.getInstance().displayImage(IMAGE_URLS[position], imageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
					}
					Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

					spinner.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					spinner.setVisibility(View.GONE);
				}
			});

			view.addView(imageLayout, 0);
			return imageLayout;
		}
		
		@Override
		public int getItemPosition(Object object) {
	 		Toast.makeText(mContext, String.valueOf(super.getItemPosition(object)), Toast.LENGTH_LONG).show();
			
			return super.getItemPosition(object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}
	}
    
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
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
    	
        Intent i = new Intent(BusinessDetail.this, Login.class);
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
