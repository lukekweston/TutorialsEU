package weston.luke.notificationsdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build


class NotificationUtil {

    fun createInboxStyleNotificationChannel(context: Context): String {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //the id of the channel.
            val channelId: String = InboxStyleMockData.mChannelId
            //The user-visible name of the channel.
            val channelName: CharSequence = InboxStyleMockData.mChannelName
            val channelDescription: String = InboxStyleMockData.mChannelDescription
            val channelImportance: Int = InboxStyleMockData.mChannelImportance
            val channelEnableVibrate: Boolean = InboxStyleMockData.mChannelEnableVibrate
            val channelLockScreenVisibility: Int = InboxStyleMockData.mChannelLockScreenVisibility

            //Initialize NotificationChannel
            val notificationChannel = NotificationChannel(channelId, channelName, channelImportance)
            notificationChannel.description = channelDescription
            notificationChannel.enableVibration(channelEnableVibrate)
            notificationChannel.lockscreenVisibility = channelLockScreenVisibility

            // Adds NotificationChannel to system. Attempting to create an existing notification
            // channel with its original values performs no operation, so it's safe to perform the
            // below sequence
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

            return channelId
        } else {
            return ""
        }
    }
}