package com.example.d10s.jsoup;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public String URL = "www.facebook.com/login.php", Username = "gorditosybonitos@live.com", Password = "quiensabe";
    //www.netflix.com/mx/Login
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnFetchData = (Button) findViewById(R.id.btnData);
        //if(haveNetworkConnection()) {
            Toast.makeText(getApplicationContext(), "Estas conectado a internet", Toast.LENGTH_LONG).show();
            /*btnFetchData.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new FetchWebsiteData().execute();
                }
            });*/
        //}
        //else Toast.makeText(getApplicationContext(), "No estas conectado a internet", Toast.LENGTH_LONG).show();

        //Document res = null;

        /*try {
            C
            int statusCode = loginFormFilled.statusCode();
            Map<String, String> cookies = loginFormFilled.cookies();
            Toast.makeText(this, "Status Code: "+statusCode, Toast.LENGTH_LONG).show();

            /*
            res = Jsoup.connect("www.facebook.com/login.php").get();

            //        .data("email","gorditosybonitos@live.com","password","quiensabe")
            //        .method(Connection.Method.POST)
            //        .execute();
            Toast.makeText(this, "res chido!!!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Status Code Fail", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    */

        //Document doc = res.parse();
        //TextView pum = (TextView) findViewById(R.id.pum);
        //Elements divs = res.select("div#globalContainer");
        //for (Element div : divs)
        //    pum.append(div.text());
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                haveConnectedWifi = ni.isConnected();
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                haveConnectedMobile = ni.isConnected();
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    private class FetchWebsiteData extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                Connection.Response loginForm = Jsoup.connect(URL)
                        .method(Connection.Method.GET)
                        .timeout(10000)
                        .execute();

                Document doc = loginForm.parse();
                String ltValue = doc.select("input[name=lt]").attr("value");
                String executionValue = doc.select("input[name=execution]").attr("value");

                doc = Jsoup.connect(URL)
                        .data("username", Username)
                        .data("password", Password)
                        .data("lt", ltValue)
                        .data("execution", executionValue)
                        .data("_eventId", "submit")
                        .data("submit", "Log in")
                        .cookies(loginForm.cookies())
                        .post();


            } catch (IOException e) {
                e.printStackTrace();
            }




            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            // Set title into TextView
            TextView txttitle = (TextView) findViewById(R.id.txtData);
            mProgressDialog.dismiss();
        }

    }
}
