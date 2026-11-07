# The Unbroken Fellowship — Android Prayer Alarm Prototype

Gate 02G.11 | Controlled development branch

## Scope
Native Android proof of concept: schedule a local alarm 2 minutes ahead, ring with a looping system alarm tone for at most 60 seconds, permit early dismissal, and show an alarm notification. No Supabase writes and no modifications to the deployed website.

## Build
Open this `android-prayer-alarm` directory as a project in Android Studio. Android SDK 35 and JDK 17 are required. Install on a test Android device.

## Test
1. Launch the app and grant notification permission when requested.
2. Grant exact-alarm permission if prompted in Android settings, then return to the app.
3. Tap **Schedule test alarm (2 minutes)** and lock the device.
4. Confirm an audible alarm starts near the scheduled time.
5. Tap **Stop alarm** in the notification to dismiss early.
6. Repeat and allow it to stop automatically within 60 seconds.

## Safety and limitations
- Test prototype only; do not distribute to members.
- Exact alarm access and background service restrictions vary by Android version and manufacturer.
- This uses a foreground service started by an exact alarm receiver. It does not bypass silent/DND settings or guarantee display over the lock screen.
- No production appointment sync, account authentication, rescheduling, reboot recovery, or duplicate suppression yet.
- The existing browser push reminders remain unchanged.
