package com.kotlin.mall.services.update

import android.app.Notification
import android.content.Intent
import android.app.PendingIntent
import android.graphics.BitmapFactory
import android.os.IBinder
import android.app.NotificationManager
import android.app.Service
import android.net.Uri
import android.os.Environment
import android.support.v4.app.NotificationCompat
import com.kotlin.mall.R
import java.io.File


/**
 * 作者：何兆鸿 on 2018/6/6.
 * 学无止境~
 *
 */
class UpdateService : Service() {

    companion object {
        /**
         * 服务器固定地址
         */
        private val APK_URL_TITLE = "http://www.imooc.com/mobile/mukewang.apk"
    }

    /**
     * 文件存放路经
     */
    private var filePath: String? = null
    /**
     * 文件下载地址
     */
    private var apkUrl: String? = null

    private var notificationManager: NotificationManager? = null
    private var mNotification: Notification? = null

    private val contentIntent: PendingIntent
        get() = PendingIntent.getActivity(this, 0, installApkIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

    /**
     * 下载完成，安装
     */
    private val installApkIntent: Intent
        get() {
            val apkfile = File(filePath)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive")
            return intent
        }

    override fun onCreate() {
        notificationManager = getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager
        filePath = if(Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()){
            Environment.getExternalStorageDirectory().absolutePath + "/imooc/imooc.apk"
        }else{
            filesDir.absolutePath + "/imooc/imooc.apk"
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        apkUrl = APK_URL_TITLE
        notifyUser(getString(R.string.update_download_start), getString(R.string.update_download_start), 0)
        startDownload()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startDownload() {
        UpdateManager.getInstance().startDownload(apkUrl, filePath, object : UpdateDownloadListener {
            override fun onStarted() {}

            override fun onProgressChanged(progress: Int, downloadUrl: String) {
                notifyUser(getString(R.string.update_download_processing),
                        getString(R.string.update_download_processing), progress)
            }

            override fun onPrepared(contentLength: Long, downloadUrl: String) {}

            override fun onPaused(progress: Int, completeSize: Int, downloadUrl: String) {
                notifyUser(getString(R.string.update_download_failed),
                        getString(R.string.update_download_failed_msg), 0)
                deleteApkFile()
                stopSelf()// 停掉服务自身
            }

            override fun onFinished(completeSize: Int, downloadUrl: String) {
                notifyUser(getString(R.string.update_download_finish), getString(R.string.update_download_finish),
                        100)
                stopSelf()// 停掉服务自身
                startActivity(installApkIntent)
            }

            override fun onFailure() {
                notifyUser(getString(R.string.update_download_failed),
                        getString(R.string.update_download_failed_msg), 0)
                deleteApkFile()
                stopSelf()// 停掉服务自身
            }
        })
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    fun notifyUser(tickerMsg: String, message: String, progress: Int) {

        notifyThatExceedLv21(tickerMsg, message, progress)
    }

    private fun notifyThatExceedLv21(tickerMsg: String, message: String, progress: Int) {
        val notification = NotificationCompat.Builder(this)
        notification.setSmallIcon(R.drawable.bg_message_imooc)
        notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.bg_message_imooc))
        notification.setContentTitle(getString(R.string.app_name))
        if (progress in 1..99) { // java写法：if (progress > 0 && progress < 100)
            notification.setProgress(100, progress, false)
        } else {
            /**
             * 0,0,false,可以将进度条影藏
             */
            notification.setProgress(0, 0, false)
            notification.setContentText(message)
        }
        notification.setAutoCancel(true)
        notification.setWhen(System.currentTimeMillis())
        notification.setTicker(tickerMsg)
        notification.setContentIntent(if (progress >= 100)
            contentIntent
        else
            PendingIntent.getActivity(this, 0, Intent(), PendingIntent.FLAG_UPDATE_CURRENT))
        mNotification = notification.build()
        notificationManager!!.notify(0, mNotification)
    }

    /**
     * 删除无用apk文件
     */
    private fun deleteApkFile(): Boolean {
        val apkFile = File(filePath)
        return if (apkFile.exists() && apkFile.isFile()) {
            apkFile.delete()
        } else false
    }

}