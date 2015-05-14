package com.karta.listadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.karta.R;
 
public class ListCategory extends ArrayAdapter<String> {
	 private final Activity context;
	 private final String[] title;
	 private final Integer[] image;
	 
	 public ListCategory(Activity context, String[] title, Integer[] image) {		 
		 super(context, R.layout.list_category, title);
		 
		 this.context=context;
		 this.title=title;
		 this.image=image;
	 }
		 
	 public View getView(int position,View view,ViewGroup parent) {
		 LayoutInflater inflater=context.getLayoutInflater();
		 View rowView=inflater.inflate(R.layout.list_category, null, true);
		 
		 TextView category_title = (TextView) rowView.findViewById(R.id.category_name);
		 ImageView category_image = (ImageView) rowView.findViewById(R.id.img_category);
		 
		 category_title.setText(title[position]);
		 category_image.setImageResource(image[position]);

		 return rowView;
	 }
	 
}