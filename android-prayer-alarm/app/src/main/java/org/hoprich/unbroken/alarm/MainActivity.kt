package org.hoprich.unbroken.alarm

import android.Manifest
import android.app.*
import android.content.*
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER; setPadding(32,32,32,32) }
        val title = TextView(this).apply { text = "The Unbroken Fellowship\nPrayer Alarm Prototype"; textSize = 23f; gravity = Gravity.CENTER }
        val schedule = Button(this).apply { text = "Schedule test alarm (2 minutes)" }
        val status = TextView(this).apply { text = "Test only. Not connected to prayer appointments."; gravity = Gravity.CENTER }
        layout.addView(title); layout.addView(schedule); layout.addView(status); setContentView(layout)
        if (Build.VERSION.SDK_INT >= 33 && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 10)
        schedule.setOnClickListener {
            val manager = getSystemService(ALARM_SERVICE) as AlarmManager
            if (Build.VERSION.SDK_INT >= 31 && !manager.canScheduleExactAlarms()) {
                startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM, Uri.parse("package:$packageName")))
                status.text = "Enable exact alarms, then tap Schedule again."
                return@setOnClickListener
            }
            val intent = Intent(this, PrayerAlarmReceiver::class.java)
            val pi = PendingIntent.getBroadcast(this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 120000L, pi)
            status.text = "Test alarm scheduled for 2 minutes from now. You may lock the screen."
        }
    }
}
