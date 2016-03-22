package com.example.nachiket.lx16;

/**
 * Created by NACHIKET on 11/01/2016.
 */
public class CloudLoader {
    public  String[] names;
    public  String[] contact;
    public  String[] mail;
    public  String[] uid;
    public  int[] pending;
    public  int[] paid;
    public  String[] colg;
    public  static int cnt;
    
    public CloudLoader(int count)
    {
        cnt=count;
        names=new String[cnt];
        contact=new String[cnt];
        mail=new String[cnt];
        uid=new String[cnt];
        colg=new String[cnt];
        pending=new int[cnt];
        paid=new int[cnt];
    }
}
