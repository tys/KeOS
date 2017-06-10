package com.keysousa.keos;

import android.graphics.Canvas;
import android.graphics.Paint;

import static com.keysousa.keos.Const.COLOR_BACK;
import static com.keysousa.keos.Const.COLOR_FORE;
import static com.keysousa.keos.Const.FONT;
import static com.keysousa.keos.Const.SH;
import static com.keysousa.keos.Const.SW;

public abstract class App{
  public Paint p;
  public MainActivity main;
  public App(MainActivity main){
    this.main=main;
    this.p=main.p;
  }
  public abstract void paint(Canvas g);
  public abstract void key(int code);
  public abstract String getAppName();
  public void paintBottomMenu(String[] menus,Canvas g){
    for(int i=0;i<menus.length;i++){
      if(menus[i].isEmpty()){
        continue;
      }
      p.setColor(COLOR_FORE);
      g.drawRect(
        i*SW/menus.length+1,
        SH-FONT-1,
        (i+1)*SW/menus.length-1,
        SH,
        p
      );
      p.setColor(COLOR_BACK);
      g.drawText(
        menus[i],
        SW*i/menus.length+(SW/menus.length-Utils.width(menus[i]))/2,
        SH-1,
        p
      );
    }
  }
}
