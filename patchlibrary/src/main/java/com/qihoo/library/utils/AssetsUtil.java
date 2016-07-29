package com.qihoo.library.utils;

import android.content.Context;

import com.qihoo.library.annotation.UnInject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@UnInject
public class AssetsUtil {

    /**
     * @param context Context
     * @param assetsFileName file:///android_asset/html/{assetsFileName}
     * @param targetPath     targetPath
     * @return is success
     */
    public static boolean copyAssetsFileToLocal(Context context, String assetsFileName, String targetPath) {
        try {
            InputStream inputStream = context.getAssets().open(assetsFileName);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(targetPath));
            StreamTool.readStream(inputStream, fileOutputStream);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
