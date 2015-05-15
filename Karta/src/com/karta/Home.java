package com.karta;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.karta.listadapter.ListCategory;

public class Home extends Activity implements ISideNavigationCallback{

    private SideNavigationView sideNavigationView;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.layout_with_header);
	    RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
	    getLayoutInflater().inflate(R.layout.home, container);
	    
	    TextView Title = (TextView) findViewById(R.id.title_bar);	    
	    TextView SubTitle = (TextView) findViewById(R.id.sub_title_bar);	 
	     
	    Title.setText("Karta");
	    SubTitle.setText("");
	    
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
    	
    	ListView list;

    	final String[] title ={
	    	 "Best Slice in Town",
	    	 "Savory Sushi",
	    	 "Craving Curry?",
	    	 "Something Sweet",
	    	 "Burger and Fries",
	    	 "Chicken"
    	};
    	 
    	Integer[] image={
	    	 R.drawable.pizza,
	    	 R.drawable.sushi,
	    	 R.drawable.cury,
	    	 R.drawable.sweet,
	    	 R.drawable.burger,
	    	 R.drawable.chicken
    	};
    	
    	ListCategory adapter=new ListCategory(this, title, image);
		list=(ListView)findViewById(R.id.list_category);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {
			 
			 @Override
			 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 String Selecteditem= title[+position];
//				 Toast.makeText(getApplicationContext(), Selecteditem, Toast.LENGTH_SHORT).show();			 
				 
				 Intent i = new Intent(Home.this, Menu.class);
				 startActivity(i);          
				 
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
	                i = new Intent(Home.this, Home.class);
	                startActivity(i);          

	                break;

	            case R.id.side_navigation_menu_item2:
	                i = new Intent(Home.this, Search.class);
	                startActivity(i);          

	                break;

	            case R.id.side_navigation_menu_item3:
	                i = new Intent(Home.this, Home.class);
	                startActivity(i);          

	                break;

	            case R.id.side_navigation_menu_item4:
	                i = new Intent(Home.this, Home.class);
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
