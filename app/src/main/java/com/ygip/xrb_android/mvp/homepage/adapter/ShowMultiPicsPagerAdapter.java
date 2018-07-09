package com.ygip.xrb_android.mvp.homepage.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ShowMultiPicsPagerAdapter extends PagerAdapter {
	public static final String TAG = "MultiShowPic";

	private List<View> mViews;
	private List<String> mPics;
	private Activity mAty;
//	private String mPicType

	public ShowMultiPicsPagerAdapter(Activity aty, List<View> views,
                                     List<String> pics) {
		// 构造方法，参数是我们的页卡，这样比较方便。
		mAty = aty;
		mViews = views;
		mPics = pics;
//		mPicType = picType;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);// 删除页卡
	}

	private void finishAty(){
		Intent intent = new Intent();
		mAty.setResult(Activity.RESULT_OK, intent);
		mAty.finish();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// 这个方法用来实例化页卡
		View view = mViews.get(position);
		String pic = mPics.get(position);

//		final PhotoView photoView = (PhotoView) view.findViewById(R.id.photoview);
		// 设置图片单击返回
//		final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

		// 二者的单击事件都是退出activity
		view.setOnClickListener(v -> finishAty());
//		photoView.setOnViewTapListener((view1, v, v1) -> finishAty());

//		Glide.with(mAty)
//				.load(pic)
//				.into(new GlideDrawableImageViewTarget(photoView){
//					@Override
//					public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
//						super.onResourceReady(resource, animation);
//						progressBar.setVisibility(View.GONE);
//					}
//				});

		container.addView(view, 0);// 添加页卡
		return view;
	}

	@Override
	public int getCount() {
		return mViews.size();// 返回页卡的数量
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;// 官方提示这样写
	}

	@Override
	public int getItemPosition(Object object) {
		// 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
		// 如果是 POSITION_UNCHANGED，就不会重新加载，默认是 POSITION_UNCHANGED
		return POSITION_NONE;
	}
}
