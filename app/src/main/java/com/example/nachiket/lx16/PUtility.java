package com.example.nachiket.lx16;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class PUtility {
    public static final String db_name="linuxication.db";

    public static final String tab_name="participant";
    public static final String tab_name1="date_info";
    public static  final String col_id="uniqueid";
    public static final String col_name="name";
    public static final String col_colg="colg";
    public static final String col_e_id="e_id";
    public static  final  String col_mob_no="mob_no";
    public static final String col_amt_paid="amt_paid";
    public static final String total_amt="total_amt";
    public static final String reg_Date="reg_date";
    public static final String date_id="date_id";
    public static final String col_amt_pending="amt_pending";
    private static final String tab_create1="create table "+tab_name1+ "(" +date_id+" integer auto increment," +reg_Date+" date primary key,"+
            total_amt+" integer );";
    private static final String tab_create="create table "+tab_name+ "(" +col_id+" text primary key,"+
            col_name+" text,"+col_colg+" text,"+col_e_id+" text,"+col_mob_no+" text,"+col_amt_paid+" integer,"+
            col_amt_pending+" integer );";
    private PUtility pUtility;
     public SQLiteDatabase sqLiteDatabase;
    private PDbUtility pDbUtility;
    private Context mContext;
    public PUtility(Context context)
    {
        mContext=context;
        pDbUtility=new PDbUtility(context);

    }
    public void open()
    {
        sqLiteDatabase=pDbUtility.getWritableDatabase();
    }
    public void close()
    {
        sqLiteDatabase.close();
    }

    public long getTaskCount() {
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, tab_name);
    }
    public long getCollectionCount()
    {
        return DatabaseUtils.queryNumEntries(sqLiteDatabase,tab_name1);
    }

    public void addParticipant(Participant participant,String date1,int amt)
    {
        ContentValues values=new ContentValues();
        values.put(col_id,participant.unique_id);
        values.put(col_name,participant.name);
        values.put(col_colg,participant.colg_name);
        values.put(col_e_id,participant.e_id);
        values.put(col_mob_no,participant.mob_no);
        values.put(col_amt_paid,participant.amt_paid);
        values.put(col_amt_pending,participant.amt_pending);
        long id1=sqLiteDatabase.insert(tab_name,null,values);
        if(-1!=id1)
        {
            addDate(date1,amt);
            Toast.makeText(mContext, "insert success", Toast.LENGTH_SHORT).show();
        }

    }
    public void addDate(String date1,int amt)
    {
        ContentValues cv = new ContentValues();

       ; // the execution is different if _id is 2
        cv.put(total_amt, amt);
        cv.put(reg_Date, date1);
        cv.put(date_id,1);
        //sqLiteDatabase.execSQL("UPDATE " + tab_name1 + " SET " + date_id + "=" + date_id + "+1"+"where "+date_id+" >0");
        int id = (int) sqLiteDatabase.insertWithOnConflict(tab_name1, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            //cv.put(total_amt,Integer.valueOf(total_amt)+amt);
            //sqLiteDatabase.update(tab_name1, cv, reg_Date+"=?", new String[] {date1});  // number 1 is the _id here, update to variable for your code
           //sqLiteDatabase.execSQL("UPDATE");
            sqLiteDatabase.execSQL("UPDATE " + tab_name1 + " SET " + total_amt + "=" + total_amt + "+"+amt + ","+ date_id+"="+date_id+" +1"+" WHERE " + reg_Date + "=?",
                    new String[] { date1 } );

        }

    }
    public void listParticipant()
    {
        Cursor cursor=sqLiteDatabase.query(tab_name,null,null,null,null,null,null);
        if(cursor!=null && cursor.moveToFirst())
        {
            do {
                String uid=cursor.getString(0);
                String name=cursor.getString(1);
                String colg=cursor.getString(2);
                String e_id=cursor.getString(3);
                String mob_no=cursor.getString(4);
                int amt_paid=cursor.getInt(5);
                int amt_pending=cursor.getInt(6);
                Toast.makeText(mContext,uid+" "+name+" "+colg+" "+mob_no+" "+e_id,Toast.LENGTH_LONG).show();
            }
            while(cursor.moveToNext());
        }
        if(null!=cursor){
            cursor.close();
        }
    }
    private class PDbUtility extends SQLiteOpenHelper{

        public PDbUtility(Context context)
        {
            super(context,db_name,null,1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(tab_create);
            db.execSQL(tab_create1);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
