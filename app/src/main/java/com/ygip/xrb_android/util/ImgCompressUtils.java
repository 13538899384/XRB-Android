package com.ygip.xrb_android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.ygip.xrb_android.base.Constant;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @Description:
 */

public class ImgCompressUtils {

    // 默认压缩图的宽, 高
    private static final int DEFAULT_BITMAP_WIDTH = 600;
    private static final int DEFAULT_BITMAP_HEIGHT = 600;

    public static String compressImg(String imgPath, int outWidth, int outHeight, int quality) {
        return compressImg(imgPath, outWidth, outHeight, quality, Bitmap.CompressFormat.JPEG);
    }

    public static String compressImg(String imgPath, int outWidth, int outHeight, int quality, Bitmap.CompressFormat format) {
        if (TextUtils.isEmpty(imgPath)) return null;
        // 如果输出宽高传进来都是0, 那就给它一个默认值300
        if (outWidth <= 0){
            outWidth = DEFAULT_BITMAP_WIDTH;
        }
        if (outHeight <= 0){
            outHeight = DEFAULT_BITMAP_HEIGHT;
        }
        Bitmap bitmap = scaleDownImg(imgPath, outWidth, outHeight);
        if (bitmap == null) return null;
        try {
            //保存图片
            String compressImgPath = reduceImgQuality(bitmap, quality, format);
            if (!bitmap.isRecycled()){
                bitmap.recycle();
                bitmap = null;
                // System.gc()调用一下系统的垃圾回收器进行回收，可以通知垃圾回收器尽快进行回收。
                // 这里需要注意的是，调用System.gc()并不能保证立即开始进行回收过程，而只是为了加快回收的到来。
                System.gc();
            }
            return compressImgPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 例如原图宽200, 高400, 宽高比是0.5, 把参数outWidth设为100, outHeight100, 而实际上压缩所得图片宽为50, 高100
    private static Bitmap scaleDownImg(String imgPath, int outWidth, int outHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        // inJustDecodeBounds设置为true, 表示不生成bitmap(避免占用过多内存), 只是为了获取bitmap的宽高,
        // 用BitmapFactory.decodeFile(imgPath, options)会得到一个bitmap对象, 这个bitmap对象为null
        options.inJustDecodeBounds = true;
        // BitmapFactory.decodeFile(imgPath, options)后options的outWidth, outHeight就有值了
        BitmapFactory.decodeFile(imgPath, options);

        // 先得到原图宽高
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        // 计算比例
        float srcRatio = srcWidth / srcHeight;
        float outRatio = outWidth / outHeight;
        // 实际输出的图片宽高
        float actualOutWidth;
        float actualOutHeight;

        if (srcWidth > outWidth || srcHeight > outHeight) {
            // 下面的意思是尽可能让压缩的图片宽高接近于你所指定的图片宽高, 可以找几个例子试一试
            if (srcRatio < outRatio) {
                actualOutHeight = outHeight;
                actualOutWidth = actualOutHeight * srcRatio;
            } else if (srcRatio > outRatio) {
                actualOutWidth = outWidth;
                actualOutHeight = actualOutWidth / srcRatio;
            } else {
                actualOutWidth = outWidth;
                actualOutHeight = outHeight;
            }
        }else{
            // 如果要求压缩得到的图片宽和高都比原图的大, 直接把压缩的图片宽高设为和原图一样
            actualOutWidth = srcWidth;
            actualOutHeight = srcHeight;
        }
        options.inSampleSize = computeSampleSize(srcWidth, srcHeight, actualOutWidth, actualOutHeight);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = null;
        options.inPurgeable = true;
        options.inInputShareable = true;
        return BitmapFactory.decodeFile(imgPath, options);
    }

    //计算压缩比
    private static int computeSampleSize(float srcWidth, float srcHeight, float actualOutWidth, float actualOutHeight) {
        int sampleSize;
        if (srcWidth > actualOutWidth || srcHeight > actualOutHeight) {
            // Math.round四舍五入, 例如math.round(2.2) = 2
            int widthRatio = Math.round(srcWidth / actualOutWidth);
            int heightRatio = Math.round(srcHeight / actualOutHeight);
            sampleSize = Math.min(widthRatio, heightRatio);
        }else{
            sampleSize = 1;
        }
        return sampleSize;
    }

    /* 将图片进行降质处理（即降低图片的质量）来达到压缩的目的 */
    public static String reduceImgQuality(Bitmap bitmap, int quality) throws Exception {
        return reduceImgQuality(bitmap, quality, Bitmap.CompressFormat.JPEG);
    }

    public static String reduceImgQuality(Bitmap bitmap, int quality, Bitmap.CompressFormat format) throws Exception {
        // 用UUID随机生成一个字符串作为文件名
        String imgPath = FileUtils.getSaveFolder(Constant.imgCachePath).getAbsolutePath()
                + File.separator
                + UUIDUtils.getUUID();
        if (format == Bitmap.CompressFormat.PNG){
            imgPath = imgPath + ".png";
        }else if (format == Bitmap.CompressFormat.JPEG){
            imgPath = imgPath + ".jpg";
        }else{
            imgPath = imgPath + ".jpg";
        }
        File file = new File(imgPath);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file, false));
        // 这里quality是指压缩质量百分比, 从0-100, 例如quality是80, 则压缩得到的图片质量为原图的80%
        bitmap.compress(format, quality, bos);
        bos.flush();
        bos.close();
        return imgPath;
    }

}
