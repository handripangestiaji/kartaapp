package com.karta;

import java.util.Date;

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
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.View.OnClickListener;
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

import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.karta.listadapter.ListComment;
import com.karta.listadapter.ListCommentAdapter;
import com.karta.model.CategoryModel;
import com.karta.model.CommentModel;
import com.karta.model.MenuModel;
import com.karta.model.RestaurantModel;
import com.karta.model.UserModel;
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

public class LeaveReview extends Activity implements ISideNavigationCallback {

	private SideNavigationView sideNavigationView;

	static Context mContext;

	Integer idMenu = 0;
	String menuName = "";
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
	        idMenu = extras.getInt("IdMenu");
	        menuName = extras.getString("MenuName");
	        restaurantName = extras.getString("RestaurantName");
	        restaurantAddress = extras.getString("RestaurantAddress");
	    }

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.layout_with_header);
	    RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
	    getLayoutInflater().inflate(R.layout.leave_review, container);
	    
		relative = (RelativeLayout) findViewById(R.id.slider_frame);

		TextView Title = (TextView) findViewById(R.id.title_bar);	    
	    TextView SubTitle = (TextView) findViewById(R.id.sub_title_bar);	 
	    
	    Title.setText(menuName);
	    SubTitle.setText(restaurantName + " | " +  restaurantAddress);

	    sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
        sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
        sideNavigationView.setMenuClickCallback(this);
		        
    	((ImageView) findViewById(R.id.main_menu)).setOnClickListener(btnClick);
    	
		RequestParams params = new RequestParams();
		AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://karta.dreamcube.co.id/v1/a-p-i-karta/menu.json?id=" + idMenu.toString() , params ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {                		
                    JSONArray obj = new JSONArray(response);
                    
                  	JSONObject Menu = new JSONObject(obj.getString(0));
              		
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
                  	String ingredientsString = "";
                  	for(int j=0 ; j<listIngredients.length() ; j++)
                  	{                       			
               			ingredients[j] = listIngredients.getString(j);
               			ingredientsString += ingredients[j] + ((j < (listIngredients.length()-1)) ? ", " : "");
                  	}
                  	
                  	// set images
                    JSONArray listImages = new JSONArray(Menu.getString("images"));
                  	String[] images = new String[listImages.length()];
                  	for(int j=0 ; j<listImages.length() ; j++)
                  	{                       			
               			images[j] = listImages.getString(j);
                  	}
                  	
                  	MenuModel menu = new MenuModel();
           			menu.setId(Menu.getInt("id"));
           			menu.setName(Menu.getString("name"));
           			menu.setCurrency(Menu.getString("currency"));
           			menu.setPrice(Menu.getDouble("price"));
           			menu.setRating(Menu.getDouble("rating"));
           			menu.setThumb_image(Menu.getString("thumb_image"));
           			menu.setDescription(Menu.getString("description"));
           			menu.setHalal(Menu.getBoolean("halal"));
           			
           			menu.setCategory(category);
           			menu.setRestaurant(restaurant);                   			
           			menu.setIngredients(ingredients);
           			menu.setImages(images);
           			           			
        			ViewPager pager = (ViewPager) findViewById(R.id.pager);
        			TextView viewPrice = (TextView) findViewById(R.id.menu_price);
        			TextView viewRating = (TextView) findViewById(R.id.menu_rating);
        			TextView viewIngredients = (TextView) findViewById(R.id.menu_ingredients);
        			TextView viewDescription = (TextView) findViewById(R.id.menu_description);
        			
        			viewPrice.setText(menu.getCurrency() + String.valueOf(menu.getPrice()));
        			viewRating.setText(String.valueOf(menu.getRating()));        			
        			viewIngredients.setText(ingredientsString);
        			viewDescription.setText(menu.getDescription());
        			
        			pager.setAdapter(new ImageAdapter(mContext, menu.getImages()));
        			pager.setCurrentItem(0);

        			PageIndicator mIndicator;
        	        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        	        mIndicator.setViewPager(pager);

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
    	
        Intent i = new Intent(LeaveReview.this, Login.class);
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
 