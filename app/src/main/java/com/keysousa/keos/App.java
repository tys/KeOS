package com.keysousa.keos;

import android.graphics.Canvas;
import android.graphics.Paint;

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
}
