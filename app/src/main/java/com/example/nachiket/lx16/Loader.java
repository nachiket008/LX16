package com.example.nachiket.lx16;

/**
 * Created by NACHIKET on 20/12/2015.
 */
public class Loader {
    public static String[] names;
    public static String[] contact;
    public static String[] mail;
    public static String[] uid;
    public static int[] pending;
    public static int[] paid;
    public static String[] colg;
    public static int cnt;

    public Loader()
    {
        cnt=(int)MainActivity.pUtility.getTaskCount();
        names=new String[cnt];
        contact=new String[cnt];
        mail=new String[cnt];
        uid=new String[cnt];
        colg=new String[cnt];
        pending=new int[cnt];
        paid=new int[cnt];
    }

}
