package com.kotlin.base.utils

import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * 作者：何兆鸿 on 2018/9/19.
 * 学无止境~
 * 文件处理工具类
 */
object FileUtils {

    private var TAG: String = "FileUtils"

    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    fun copy(source: File, target: File) {
        var fileInputStream: FileInputStream? = null
        var fileOutputStream: FileOutputStream? = null
        try {
            fileInputStream = FileInputStream(source)
            fileOutputStream = FileOutputStream(target)
            val buffer = ByteArray(1024)
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close()
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 复制文件
     * @param bytes 输入数据
     * @param target 输出文件
     */
    fun copy(bytes: ByteArray, target: File) {
        try {
            //如果手机已插入sd卡,且app具有读写sd卡的权限
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                var output = FileOutputStream(target)
                output.write(bytes)
                Log.e(TAG, "copy: success，" + target)
                output.close()
            } else {
                Log.i(TAG, "copy:fail, " + target)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}


