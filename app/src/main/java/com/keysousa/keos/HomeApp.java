package com.keysousa.keos;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.KeyEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import static com.keysousa.keos.Const.*;

import static com.keysousa.keos.Const.FONT;

public class HomeApp extends App{
  SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd(E)");
  public HomeApp(MainActivity main){
    super(main);
  }
  @Override
  public void paint(Canvas g){
    p.setColor(Color.BLACK);
    String date=sdf.format(new Date());
    g.drawText(
      date,
      (SW-Utils.width(date))/2,
      (SH-FONT)/2,
      p
    );
    paintBottomMenu(new String[]{"","ﾒﾆｭｰ",""},g);
  }
  @Override
  public void key(int code){
    switch(code){
      case KeyEvent.KEYCODE_DPAD_CENTER:
        main.tasks.push(new MenuApp(main));
        break;
      case KeyEvent.KEYCODE_F3:
        main.tasks.push(new SMSApp(main));
        break;
      case KeyEvent.KEYCODE_CALL:
        main.tasks.push(new TelApp(main));
        break;
    }
  }
  @Override
  public String getAppName(){
    return null;
  }
}
