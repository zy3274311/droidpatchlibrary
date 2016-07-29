package com.qihoo.library;

import android.content.Context;

import com.qihoo.library.annotation.UnInject;
import com.qihoo.library.utils.AssetsUtil;
import com.qihoo.library.utils.DexUtils;
import com.qihoo.library.utils.FileUtil;

import java.io.File;

/**
 * Created by zhangying-pd on 2016/6/15.
 */
@UnInject
public class PatchHelper {
    @UnInject
    private static class PatchHelperChild{
        private static PatchHelper helper = new PatchHelper();
    }

    private PatchHelper(){}

    public static PatchHelper getSingleton(){
        return PatchHelperChild.helper;
    }

    public void loadPatchFromAssets(Context context, String assetsFileName) {
        File dexDir = context.getDir("patch_dex_dir", Context.MODE_PRIVATE);
        File dexFile = new File(dexDir.getAbsolutePath(), assetsFileName);
        String dexPath = dexFile.getAbsolutePath();
        AssetsUtil.copyAssetsFileToLocal(context, assetsFileName, dexPath);
        loadPatch(context, dexPath);
    }

    private void loadPatch(Context context, String dexPath) {
        File optimizedDir = context.getDir("patch_optimized_dir", Context.MODE_PRIVATE);
        if(optimizedDir.exists()){
            FileUtil.delete(optimizedDir);
        }
        String optimizedPath = optimizedDir.getAbsolutePath();
        try {
            DexUtils.injectDexAtFirst(dexPath, optimizedPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
