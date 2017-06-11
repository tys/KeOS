package com.keysousa.keos;

import java.io.UnsupportedEncodingException;

import static com.keysousa.keos.Const.FONT;

public class Utils{
  public static int width(String a){
    try{
      return FONT*a.getBytes("Shift_JIS").length/2;
    }catch(UnsupportedEncodingException e){
      return FONT*a.length();
    }
  }
  public static String telNumberDash(String a){
    for(int i=0;i<11;i++){
      if(i==3){
        a=a.substring(0,3)+"-"+a.substring(3);
      }
      if(i==8){
        a=a.substring(0,8)+"-"+a.substring(8);
      }
    }
    return a;
  }
}
