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
}
