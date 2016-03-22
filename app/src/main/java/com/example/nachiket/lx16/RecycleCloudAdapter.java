package com.example.nachiket.lx16;

/**
 * Created by NACHIKET on 13/01/2016.
 */

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
public class RecycleCloudAdapter extends RecyclerView.Adapter<RecycleCloudAdapter.ViewHolder>{
    private CloudDatabase cloudDatabase;

    // MainActivity.cloudLoader MainActivity.cloudLoader;

    public RecycleCloudAdapter(CloudDatabase cloudDatabase){
        this.cloudDatabase=cloudDatabase;
    }



    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int i){
        View v = LayoutInflater.from(cloudDatabase).inflate(R.layout.row_list,viewGroup,false);
        return new ViewHolder(v);
    }
    public void onBindViewHolder(ViewHolder viewHolder,int i) {

        viewHolder.name.setText("Name : "+SettingsActivity.cloudLoader.names[i]);
        viewHolder.contact.setText("Contact : "+SettingsActivity.cloudLoader.contact[i]);
        viewHolder.mail.setText("Email : "+SettingsActivity.cloudLoader.mail[i]);
        viewHolder.id.setText("Unique ID : "+SettingsActivity.cloudLoader.uid[i]);
        viewHolder.pending.setText("Pending : "+SettingsActivity.cloudLoader.pending[i]);
        viewHolder.paid.setText("Paid : "+SettingsActivity.cloudLoader.paid[i]);
        viewHolder.colg.setText("College : "+SettingsActivity.cloudLoader.colg[i]);

        YoYo.with(Techniques.FlipInX).duration(1000).playOn(viewHolder.rowlinear);


    }

    public int getItemCount(){
        return (int)SettingsActivity.cloudcnt;
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
