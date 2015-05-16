package com.karta.listadapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.karta.LeaveReview;
import com.karta.MenuDetail;
import com.karta.R;
import com.karta.model.MenuModel;
import android.view.View.OnClickListener; 

public class ListMenuAdapter extends BaseAdapter {
	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<MenuModel> MenuModelList = null;
	private ArrayList<MenuModel> arraylist;
 
	public ListMenuAdapter(Context context, List<MenuModel> MenuModelList) {
		mContext = context;
		this.MenuModelList = MenuModelList;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<MenuModel>();
		this.arraylist.addAll(MenuModelList);
	}
 
	public class ViewHolder {
		TextView name;
		TextView description;
		TextView price;
		TextView rating;
		TextView distance;
		TextView restoran_name;
	}
 
	@Override
	public int getCount() {
		return MenuModelList.size();
	}
 
	@Override
	public MenuModel getItem(int position) {
		return MenuModelList.get(position);
	}
 
	@Override
	public long getItemId(int position) {
		return position;
	}
 
	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.list_menu, null, true);
			holder.name = (TextView) view.findViewById(R.id.menu_title);
			holder.price = (TextView) view.findViewById(R.id.menu_price);
			holder.rating = (TextView) view.findViewById(R.id.menu_rating);
			holder.distance = (TextView) view.findViewById(R.id.restorant_distance);
			holder.restoran_name = (TextView) view.findViewById(R.id.restoran_name);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.name.setText(MenuModelList.get(position).getName());
		holder.price.setText(String.valueOf(MenuModelList.get(position).getPrice()));
		holder.rating.setText(String.valueOf(MenuModelList.get(position).getRating()));
		holder.distance.setText("2.1");
		holder.restoran_name.setText("Ledo's Pizza");
 
		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				if(mContext.getClass().getSimpleName().equals("Search")){
					Intent intent = new Intent(mContext, MenuDetail.class);
//					intent.putExtra("rank",(MenuModelList.get(position).getRank()));
//					intent.putExtra("country",(MenuModelList.get(position).getCountry()));
//					intent.putExtra("population",(MenuModelList.get(position).getPopulation()));
					mContext.startActivity(intent);					
				}
				else if(mContext.getClass().getSimpleName().equals("AddReview")){
					Intent intent = new Intent(mContext, LeaveReview.class);
//					intent.putExtra("rank",(MenuModelList.get(position).getRank()));
//					intent.putExtra("country",(MenuModelList.get(position).getCountry()));
//					intent.putExtra("population",(MenuModelList.get(position).getPopulation()));
					mContext.startActivity(intent);					
				}
			}
		});
 
		return view;
	}
 
	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		MenuModelList.clear();
		if (charText.length() < 2) {
//			MenuModelList.addAll(arraylist);
		} 
		else 
		{
			for (MenuModel wp : arraylist) 
			{
				if (wp.getDescription().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					MenuModelList.add(wp);
				}
				else if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					MenuModelList.add(wp);
				}
				else if (wp.getCategory().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					MenuModelList.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}	 
}