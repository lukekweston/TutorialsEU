package weston.luke.notificationsdemo

import android.app.NotificationManager
import androidx.core.app.NotificationCompat

object BigPictureStyleMockData {
    const val mContentTitle = "Bob's Post"
    const val mContentText = "[Picture] Like my shot of Earth?"
    const val mPriority = NotificationCompat.PRIORITY_HIGH

    const val mBigImage = R.drawable.earth
    const val mBigContentTitle = "Bob's Post"
    const val mSummaryText = "Like my shot of Earth?"

    const val mChannelId = "channel_social_1"
    const val mChannelName = "Sample Social"
    const val mChannelDescription = "Sample Social Notifications"
    const val mChannelImportance = NotificationManager.IMPORTANCE_HIGH
    const val mChannelEnableVibrate = true
    const val mChannelLockScreenVisibility = NotificationCompat.VISIBILITY_PRIVATE

    fun mParticipants(): ArrayList<String>{

        val list = ArrayList<String>()

        list.add("Luke Luke")

        return list
    }
}