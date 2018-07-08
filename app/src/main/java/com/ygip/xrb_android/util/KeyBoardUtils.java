package com.ygip.xrb_android.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ygip.xrb_android.App;


/**
 * 
 * ClassName: KeyBoardUtils
 * 
 * @Description: 输入法相关的工具类
 * @author kesar
 * @date 2015年10月9日
 */
public class KeyBoardUtils {
	private InputMethodManager manager;

	private OnResultHidden mResultHidden;

	private OnResultShown mResultShown;

	private ResultReceiver mResultReceiver;

	private KeyBoardUtils()
	{
		mResultReceiver = new ResultReceiver(new Handler())
		{
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData)
			{
				switch (resultCode)
				{
				case InputMethodManager.RESULT_HIDDEN: // 软键盘窗口从显示切换到隐藏时的状态。
				{
					if (mResultHidden != null)
					{
						mResultHidden.onDone();
					}
					break;
				}
				case InputMethodManager.RESULT_SHOWN: // 软键盘窗口从隐藏切换到显示时的状态。
				{
					if (mResultShown != null)
					{
						mResultShown.onDone();
					}
					break;
				}
				case InputMethodManager.RESULT_UNCHANGED_HIDDEN: // 软键盘窗口不变保持隐藏时的状态。
				{
					if (mResultShown != null)
					{
						mResultShown.onDone();
					}
					break;
				}
				case InputMethodManager.RESULT_UNCHANGED_SHOWN: // 软键盘窗口不变保持显示时的状态。
				{
					if (mResultShown != null)
					{
						mResultShown.onDone();
					}
					break;
				}
				default:
				{
					if (mResultShown != null)
					{
						mResultShown.onDone();
					}
					break;
				}
				}
				super.onReceiveResult(resultCode, resultData);
			}
		};

		manager = (InputMethodManager) App.getInstance()
				.getApplicationContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	/**
	 * 
	 * @Description: 取得唯一的单例
	 * @param @return   
	 * @return KeyBoardUtils  
	 * @throws
	 * @author kesar
	 * @date 2015年10月9日
	 */
	public static KeyBoardUtils getInstance()
	{
		return SingleTon.utils;
	}

	/**
	 * 
	 * ClassName: SingleTon 
	 * @Description: 携带单例的类
	 * @author kesar
	 * @date 2015年10月9日
	 */
	static class SingleTon
	{
		public static KeyBoardUtils utils = new KeyBoardUtils();
	}

	/**
	 * 
	 * @Description: 隐藏软键盘
	 * @param @param activity
	 * @return void
	 * @throws
	 * @author kesar
	 * @date 2015年10月9日
	 */
	public void hideKeyboard(Activity activity)
	{
		hideKeyboard(activity, null);
	}

	/**
	 * 
	 * @Description: 隐藏软键盘
	 * @param @param activity
	 * @param @param callBack
	 * @return void
	 * @throws
	 * @author kesar
	 * @date 2015年10月9日
	 */
	public void hideKeyboard(Activity activity, OnResultHidden callBack)
	{
		boolean isDone=false;
		if (null!=manager&&manager.isActive())
		{
			View v = activity.getCurrentFocus();
			if (v != null)
			{
				if (callBack != null)
				{
					mResultHidden = callBack;
					isDone = manager.hideSoftInputFromWindow(v.getWindowToken(),
							0, mResultReceiver);
				}
				else
				{
					manager.hideSoftInputFromWindow(v.getWindowToken(),
							0);
				}
			}
		}
		if (!isDone)
		{
			if(callBack!=null)
			{				
				callBack.onDone();
			}
		}
	}

	/**
	 * 
	 * @Description: 显示软键盘
	 * @param @param activity
	 * @return void
	 * @throws
	 * @author kesar
	 * @date 2015年10月9日
	 */
	public void showKeyboard(Activity activity)
	{
		showKeyboard(activity, null);
	}

	/**
	 * 
	 * @Description: 显示软键盘
	 * @param @param activity
	 * @return void
	 * @throws
	 * @author kesar
	 * @date 2015年10月9日
	 */
	public void showKeyboard(Activity activity, OnResultShown callBack)
	{
		boolean isDone=false;
		if (null!=manager)
		{
			View v = activity.getCurrentFocus();
			if (v != null)
			{
				if (callBack != null)
				{
					mResultShown = callBack;
					isDone = manager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT,
							mResultReceiver);
				}
				else
				{
					manager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
				}
			}
		}
		if(!isDone)
		{
			if(callBack!=null)
			{				
				callBack.onDone();
			}
		}
	}

	/**
	 * 
	 * ClassName: OnResultHidden
	 * 
	 * @Description: 软键盘窗口从显示切换到隐藏时的状态。
	 * @author kesar
	 * @date 2015年10月9日
	 */
	public interface OnResultHidden
	{
		public void onDone();
	}

	/**
	 * 
	 * ClassName: OnResultShown
	 * 
	 * @Description: 软键盘窗口从隐藏切换到显示时的状态。
	 * @author kesar
	 * @date 2015年10月9日
	 */
	public interface OnResultShown
	{
		public void onDone();
	}
}
