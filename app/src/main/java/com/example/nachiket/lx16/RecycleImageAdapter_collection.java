package com.example.nachiket.lx16;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by R on 04-09-2015.
 */
public class RecycleImageAdapter_collection extends RecyclerView.Adapter<RecycleImageAdapter_collection.ViewHolder>{
    private Collection collection;

    Collection_Loader collection_loader;

    public RecycleImageAdapter_collection(Collection collection){
        this.collection=collection;
    }



    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int i){
        View v = LayoutInflater.from(collection).inflate(R.layout.date_list,viewGroup,false);
         collection_loader=new Collection_Loader();
        Cursor cursor= MainActivity.pUtility.sqLiteDatabase.query(PUtility.tab_name1,null,null,null,null,null,null);
        if(cursor!=null && cursor.moveToFirst())
        {
            int x=0;
            do {
                Collection_Loader.s_no[x]=cursor.getInt(0);
                Collection_Loader.date[x]=new String(cursor.getString(1));
                Collection_Loader.total_amt[x]=cursor.getInt(2);
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

        viewHolder.s_no.setText("Count : " + Collection_Loader.s_no[(int) MainActivity.pUtility.getCollectionCount()-1-i]);
        viewHolder.date.setText("Date : " + Collection_Loader.date[(int) MainActivity.pUtility.getCollectionCount()-1-i]);
        viewHolder.amt.setText("Amount : "+Collection_Loader.total_amt[(int) MainActivity.pUtility.getCollectionCount()-1-i]);

        YoYo.with(Techniques.FlipInX).duration(1000).playOn(viewHolder.rowlinear);


    }

    public int getItemCount(){
        return (int) MainActivity.pUtility.getCollectionCount();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        //public ImageView imageView;
        public TextView s_no;
        public  TextView date;
        public  TextView amt;

        public LinearLayout rowlinear;

        public ViewHolder(View itemView){
            super(itemView);
            s_no=(TextView)itemView.findViewById(R.id.sno);
            date=(TextView)itemView.findViewById(R.id.date);
            amt=(TextView)itemView.findViewById(R.id.amt);
            rowlinear=(LinearLayout)itemView.findViewById(R.id.rowlinear2);
            //imageView=(ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
