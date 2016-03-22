package com.example.nachiket.lx16;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

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
import java.text.SimpleDateFormat;
import java.util.Date;

import info.hoang8f.widget.FButton;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener{

    EditText name,colg,contact,mail,paid,pending;
    TextView ID;
    FButton registerButton,clearButton;
    Spinner event,spinner;
    Context context;
    private static final String url = "jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6100175";
    private static final String user = "sql6100175";
    private static final String pass = "S3h6C8743b";

    public static PUtility pUtility;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        YoYo.with(Techniques.StandUp).duration(3000).playOn(toolbar);
        setSupportActionBar(toolbar);

        context=getApplicationContext();

        //spinner code
         spinner=(Spinner)findViewById(R.id.mail_spinner);
        ArrayAdapter adapter =ArrayAdapter.createFromResource(this,R.array.email_service,R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(adapter);
        event=(Spinner)findViewById(R.id.event);
        ArrayAdapter adapter1 =ArrayAdapter.createFromResource(this,R.array.courses,R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        event.setAdapter(adapter1);

        //findedits
        name= (EditText) findViewById(R.id.editname);
        colg= (EditText) findViewById(R.id.editcolg);
        mail= (EditText) findViewById(R.id.editmail);
        contact= (EditText) findViewById(R.id.editmobile);
        paid= (EditText) findViewById(R.id.editpaid);
        pending= (EditText) findViewById(R.id.editrem);

        ID=(TextView)findViewById(R.id.textid);

        registerButton=(FButton)findViewById(R.id.register);
        clearButton=(FButton)findViewById(R.id.clear);

        registerButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);


        //local database
        pUtility=new PUtility(this);
        pUtility.open();

        //Toast.makeText(this, Long.toString(pUtility.getTaskCount()), Toast.LENGTH_LONG).show();

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_basic) {
            startActivity(new Intent(this,Syllabus_basic.class));
            // Handle the camera action
        } else if (id == R.id.nav_adv) {
            startActivity(new Intent(this,Collection.class));
        } else if (id == R.id.nav_db) {
            startActivity(new Intent(this,Database.class));

        } else if (id == R.id.nav_abt) {
            startActivity(new Intent(this,About.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.clear:
                name.setText("");
                colg.setText("");
                mail.setText("");
                contact.setText("");
                paid.setText("");
                pending.setText("");
                ID.setText("Unique ID : ");
                break;

            case R.id.register:
                if(name.getText().toString().equals(""))
                {
                    YoYo.with(Techniques.Shake).duration(1000).playOn(name);
                    Toast.makeText(this,"Enter all details",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(colg.getText().toString().equals(""))
                {
                    YoYo.with(Techniques.Shake).duration(1000).playOn(colg);
                    Toast.makeText(this,"Enter all details",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mail.getText().toString().equals(""))
                {
                    YoYo.with(Techniques.Shake).duration(1000).playOn(mail);
                    Toast.makeText(this,"Enter all details",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(contact.getText().toString().equals(""))
                {
                    YoYo.with(Techniques.Shake).duration(1000).playOn(contact);
                    Toast.makeText(this,"Enter all details",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(paid.getText().toString().equals(""))
                {
                    YoYo.with(Techniques.Shake).duration(1000).playOn(paid);
                    Toast.makeText(this,"Enter all details",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pending.getText().toString().equals(""))
                {
                    YoYo.with(Techniques.Shake).duration(1000).playOn(pending);
                    Toast.makeText(this,"Enter all details",Toast.LENGTH_SHORT).show();
                    return;
                }

                String UID="LX16";
                if(event.getSelectedItem().toString().equals("FE"))
                    UID=UID+"FE";
                else if(event.getSelectedItem().toString().equals("SE"))
                    UID=UID+"SE";
                else if(event.getSelectedItem().toString().equals("TE"))
                    UID=UID+"TE";
                else if(event.getSelectedItem().toString().equals("Diploma"))
                    UID=UID+"DP";
                else if(event.getSelectedItem().toString().equals("Others"))
                    UID=UID+"OT";


                UID=UID+(new StringBuffer(contact.getText().toString()).reverse().toString());
                Toast.makeText(this,UID,Toast.LENGTH_LONG).show();

                ID.setText("Unique ID : "+UID);

                //date shit
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String currentDateandTime = sdf.format(new Date());

                String email,sname,scolg,mobno;
                 int ipaid,ipending;
                email=mail.getText().toString()+spinner.getSelectedItem().toString();
                sname=name.getText().toString();
                scolg=colg.getText().toString();
                ipaid=Integer.parseInt(paid.getText().toString());
                ipending=Integer.parseInt(pending.getText().toString());
                mobno=contact.getText().toString();

                pUtility.addParticipant(new Participant(UID, sname, scolg, email, mobno, ipaid, ipending),currentDateandTime,ipaid);
                //pUtility.addDate(currentDateandTime,ipaid);

                //Cloud cloud=new Cloud(new Participant(UID, sname, scolg, email, mobno, ipaid, ipending));
               // cloud.execute();

                //Texter texter = new Texter(new Participant(UID, sname, scolg, email, mobno, ipaid, ipending));
               //texter.execute();

                Msg msg = new Msg(new Participant(UID, sname, scolg, email, mobno, ipaid, ipending));
                msg.execute();
                Royal royal =new Royal(new Participant(UID, sname, scolg, email, mobno, ipaid, ipending));
                royal.execute();
                Mailer mailer=new Mailer(new Participant(UID, sname, scolg, email, mobno, ipaid, ipending));
                mailer.execute();

                Store store=new Store(new Participant(UID, sname, scolg, email, mobno, ipaid, ipending));
                store.execute();



               // pUtility.listParticipant();


                break;
        }
    }


    public class Texter extends  AsyncTask<Void,Void,String>
    {
        Participant participant;
        public Texter(Participant participant)
        {
            this.participant=participant;
        }


        @Override
        protected String doInBackground(Void... params) {
            String stat="Text sending failed";
            try
            {
                URL url = new URL("http://lx16.6te.net/SMS/index.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                String msg="Hi "+participant.name+". Your unique id is"+participant.unique_id+". The amount pending is Rs. "+participant.amt_pending+". -Linuxication2k16 Team";
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("uid", "9420152586")
                        .appendQueryParameter("pwd", "lx16mcug")
                        .appendQueryParameter("to", participant.mob_no)
                        .appendQueryParameter("msg", msg);

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
                stat="Text sent";
            }
            catch (Exception e)
            {
                Log.d("Nachiket",e.toString());
                e.printStackTrace();
            }
            return stat;
        }
        protected void onPostExecute(String stat) {

            Toast.makeText(context, stat, Toast.LENGTH_LONG).show();


        }
    }

    public class Store extends AsyncTask<Void,Void,String>
    {
        Participant participant;
        public Store(Participant participant)
        {
            this.participant=participant;
        }

        @Override
        protected String doInBackground(Void... params) {
            String stat="Cloud Update Failed";
            try {
                URL url = new URL("http://lx16.comxa.com/Register.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("uniqueid", participant.unique_id)
                        .appendQueryParameter("name", participant.name)
                        .appendQueryParameter("colg", participant.colg_name)
                        .appendQueryParameter("e_id", participant.e_id)
                        .appendQueryParameter("mob_no", participant.mob_no)
                        .appendQueryParameter("amt_paid",Integer.toString(participant.amt_paid))
                        .appendQueryParameter("amt_pending", Integer.toString(participant.amt_pending));
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
                stat="Cloud Updated";

            }catch (Exception e) {
                Log.d("Nachiket",e.toString());
                e.printStackTrace();
            }
            return  stat;
        }

        protected void onPostExecute(String stat) {

           // Toast.makeText(context, stat, Toast.LENGTH_LONG).show();


        }
    }



    public class Cloud extends AsyncTask<Void,Void,String>
    {
        Participant participant;
        public Cloud(Participant participant)
        {
            this.participant=participant;
        }

        @Override
        protected String doInBackground(Void... params) {
            String lol="Cloud update failed";

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection con= DriverManager.getConnection(url, user, pass);

                String qq="insert into part values('"+participant.unique_id+"','"+participant.name+"','"+participant.colg_name+"','"
                        +participant.e_id+"','"+participant.mob_no+"',"+participant.amt_paid+","+participant.amt_pending+");";


                Statement st = con.createStatement();
                st.executeUpdate(qq);
                lol="Cloud updated";


            }
            catch(Exception e) {

                Log.d("Nachiket", e.toString());

            }

            return lol;
        }

        protected void onPostExecute(String lol) {

           // Toast.makeText(context, lol, Toast.LENGTH_LONG).show();

        }
    }



    public class Mailer extends AsyncTask<Void,Void,String>
    {
        Participant participant;

        public Mailer(Participant participant)
        {
            this.participant=participant;
        }

        @Override
        protected String doInBackground(Void... params) {
            String stat="Mail Sending Failed";
            try {
                URL url = new URL("http://lx16.comxa.com/Mail.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("uniqueid", participant.unique_id)
                        .appendQueryParameter("name", participant.name)
                        .appendQueryParameter("colg", participant.colg_name)
                        .appendQueryParameter("e_id", participant.e_id)
                        .appendQueryParameter("mob_no", participant.mob_no)
                        .appendQueryParameter("amt_paid",Integer.toString(participant.amt_paid))
                        .appendQueryParameter("amt_pending", Integer.toString(participant.amt_pending));
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
                InputStream responseStream = new BufferedInputStream(conn.getInputStream());
                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = responseStreamReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                responseStreamReader.close();

                String response = stringBuilder.toString();
                Log.d("Nachiket", response);
                stat="Mail sent";

            }catch (Exception e) {
                Log.d("Nachiket",e.toString());
                e.printStackTrace();
            }
            return  stat;

        }

        protected void onPostExecute(String stat) {

          //  Toast.makeText(context, stat, Toast.LENGTH_LONG).show();


        }
    }

    public class Msg extends AsyncTask<Void,Void,String>
    {
        Participant participant;
        public Msg(Participant participant){this.participant=participant;}

        @Override
        protected String doInBackground(Void... params) {
            String stat="Text Sending Failed";
            try
            {
                URL url = new URL("http://api.msg91.com/api/sendhttp.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);
//http://api.msg91.com/api/sendhttp.php?authkey=102678AltkiuBp22N569b4eb7&mobiles=918237628207&message=Hi+Nachiket+Ghorpade.+Your+unique+id+is++LX16TE8237628207.+The+amount+pending+is+Rs.+150.+-Linuxication2k16+Team&sender=MCUGLX&route=4&country=9
                //http://api.msg91.com/api/sendhttp.php?authkey=YourAuthKey&mobiles=919999999990,919999999999&message=message&sender=senderid&route=1&country=0
                String msg="Hello "+participant.name+". This is your unique registration number "+participant.unique_id+". The amount pending is Rs. "+participant.amt_pending+". Thank you for participating in Linuxication2k16.\n-Team MCUG";
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("authkey", "102678AltkiuBp22N569b4eb7")
                        .appendQueryParameter("mobiles","91"+participant.mob_no)
                        .appendQueryParameter("message", msg)
                        .appendQueryParameter("sender", "MCUGLX")
                        .appendQueryParameter("route", "4")
                        .appendQueryParameter("country", "91");

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
                stat="Text sent";
            }
            catch (Exception e)
            {
                Log.d("Nachiket",e.toString());
                e.printStackTrace();
            }
            return stat;
        }
        protected void onPostExecute(String stat) {

            Toast.makeText(context, stat, Toast.LENGTH_LONG).show();


        }
    }

    public class Royal extends AsyncTask<Void,Void,String>
    {
        Participant participant;
        public Royal(Participant participant)
        {
            this.participant=participant;
        }

        @Override
        protected String doInBackground(Void... params) {
            String stat="Cloud Update Failed";
            try {
                URL url = new URL("http://lx16.royalwebhosting.net/Register.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("uniqueid", participant.unique_id)
                        .appendQueryParameter("name", participant.name)
                        .appendQueryParameter("colg", participant.colg_name)
                        .appendQueryParameter("e_id", participant.e_id)
                        .appendQueryParameter("mob_no", participant.mob_no)
                        .appendQueryParameter("amt_paid",Integer.toString(participant.amt_paid))
                        .appendQueryParameter("amt_pending", Integer.toString(participant.amt_pending));
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
                stat="Cloud Updated";

            }catch (Exception e) {
                Log.d("Nachiket",e.toString());
                e.printStackTrace();
            }
            return  stat;
        }

        protected void onPostExecute(String stat) {

            Toast.makeText(context, stat, Toast.LENGTH_LONG).show();


        }
    }

}
