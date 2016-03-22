package com.example.nachiket.lx16;

/**
 * Created by Ankit on 14-01-2016.
 */
public class Collection_Loader {
    public static String[] date;
    public static int[] total_amt;
    public static int[] s_no;
    public static int cnt;

    public Collection_Loader()
    {
        cnt=(int) MainActivity.pUtility.getCollectionCount();
        date=new String[cnt];
        total_amt=new int[cnt];
        s_no=new int[cnt];

    }

}


