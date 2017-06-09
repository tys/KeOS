package com.keysousa.keos;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.KeyEvent;

import java.util.HashMap;
import java.util.Map;

import static com.keysousa.keos.Const.*;

public class SMSApp extends App{
  String buf="";
  Integer ccode,cindex;
  Map<Integer,String> keymap;

  public SMSApp(MainActivity main){
    super(main);
    keymap=new HashMap<>();
    keymap.put(KeyEvent.KEYCODE_1,"あいうえおぁぃぅぇぉ");
    keymap.put(KeyEvent.KEYCODE_2,"かきくけこ");
    keymap.put(KeyEvent.KEYCODE_3,"さしすせそ");
    keymap.put(KeyEvent.KEYCODE_4,"たちつてとっ");
    keymap.put(KeyEvent.KEYCODE_5,"なにぬねの");
    keymap.put(KeyEvent.KEYCODE_6,"はひふへほ");
    keymap.put(KeyEvent.KEYCODE_7,"まみむめも");
    keymap.put(KeyEvent.KEYCODE_8,"やゆよゃゅょ");
    keymap.put(KeyEvent.KEYCODE_9,"らりるれろ");
    keymap.put(KeyEvent.KEYCODE_0,"わをんゎ、。ー・〜！？．　");
    keymap.put(KeyEvent.KEYCODE_STAR,"、。ー・〜！？　");
  }
  @Override
  public void paint(Canvas g){
    p.setColor(Color.BLACK);
    int y=APP_Y;
    y+=FONT;

    String buf2=buf;
    if(ccode!=null){
      buf2+=keymap.get(ccode).charAt(cindex);
    }
    int s=0,x=0;
    for(int i=0;i<buf2.length();i++){
      x+=Utils.width(""+buf2.charAt(i));
      int nw=(i==buf2.length()-1)?0:Utils.width(""+buf2.charAt(i+1));
      if(x+nw>SW||i==buf2.length()-1){
        g.drawText(buf2.substring(s,i+1),0,y,p);
        s=i+1;
        x=0;
        y+=FONT;
      }
    }
  }
  @Override
  public void key(int code){
    switch(code){
      case KeyEvent.KEYCODE_BACK:
        if(ccode!=null){
          ccode=null;
        }else if(!buf.isEmpty()){
          buf=buf.substring(0,buf.length()-1);
        }else{
          main.tasks.pop();
        }
        break;
      case KeyEvent.KEYCODE_DPAD_RIGHT:
        buf+=keymap.get(ccode).charAt(cindex);
        ccode=null;
        cindex=0;
        break;
      case KeyEvent.KEYCODE_POUND:
        if(ccode!=null){
          cindex=(cindex-1+keymap.get(ccode).length())
            %keymap.get(ccode).length();
        }
        break;
      default:
        if(keymap.containsKey(code)){
          if(ccode==null||code!=ccode){
            if(ccode!=null){
              buf+=keymap.get(ccode).charAt(cindex);
            }
            ccode=code;
            cindex=0;
          }else{
            cindex=(cindex+1)%keymap.get(ccode).length();
          }
        }
        break;
    }
  }
  @Override
  public String getAppName(){
    return "SMS";
  }
}
