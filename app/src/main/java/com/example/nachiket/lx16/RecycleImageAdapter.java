package com.example.nachiket.lx16;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by R on 04-09-2015.
 */
public class RecycleImageAdapter extends RecyclerView.Adapter<RecycleImageAdapter.ViewHolder>{
    private Database database;

    Loader loader;

    public RecycleImageAdapter(Database database){
        this.database=database;
    }



    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int i){
        View v = LayoutInflater.from(database).inflate(R.layout.row_list,viewGroup,false);
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
        return new ViewHolder(v);
    }
    public void onBindViewHolder(ViewHolder viewHolder,int i) {
        //int resourceID=mainActivity.getResources().getIdentifier("i" + ((i + 1)%9+1), "drawable", mainActivity.getPackageName());

        //viewHolder.imageView.setImageResource(resourceID);

        viewHolder.name.setText("Name : "+Loader.names[(int)MainActivity.pUtility.getTaskCount()-1-i]);
        viewHolder.contact.setText("Contact : "+Loader.contact[(int)MainActivity.pUtility.getTaskCount()-1-i]);
        viewHolder.mail.setText("Email : "+Loader.mail[(int)MainActivity.pUtility.getTaskCount()-1-i]);
        viewHolder.id.setText("Unique ID : "+Loader.uid[(int)MainActivity.pUtility.getTaskCount()-1-i]);
        viewHolder.pending.setText("Pending : "+Loader.pending[(int)MainActivity.pUtility.getTaskCount()-1-i]);
        viewHolder.paid.setText("Paid : "+Loader.paid[(int)MainActivity.pUtility.getTaskCount()-1-i]);
        viewHolder.colg.setText("College : "+Loader.colg[(int)MainActivity.pUtility.getTaskCount()-1-i]);

        YoYo.with(Techniques.FlipInX).duration(1000).playOn(viewHolder.rowlinear);


    }

    public int getItemCount(){
        return (int)MainActivity.pUtility.getTaskCount();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView name;
        public  TextView contact;
        public  TextView mail;
        public  TextView id;
        public  TextView pending;
        public  TextView paid;
        public  TextView colg;
        public LinearLayout rowlinear;

        public ViewHolder(View itemView){
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.rowname);
            contact=(TextView)itemView.findViewById(R.id.rowcontact);
            mail=(TextView)itemView.findViewById(R.id.rowmail);
            id=(TextView)itemView.findViewById(R.id.rowid);
            pending=(TextView)itemView.findViewById(R.id.rowpending);
            paid=(TextView)itemView.findViewById(R.id.rowpaid);
            colg=(TextView)itemView.findViewById(R.id.rowcolg);
            rowlinear=(LinearLayout)itemView.findViewById(R.id.rowlinear);
            //imageView=(ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
