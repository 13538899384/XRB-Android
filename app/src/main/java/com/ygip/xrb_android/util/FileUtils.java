package com.ygip.xrb_android.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.ImageColumns;

import java.io.File;

/**
 * 
 * ClassName: FileUtils
 * 
 * @Description: 文件工具类
 * @author kesar
 * @date 2015-11-12
 */
public class FileUtils
{
	/**
	 * 
	 * @Description: uri转FilePath
	 * @param @param context
	 * @param @param uri
	 * @param @return
	 * @return String
	 * @throws
	 * @author kesar
	 * @date 2015-11-12
	 */
	public static String Uri2FilePath(Context context, Uri uri)
	{
		if (null == uri)
		{
			return null;
		}
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
		{
			data = uri.getPath();
		}
		else if (ContentResolver.SCHEME_FILE.equals(scheme))
		{
			data = uri.getPath();
		}
		else if (ContentResolver.SCHEME_CONTENT.equals(scheme))
		{
			Cursor cursor = context.getContentResolver().query(uri,
					new String[] { ImageColumns.DATA }, null, null, null);
			if (null != cursor)
			{
				if (cursor.moveToFirst())
				{
					int index = cursor.getColumnIndex(ImageColumns.DATA);
					if (index > -1)
					{
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	/**
	 * 
	 * @Description: Uri转File
	 * @param @param context
	 * @param @param uri
	 * @param @return
	 * @return File
	 * @throws
	 * @author kesar
	 * @date 2015-11-12
	 */
	public static File Uri2File(Context context, Uri uri)
	{
		String path = Uri2FilePath(context, uri);
		return path == null ? null : new File(path);
	}

	/**
	 * 
	 * @Description: filePath转uri
	 * @param @param filePath
	 * @param @return
	 * @return Uri
	 * @throws
	 * @author kesar
	 * @date 2015-12-1
	 */
	public static Uri File2Uri(String filePath)
	{
		return File2Uri(new File(filePath));
	}

	/**
	 * 
	 * @Description: file转uri
	 * @param @param file
	 * @param @return
	 * @return Uri
	 * @throws
	 * @author kesar
	 * @date 2015-12-1
	 */
	public static Uri File2Uri(File file)
	{
		return Uri.fromFile(file);
	}

	/**
	 * 获取文件夹对象
	 * 
	 * @return 返回SD卡下的指定文件夹对象，若文件夹不存在则创建
	 */
	public static File getSaveFolder(String folderName)
	{
		File file = new File(getSDCardPath() + File.separator + folderName
				+ File.separator);
		file.mkdirs();
		return file;
	}

	public static String getSDCardPath()
	{
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}
}
