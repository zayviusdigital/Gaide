package com.kepoyah.gaide_ai.config;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class FormatDate {


    public static String GetDateTime(String time) {
        String inputPattern = "yyyyMMddHHmmss";
        String outputPattern = "dd-MMM-yyyy HH:mm";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(Objects.requireNonNull(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String GetDateOnly(String time) {
        String inputPattern = "yyyyMMddHHmmss";
        String outputPattern = "dd-MMM-yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(Objects.requireNonNull(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String DateNow(){
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(Calendar.getInstance().getTime());
    }

    public static String DB6_4(String coded){
        byte[] valueDecoded;
        valueDecoded = Base64.decode(coded.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        return new String(valueDecoded);
    }

    public static String OK(String string){

        return getOK(string);
    }

    private static String getOK(String string){
        return getYA(DB6_4(string));
    }

    private static String getYA(String string){
        return getNOW(DB6_4(DB6_4(string)));
    }

    private static String getNOW(String string){
        return DB6_4(DB6_4(string));
    }

    private static String EB6_4(String value){
        if (value == null)
            value = "";
        return Base64.encodeToString(value.trim().getBytes(), Base64.DEFAULT);
    }
}
