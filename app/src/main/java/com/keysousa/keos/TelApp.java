package com.keysousa.keos;

import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.view.KeyEvent;

import java.util.HashMap;
import java.util.Map;

import static com.keysousa.keos.Const.*;

public class TelApp extends App{
  Map<Integer,String> keymap;
  String telnumber="";
  @Override
  public void paint(Canvas g){
    int y=APP_Y;
    p.setColor(COLOR_FORE);
    p.setTextSize(FONT);
    y+=FONT;
    g.drawText(telnumber,0,y,p);
    paintBottomMenu(new String[]{"戻る","","発信"},g);
  }
  @Override
  public void key(int code){
    switch(code){
      case KeyEvent.KEYCODE_BACK:
        if(!telnumber.isEmpty()){
          telnumber=telnumber.substring(0,telnumber.length()-1);
        }
        break;
      case KeyEvent.KEYCODE_F1:
        main.tasks.pop();
        break;
      case KeyEvent.KEYCODE_F2:
        main.startActivity(
          new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+telnumber))
        );
        break;
      default:
        if(keymap.containsKey(code)){
          telnumber+=keymap.get(code);
        }
        break;
    }
  }
  @Override
  public String getAppName(){
    return "電話をかける";
  }
  public TelApp(MainActivity main){
    super(main);
    keymap=new HashMap<>();
    keymap.put(KeyEvent.KEYCODE_1,"1");
    keymap.put(KeyEvent.KEYCODE_2,"2");
    keymap.put(KeyEvent.KEYCODE_3,"3");
    keymap.put(KeyEvent.KEYCODE_4,"4");
    keymap.put(KeyEvent.KEYCODE_5,"5");
    keymap.put(KeyEvent.KEYCODE_6,"6");
    keymap.put(KeyEvent.KEYCODE_7,"7");
    keymap.put(KeyEvent.KEYCODE_8,"8");
    keymap.put(KeyEvent.KEYCODE_9,"9");
    keymap.put(KeyEvent.KEYCODE_STAR,"*");
    keymap.put(KeyEvent.KEYCODE_0,"0");
    keymap.put(KeyEvent.KEYCODE_POUND,"#");
  }
}
