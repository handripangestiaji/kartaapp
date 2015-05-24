package com.karta;

import android.app.Activity;
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
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;
import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.google.android.gms.drive.query.internal.Operator;
import com.karta.listadapter.ListMenu;

public class Menu extends Activity implements ISideNavigationCallback {

	private SideNavigationView sideNavigationView;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.layout_with_header);
	    RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
	    getLayoutInflater().inflate(R.layout.menu, container);
	    
	    TextView Title = (TextView) findViewById(R.id.title_bar);	    
	    TextView SubTitle = (TextView) findViewById(R.id.sub_title_bar);	 
	    
	    Title.setText("Best Pizza");
	    SubTitle.setText("Washington, D.C.");
	    
        sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
        sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
        sideNavigationView.setMenuClickCallback(this);
		        
    	((ImageView) findViewById(R.id.main_menu)).setOnClickListener(btnClick);
    	
    	final String[] title ={
	    	 "Pizza name one",
	    	 "Pizza name two",
	    	 "Pizza name three",
	    	 "Pizza name four",
	    	 "Pizza name five",
	    	 "Pizza name six",
	    	 "Pizza name seven",
	    	 "Pizza name eight",
	    	 "Pizza name nine",
	    	 "Pizza name ten",
	    	 "Pizza name eleven",
	    	 "Pizza name twelve",
	    	 "Pizza name thirteen",
	    	 "Pizza name fourteen",
	    	 "Pizza name fiveteen",
	    	 "Pizza name sixteen",
	    	 "Pizza name seventeen",
	    	 "Pizza name eightteen"
    	};
    	 
    	String[] rating ={
   	    	 "4.0",
   	    	 "4.5",
   	    	 "2.5",
   	    	 "3.0",
   	    	 "3.5",
   	    	 "4.0",
   	    	 "4.5",
   	    	 "4.0",
   	    	 "4.5",
   	    	 "2.5",
   	    	 "3.0",
   	    	 "3.5",
   	    	 "4.0",
   	    	 "4.5",
   	    	 "4.0",
   	    	 "4.5",
   	    	 "2.5",
   	    	 "3.0" 
		};

    	Integer[] image={
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb,
	    	 R.drawable.thumb    	
	    };
    	
    	SwipeMenuListView list;
    	
    	ListMenu adapter=new ListMenu(this, title, image, rating);
		list=(SwipeMenuListView)findViewById(R.id.list_menu);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {
			 
			 @Override
			 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 String Selecteditem= title[+position];
				 
				 Intent i = new Intent(Menu.this, MenuDetail.class);
				 startActivity(i);          
			 }
		});
				
		SwipeMenuCreator swipeMenu = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem reviewItem = new SwipeMenuItem(getApplicationContext());
				reviewItem.setBackground(new ColorDrawable(Color.rgb(0x43, 0xA1, 0x47)));
				reviewItem.setWidth(dp2px(80));
				reviewItem.setIcon(R.drawable.star_review);
				reviewItem.setTitle("Review");
				reviewItem.setTitleSize(14);
				reviewItem.setTitleColor(Color.WHITE);
				menu.addMenuItem(reviewItem);
			}
		};

		list.setMenuCreator(swipeMenu);

		list.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				String Selecteditem= title[+position];
				switch (index) {
				case 0:
                    //Toast.makeText(getApplicationContext(), Selecteditem + "1", Toast.LENGTH_LONG).show();
	   				Intent i = new Intent(Menu.this, LeaveReview.class);
	   				startActivity(i);          
					break;
				}
				return false;
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
                i = new Intent(this, AddReview.class);
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
