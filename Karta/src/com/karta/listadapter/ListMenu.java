package com.karta.listadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.karta.R;
 
public class ListMenu extends ArrayAdapter<String> {
	 private final Activity context;
	 private final String[] title;
	 private final String[] rating;
	 private final Integer[] image;
	 
	 public ListMenu(Activity context, String[] title, Integer[] image, String[] rating) {		 
		 super(context, R.layout.list_menu, title);
		 
		 this.context=context;
		 this.title=title;
		 this.rating=rating;
		 this.image=image;
	 }
		 
	 public View getView(int position,View view,ViewGroup parent) {
		 LayoutInflater inflater=context.getLayoutInflater();
		 View rowView=inflater.inflate(R.layout.list_menu, null, true);
		 
		 TextView menu_title = (TextView) rowView.findViewById(R.id.menu_title);
		 ImageView menu_image = (ImageView) rowView.findViewById(R.id.img_menu);
		 TextView menu_rating = (TextView) rowView.findViewById(R.id.menu_rating);
		 
		 menu_title.setSelected(true);
		 
		 menu_title.setText(title[position]);
		 menu_image.setImageResource(image[position]);
		 menu_rating.setText(rating[position]);

		 return rowView;
	 }
	 
}