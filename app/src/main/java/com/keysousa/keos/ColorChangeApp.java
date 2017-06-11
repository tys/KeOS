package com.keysousa.keos;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;

import static com.keysousa.keos.Const.APP_Y;
import static com.keysousa.keos.Const.COLOR_BACK;
import static com.keysousa.keos.Const.COLOR_FORE;
import static com.keysousa.keos.Const.FONT;

public class ColorChangeApp extends App{
  int curpos=0;
  String[][] COLORS=new String[][]{
    new String[]{"ホワイト","#FFFFFF"},
    new String[]{"グレー","#CCCCCC"},
    new String[]{"ピンク","#FFCCCC"},
    new String[]{"ブルー","#CCFFFF"},
    new String[]{"オレンジ","#FFCC99"},
    new String[]{"パープル","#FFCCFF"},
    new String[]{"グリーン","#CCFFCC"},
    new String[]{"イエロー","#FFFF99"}
  };
  public ColorChangeApp(MainActivity main){
    super(main);
    for(int i=0;i<COLORS.length;i++){
      if(Color.parseColor(COLORS[i][1])==Const.COLOR_BACK){
        curpos=i;
        break;
      }
    }
  }
  @Override
  public void paint(Canvas g){
    p.setColor(COLOR_FORE);
    int y=APP_Y;
    for(int i=0;i<COLORS.length;i++){
      String s=COLORS[i][0];
      if(i==curpos){
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(COLOR_FORE);
        g.drawRect(0,y,Utils.width(s),y+FONT+2,p);
        p.setColor(COLOR_BACK);
      }else{
        p.setColor(COLOR_FORE);
      }
      g.drawText(s,0,y+FONT,p);
      y+=FONT+2;
    }
    paintBottomMenu(new String[]{"戻る","",""},g);
  }
  @Override
  public void key(int code){
    switch(code){
      case KeyEvent.KEYCODE_DPAD_DOWN:
        if(curpos<COLORS.length-1){
          curpos++;
          Const.COLOR_BACK=Color.parseColor(COLORS[curpos][1]);
        }
        break;
      case KeyEvent.KEYCODE_DPAD_UP:
        if(curpos>0){
          curpos--;
          Const.COLOR_BACK=Color.parseColor(COLORS[curpos][1]);
        }
        break;
      case KeyEvent.KEYCODE_F1:
        main.tasks.pop();
        break;
    }
  }
  @Override
  public String getAppName(){
    return "配色設定";
  }
}

