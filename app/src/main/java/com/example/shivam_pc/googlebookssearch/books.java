package com.example.shivam_pc.googlebookssearch;

/**
 * Created by Shivam-PC on 19-01-2018.
 */

public class books {
    private String mauthor;

    private String mname;

    private String mpublisher;

    private String mlanguage;

    private String murl;

    private String mbit;

    public books(String author, String name, String publisher,String language, String url,String bit)
    {
        mauthor=author;
        mname=name;
        mlanguage=language;
        mpublisher=publisher;
        murl=url;
        mbit=bit;
    }

    public String getname(){
        return mname;
    }
    public String getauthor(){
        return mauthor;
    }
    public String getpublisher(){
        return mpublisher;
    }
    public String getlanguage(){
        return mlanguage;
    }
    public String geturl(){
        return murl;
    }

    public String getimage()
    {
        return mbit;
    }

}

