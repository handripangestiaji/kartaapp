package com.karta.listadapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.karta.R;
import com.karta.listadapter.ListMenuAdapter.ViewHolder;
import com.karta.model.CategoryModel;
import com.karta.model.MenuModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
 
public class ListMenu extends ArrayAdapter<MenuModel> {
	 private final Context context;
	 private List<MenuModel> MenuModelList = null;
	 private ArrayList<MenuModel> arraylist;
	 
	 protected ImageLoader imageLoader;
	 
	 private DisplayImageOptions options;
	 private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	 
	 public ListMenu(Context context, ArrayList<MenuModel> menu) {		 
		 super(context, R.layout.list_menu, menu);
		 
		 this.context=context;
		 this.MenuModelList = menu;
		 this.arraylist = new ArrayList<MenuModel>();
		 this.arraylist.addAll(menu);
	 }
	 
	public class ViewHolder {
		TextView name;
		TextView description;
		TextView price;
		TextView rating;
		TextView distance;
		TextView restoran_name;
		TextView restoran_address;
		ImageView thumb_image;
	}

		 
	 public View getView(int position,View view,ViewGroup parent) {
		 LayoutInflater inflater=LayoutInflater.from(context);
//		 View rowView=inflater.inflate(R.layout.list_menu, null, true);
		 
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.list_menu, null, true);
			holder.name = (TextView) view.findViewById(R.id.menu_title);
			holder.price = (TextView) view.findViewById(R.id.menu_price);
			holder.rating = (TextView) view.findViewById(R.id.menu_rating);
			holder.distance = (TextView) view.findViewById(R.id.restorant_distance);
			holder.restoran_name = (TextView) view.findViewById(R.id.restoran_name);
			holder.restoran_address = (TextView) view.findViewById(R.id.restoran_address);
			holder.thumb_image = (ImageView) view.findViewById(R.id.img_menu);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		// Set the results into TextViews
		holder.name.setText(MenuModelList.get(position).getName());
		holder.price.setText(MenuModelList.get(position).getCurrency() + String.valueOf(MenuModelList.get(position).getPrice()));
		holder.rating.setText(String.valueOf(MenuModelList.get(position).getRating()));
		holder.distance.setText("2.1 Miles");
		holder.restoran_name.setText(MenuModelList.get(position).getRestaurant().getName());
		holder.restoran_address.setText(MenuModelList.get(position).getRestaurant().getAddress());
		
		if(view != null)
			ImageLoader.getInstance().displayImage(MenuModelList.get(position).getThumb_image(), holder.thumb_image, options, animateFirstListener);

		 return view;
	 }
	 
		private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

			static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if (loadedImage != null) {
					ImageView imageView = (ImageView) view;
					boolean firstDisplay = !displayedImages.contains(imageUri);
					if (firstDisplay) {
						FadeInBitmapDisplayer.animate(imageView, 500);
						displayedImages.add(imageUri);
					}
				}
			}
		}
	 
		// Filter Class
		public void filter(String charText) {
			charText = charText.toLowerCase(Locale.getDefault());
			MenuModelList.clear();
			if (charText.length() < 2) {
//				MenuModelList.addAll(arraylist);
			} 
			else 
			{
				for (MenuModel menu : arraylist) 
				{				
					Boolean finished = false;
					if (menu.getName().toLowerCase(Locale.getDefault()).contains(charText)) 
					{
						MenuModelList.add(menu);
						finished = true;
					}
					else if (menu.getRestaurant().getAddress().toLowerCase(Locale.getDefault()).contains(charText)) 
					{
						MenuModelList.add(menu);
						finished = true;
					}
					
					if(!finished)
					{
						for(String ingredient : menu.getIngredients())
						{
							if (ingredient.toLowerCase(Locale.getDefault()).contains(charText)) 
							{
								MenuModelList.add(menu);
								finished = true;
							}					
						}
					}
					
					if(!finished)
					{
						for(CategoryModel category : menu.getCategory())
						{
							if (category.getName().toLowerCase(Locale.getDefault()).contains(charText)) 
							{
								MenuModelList.add(menu);
								finished = true;
							}					
						}
					}				
				}
			}
			notifyDataSetChanged();
		}	 
	 
}