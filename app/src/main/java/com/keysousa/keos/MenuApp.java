package com.keysousa.keos;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.view.KeyEvent;

import static com.keysousa.keos.Const.*;

public class MenuApp extends App{
  int curpos=0;
  String[] APPS=new String[]{
    "電話をかける",
    "マナーモード",
    "SMS"
  };
  @Override
  public void paint(Canvas g){
    int y=APP_Y;
    p.setTextSize(FONT);
    for(int i=0;i<APPS.length;i++){
      String s=APPS[i];
      if(i==1){
        s=(main.am.getRingerMode()==AudioManager.RINGER_MODE_NORMAL)
          ?"マナーモードに設定"
          :"マナーモードを解除";
      }
      if(i==curpos){
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(Color.BLACK);
        g.drawRect(0,y,Utils.width(s),y+FONT+2,p);
      }
      p.setStyle(Paint.Style.FILL);
      p.setColor(i==curpos?Color.WHITE:Color.BLACK);
      g.drawText(s,0,y+FONT,p);
      y+=FONT+2;
    }
  }
  @Override
  public void key(int code){
    switch(code){
      case KeyEvent.KEYCODE_DPAD_DOWN:
        if(curpos<APPS.length-1){
          curpos++;
        }
        break;
      case KeyEvent.KEYCODE_DPAD_UP:
        if(curpos>0){
          curpos--;
        }
        break;
      case KeyEvent.KEYCODE_DPAD_CENTER:
        switch(curpos){
          case 0:
            main.tasks.push(new TelApp(main));
            break;
          case 1:
            main.am.setRingerMode(
              (main.am.getRingerMode()==AudioManager.RINGER_MODE_NORMAL)
                ?AudioManager.RINGER_MODE_VIBRATE
                :AudioManager.RINGER_MODE_NORMAL
            );
            break;
          case 2:
            main.tasks.push(new SMSApp(main));
            break;
        }
        break;
    }
  }
  @Override
  public String getAppName(){
    return "メニュー";
  }
  public MenuApp(MainActivity main){
    super(main);
  }
}
