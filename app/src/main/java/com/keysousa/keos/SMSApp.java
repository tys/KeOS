package com.keysousa.keos;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.telephony.SmsManager;
import android.view.KeyEvent;

import java.util.HashMap;
import java.util.Map;

import static com.keysousa.keos.Const.*;

public class SMSApp extends App{
  String buf="";
  Integer ccode,cindex;
  Map<Integer,String> keymapTel;
  Map<Integer,String> keymap;
  String telnumber="";
  int curpos=0;

  public SMSApp(MainActivity main){
    super(main);
    keymapTel=new HashMap<>();
    keymapTel.put(KeyEvent.KEYCODE_1,"1");
    keymapTel.put(KeyEvent.KEYCODE_2,"2");
    keymapTel.put(KeyEvent.KEYCODE_3,"3");
    keymapTel.put(KeyEvent.KEYCODE_4,"4");
    keymapTel.put(KeyEvent.KEYCODE_5,"5");
    keymapTel.put(KeyEvent.KEYCODE_6,"6");
    keymapTel.put(KeyEvent.KEYCODE_7,"7");
    keymapTel.put(KeyEvent.KEYCODE_8,"8");
    keymapTel.put(KeyEvent.KEYCODE_9,"9");
    keymapTel.put(KeyEvent.KEYCODE_STAR,"*");
    keymapTel.put(KeyEvent.KEYCODE_0,"0");
    keymapTel.put(KeyEvent.KEYCODE_POUND,"#");
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
    //宛先----------------------------------------------------------------------
    if(curpos==0){
      p.setStyle(Paint.Style.FILL_AND_STROKE);
      p.setColor(Color.BLACK);
      g.drawRect(0,y,SW,y+FONT+2,p);
      p.setColor(Color.WHITE);
    }else{
      p.setColor(Color.BLACK);
    }
    g.drawText("[宛先] "+telnumber,0,y+FONT,p);
    //本文----------------------------------------------------------------------
    y+=FONT+2;
    if(curpos==1){
      p.setStyle(Paint.Style.FILL_AND_STROKE);
      p.setColor(Color.BLACK);
      g.drawRect(0,y,SW,SH-FONT-2,p);
      p.setColor(Color.WHITE);
    }else{
      p.setColor(Color.BLACK);
    }
    y+=FONT;
    g.drawText("[本文]",0,y,p);
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
    paintBottomMenu(new String[]{"戻る","","送信"},g);
  }
  @Override
  public void key(int code){
    switch(code){
      case KeyEvent.KEYCODE_DPAD_DOWN:
        if(curpos<1){
          curpos++;
        }
        break;
      case KeyEvent.KEYCODE_DPAD_UP:
        if(curpos>0){
          curpos--;
        }
        break;
      case KeyEvent.KEYCODE_BACK:
        switch(curpos){
          case 0:
            if(!telnumber.isEmpty()){
              telnumber=telnumber.substring(0,telnumber.length()-1);
            }
            break;
          case 1:
            if(ccode!=null){
              ccode=null;
            }else if(!buf.isEmpty()){
              buf=buf.substring(0,buf.length()-1);
            }
            break;
        }
        break;
      case KeyEvent.KEYCODE_DPAD_RIGHT:
        if(ccode!=null){
          buf+=keymap.get(ccode).charAt(cindex);
          ccode=null;
          cindex=0;
        }
        break;
      case KeyEvent.KEYCODE_POUND:
        if(ccode!=null){
          cindex=(cindex-1+keymap.get(ccode).length())
            %keymap.get(ccode).length();
        }
        break;
      case KeyEvent.KEYCODE_F1:
        main.tasks.pop();
        break;
      case KeyEvent.KEYCODE_F2:
        if(ccode!=null){
          buf+=keymap.get(ccode).charAt(cindex);
          ccode=null;
        }
        try{
          SmsManager.getDefault().sendTextMessage(telnumber,null,buf,null,null);
        }catch(Exception e){
          e.printStackTrace();
        }
        main.tasks.pop();
        break;
      default:
        switch(curpos){
          case 0:
            if(keymap.containsKey(code)){
              telnumber+=keymapTel.get(code);
            }
            break;
          case 1:
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
        break;
    }
  }
  @Override
  public String getAppName(){
    return "SMS";
  }
}
