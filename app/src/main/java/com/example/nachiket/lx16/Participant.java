package com.example.nachiket.lx16;


public class Participant {
    public final String name;
    public final String colg_name;
    public final String e_id;
    public final String mob_no;
    public final int amt_paid;
    public final int amt_pending;
    public final String unique_id;

    public Participant(String unique_id,String name, String colg_name, String e_id, String mob_no, int amt_paid, int amt_pending) {
        this.name = name;
        this.colg_name = colg_name;
        this.e_id = e_id;
        this.mob_no = mob_no;
        this.amt_paid = amt_paid;
        this.amt_pending = amt_pending;
        this.unique_id=unique_id;
    }
}
