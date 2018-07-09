package com.ygip.xrb_android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by XQM on 2017/6/3.
 */

public class ImageUtils {

//    static String getToList = "";
//    private static Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.arg1 == 0){
//                Bundle bundle = new Bundle();
//                bundle = msg.getData();
//                getToList = bundle.getString("list");
//            }
//        }
//    };
    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath,int reqWidth,int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(String.valueOf(filePath), options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = Math.round((float) height/ (float) reqHeight);
            final int halfWidth= Math.round((float) width / (float) reqWidth);
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth){
                inSampleSize *= 2;
            }
//            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    // bitmap转成String
    public static String bitmapToBase64(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bitmap == null) return null;
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        if(bitmap != null && !bitmap.isRecycled()){
            // 回收并且置为null
            bitmap.recycle();
            bitmap = null;
        }
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    //bitmap转成string
    public static String bitmapToString(List<String> filePath) throws IOException {
        Bitmap bitmap = null;
        String getList = "";
        ByteArrayOutputStream outputStream = null;
        String stringDecode = null;
        byte[] bytes;
        for (int i = 0;i<filePath.size();i++){
            try {
                if (!("").equals(filePath.get(i))){
                    bitmap = getSmallBitmap(filePath.get(i),480,800);
                    outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 40, outputStream);
                    outputStream.flush();
                    bytes = outputStream.toByteArray();
                    stringDecode = Base64.encodeToString(bytes, Base64.DEFAULT);
                    getList += stringDecode;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (outputStream != null){
                    outputStream.close();

                }
            }
        }

//        new Thread(new Runnable() {//开启多线程进行压缩处理
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                Bitmap bitmap = null;
//                String getList = "";
//                ByteArrayOutputStream outputStream = null;
//                String stringDecode = null;
//                byte[] bytes;
//                int options = 100;
//                String imgPath ="/*image";
//                for (int i = 0;i<filePath.size();i++){
//                    if (!("").equals(filePath.get(i))){
//                        bitmap = getSmallBitmap(filePath.get(i));
//                        outputStream = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, options, outputStream);//质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)
//                        bytes = outputStream.toByteArray();
//                        stringDecode = Base64.encodeToString(bytes, Base64.DEFAULT);
//                        while (stringDecode.length() / 1024 > 100) {//循环判断如果压缩后图片是否大于100kb,大于继续压缩
//                            outputStream.reset();//重置baos即让下一次的写入覆盖之前的内容
//                            options -= 10;//图片质量每次减少10
//                            if(options<0)
//                            {
//                                options=0;//如果图片质量小于10，则将图片的质量压缩到最小值
//                                bitmap.compress(Bitmap.CompressFormat.JPEG, options, outputStream);//将压缩后的图片保存到baos中
//                                bytes = outputStream.toByteArray();
//                                stringDecode = Base64.encodeToString(bytes, Base64.DEFAULT);
//                                Message message = new Message();
//                                message.arg1 = 0;
//                                Bundle bundle = new Bundle();
//                                bundle.putString("list",stringDecode);
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                            }
//                            if(options==0) {
//                                Message message = new Message();
//                                message.arg1 = 0;
//                                Bundle bundle = new Bundle();
//                                bundle.putString("list",stringDecode);
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                break;
//                            }
//                            //如果图片的质量已降到最低则，不再进行压缩
//                        }
//                        try {
//                            FileOutputStream fos = new FileOutputStream(new File(imgPath));//将压缩后的图片保存的本地上指定路径中
//                            fos.write(outputStream.toByteArray());
//                            fos.flush();
//                            fos.close();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//            }
//        }).start();
        Log.e("getList",getList);
        Log.e("getList", String.valueOf(getList.length()));
//        if (getList.length()>=1001024){
//
//        }
        return getList;
    }
}
