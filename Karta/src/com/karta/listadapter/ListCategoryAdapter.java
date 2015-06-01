package com.karta.listadapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karta.Home;
import com.karta.LeaveReview;
import com.karta.Menu;
import com.karta.MenuDetail;
import com.karta.R;
import com.karta.model.CategoryModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ListCategoryAdapter extends BaseAdapter {
	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<CategoryModel> CategoryModelList = null;
	private ArrayList<CategoryModel> arraylist;
	protected ImageLoader imageLoader;
	
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	public ListCategoryAdapter(Context context, List<CategoryModel> CategoryModelList) {
		mContext = context;
		this.CategoryModelList = CategoryModelList;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<CategoryModel>();
		this.arraylist.addAll(CategoryModelList);
		
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.big_loading_image)
				.showImageForEmptyUri(R.drawable.big_no_image)
				.showImageOnFail(R.drawable.big_cant_load_image)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(0)).build();
		
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));

	}
 
	public class ViewHolder {
		TextView name;
		ImageView image;
	}
 
	@Override
	public int getCount() {
		return CategoryModelList.size();
	}
 
	@Override
	public CategoryModel getItem(int position) {
		return CategoryModelList.get(position);
	}
 
	@Override
	public long getItemId(int position) {
		return position;
	}
 
	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.list_category, null, true);
			holder.name = (TextView) view.findViewById(R.id.category_name);
			holder.image = (ImageView) view.findViewById(R.id.img_category);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.name.setText(CategoryModelList.get(position).getName());
		
		if(view != null)
			ImageLoader.getInstance().displayImage(CategoryModelList.get(position).getImage(), holder.image, options, animateFirstListener);
 
		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
//		 		Toast.makeText(mContext, CategoryModelList.get(position).getId() + ". " + CategoryModelList.get(position).getName().toString(), Toast.LENGTH_LONG).show();

				Intent intent = new Intent(mContext, Menu.class);
				intent.putExtra("IdCategory", CategoryModelList.get(position).getId());
				intent.putExtra("CategoryName", CategoryModelList.get(position).getName());
			 	mContext.startActivity(intent);					
			}
		});
 
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
//	public void filter(String charText) {
//		charText = charText.toLowerCase(Locale.getDefault());
//		MenuModelList.clear();
//		if (charText.length() < 2) {
////			MenuModelList.addAll(arraylist);
//		} 
//		else 
//		{
//			for (MenuModel wp : arraylist) 
//			{
//				if (wp.getDescription().toLowerCase(Locale.getDefault()).contains(charText)) 
//				{
//					MenuModelList.add(wp);
//				}
//				else if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) 
//				{
//					MenuModelList.add(wp);
//				}
//				else if (wp.getCategory().toLowerCase(Locale.getDefault()).contains(charText)) 
//				{
//					MenuModelList.add(wp);
//				}
//			}
//		}
//		notifyDataSetChanged();
//	}	 

}