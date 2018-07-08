package com.ygip.xrb_android.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;


import java.io.File;
import java.io.IOException;

/**
 * ClassName: AddImageUtils
 *
 * @author kesar
 * @Description: 获取图片工具类(加权限：WRITE_EXTERNAL_STORAGE)
 * @date 2015-11-12
 */
public class AddImageUtils {
    public static final int REQUEST_CODE_TACKPHONE = 0x144;// 裁剪照片
    public static final int REQUEST_CODE_ALBUM = 0x245;// 从相册获取

    public static File photoFile; // 照片的文件
    static public String IMAGE_DIR="/dianyi/img";

    /**
     * @param @param aty
     * @return void
     * @throws
     * @Description: 从相册中获取图片
     * @author kesar
     * @date 2015-11-12
     */
    public static void fromAlbum(Activity aty) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_PICK);
        }
        intent.setType("image/*");
        aty.startActivityForResult(intent, REQUEST_CODE_ALBUM);
    }

    /**
     * @param @param aty
     * @return void
     * @throws
     * @Description: 自行拍照
     * @author kesar
     * @date 2015-11-12
     */
    public static void takePhoto(Activity aty) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = creatImagePath(IMAGE_DIR);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile)); // 有这一句intent就会为空
        aty.startActivityForResult(intent, REQUEST_CODE_TACKPHONE);
    }

    /**
     * @param @param aty
     * @param @param imgCachePath 图片保存路径
     * @return void
     * @throws
     * @Description: 自行拍照
     * @author kesar
     * @date 2015-11-13
     */
    public static void takePhoto(Activity aty, String imgCachePath) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = creatImagePath(imgCachePath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile)); // 有这一句intent就会为空
        aty.startActivityForResult(intent, REQUEST_CODE_TACKPHONE);
    }

    /**
     * @param @return
     * @return File
     * @throws
     * @Description: 创建保存相册的路径
     * @author kesar
     * @date 2015-11-12
     */
    public static File creatImagePath(String imgCachePath) {
        if (CommonUtils.isExitsSdcard()) {
            File saveDir = FileUtils.getSaveFolder(imgCachePath);
            File file = new File(saveDir, UUIDUtils.getUUID() + ".png");
            file.delete();
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return file;
        }
        return null;
    }

    /**
     * @param @return
     * @return File
     * @throws
     * @Description: 获取照片的图片文件
     * @author kesar
     * @date 2015-11-13
     */
    public static File getPhotoFile() {
        return photoFile;
    }
}