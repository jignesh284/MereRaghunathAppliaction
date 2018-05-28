package sumanbhaiji.org.mereraghunath_.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import sumanbhaiji.org.mereraghunath_.MainActivity;
import sumanbhaiji.org.mereraghunath_.R;

/**
 * Created by jigneshmodi on 13/02/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private Bitmap bitmap;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "FROM:" + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message Data:" + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Body : " + remoteMessage.getNotification().getBody());

            //The message which i send will have keys named [message, image, AnotherActivity] and corresponding values.
            //You can change as per the requirement.

            //message will contain the Push Message

            String message = remoteMessage.getData().get("message");

            //imageUri will contain URL of the image to be displayed with Notification

            String imageUri = remoteMessage.getData().get("image");

            //If the key AnotherActivity has  value as True then when the user taps on notification, in the app AnotherActivity will be opened.
            //If the key AnotherActivity has  value as False then when the user taps on notification, in the app MainActivity will be opened.

            String TrueOrFlase = remoteMessage.getData().get("AnotherActivity");

            //To get a Bitmap image from the URL received
            bitmap = getBitmapfromUrl(imageUri);

            sendNotification(message, bitmap, TrueOrFlase);

        }
    }

    /***
     * Display Notification
     * @param body
     */
    private void sendNotification(String body, Bitmap image, String TrueOrFalse) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("AnotherActivity", TrueOrFalse);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0/* Request Code Id*/, intent, PendingIntent.FLAG_ONE_SHOT);
        //set Sound Of notification
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(image)
                .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(image))/*Notification with Image*/
                .setContentTitle("Mere Raghunath")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /*ID of Notification*/, notifBuilder.build());
    }

    /*
   *To get a Bitmap image from the URL received
   * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}




