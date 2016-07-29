package com.qihoo.library.utils;

import com.qihoo.library.annotation.UnInject;

import java.io.File;

/**
 * Created by zhangying-pd on 2016/7/8.
 */
@UnInject
public class FileUtil {
    public static void delete(File file){
        if(file!=null){
            if(file.isFile()){
                file.deleteOnExit();
            } else {
                File[] files = file.listFiles();
                if(files!=null){
                    for(File child:files){
                        delete(child);
                    }
                }
            }
        }
    }
}
