package com.example.kira666.mapofcontacts;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    //provided by sir
//    MapController mc = mapView.getController();
//    double lat = Double.parseDouble("48.85827758964043");
//    double lon = Double.parseDouble("2.294543981552124");
//    GeoPoint geoPoint = new GeoPoint((int)(lat * 1E6), (int)(lon * 1E6));
//    mc.animateTo(geoPoint);
//    mc.setZoom(15);
//    mapView.invalidate();
    //provided by sir
    String contactDetailsFileString = null;
    ArrayList<String[]> arrayList = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadContacts contacts = new DownloadContacts();
        contacts.execute();
        try {
            contactDetailsFileString = contacts.get();
            if(contactDetailsFileString!=null){
                arrayList = new ArrayList<>();
                String[] contactSplited = contactDetailsFileString.split("[\\n]");
                for (String string:contactSplited) {
                    arrayList.add(string.split("[\\s]"));
                    System.out.println(Arrays.toString(string.split("[\\s]")));
                }
            }
        }catch (ExecutionException|InterruptedException exp){
            Log.e("MainActivityGetContacts",exp.getMessage());
        }
    }
    private class DownloadContacts extends AsyncTask<Void,Long,String>{
        public ProgressDialog progressDialog= null ;
        @Override
        protected String doInBackground(Void... params) {

            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL("http://www.cs.columbia.edu/~coms6998-8/assignments/homework2/contacts/contacts.txt");
                URLConnection connection = url.openConnection();
                BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
                int input;
                while ((input=inputStream.read())!=-1){
                    stringBuilder.append((char)input);
                }

            }catch (Exception exp){
                Log.e("MainActivityDownload:",exp.getMessage());
                return null;
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPreExecute() {
//            progressDialog = ProgressDialog.show(getApplicationContext(),"Downloading..",null);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

             super.onPostExecute(s);
        }
    }
}
