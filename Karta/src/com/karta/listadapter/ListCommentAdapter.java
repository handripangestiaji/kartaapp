package com.karta.listadapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.Comment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.wallet.wobs.CommonWalletObject;
import com.karta.LeaveReview;
import com.karta.MenuDetail;
import com.karta.R;
import com.karta.model.CategoryModel;
import com.karta.model.CommentModel;
import com.karta.model.MenuModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.view.View.OnClickListener; 

public class ListCommentAdapter extends BaseAdapter {
	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<CommentModel> CommentModelList = null;
	private ArrayList<CommentModel> arraylist;
	protected ImageLoader imageLoader;
 
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public ListCommentAdapter(Context context, List<CommentModel> CommentModelList) {
		mContext = context;
		this.CommentModelList = CommentModelList;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<CommentModel>();
		this.arraylist.addAll(CommentModelList);

		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.little_loading_image)
		.showImageForEmptyUri(R.drawable.little_no_image)
		.showImageOnFail(R.drawable.little_cant_load_image)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(0)).build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
	}
 
	public class ViewHolder {
		ImageView user_image;
		TextView title;
		TextView comment;
		TextView rating;		
	}
 
	@Override
	public int getCount() {
		return CommentModelList.size();
	}
 
	@Override
	public CommentModel getItem(int position) {
		return CommentModelList.get(position);
	}
 
	@Override
	public long getItemId(int position) {
		return position;
	}
 
	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.list_comment, null, true);
			holder.title = (TextView) view.findViewById(R.id.comment_name_and_date);
			holder.comment = (TextView) view.findViewById(R.id.comment);
			holder.rating = (TextView) view.findViewById(R.id.comment_rating);
			holder.user_image = (ImageView) view.findViewById(R.id.img_user);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		// Set the results into TextViews
		holder.title.setText("Budi");
		holder.rating.setText(String.valueOf(CommentModelList.get(position).getRating()));
		holder.comment.setText(CommentModelList.get(position).getComment());
		
		if(view != null)
			ImageLoader.getInstance().displayImage(CommentModelList.get(position).getUser().getImage(), holder.user_image, options, animateFirstListener);
 
		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				Toast.makeText(mContext, CommentModelList.get(position).getComment(), Toast.LENGTH_SHORT).show();			 

//				if(mContext.getClass().getSimpleName().equals("AddReview")){
//					Intent intent = new Intent(mContext, LeaveReview.class);
////					intent.putExtra("IdCategory", CategoryModelList.get(position).getId());
////					intent.putExtra("CategoryName", CategoryModelList.get(position).getName());
//					mContext.startActivity(intent);					
//				}
//				else {
//					Intent intent = new Intent(mContext, MenuDetail.class);
//					mContext.startActivity(intent);					
//				}
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
	public void filter(String charText) {
//		charText = charText.toLowerCase(Locale.getDefault());
//		CommentModelList.clear();
//		if (charText.length() < 2) {
////			MenuModelList.addAll(arraylist);
//		} 
//		else 
//		{
//			for (MenuModel menu : arraylist) 
//			{				
//				Boolean finished = false;
//				if (menu.getName().toLowerCase(Locale.getDefault()).contains(charText)) 
//				{
//					MenuModelList.add(menu);
//					finished = true;
//				}
//				else if (menu.getRestaurant().getAddress().toLowerCase(Locale.getDefault()).contains(charText)) 
//				{
//					MenuModelList.add(menu);
//					finished = true;
//				}
//				
//				if(!finished)
//				{
//					for(String ingredient : menu.getIngredients())
//					{
//						if (ingredient.toLowerCase(Locale.getDefault()).contains(charText)) 
//						{
//							MenuModelList.add(menu);
//							finished = true;
//						}					
//					}
//				}
//				
//				if(!finished)
//				{
//					for(CategoryModel category : menu.getCategory())
//					{
//						if (category.getName().toLowerCase(Locale.getDefault()).contains(charText)) 
//						{
//							MenuModelList.add(menu);
//							finished = true;
//						}					
//					}
//				}				
//			}
//		}
		notifyDataSetChanged();
	}	 
}