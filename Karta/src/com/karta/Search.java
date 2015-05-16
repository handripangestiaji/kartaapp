package com.karta;

import java.util.ArrayList;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.karta.R.color;
import com.karta.listadapter.ListComment;
import com.karta.listadapter.ListMenu;
import com.karta.listadapter.ListMenuAdapter;
import com.karta.model.MenuModel;

public class Search extends Activity implements ISideNavigationCallback {

	private SideNavigationView sideNavigationView;
	
	ListView list;
	ListMenuAdapter adapter;
	EditText editsearch;
	ArrayList<MenuModel> arraylist = new ArrayList<MenuModel>();
    EditText inputSearch;	
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.layout_with_header);
	    RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
	    getLayoutInflater().inflate(R.layout.search, container);
	    
	    TextView Title = (TextView) findViewById(R.id.title_bar);	    
	    TextView SubTitle = (TextView) findViewById(R.id.sub_title_bar);	 
	    
	    inputSearch = (EditText) findViewById(R.id.keyword_search);
	    
	    Title.setText("Search");
	    SubTitle.setText("");

	    sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
        sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
        sideNavigationView.setMenuClickCallback(this);
		        
    	((ImageView) findViewById(R.id.main_menu)).setOnClickListener(btnClick);

    	String[] title ={
      	    	 "Pizza name one",
      	    	 "Pizza name two",
      	    	 "Pizza name three"
          	};
          	 
        double[] rating ={
         	    	 4.0,
         	    	 4.5,
         	    	 2.5
      		};

        Integer[] image={
      	    	 R.drawable.thumb,
      	    	 R.drawable.thumb,
      	    	 R.drawable.thumb
      	    };
          	
      	for (int i = 0; i < rating.length; i++) 
		{
      		String[] temp = {"asd", "dasadad"};
			MenuModel wp = new MenuModel(title[i], "description", rating[i], rating[i], rating[i], rating[i], "Pizza", "Lodon's Pizza", temp);
			arraylist.add(wp);
		}
      	
      	list = (ListView) findViewById(R.id.list_search_result);
      	adapter = new ListMenuAdapter(this, arraylist);
   		list.setAdapter(adapter);
   		adapter.filter("");
               	        
        inputSearch.addTextChangedListener(new TextWatcher() {	            
	        @Override
		    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	        	String text = String.valueOf(cs);
				adapter.filter(text);
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
        
        String[] category ={ "Pizza", "Sashimi", "Spainshi Tapas", "Tofu", "Dim Sum", "Chili Dogs" };

    	LinearLayout linLayout = (LinearLayout)findViewById(R.id.treding_category);
        for (int i = 0; i < category.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 5, 5, 5);
            final Button btn = new Button(this);
            btn.setText(category[i]);
            btn.setTextSize(12);
            btn.setLines(1);
            btn.setTextColor(getResources().getColor(R.color.buttonTextColor));
            btn.setBackgroundResource(R.drawable.button_custom);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    searchCategory(String.valueOf(btn.getText()));
                }
            });
            
            linLayout.addView(btn, params);
        }
        
	}	
    
    public void searchCategory(String keyword){
    	inputSearch.setText(keyword);
		adapter.filter(keyword);   	
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
    	
        Intent i = new Intent(Search.this, Login.class);
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
