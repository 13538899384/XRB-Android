package com.ygip.xrb_android.base;

import com.ygip.xrb_android.util.CompressTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQM on 2017/8/30.
 */

public class PicUploadModel {
    private List<String> compressPicPaths = new ArrayList<String>(); //存放压缩的图片
    private List<String> base64Strs = new ArrayList<String>(); //存放Base64字符串

    public void addPic(String path){
        CompressTask compressTask = new CompressTask(compressPicPaths,base64Strs);
        compressTask.execute(path);
    }

    public void addPics(List<String> paths){
        CompressTask compressTask = new CompressTask(compressPicPaths, base64Strs);
        compressTask.execute(paths.toArray(new String[paths.size()]));
    }

    public void removePic(int position){
        if (position >= compressPicPaths.size()) return;
        new File(compressPicPaths.get(position)).delete();
        compressPicPaths.remove(position);
        base64Strs.remove(position);
    }

    public void removeAllPic(){
        List<File> files = new ArrayList<File>();
        for (String compressPicPath: compressPicPaths){
            files.add(new File(compressPicPath));
        }
        for(File file: files){
            file.delete();
        }
        compressPicPaths = null;
        base64Strs = null;
    }

    public List<String> getCompressPicPaths() {
        return compressPicPaths;
    }

    public List<String> getBase64Strs() {
        return base64Strs;
    }
}
