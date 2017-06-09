package com.keysousa.keos;

import android.view.KeyEvent;

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
  public static String keyCodeToString(int c){
    switch(c){
      case KeyEvent.KEYCODE_1:return "1";
      case KeyEvent.KEYCODE_2:return "2";
      case KeyEvent.KEYCODE_3:return "3";
      case KeyEvent.KEYCODE_4:return "4";
      case KeyEvent.KEYCODE_5:return "5";
      case KeyEvent.KEYCODE_6:return "6";
      case KeyEvent.KEYCODE_7:return "7";
      case KeyEvent.KEYCODE_8:return "8";
      case KeyEvent.KEYCODE_9:return "9";
      case KeyEvent.KEYCODE_STAR:return "*";
      case KeyEvent.KEYCODE_0:return "0";
      case KeyEvent.KEYCODE_POUND:return "#";
    }
    return "";
  }
}
