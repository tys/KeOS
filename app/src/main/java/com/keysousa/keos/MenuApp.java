package com.keysousa.keos;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;

import java.io.UnsupportedEncodingException;

import static com.keysousa.keos.Const.*;

public class MenuApp extends App{
  int curpos=0;
  String[] APPS=new String[]{
    "電話をかける"
  };
  @Override
  public void paint(Canvas g){
    int y=APP_Y;
    //カーソル-------------------------------------------------------------------
    p.setStyle(Paint.Style.FILL_AND_STROKE);
    p.setColor(Color.BLACK);
    g.drawRect(0,(curpos)*(FONT+2)+y,width(APPS[curpos]),(curpos+1)*(FONT+2)+y,p);
    //リスト-------------------------------------------------------------------
    p.setColor(Color.BLACK);
    p.setStyle(Paint.Style.FILL);
    p.setTextSize(FONT);
    for(int i=0;i<APPS.length;i++){
      String s=APPS[i];
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
        }
        break;
    }
  }
  int width(String a){
    try{
      return FONT*a.getBytes("Shift_JIS").length/2;
    }catch(UnsupportedEncodingException e){
      return FONT*a.length();
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
