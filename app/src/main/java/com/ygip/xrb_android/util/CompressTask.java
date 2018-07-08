package com.ygip.xrb_android.util;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQM on 2017/8/30.
 */

public class CompressTask extends AsyncTask<String, Void, String> {
    private List<String> compressPicPaths = new ArrayList<String>(); //存放压缩的图片
    private List<String> base64Strs = new ArrayList<String>(); //存放Base64字符串

    public CompressTask(List<String> compressPicPaths, List<String> base64Strs) {
        this.compressPicPaths = compressPicPaths;
        this.base64Strs = base64Strs;
    }

    @Override
    protected String doInBackground(String... strings) {
        for (String path:strings){
            String compressPicPath = ImgCompressUtils.compressImg(path,0,0,100);
            if (TextUtils.isEmpty(path)) return null;
            compressPicPaths.add(compressPicPath);
            base64Strs.add(ImageUtils.bitmapToBase64(compressPicPath));
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
