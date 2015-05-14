package com.karta;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.karta.listadapter.ListMenu;

public class BusinessDetail extends Activity implements ISideNavigationCallback {

	private SideNavigationView sideNavigationView;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.layout_with_header);
	    RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
	    getLayoutInflater().inflate(R.layout.detail_business, container);
	    
	    TextView Title = (TextView) findViewById(R.id.title_bar);	    
	    TextView SubTitle = (TextView) findViewById(R.id.sub_title_bar);	 
	    
	    Title.setText("Ledo's Pizza");
	    SubTitle.setText("12345 Main St.");

	    sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
        sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
        sideNavigationView.setMenuClickCallback(this);
		        
    	((ImageView) findViewById(R.id.main_menu)).setOnClickListener(btnClick);

    	ListView listTopRatedMenu;
    	ListView listFullMenu;

    	final String[] title ={
   	    	 "Pizza name one",
   	    	 "Pizza name two",
   	    	 "Pizza name three"
       	};
       	 
       	String[] rating ={
      	    	 "4.0",
      	    	 "4.5",
      	    	 "2.5"
   		};

       	Integer[] image={
   	    	 R.drawable.thumb,
   	    	 R.drawable.thumb,
   	    	 R.drawable.thumb
   	    };
       	
    	final ListMenu adapter=new ListMenu(this, title, image, rating);
		listTopRatedMenu=(ListView)findViewById(R.id.list_top_rated_menu);
		listTopRatedMenu.setAdapter(adapter);
		
		setListViewHeightBasedOnChildren(listTopRatedMenu);		
		        		
		listTopRatedMenu.setOnItemClickListener(new OnItemClickListener() {			 
			@Override
			 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 String Selecteditem= title[+position];
//				 Toast.makeText(getApplicationContext(), Selecteditem, Toast.LENGTH_SHORT).show();			 

				 Intent i = new Intent(BusinessDetail.this, MenuDetail.class);
				 startActivity(i);          
			 }
		 });				    	
		

    	final String[] title1 ={
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
       	 
       	String[] rating1 ={
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

       	Integer[] image1={
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

       	final ListAdapter adapter1=new ListMenu(this, title1, image1, rating1);
		listFullMenu=(ListView)findViewById(R.id.list_full_menu);
		listFullMenu.setAdapter(adapter);
		
		setListViewHeightBasedOnChildren(listFullMenu);		
		        		
		listFullMenu.setOnItemClickListener(new OnItemClickListener() {			 
			@Override
			 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 String Selecteditem= title1[+position];
//				 Toast.makeText(getApplicationContext(), Selecteditem, Toast.LENGTH_SHORT).show();			
				 
				 Intent i = new Intent(BusinessDetail.this, MenuDetail.class);
				 startActivity(i);          
			 }
		 });	
		
	    ScrollView mainScroll = (ScrollView) findViewById(R.id.main_scroll_view);
        mainScroll.smoothScrollTo(0, 0);
        
        EditText inputSearch = (EditText) findViewById(R.id.inputSearch);	
        inputSearch.addTextChangedListener(new TextWatcher() {
            
            @Override
    	    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
    	        // When user changed the Text
//            	((ArrayAdapter<?>)BusinessDetail.this.adapter).getFilter().filter(cs);   
				 Toast.makeText(getApplicationContext(), cs, Toast.LENGTH_SHORT).show();			 
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
                i = new Intent(BusinessDetail.this, Home.class);
                startActivity(i);          

                break;

            case R.id.side_navigation_menu_item2:
                i = new Intent(BusinessDetail.this, Home.class);
                startActivity(i);          

                break;

            case R.id.side_navigation_menu_item3:
                i = new Intent(BusinessDetail.this, Home.class);
                startActivity(i);          

                break;

            case R.id.side_navigation_menu_item4:
                i = new Intent(BusinessDetail.this, Home.class);
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
