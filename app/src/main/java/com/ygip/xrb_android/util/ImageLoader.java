package com.ygip.xrb_android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


/**
 * Created by Administrator on 2016/8/31.
 *
 * @description:图片加载器(加载本地图片)
 */
public class ImageLoader {

    //图片缓存
    private LruCache<String, Bitmap> mLruCache;

    //线程池
    private ExecutorService mThreadPool;

    //线程池的线程数量，默认为1
    private int mThreadCount = 1;

    //队列的调度方式
    private Type mType = Type.LIFO;

    //任务队列
    private LinkedList<Runnable> mTasks;

    //轮询的线程
    private Thread mPoolThread;
    private Handler mPoolThreadHander;

    //UI线程
    private Handler mHandler;

    //信号量(同步)
    private volatile Semaphore mSemaphore = new Semaphore(0);

    private volatile Semaphore mPoolSemaphore;

    private static ImageLoader mInstance;

    //队列调度方式
    public enum Type {
        FIFO, LIFO
    }

    private ImageLoader(int threadCount, Type type) {
        init(threadCount, type);
    }

    private void init(int threadCount, Type type) {
        //循环线程，一直循环执行任务（当有任务时）
        mPoolThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHander = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        mThreadPool.execute(getTask());
                        try {
                            mPoolSemaphore.acquire();//获取一个信号量，没有就等待
                        } catch (InterruptedException e) {

                        }
                    }
                };
                mSemaphore.release();//释放信号量
                Looper.loop();
            }
        };
        mPoolThread.start();

        //获取最大内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {

            @Override
            protected int sizeOf(String key, Bitmap value) {
                //计算bitmap的大小
                return value.getRowBytes() * value.getHeight();
            }
        };
        //创建线程池
        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mPoolSemaphore = new Semaphore(threadCount);
        mTasks = new LinkedList<Runnable>();
        mType = type == null ? Type.LIFO : type;

    }

    /**
     * @return
     * @description；获取单例
     */
    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader(1, Type.LIFO);
                }
            }
        }
        return mInstance;
    }

    /**
     * @param threadCount
     * @param type
     * @return
     * @description:通过指定线程数量和类型获取单例
     *
     *
     */
    public static ImageLoader getInstance(int threadCount,Type type){
        if (mInstance==null){
            synchronized (ImageLoader.class){
                if (mInstance==null){
                    mInstance=new ImageLoader(threadCount,type);
                }
            }
        }
        return mInstance;
    }

    //加载图片
//    public void loadImage(final String path, final ImageView imageView) {
//        //set tag
//        imageView.setTag(path);
//        //UI线程
//        if (mHandler == null) {
//            mHandler = new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
//                    ImageView imageView = holder.imageView;
//                    Bitmap bitmap = holder.bitmap;
//                    String path = holder.path;
//                    if (imageView.getTag().toString().endsWith(path)) {
//                        imageView.setImageBitmap(bitmap);
//                    }
//                }
//            };
//        }
//        //获取位图，循环线程
//        Bitmap bitmap = getBitmapFormLruCache(path);
//        if (bitmap != null) {
//            ImgBeanHolder holder = new ImgBeanHolder();
//            holder.bitmap = bitmap;
//            holder.imageView = imageView;
//            holder.path = path;
//            Message message = Message.obtain();
//            message.obj = holder;
//            mHandler.sendMessage(message);
//        } else {
//            addTask(new Runnable() {
//                @Override
//                public void run() {
//                    ImageSize imageSize = getImageViewWidth(imageView);
//                    int reqWidth = imageSize.width;
//                    int reqHeight = imageSize.height;
//                    Bitmap bitmap=decodeSampledBitmapFromResource(path,reqWidth,
//                            reqHeight);
//                    addBitmapToLruCache(path,bitmap);
//                    ImgBeanHolder holder=new ImgBeanHolder();
//                    holder.bitmap=getBitmapFormLruCache(path);
//                    holder.imageView=imageView;
//                    holder.path=path;
//                    Message message=Message.obtain();
//                    message.obj=holder;
//                    mHandler.sendMessage(message);
//                    mPoolSemaphore.release();
//
//                }
//            });
//        }
//    }

    //加载图片,为了在选择心墙图片时能让被选择的图片变灰,加多了一个isSelected参数
    public void loadImage(final String path, final ImageView imageView, final boolean isSelected) {
        //set tag
        imageView.setTag(path);
        //UI线程
        if (mHandler == null) {
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
                    ImageView imageView = holder.imageView;
                    Bitmap bitmap = holder.bitmap;
                    String path = holder.path;
                    Boolean isSelected = holder.isSelected;
                    if (imageView.getTag().toString().endsWith(path)) {
                        imageView.setImageBitmap(bitmap);
                        if (isSelected){
                            Log.i("ImageLoader","toGray true");
                            changeImgBgGray(imageView);
                        }else{
                            Log.i("ImageLoader","toGray false");
                            resetImgBg(imageView);
                        }
                    }
                }
            };
        }
        //获取位图，循环线程
        Bitmap bitmap = getBitmapFormLruCache(path);
        if (bitmap != null) {
            ImgBeanHolder holder = new ImgBeanHolder();
            holder.bitmap = bitmap;
            holder.imageView = imageView;
            holder.path = path;
            holder.isSelected = isSelected;
            Message message = Message.obtain();
            message.obj = holder;
            mHandler.sendMessage(message);
        } else {
            addTask(new Runnable() {
                @Override
                public void run() {
                    ImageSize imageSize = getImageViewWidth(imageView);
                    int reqWidth = imageSize.width;
                    int reqHeight = imageSize.height;
                    Bitmap bitmap=decodeSampledBitmapFromResource(path,reqWidth,
                            reqHeight);
                    addBitmapToLruCache(path,bitmap);
                    ImgBeanHolder holder=new ImgBeanHolder();
                    holder.bitmap=getBitmapFormLruCache(path);
                    holder.imageView=imageView;
                    holder.path=path;
                    holder.isSelected = isSelected;
                    Message message= Message.obtain();
                    message.obj=holder;
                    mHandler.sendMessage(message);
                    mPoolSemaphore.release();

                }
            });
        }
    }

    /**
     * @param imageView
     * @return
     * @description:根据ImageView获得适当的压缩的宽和高
     */
    private ImageSize getImageViewWidth(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        final DisplayMetrics displayMetrics = imageView.getContext()
                .getResources().getDisplayMetrics();
        final ViewGroup.LayoutParams params = imageView.getLayoutParams();

        //先判断宽是否是wrap_content,是的话直接赋值为 displayMetrics的宽
        int width = params.width == ViewGroup.LayoutParams.WRAP_CONTENT
                ? 0 : imageView.getWidth();
        if (width <= 0)
            width = params.width;
        if (width <= 0)
            width = getImageViewFieldValue(imageView, "mMaxWidth");
        if (width <= 0)
            width = displayMetrics.widthPixels;

        int height = params.height == ViewGroup.LayoutParams.WRAP_CONTENT
                ? 0 : imageView.getHeight();
        if (height <= 0)
            height = params.height;
        if (height <= 0)
            height = getImageViewFieldValue(imageView, "mMaxHeight");
        if (height <= 0)
            height = displayMetrics.heightPixels;

        imageSize.width = width;
        imageSize.height = height;
        return imageSize;


    }


    /**
     * @param object
     * @param fieldName
     * @return
     * @description:反射获得ImageView设置的最大宽度和高度
     */
    private static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
                Log.e("fieldValue", value + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * @param pathName
     * @param reqWidth
     * @param reqHeight
     * @return
     * @description:压缩图片
     */
    private Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(pathName,options);

        options.inSampleSize=calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds=false;
        Bitmap bitmap= BitmapFactory.decodeFile(pathName,options);
        return bitmap;
    }

    /**
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     * @description；根据要求的宽高计算inSampleSize
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (width > reqWidth && height > reqHeight) {
            //计算比率
            int widthRatio = Math.round((float) width / (float) reqWidth);
            int heightRatio = Math.round((float) height / (float) (reqHeight));
            inSampleSize = Math.max(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    /**
     * @param key
     * @return
     * @description:从LruCache中获取一张图片，可能会返回null
     */
    private Bitmap getBitmapFormLruCache(String key) {
        return mLruCache.get(key);
    }


    /**
     * @param key
     * @param bitmap
     * @descripion:在LruCache添加一张图片
     */
    private void addBitmapToLruCache(String key, Bitmap bitmap) {
        if (getBitmapFormLruCache(key) == null) {
            if (bitmap != null) {
                mLruCache.put(key, bitmap);
            }
        }
    }

    //添加一个任务
    private synchronized void addTask(Runnable runnable) {
        try {
            // 请求信号量，防止mPoolThreadHander为null
            if (mPoolThreadHander == null) {
                mSemaphore.acquire();
            }
        } catch (InterruptedException e) {
        }
        mTasks.add(runnable);
        mPoolThreadHander.sendEmptyMessage(0x110);
    }


    /**
     * @return
     * @descripiton：取出一个任务
     */
    private synchronized Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTasks.removeFirst();
        } else if (mType == Type.LIFO) {
            return mTasks.removeLast();
        }
        return null;
    }

    //图片holder
    private class ImgBeanHolder {
        Bitmap bitmap;
        ImageView imageView;
        String path;
        Boolean isSelected;
    }

    private class ImageSize {
        int width;
        int height;
    }

    public void showImageByThead(final ImageView iv, final String url){
        mHandler = new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                iv.setImageBitmap((Bitmap) msg.obj);
            }
        };
        addTask(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = getBitmapFromUrl(url);
                Message message = Message.obtain();
                message.obj=bitmap;
                mHandler.sendMessage(message);
            }
        });
    }

    private Bitmap getBitmapFromUrl(String urlString){
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL mUrl= new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap= BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 下面这两个是选择心墙图片用的(让图片背景变灰)
    // 让图片背景变暗
    private void changeImgBgGray(ImageView iv){
        Drawable drawable = iv.getDrawable();
        if (drawable != null){
            drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            iv.setImageDrawable(drawable);
        }
    }

    // 让图片背景恢复原样
    private void resetImgBg(ImageView iv){
        Drawable drawable = iv.getDrawable();
        if (drawable != null){
            drawable.clearColorFilter();
            iv.setImageDrawable(drawable);
        }
    }
}
