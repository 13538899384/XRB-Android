package com.ygip.xrb_android.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * CBC加密方式
 */

public class AES
{        
    public final static String key="comyiduwindbell1";
    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {    
            System.out.print("Key为空null");    
            return null;    
        }    
        // 判断Key是否为16位     
        if (sKey.length() != 16) {    
            System.out.print("Key长度不是16位");    
            return null;    
        }    
        byte[] raw = sKey.getBytes("UTF-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes("UTF-8"));//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));
        return android.util.Base64.encodeToString(encrypted,android.util.Base64.DEFAULT);//此处使用BAES64做转码功能，同时能起到2次加密的作用。
    }    
    
    // 解密     
    public static String Decrypt(String sSrc, String sKey) {
        try {    
            // 判断Key是否正确     
            if (sKey == null) {    
                System.out.print("Key为空null");    
                return null;    
            }    
            // 判断Key是否为16位     
            if (sKey.length() != 16) {    
                System.out.print("Key长度不是16位");    
                return null;    
            }    
            byte[] raw = sKey.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");    
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");    
            IvParameterSpec iv = new IvParameterSpec("0102030405060708"    
                    .getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);    
            byte[] encrypted1 = android.util.Base64.decode(sSrc, android.util.Base64.DEFAULT);//先用bAES64解密
            try {    
                byte[] original = cipher.doFinal(encrypted1);    
                String originalString = new String(original);    
                return originalString;    
            } catch (Exception e) {    
                System.out.println(e.toString());    
                return null;    
            }    
        } catch (Exception ex) {    
            System.out.println(ex.toString());    
            return null;    
        }    
    }    
    
  
}   