package com.besco.innova.codefoncier.Function;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;


import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

import java.io.File;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED;


/**
 * Created by root on 05/11/15.
 */
public class Functions {


    public static String hashPassword(String salt, String clearPassword) {
        String hash = "";
        try {
            //Log.d("AuthProvider", "start hashing password...");
            String salted = null;
            if (salt == null || "".equals(salt)) {
                salted = clearPassword;
            } else {
                salted = clearPassword + "{" + salt + "}";
            }
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte sha[] = md.digest(salted.getBytes());
            for (int i = 1; i < 5000; i++) {
                byte c[] = new byte[sha.length + salted.getBytes().length];
                System.arraycopy(sha, 0, c, 0, sha.length);
                System.arraycopy(salted.getBytes(), 0, c, sha.length, salted.getBytes().length);
                sha = md.digest(c);
            }
            hash = new String(Base64.encode(sha, Base64.NO_WRAP));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //do something with this exception
        }
        //Log.d("AuthProvider", "hashing password is done!");
        return hash;
    }

    //Get imei for the device
    public String getIMEI(Context context) {

        String imei = null;
        TelephonyManager mngr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        imei = mngr.getDeviceId();
        return imei;

    }

    //Check if email is valid
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //Remove accents
    public static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    //Get gmail account email
    public String getUsername(Context context) {

        AccountManager manager = AccountManager.get(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");
            if (parts.length > 0 && parts[0] != null)
                return parts[0];
            else
                return null;
        } else
            return null;
    }

    public boolean isGPSEnabled (Context mContext){
        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void turnGPSOn(Context ctx)
    {

        if(ctx!=null) {
            try
            {

                String provider = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


                if(!provider.contains("gps")){ //if gps is disabled
                    final Intent poke = new Intent();
                    poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                    poke.setData(Uri.parse("3"));
                    ctx.sendBroadcast(poke);
                }
            }
            catch (Exception e) {
                e.getMessage();
            }
        }
    }
    // automatic turn off the gps
    public void turnGPSOff(Context ctx)
    {
        if(ctx!=null) {
            String provider = Settings.Secure.getString(ctx.getContentResolver(), LOCATION_PROVIDERS_ALLOWED);
            if (provider.contains("gps")) { //if gps is enabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                ctx.sendBroadcast(poke);
            }
        }
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }


    //Check if URL is valid
    public static boolean checkValidUrl(String potentialUrl)
    {
        boolean result ;
        result = Patterns.WEB_URL.matcher(potentialUrl).matches();
        return result;
    }


    //Check if network is reachable
    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public boolean isNetworkAvailable2(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    //Send sms
    public static boolean sendSMSMessage(Context context,String phoneNo,int code) {

        boolean response ;

        String message = "Maroc Amicale code : " + code;

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            response = true;
        }

        catch (Exception e) {
            response = false;
        }

        return response;
    }

    public void deleteSugarDB(Context context)
    {
        SugarContext.terminate();
        SchemaGenerator schemaGenerator = new SchemaGenerator(context);
        schemaGenerator.deleteTables(new SugarDb(context).getDB());
        SugarContext.init(context);
        schemaGenerator.createDatabase(new SugarDb(context).getDB());
    }

    //Get country code
    public static String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toUpperCase(Locale.US);
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toUpperCase(Locale.US);
                }
            }
        }
        catch (Exception ignored) { }
        return null;
    }

    //Make a random code
    public static int getCodeRandom()
    {
        int code;
        int min = 1000;
        int max = 9998;
        Random r = new Random();
        code = r.nextInt(max - min + 1) + min;

        return code;
    }

    //Resize image
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public static long getFolderSize(File f) {
        long size = 0;
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                size += getFolderSize(file);
            }
        } else {
            size=f.length();
        }
        return size;
    }

    public boolean  createfolder() {//Create Folder
        boolean reponse ;

        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/MarocAmicale");
        reponse = !folder.exists() && folder.mkdirs();

        return reponse;
    }


    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        }
        return false;
    }


    public String getCompleteAddressString(double LATITUDE, double LONGITUDE, Context context) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.v("Loction address", "" + strReturnedAddress.toString());
            } else {
                //Log.w("My Current loction address", "No Address returned!");
                Log.v("Loction address","No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

}
