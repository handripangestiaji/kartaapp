package com.karta;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.karta.listadapter.ListComment;

public class MenuDetail extends Activity implements ISideNavigationCallback {

	private SideNavigationView sideNavigationView;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.layout_with_header);
	    RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
	    getLayoutInflater().inflate(R.layout.detail_menu, container);
	    
	    TextView Title = (TextView) findViewById(R.id.title_bar);	    
	    TextView SubTitle = (TextView) findViewById(R.id.sub_title_bar);	 
	    
	    Title.setText("Pizza name one");
	    SubTitle.setText("Ledo's Pizza | 12345 Main St.");

	    sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
        sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
        sideNavigationView.setMenuClickCallback(this);
		        
    	((ImageView) findViewById(R.id.main_menu)).setOnClickListener(btnClick);

    	((TextView) findViewById(R.id.btn_view_business)).setOnClickListener(btnClick);
    	((TextView) findViewById(R.id.btn_leave_review)).setOnClickListener(btnClick);
    	
    	ListView list;

    	final String[] title ={
	    	 "Lebron James | Mar, 14, 2015",
	    	 "Brian Beavers | Mar, 5, 2015",
	    	 "Kevin Williams | Feb, 12, 2015",
	    	 "Michael Jordan | Jan, 21, 2015"
    	};
    	 
    	String[] rating ={
   	    	 "4.0",
   	    	 "4.5",
   	    	 "3.5",
   	    	 "3.0"
       	};

    	String[] comment ={
      	    	 "Review goes here Review goes here Review goes here Review goes here Review goes here",
      	    	 "Review goes here Review goes here Review goes here Review goes here Review goes here Review goes here Review goes here Review goes here Review goes here",
      	    	 "Review goes here Review goes here Review goes here Review goes here Review goes here Review goes here",
      	    	 "Review goes here Review goes here Review goes here Review goes here"
      	};

    	Integer[] image={
	    	 R.drawable.p2,
	    	 R.drawable.p1,
	    	 R.drawable.p3,
	    	 R.drawable.p5
    	};
    	    	
    	ListComment adapter=new ListComment(this, title, comment, image, rating);
		list=(ListView)findViewById(R.id.list_comment);
		list.setAdapter(adapter);
		
		setListViewHeightBasedOnChildren(list);		

//		Toast.makeText(getApplicationContext(), String.valueOf(totalHeight), Toast.LENGTH_SHORT).show();			 
		        
	    ScrollView mainScroll = (ScrollView) findViewById(R.id.main_scroll_view);
        mainScroll.smoothScrollTo(0, 0);
		
		list.setOnItemClickListener(new OnItemClickListener() {
			 
			 @Override
			 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 String Selecteditem= title[+position];
				 Toast.makeText(getApplicationContext(), Selecteditem, Toast.LENGTH_SHORT).show();			 
			 }
		 });				    	
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
    	
        Intent i = new Intent(MenuDetail.this, Login.class);
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
                    case R.id.btn_view_business:{                            
                        Intent i = new Intent(MenuDetail.this, BusinessDetail.class);
                        startActivity(i);          
                    	
                    	break;
                    }
                    case R.id.btn_leave_review:{                            
                        Intent i = new Intent(MenuDetail.this, LeaveReview.class);
                        startActivity(i);          
                    	
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
                i = new Intent(MenuDetail.this, Home.class);
                startActivity(i);          

                break;

            case R.id.side_navigation_menu_item2:
                i = new Intent(MenuDetail.this, Home.class);
                startActivity(i);          

                break;

            case R.id.side_navigation_menu_item3:
                i = new Intent(MenuDetail.this, Home.class);
                startActivity(i);          

                break;

            case R.id.side_navigation_menu_item4:
                i = new Intent(MenuDetail.this, Home.class);
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
