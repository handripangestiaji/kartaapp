package com.karta.listadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.karta.R;
 
public class ListComment extends ArrayAdapter<String> {
	 private final Activity context;
	 private final String[] title;
	 private final String[] comment;
	 private final String[] rating;
	 private final Integer[] image;
	 
	 public ListComment(Activity context, String[] title, String[] comment, Integer[] image, String[] rating) {		 
		 super(context, R.layout.list_comment, title);
		 
		 this.context=context;
		 this.title=title;
		 this.rating=rating;
		 this.image=image;
		 this.comment=comment;
	 }
		 
	 public View getView(int position,View view,ViewGroup parent) {
		 LayoutInflater inflater=context.getLayoutInflater();
		 View rowView=inflater.inflate(R.layout.list_comment, null, true);
		 
		 TextView comment_title = (TextView) rowView.findViewById(R.id.comment_name_and_date);
		 ImageView comment_image = (ImageView) rowView.findViewById(R.id.img_user);
		 TextView comment_rating = (TextView) rowView.findViewById(R.id.comment_rating);
		 TextView comment_description = (TextView) rowView.findViewById(R.id.comment);
		 
		 comment_title.setText(title[position]);
		 comment_image.setImageResource(image[position]);
		 comment_rating.setText(rating[position]);
		 comment_description.setText(comment[position]);

		 return rowView;
	 }
	 
}