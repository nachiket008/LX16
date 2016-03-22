package com.example.nachiket.lx16;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import info.hoang8f.widget.FButton;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    public static int cloudcnt=0;
    public static CloudLoader cloudLoader;
    FButton sync,cloud,cloudsync;
    Context context;
    EditText IP,pass;
    String textip;
    private static final String url1 = "jdbc:mysql://";
    private static final String url2=":3306/lx16";
    private static final String user = "root";
    private static final String password = "nachiket";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        context=this;
        sync=(FButton)findViewById(R.id.sync);
        IP= (EditText) findViewById(R.id.ip);
        cloudsync= (FButton) findViewById(R.id.cloudsync);
        pass= (EditText) findViewById(R.id.password);
        cloud= (FButton) findViewById(R.id.cloud);
        cloud.setOnClickListener(this);
        sync.setOnClickListener(this);
        cloudsync.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sync:
                textip=IP.getText().toString();
                if(!textip.equals(""))
                { Toast.makeText(this,IP.getText(),Toast.LENGTH_SHORT).show();
                Local local = new Local();
                local.execute();}
                break;

            case R.id.cloud:
                //Toast.makeText(this,pass.getText(),Toast.LENGTH_SHORT).show();

                if(pass.getText().toString().equals("mcug2k16_lx16"))
                {
                    Toast.makeText(this,"Password Accepted",Toast.LENGTH_SHORT).show();
                    Fetcher fetcher = new Fetcher();
                    fetcher.execute();
                }
                else
                    Toast.makeText(this,"Wrong Password",Toast.LENGTH_SHORT).show();
                break;
            case R.id.cloudsync:
                    CloudSync cloudSync = new CloudSync();
                    cloudSync.execute();
                    //Toast.makeText(this,"CLICKED",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public class Local extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... params) {
            String stat="LOCAL SYNC FAILED";
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Log.d("Nachiket",url1+textip+url2);
                Connection con= DriverManager.getConnection(url1+textip+url2, user, password);

                String getall="select * from part;";
                Loader loader=new Loader();
                Cursor cursor=MainActivity.pUtility.sqLiteDatabase.query(PUtility.tab_name,null,null,null,null,null,null);
                if(cursor!=null && cursor.moveToFirst())
                {
                    int x=0;
                    do {
                        Loader.uid[x]=new String(cursor.getString(0));
                        Loader.names[x]=new String(cursor.getString(1));
                        Loader.colg[x]=new String(cursor.getString(2));
                        Loader.mail[x]=new String(cursor.getString(3));
                        Loader.contact[x]=new String(cursor.getString(4));
                        Loader.paid[x]=cursor.getInt(5);
                        Loader.pending[x]=cursor.getInt(6);
                        x++;
                        // Toast.makeText(mContext, uid + " " + name + " " + colg + " " + mob_no + " " + e_id, Toast.LENGTH_LONG).show();
                    }
                    while(cursor.moveToNext());
                }
                if(null!=cursor){
                    cursor.close();
                }
                int cnter=(int)MainActivity.pUtility.getTaskCount();;

                for(int i=0;i<cnter;i++)
                {
                    try
                    {
                        Participant participant = new Participant(Loader.uid[i],Loader.names[i],Loader.colg[i],Loader.mail[i],Loader.contact[i],Loader.paid[i],Loader.pending[i]);
                        String qq="insert into part values('"+participant.unique_id+"','"+participant.name+"','"+participant.colg_name+"','"
                                +participant.e_id+"','"+participant.mob_no+"',"+participant.amt_paid+","+participant.amt_pending+");";

                        Statement st = con.createStatement();
                        st.executeUpdate(qq);
                    }
                    catch (Exception e)
                    {
                        Log.d("Nachiket",e.toString());
                    }
                }

                stat= "LOCAL SYNC SUCCESSFUL";






            }
            catch(Exception e) {

                Log.d("Nachiket", e.toString());

            }
            return stat;
        }

        protected void onPostExecute(String stat) {

            Toast.makeText(context, stat, Toast.LENGTH_LONG).show();


        }
    }

    public class CloudSync extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... params) {
            String stat="CLOUD SYNC FAILED";
            try {

                Loader loader=new Loader();
                Cursor cursor=MainActivity.pUtility.sqLiteDatabase.query(PUtility.tab_name,null,null,null,null,null,null);
                if(cursor!=null && cursor.moveToFirst())
                {
                    int x=0;
                    do {
                        Loader.uid[x]=new String(cursor.getString(0));
                        Loader.names[x]=new String(cursor.getString(1));
                        Loader.colg[x]=new String(cursor.getString(2));
                        Loader.mail[x]=new String(cursor.getString(3));
                        Loader.contact[x]=new String(cursor.getString(4));
                        Loader.paid[x]=cursor.getInt(5);
                        Loader.pending[x]=cursor.getInt(6);
                        x++;
                        // Toast.makeText(mContext, uid + " " + name + " " + colg + " " + mob_no + " " + e_id, Toast.LENGTH_LONG).show();
                    }
                    while(cursor.moveToNext());
                }
                if(null!=cursor){
                    cursor.close();
                }
                int cnter=(int)MainActivity.pUtility.getTaskCount();;

                for(int i=0;i<cnter;i++)
                {
                    URL url = new URL("http://lx16.royalwebhosting.net/Sync.php");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("uniqueid",Loader.uid[i]);

                    String query = builder.build().getEncodedQuery();
                    Log.d("Nachiket", query);

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();

                    int code = conn.getResponseCode();
                    Log.d("Nachiket", Integer.toString(code));

                    InputStream responseStream = new BufferedInputStream(conn.getInputStream());
                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = responseStreamReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    responseStreamReader.close();

                    String response = stringBuilder.toString();

                    Log.d("Nachiket",response);
                    if(!response.equals("1"))
                    {
                        Log.d("Nachiket","Blah");
                        try
                        {
                            URL url1 = new URL("http://api.msg91.com/api/sendhttp.php");
                            HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
                            conn1.setReadTimeout(10000);
                            conn1.setConnectTimeout(15000);
                            conn1.setRequestMethod("GET");
                            conn1.setDoInput(true);
                            conn1.setDoOutput(true);
//http://api.msg91.com/api/sendhttp.php?authkey=102678AltkiuBp22N569b4eb7&mobiles=918237628207&message=Hi+Nachiket+Ghorpade.+Your+unique+id+is++LX16TE8237628207.+The+amount+pending+is+Rs.+150.+-Linuxication2k16+Team&sender=MCUGLX&route=4&country=9
                            //http://api.msg91.com/api/sendhttp.php?authkey=YourAuthKey&mobiles=919999999990,919999999999&message=message&sender=senderid&route=1&country=0
                            String msg="Hello "+Loader.names[i]+". This is your unique registration number "+Loader.uid[i]+". The amount pending is Rs. "+Loader.pending[i]+". Thank you for participating in Linuxication2k16.\n-Team MCUG";
                            Uri.Builder builder1 = new Uri.Builder()
                                    .appendQueryParameter("authkey", "102678AltkiuBp22N569b4eb7")
                                    .appendQueryParameter("mobiles","91"+Loader.contact[i])
                                    .appendQueryParameter("message", msg)
                                    .appendQueryParameter("sender", "MCUGLX")
                                    .appendQueryParameter("route", "4")
                                    .appendQueryParameter("country", "91");

                            String query1 = builder1.build().getEncodedQuery();
                            Log.d("Nachiket",query1);
                            OutputStream os1 = conn1.getOutputStream();
                            BufferedWriter writer1 = new BufferedWriter(
                                    new OutputStreamWriter(os1, "UTF-8"));
                            writer1.write(query1);
                            writer1.flush();
                            writer1.close();
                            os1.close();
                            int code1 = conn1.getResponseCode();
                            Log.d("Nachiket", Integer.toString(code1));
                            conn1.connect();

                        }
                        catch (Exception e)
                        {
                            Log.d("Nachiket",e.toString());
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Log.d("Nachiket","Exists");
                    }

                }


                for(int i=0;i<cnter;i++)
                {


                            URL url = new URL("http://lx16.royalwebhosting.net/Register.php");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setReadTimeout(10000);
                            conn.setConnectTimeout(15000);
                            conn.setRequestMethod("POST");
                            conn.setDoInput(true);
                            conn.setDoOutput(true);

                            Uri.Builder builder = new Uri.Builder()
                                    .appendQueryParameter("uniqueid",Loader.uid[i])
                                    .appendQueryParameter("name",Loader.names[i])
                                    .appendQueryParameter("colg", Loader.colg[i])
                                    .appendQueryParameter("e_id",Loader.mail[i])
                                    .appendQueryParameter("mob_no", Loader.contact[i])
                                    .appendQueryParameter("amt_paid", Integer.toString(Loader.paid[i]))
                                    .appendQueryParameter("amt_pending", Integer.toString(Loader.pending[i]));
                            String query = builder.build().getEncodedQuery();
                            Log.d("Nachiket",query);


                            OutputStream os = conn.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(
                                    new OutputStreamWriter(os, "UTF-8"));
                            writer.write(query);
                            writer.flush();
                            writer.close();
                            os.close();
                            int code = conn.getResponseCode();
                            Log.d("Nachiket", Integer.toString(code));
                            conn.connect();

                }

                stat= "CLOUD SYNC SUCCESSFUL";
            }
            catch(Exception e) {

                Log.d("Nachiket", e.toString());

            }
            return stat;
        }

        protected void onPostExecute(String stat) {

            Toast.makeText(context, stat, Toast.LENGTH_LONG).show();


        }
    }


    public class Fetcher extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("http://lx16.royalwebhosting.net/Fetch.php");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                int code = conn.getResponseCode();
                Log.d("code", Integer.toString(code));

                InputStream responseStream = new BufferedInputStream(conn.getInputStream());
                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = responseStreamReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                responseStreamReader.close();

                String response = stringBuilder.toString();
                Log.d("response",response);
                JSONArray jsonArray = new JSONArray(response);
                //JSONObject jsonResponse = new JSONObject(response);
              //  Log.d("Nachiket",Integer.toString(jsonArray.length()));

                SettingsActivity.cloudcnt=jsonArray.length();

                CloudLoader cloudLoader = new CloudLoader(jsonArray.length());

                for(int i=0;i<jsonArray.length();i++)
                {
                    cloudLoader.names[i]=jsonArray.getJSONObject(i).getString("name");
                    cloudLoader.colg[i]=jsonArray.getJSONObject(i).getString("colg");
                    cloudLoader.contact[i]=jsonArray.getJSONObject(i).getString("mob_no");
                    cloudLoader.mail[i]=jsonArray.getJSONObject(i).getString("e_id");
                    cloudLoader.uid[i]=jsonArray.getJSONObject(i).getString("uniqueid");
                    cloudLoader.paid[i]=Integer.parseInt(jsonArray.getJSONObject(i).getString("amt_paid"));
                    cloudLoader.pending[i]=Integer.parseInt(jsonArray.getJSONObject(i).getString("amt_pending"));
                  //  Log.d("Nachiket", jsonArray.getJSONObject(i).getString("name"));
                }

                SettingsActivity.cloudLoader = cloudLoader;
               /* if(jsonResponse.length() == 0){
                    returnedUser = null;
                }else{
                    String name = jsonResponse.getString("name");
                    returnedUser = new User(name,user.email,user.password);
                    Log.d("returned user",returnedUser.email);
                }*/

            } catch (Exception e) {
                Log.d("Nachiket",e.toString());
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void params) {
            startActivity(new Intent(SettingsActivity.this,CloudDatabase.class));
            //Toast.makeText(mContext, "Execution completed", Toast.LENGTH_LONG).show();


        }
    }


}
