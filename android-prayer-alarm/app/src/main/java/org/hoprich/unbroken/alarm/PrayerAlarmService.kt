package org.hoprich.unbroken.alarm

import android.app.*
import android.content.*
import android.media.*
import android.os.*

class PrayerAlarmService : Service() {
    private val handler = Handler(Looper.getMainLooper())
    private var player: MediaPlayer? = null
    private val stop = Runnable { stopSelf() }
    override fun onBind(intent: Intent?) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == "STOP") { stopSelf(); return START_NOT_STICKY }
        val channel = NotificationChannel("prayer_alarm", "Prayer alarms", NotificationManager.IMPORTANCE_HIGH)
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
        val stopIntent = PendingIntent.getService(this, 102, Intent(this, PrayerAlarmService::class.java).setAction("STOP"), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val notification = Notification.Builder(this, "prayer_alarm")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("The Unbroken Fellowship")
            .setContentText("Prayer time — alarm stops automatically after 60 seconds")
            .setOngoing(true)
            .addAction(Notification.Action.Builder(null, "Stop alarm", stopIntent).build())
            .build()
        startForeground(202, notification)
        try {
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            player = MediaPlayer.create(this, uri)?.apply {
                isLooping = true
                start()
            }
        } catch (_: Exception) { stopSelf(); return START_NOT_STICKY }
        handler.removeCallbacks(stop)
        handler.postDelayed(stop, 60000L)
        return START_NOT_STICKY
    }
    override fun onDestroy() {
        handler.removeCallbacks(stop)
        player?.run { if (isPlaying) stop(); release() }
        player = null
        super.onDestroy()
    }
}
