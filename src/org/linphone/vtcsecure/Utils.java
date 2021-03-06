package org.linphone.vtcsecure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import org.linphone.R;
import org.linphone.core.Reason;

/**
 * Created by Patrick on 3/2/16.
 */
public class Utils {

    public static String getReasonText(Reason reason, Context context) {
        String res = "";
        if (reason == Reason.None) {
            res =  context.getString(R.string.Reason_None);
        } else if (reason == Reason.NoResponse) {
            res =  context.getString(R.string.Reason_NoResponse);
        } else if (reason == Reason.BadCredentials) {
            res =  context.getString(R.string.Reason_BadCredentials);
        } else if (reason == Reason.Declined) {
            res =  context.getString(R.string.Reason_Declined);
        } else if (reason == Reason.NotFound) {
            res =  context.getString(R.string.Reason_NotFound);
        } else if (reason == Reason.NotAnswered) {
            res =  context.getString(R.string.Reason_NotAnswered);
        } else if (reason == Reason.Busy) {
            //The number/address you are trying to reach is busy
            res =  context.getString(R.string.Reason_Busy);
        } else if (reason == Reason.Media) {
            res =  context.getString(R.string.Reason_Media);
        } else if (reason == Reason.IOError) {
            res =  context.getString(R.string.Reason_IOError);
        } else if (reason == Reason.DoNotDisturb) {
            res =  context.getString(R.string.Reason_DoNotDisturb);
        } else if (reason == Reason.Unauthorized) {
            res =  context.getString(R.string.Reason_Unauthorized);
        } else if (reason == Reason.NotAcceptable) {
            res =  context.getString(R.string.Reason_NotAcceptable);
        } else if (reason == Reason.NoMatch) {
            //400 and others ?
            res =  context.getString(R.string.Reason_NoMatch);
        } else if (reason == Reason.MovedPermanently) {
            res =  context.getString(R.string.Reason_MovedPermanently);
        } else if (reason == Reason.Gone) {
            //The number/address you are trying to reach is no longer available.
            res =  context.getString(R.string.Reason_Gone);
        } else if (reason == Reason.TemporarilyUnavailable) {
            res =  context.getString(R.string.Reason_TemporarilyUnavailable);
        } else if (reason == Reason.AddressIncomplete) {
            res =  context.getString(R.string.Reason_AddressIncomplete);
        } else if (reason == Reason.NotImplemented) {
            res =  context.getString(R.string.Reason_NotImplemented);
        } else if (reason == Reason.BadGateway) {
            res =  context.getString(R.string.Reason_BadGateway);
        } else if (reason == Reason.ServerTimeout) {
            res =  context.getString(R.string.Reason_ServerTimeout);
        } else if (reason == Reason.Unknown) {
            res =  context.getString(R.string.Reason_Unknown);
        }
        else
            res = context.getString(R.string.Reason_other);

        return res;
    }

    public static String removeExtraQuotesFromStringIfPresent(String inputStr){
        String result = inputStr;
        //Check if string has extra quotes
        if(inputStr.startsWith("\"")&&inputStr.endsWith("\"")){
            result=removeQuotesFromStartAndEndOfString(inputStr);
        }
        return result;
    }

    private static String removeQuotesFromStartAndEndOfString(String inputStr) {
        String result = inputStr;
        int firstQuote = inputStr.indexOf('\"');
        int lastQuote = result.lastIndexOf('\"');
        int strLength = inputStr.length();
        if (firstQuote == 0 && lastQuote == strLength - 1) {
            result = result.substring(1, strLength - 1);
        }

        return result;
    }

    private static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static Drawable resize(Drawable image, Activity activity, int muliplication_factor) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        int width=muliplication_factor*b.getWidth();
        int height=muliplication_factor*b.getHeight();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, width, height, false);
        return new BitmapDrawable(activity.getResources(), bitmapResized);
    }

    public static boolean check_network_status(final Activity activity, final int ACTIVITY_RESULT_INT) {
       if (!isNetworkAvailable(activity)) {
            String message = "Network not reachable, please confirm your device is connected to the internet.";
            new AlertDialog.Builder(activity)
                    .setMessage(message)
                    .setTitle("Connection Error")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Turn on WIFI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(ACTIVITY_RESULT_INT!=-1) {//If you don't want to refresh app after wifi activity.
                                activity.startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), ACTIVITY_RESULT_INT);
                            }else{
                                activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            }
                            dialog.cancel();
                        }
                    })
                    .show();
            return false;
        }else{
            return true;
        }
    }

}
