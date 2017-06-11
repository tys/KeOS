package com.keysousa.keos;

import android.database.Cursor;
import android.graphics.Canvas;
import android.net.Uri;
import android.view.KeyEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.keysousa.keos.Const.APP_Y;
import static com.keysousa.keos.Const.COLOR_FORE;
import static com.keysousa.keos.Const.FONT;
import static com.keysousa.keos.Const.SH;
import static com.keysousa.keos.Const.SW;

public class SMSInboxApp extends App{
  int offset=0;
  int maxpos=0;
  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd(E) HH:mm");
  public SMSInboxApp(MainActivity main){
    super(main);
    Uri.parse("content://sms/");
    Cursor c=main.getContentResolver().query(
      Uri.parse("content://sms/"),
      new String[]{"address","date","body"},
      null,
      null,
      null
    );
    c.moveToFirst();
    for(int i=0;i<c.getCount();i++){
      buf+=Utils.telNumberDash(c.getString(0))+"\n"
        +sdf.format(new Date(c.getLong(1)))
        +c.getString(2)+"\n\n";
      c.moveToNext();
    }
  }
  String buf="";
  @Override
  public void paint(Canvas g){
    int y=APP_Y;
    p.setColor(COLOR_FORE);
    int s=0,x=0;
    int y2=0;
    for(int i=0;i<buf.length();i++){
      x+=Utils.width(""+buf.charAt(i));
      int nw=(i==buf.length()-1)?0:Utils.width(""+buf.charAt(i+1));
      if(x+nw>SW||i==buf.length()-1||buf.charAt(i)=='\n'){
        if(y2>=offset&&y<SH-FONT*2){
          g.drawText(buf.substring(s,i+1).replace("\n",""),0,y+FONT,p);
          y+=FONT;
        }
        y2+=FONT;
        s=i+1;
        x=0;
      }
    }
    maxpos=y2-(SH-APP_Y-FONT);
    paintBottomMenu(new String[]{"戻る","",""},g);
  }
  @Override
  public void key(int code){
    switch(code){
      case KeyEvent.KEYCODE_DPAD_DOWN:
        if(offset<maxpos){
          offset+=FONT;
        }
        break;
      case KeyEvent.KEYCODE_DPAD_UP:
        if(offset-FONT>=0){
          offset-=FONT;
        }
        break;
      case KeyEvent.KEYCODE_F1:
        main.tasks.pop();
        break;
    }
  }
  @Override
  public String getAppName(){
    return "受信メール";
  }
}
