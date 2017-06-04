package com.keysousa.keos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity{
  String[] APPS=new String[]{
    "[1]アプリ1","[2]アプリ2","[3]アプリ3","[4]アプリ4",
    "[5]アプリ5","[6]アプリ6","[7]アプリ7"
  };
  SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
  int curpos=0;
  public void paint(){
    //画面クリア-----------------------------------------------------------------
    p.setColor(Color.WHITE);
    g.drawRect(0,0,SW,SH,p);
    //カーソル-------------------------------------------------------------------
    p.setStyle(Paint.Style.FILL_AND_STROKE);
    p.setColor(Color.BLACK);
    g.drawRect(0,(curpos+2)*(FONT+2),width(APPS[curpos]),(curpos+3)*(FONT+2),p);
    int y=0;
    //電池----------------------------------------------------------------------
    p.setStyle(Paint.Style.STROKE);
    p.setColor(Color.BLACK);
    g.drawRect(2,1,16,11,p);
    p.setStyle(Paint.Style.FILL);
    g.drawRect(1,4,2,8,p);
    if(batteryLevel>75){
      g.drawRect(4,3,7,10,p);
    }
    if(batteryLevel>50){
      g.drawRect(8,3,11,10,p);
    }
    if(batteryLevel>25){
      g.drawRect(12,3,15,10,p);
    }
    //アンテナ-------------------------------------------------------------------
    p.setColor(Color.BLACK);
    p.setStyle(Paint.Style.STROKE);
    Path ph=new Path();
    ph.moveTo(19,1);
    ph.lineTo(29,1);
    ph.lineTo(24,6);
    ph.close();
    g.drawPath(ph,p);
    p.setStyle(Paint.Style.FILL);
    g.drawRect(23,1,25,12,p);
    g.drawRect(27,7,29,12,p);
    g.drawRect(30,4,32,12,p);
    g.drawRect(33,1,35,12,p);
    //時刻
    p.setColor(Color.BLACK);
    p.setTextSize(FONT);
    g.drawText(sdf.format(new Date()),90,FONT,p);
    //タイトル-------------------------------------------------------------------
    y+=FONT+1;
    p.setStyle(Paint.Style.FILL);
    p.setColor(Color.BLACK);
    g.drawRect(0,y,SW,y+FONT+2,p);
    p.setTextSize(FONT);
    p.setColor(Color.WHITE);
    g.drawText("メニュー",0,y+FONT,p);
    y+=FONT+3;
    //本文----------------------------------------------------------------------
    p.setColor(Color.BLACK);
    p.setStyle(Paint.Style.FILL);
    p.setTextSize(FONT);
    int i=0;
    for(String s: APPS){
      p.setColor(i==curpos?Color.WHITE:Color.BLACK);
      g.drawText(s,0,y+FONT,p);
      y+=FONT+2;
      i++;
    }
  }
  void key(int code){
    switch(code){
      //下----------------------------------------------------------------------
      case KeyEvent.KEYCODE_DPAD_DOWN:
        if(curpos<APPS.length-1){
          curpos++;
          view.invalidate();
        }
        break;
      //上---------------------------------------------------------------------
      case KeyEvent.KEYCODE_DPAD_UP:
        if(curpos>0){
          curpos--;
          view.invalidate();
        }
        break;
      //決定--------------------------------------------------------------------
      case KeyEvent.KEYCODE_DPAD_CENTER:
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

  //システム処理=================================================================
  int SW=120,SH=213,FONT=12;
  Canvas g;
  Paint p;
  Bitmap bitmap;
  Typeface typeface;
  View view;
  int batteryLevel=0;
  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    //描画系---------------------------------------------------------------------
    bitmap=Bitmap.createBitmap(SW,SH,Bitmap.Config.ARGB_8888);
    g=new Canvas(bitmap);
    typeface=Typeface.createFromAsset(getAssets(),"PixelMplus12-Regular.ttf");
    //typeface=Typeface.createFromAsset(getAssets(),"SH G30.ttc");
    p=new Paint();
    p.setTypeface(typeface);
    view=new View(this){
      @Override
      protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        paint();
        canvas.drawBitmap(bitmap,new Rect(0,0,SW,SH),new Rect(0,0,480,854),p);
      }
    };
    //電池----------------------------------------------------------------------
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver(){
      @Override
      public void onReceive(Context context,Intent intent){
        if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
          batteryLevel=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
          view.invalidate();
        }
      }
    };
    registerReceiver(broadcastReceiver,intentFilter);
    //アンテナ-------------------------------------------------------------------
    TelephonyManager telephonyManager=
      (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
    telephonyManager.listen(new PhoneStateListener(){
      @Override
      public void onSignalStrengthsChanged(SignalStrength signalStrength){
        Log.v("★SIGNAL",signalStrength.getGsmSignalStrength()+"db");
      }
    },PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    setContentView(view);
  }
  @Override
  protected void onResume(){
    super.onResume();
    getWindow().getDecorView().setSystemUiVisibility(
      View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN
    );
  }
  @Override
  public boolean dispatchKeyEvent(KeyEvent event){
    if(event.getAction()==KeyEvent.ACTION_UP){
      key(event.getKeyCode());
    }
    return false;
  }
  //============================================================================
}
