package com.luck.picture.lib.config;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.luck.picture.lib.R;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import java.io.File;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.lib.config
 * describe：PictureSelector Final Class
 * email：893855882@qq.com
 * data：2017/5/24
 */

public final class PictureConfig {
    public final static String FC_TAG = "picture";
    public final static String EXTRA_RESULT_SELECTION = "extra_result_media";
    public final static String EXTRA_LOCAL_MEDIAS = "localMedias";
    public final static String EXTRA_PREVIEW_SELECT_LIST = "previewSelectList";
    public final static String EXTRA_SELECT_LIST = "selectList";
    public final static String EXTRA_POSITION = "position";
    public final static String EXTRA_MEDIA = "media";
    public final static String DIRECTORY_PATH = "directory_path";
    public final static String BUNDLE_CAMERA_PATH = "CameraPath";
    public final static String BUNDLE_ORIGINAL_PATH = "OriginalPath";
    public final static String EXTRA_BOTTOM_PREVIEW = "bottom_preview";
    public final static String EXTRA_CONFIG = "PictureSelectorConfig";
    public final static String IMAGE = "image";
    public final static String VIDEO = "video";


    public final static int UPDATE_FLAG = 2774;// 预览界面更新选中数据 标识
    public final static int CLOSE_PREVIEW_FLAG = 2770;// 关闭预览界面 标识
    public final static int PREVIEW_DATA_FLAG = 2771;// 预览界面图片 标识
    public final static int TYPE_ALL = 0;
    public final static int TYPE_IMAGE = 1;
    public final static int TYPE_VIDEO = 2;
    public final static int TYPE_AUDIO = 3;

    public static final int MAX_COMPRESS_SIZE = 100;
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;

    public final static int SINGLE = 1;
    public final static int MULTIPLE = 2;

    public final static int CHOOSE_REQUEST = 188;
    public final static int REQUEST_CAMERA = 909;
    public final static int READ_EXTERNAL_STORAGE = 0x01;
    public final static int CAMERA = 0x02;

    public static String Consumer_Dir = "consumer";
    public static String Courier_Dir = "courier";

    /**压缩图片和拍照的保存路径*/
    public static String getCacheDir(String packageName,String consumer_PackageName) {
        File sdDir = null;
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            sdDir = Environment.getExternalStorageDirectory();
        } else {
//            sdDir = context.getCacheDir();
            sdDir = Environment.getDataDirectory();
        }

        File cacheDir = null;

        cacheDir = new File(sdDir, "gemmyapp");
        File childDir = null;

        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        if(packageName.equals(consumer_PackageName)){
            childDir = new File(cacheDir, Consumer_Dir);
        }else{
            childDir = new File(cacheDir, Courier_Dir);
        }

        if(!childDir.exists()){
            childDir.mkdirs();

            File noMediaFolder = new File(childDir, ".nomedia");

            if (!noMediaFolder.exists()) {
                noMediaFolder.mkdirs();
            }
        }
        String downloadDirectoryPath = childDir + "/Luban";
        return downloadDirectoryPath;
    }


    /**设置读写权限*/
    public static void setPermissions(final Activity context){
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(context);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(context);
                } else {
                    Toast.makeText(context,
                            context.getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }


}
