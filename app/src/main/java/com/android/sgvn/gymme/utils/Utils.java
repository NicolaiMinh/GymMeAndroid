package com.android.sgvn.gymme.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.android.sgvn.gymme.R;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


/**
 * Created by soantrinh
 */

public class Utils {
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String getTimeFormat(long milis, String format) {
        final long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        // long elapsedDays = milis / daysInMilli;
        milis = milis % daysInMilli;

        long elapsedHours = milis / hoursInMilli;
        milis = milis % hoursInMilli;

        long elapsedMinutes = milis / minutesInMilli;
        milis = milis % minutesInMilli;

        long elapsedSeconds = milis / secondsInMilli;

        String hh;
        String mm = "";
        String ss = "";
        if (elapsedHours > 0) {
            hh = elapsedHours + "";
        } else {
            hh = "00";
        }
        if (elapsedMinutes > 0) {
            if (elapsedMinutes < 10) {
                mm = "0";
            }
            mm += elapsedMinutes;
        } else {
            mm = "00";
        }
        if (elapsedSeconds > 0) {
            if (elapsedSeconds < 10) {
                ss = "0";
            }
            ss += elapsedSeconds;
        } else {
            ss = "00";
        }

        if ("00".equals(mm) && "00".equals(ss)) {
            return "";
        }

        return String.format(format, hh, mm, ss);
    }

    public static boolean isConnectInternet(Context context) {
        NetworkInfo i = null;
        try {
            Context applicationContext = context.getApplicationContext();
            ConnectivityManager conMgr = (ConnectivityManager) applicationContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            i = conMgr.getActiveNetworkInfo();
        } catch (Exception e) {
        }
        if (i == null) {
            return false;
        } else if (!i.isConnected()) {
            return false;
        } else return i.isAvailable();
    }


    public static String getRealPathFromURI(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MTraZeo");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MTraZeo", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    public static String getPathFromUri(Context context, Uri contentUri) {
        if (contentUri == null) return null;
        String filePath;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            filePath = cursor.getString(column_index);
            cursor.close();
        }
        return filePath;
    }

    public static long getLong(String dateString) {
        long startDate = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(dateString);

            startDate = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public static String getCountryNameByCode(String code) {
        Locale loc = new Locale("en",code);
        return loc.getDisplayCountry();
    }

    public static String getCountryCodeNew(String countryName) {
        String[] isoCountryCodes = Locale.getISOCountries();
        for (String code : isoCountryCodes) {
            Locale locale = new Locale("", code);
            if (countryName.equalsIgnoreCase(locale.getDisplayCountry())) {
                return code;
            }
        }
        return "us";
    }

    public static String getLanguageZipCode(Context mContext, String language) {
        String languageCode = "";

        String[] rl = mContext.getResources().getStringArray(R.array.LanguageCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(language.trim())) {
                languageCode = g[0];
                break;
            }
        }
        return languageCode;
    }

    public static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public static void setDisable(EditText mEdittext) {
        mEdittext.setEnabled(false);
        mEdittext.setFocusable(false);
        mEdittext.setCursorVisible(false);
        mEdittext.setKeyListener(null);
    }

    public static void setVisiable(Context context,EditText mEdittext) {
        mEdittext.setEnabled(true);
        mEdittext.setInputType(InputType.TYPE_CLASS_PHONE);
        mEdittext.setFocusable(true);
        mEdittext.setCursorVisible(true);
        mEdittext.setFocusableInTouchMode(true);
    }

    public static void showKeyboard(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }
}
