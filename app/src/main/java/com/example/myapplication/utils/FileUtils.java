package com.example.myapplication.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

public class FileUtils {
    public static void saveImg(Bitmap bm, String folderPath, String fileName) throws IOException {
        //如果不保存在sd下面下面这几行可以不加
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e("","SD卡异常");
            return;
        }

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String savePath = folder.getPath() + File.separator + fileName + ".jpg";
        File file = new File(savePath);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        Log.e("",savePath + " 保存成功");
        bos.flush();
        bos.close();
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    public static String getFileType(String filePath)
    {
        return filePath.substring(filePath.lastIndexOf(".")+1);
    }
    public static String getFileName(String fileNameType)
    {
        return fileNameType.substring(0,fileNameType.lastIndexOf("."));
    }
}
