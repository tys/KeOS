package com.keysousa.keos;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.view.KeyEvent;

import static com.keysousa.keos.Const.*;

public class TelApp extends App{
  String telnumber="";
  @Override
  public void paint(Canvas g){
    int y=APP_Y;
    p.setColor(Color.BLACK);
    p.setTextSize(FONT);
    y+=FONT;
    g.drawText(telnumber,0,y,p);
  }
  @Override
  public void key(int code){
    switch(code){
      case KeyEvent.KEYCODE_DPAD_CENTER:
        main.startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+telnumber)));
        break;
      case KeyEvent.KEYCODE_BACK:
        if(telnumber.isEmpty()){
          main.tasks.pop();
        }else{
          telnumber=telnumber.substring(0,telnumber.length()-1);
        }
        break;
      case KeyEvent.KEYCODE_1:
        telnumber+="1";
        break;
      case KeyEvent.KEYCODE_2:
        telnumber+="2";
        break;
      case KeyEvent.KEYCODE_3:
        telnumber+="3";
        break;
      case KeyEvent.KEYCODE_4:
        telnumber+="4";
        break;
      case KeyEvent.KEYCODE_5:
        telnumber+="5";
        break;
      case KeyEvent.KEYCODE_6:
        telnumber+="6";
        break;
      case KeyEvent.KEYCODE_7:
        telnumber+="7";
        break;
      case KeyEvent.KEYCODE_8:
        telnumber+="8";
        break;
      case KeyEvent.KEYCODE_9:
        telnumber+="9";
        break;
      case KeyEvent.KEYCODE_STAR:
        telnumber+="*";
        break;
      case KeyEvent.KEYCODE_0:
        telnumber+="0";
        break;
      case KeyEvent.KEYCODE_POUND:
        telnumber+="#";
        break;
    }
  }
  @Override
  public String getAppName(){
    return "電話をかける";
  }
  public TelApp(MainActivity main){
    super(main);
  }
}
