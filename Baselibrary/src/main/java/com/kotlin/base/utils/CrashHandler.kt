package com.kotlin.base.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.util.Log
import com.kotlin.base.R
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.Thread.UncaughtExceptionHandler
import java.text.SimpleDateFormat
import java.util.*


/**
 * 作者：何兆鸿 on 2018/6/25.
 * 学无止境~
 * Crash日志文件
 */
object CrashHandler : UncaughtExceptionHandler {

    private var mDefaultHandler: UncaughtExceptionHandler? = null
    private var mContext: Context? = null
    private val infos = HashMap<String, String>()
    private val formatter = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
    private var filePath: String? = null

    fun init(context: Context) {
        mContext = context
        filePath = CacheHandler.getCrashlogDir(mContext!!).getAbsolutePath()

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    private fun handleException(e: Throwable?): Boolean {
        if (e == null) {
            return false
        }
        object : Thread() {
            override fun run() {
                Looper.prepare()
                mContext!!.toast(mContext!!.getString(R.string.message_crash))

                Looper.loop()
            }
        }.start()
        collectDeviceInfo(mContext)
        saveCrashInfoFile(e)
        return true
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                Log.e("CrashHandler", "error : ", e)
            }

            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }
    }

    fun collectDeviceInfo(context: Context?) {
        try {
            val packageManager = context!!.getPackageManager()
            val packageInfo = packageManager.getPackageInfo(context!!.getPackageName(), PackageManager.GET_ACTIVITIES)
            if (packageInfo != null) {
                val versionName = if (packageInfo!!.versionName == null) "null" else packageInfo!!.versionName
                val versionCode = packageInfo!!.versionCode.toString()
                infos.put("versionName", versionName)
                infos.put("versionCode", versionCode)
            }
        } catch (e: PackageManager.NameNotFoundException) {

            Log.e("CrashHandler", "an error occured when collect package info", e)
        }

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infos.put(field.name, field.get(null).toString())
                Log.d("CrashHandler", field.name + " : " + field.get(null))
            } catch (e: Exception) {
                Log.e("CrashHandler", "an error occured when collect crash info", e)
            }

        }
    }

    /**
     * 保存crash文件
     *
     */
    private fun saveCrashInfoFile(ex: Throwable): String? {
        val stringBuffer = StringBuffer()
        for ((key, value) in infos) {
            stringBuffer.append(key + "=" + value + "\n")
        }
        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        stringBuffer.append(result)
        try {
            val timestamp = System.currentTimeMillis()
            val time = formatter.format(Date())
            val fileName = "crash-$time-$timestamp.txt"
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                val path = filePath
                val dir = File(path)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                val fileOutputStream = FileOutputStream(File(path, fileName))
                fileOutputStream.write(stringBuffer.toString().toByteArray())
                fileOutputStream.close()
            }
            return fileName
        } catch (e: Exception) {

        }

        return null
    }

}
