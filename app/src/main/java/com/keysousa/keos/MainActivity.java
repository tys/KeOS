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
import android.media.AudioManager;
import android.os.BatteryManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

import static com.keysousa.keos.Const.*;

public class MainActivity extends AppCompatActivity{
  Stack<App> tasks=new Stack<>();
  SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
  public void paint(){
    //画面クリア-----------------------------------------------------------------
    p.setColor(Color.WHITE);
    g.drawRect(0,0,SW,SH,p);
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
    //マナーモード---------------------------------------------------------------
    p.setColor(Color.BLACK);
    p.setTextSize(FONT);
    g.drawText(
      (am.getRingerMode()==AudioManager.RINGER_MODE_NORMAL)?"":"マ",
      38,FONT-2,p
    );
    //時刻-----------------------------------------------------------------------
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
    g.drawText(tasks.peek().getAppName(),0,y+FONT,p);
    y+=FONT+3;
    //本文----------------------------------------------------------------------
    tasks.peek().paint(g);
  }
  void key(int code){
    tasks.peek().key(code);
    view.invalidate();
  }
  //システム処理=================================================================
  Canvas g;
  Paint p;
  Bitmap bitmap;
  Typeface typeface;
  View view;
  Handler handler=new Handler();
  int DELAY=1000;
  int batteryLevel=0;
  AudioManager am;
  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    //描画系---------------------------------------------------------------------
    bitmap=Bitmap.createBitmap(SW,SH,Bitmap.Config.ARGB_8888);
    g=new Canvas(bitmap);
    typeface=Typeface.createFromAsset(getAssets(),"PixelMplus12-Regular.ttf");
    p=new Paint();
    p.setTypeface(typeface);
    tasks.add(new MenuApp(this));
    view=new View(this){
      @Override
      protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        paint();
        double z=(double)getWidth()/SW;
        canvas.drawBitmap(bitmap,new Rect(0,0,SW,SH),new Rect(0,0,getWidth(),(int)(SH*z)),p);
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
        //Log.v("★SIGNAL",signalStrength.getGsmSignalStrength()+"db");
      }
    },PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    setContentView(view);
    //マナーモード---------------------------------------------------------------
    am=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
  }
  @Override
  protected void onResume(){
    super.onResume();
    getWindow().getDecorView().setSystemUiVisibility(
      View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN
    );
    handler.postDelayed(new Runnable(){
      @Override
      public void run(){
        view.invalidate();
        handler.postDelayed(this,DELAY);
      }
    },DELAY);
  }
  @Override
  public boolean onKeyDown(int keyCode,KeyEvent event){
    key(keyCode);
    return false;
  }
  //============================================================================
}
