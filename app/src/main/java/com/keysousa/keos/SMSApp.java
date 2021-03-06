package com.keysousa.keos;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.telephony.SmsManager;
import android.view.KeyEvent;

import java.util.HashMap;
import java.util.Map;

import static com.keysousa.keos.Const.*;

public class SMSApp extends App{
  String buf="";
  Integer ccode,cindex,dindex;
  Map<Integer,String> keymapTel;
  Map<Integer,String> keymap;
  Map<String,String> dakuon;
  String telnumber="";
  int curpos=1;

  public SMSApp(MainActivity main){
    super(main);
    keymapTel=new HashMap<>();
    keymapTel.put(KeyEvent.KEYCODE_1,"1");
    keymapTel.put(KeyEvent.KEYCODE_2,"2");
    keymapTel.put(KeyEvent.KEYCODE_3,"3");
    keymapTel.put(KeyEvent.KEYCODE_4,"4");
    keymapTel.put(KeyEvent.KEYCODE_5,"5");
    keymapTel.put(KeyEvent.KEYCODE_6,"6");
    keymapTel.put(KeyEvent.KEYCODE_7,"7");
    keymapTel.put(KeyEvent.KEYCODE_8,"8");
    keymapTel.put(KeyEvent.KEYCODE_9,"9");
    keymapTel.put(KeyEvent.KEYCODE_STAR,"*");
    keymapTel.put(KeyEvent.KEYCODE_0,"0");
    keymapTel.put(KeyEvent.KEYCODE_POUND,"#");
    keymap=new HashMap<>();
    keymap.put(KeyEvent.KEYCODE_1,"あいうえおぁぃぅぇぉ");
    keymap.put(KeyEvent.KEYCODE_2,"かきくけこ");
    keymap.put(KeyEvent.KEYCODE_3,"さしすせそ");
    keymap.put(KeyEvent.KEYCODE_4,"たちつてとっ");
    keymap.put(KeyEvent.KEYCODE_5,"なにぬねの");
    keymap.put(KeyEvent.KEYCODE_6,"はひふへほ");
    keymap.put(KeyEvent.KEYCODE_7,"まみむめも");
    keymap.put(KeyEvent.KEYCODE_8,"やゆよゃゅょ");
    keymap.put(KeyEvent.KEYCODE_9,"らりるれろ");
    keymap.put(KeyEvent.KEYCODE_0,"わをんゎ、。ー・〜！？．　");
    keymap.put(KeyEvent.KEYCODE_STAR,"、。ー・〜！？　");
    dakuon=new HashMap<>();
    dakuon.put("あ","ぁ");
    dakuon.put("い","ぃ");
    dakuon.put("う","ぅヴ");
    dakuon.put("え","ぇ");
    dakuon.put("お","ぉ");
    dakuon.put("ぁ","あ");
    dakuon.put("ぃ","い");
    dakuon.put("ぅ","う");
    dakuon.put("ぇ","え");
    dakuon.put("ぉ","お");
    dakuon.put("か","が");
    dakuon.put("き","ぎ");
    dakuon.put("く","ぐ");
    dakuon.put("け","げ");
    dakuon.put("こ","ご");
    dakuon.put("さ","ざ");
    dakuon.put("し","じ");
    dakuon.put("す","ず");
    dakuon.put("せ","ぜ");
    dakuon.put("そ","ぞ");
    dakuon.put("た","だ");
    dakuon.put("ち","ぢ");
    dakuon.put("つ","っ");
    dakuon.put("て","で");
    dakuon.put("と","ど");
    dakuon.put("っ","づつ");
    dakuon.put("は","ばぱ");
    dakuon.put("ひ","びぴ");
    dakuon.put("ふ","ぶぷ");
    dakuon.put("へ","べぺ");
    dakuon.put("ほ","ぼぽ");
    dakuon.put("や","ゃ");
    dakuon.put("ゆ","ゅ");
    dakuon.put("よ","ょ");
    dakuon.put("わ","ゎ");
    dakuon.put("ゎ","わ");
    for(Map.Entry<String,String> e:dakuon.entrySet()){
      dakuon.put(e.getKey(),e.getKey()+e.getValue());
    }
  }
  @Override
  public void paint(Canvas g){
    p.setColor(COLOR_FORE);
    int y=APP_Y;
    //宛先----------------------------------------------------------------------
    g.drawText("[宛先] "+telnumber,0,y+FONT,p);
    int x=Utils.width("[宛先] "+telnumber);
    if(curpos==0){
      g.drawLine(x,y+2,x,y+FONT+1,p);
    }
    //本文----------------------------------------------------------------------
    y+=FONT+2;
    y+=FONT;
    g.drawText("[本文]",0,y,p);
    String buf2=buf;
    if(ccode!=null){
      if(dindex!=null){
        buf2+=dakuon.get(""+keymap.get(ccode).charAt(cindex)).charAt(dindex);
      }else{
        buf2+=keymap.get(ccode).charAt(cindex);
      }
    }
    x=0;
    int s=0;
    for(int i=0;i<buf2.length();i++){
      x+=Utils.width(""+buf2.charAt(i));
      int nw=(i==buf2.length()-1)?0:Utils.width(""+buf2.charAt(i+1));
      if(x+nw>SW||i==buf2.length()-1){
        g.drawText(buf2.substring(s,i+1),0,y+FONT,p);
        if(i<buf2.length()-1){
          s=i+1;
          x=0;
          y+=FONT;
        }
      }
    }
    if(curpos==1){
      g.drawLine(x,y+2,x,y+FONT+1,p);
    }
    paintBottomMenu(new String[]{"戻る","","送信"},g);
  }
  @Override
  public void key(int code){
    switch(code){
      case KeyEvent.KEYCODE_DPAD_DOWN:
        if(curpos<1){
          curpos++;
        }
        break;
      case KeyEvent.KEYCODE_DPAD_UP:
        if(curpos>0){
          curpos--;
        }
        break;
      case KeyEvent.KEYCODE_BACK:
        switch(curpos){
          case 0:
            if(!telnumber.isEmpty()){
              telnumber=telnumber.substring(0,telnumber.length()-1);
            }
            break;
          case 1:
            if(ccode!=null){
              ccode=null;
              dindex=null;
            }else if(!buf.isEmpty()){
              buf=buf.substring(0,buf.length()-1);
            }
            break;
        }
        break;
      case KeyEvent.KEYCODE_DPAD_RIGHT:
        if(ccode!=null){
          buf+=keymap.get(ccode).charAt(cindex);
          ccode=null;
          cindex=0;
          dindex=null;
        }else{
          buf+="　";
        }
        break;
      case KeyEvent.KEYCODE_POUND:
        if(ccode!=null){
          cindex=(cindex-1+keymap.get(ccode).length())
            %keymap.get(ccode).length();
        }
        break;
      case KeyEvent.KEYCODE_F1:
        main.tasks.pop();
        break;
      case KeyEvent.KEYCODE_F2:
        if(ccode!=null){
          if(dindex!=null){
            buf+=dakuon.get(""+keymap.get(ccode).charAt(cindex)).charAt(dindex);
          }else{
            buf+=keymap.get(ccode).charAt(cindex);
          }
          ccode=null;
          dindex=null;
        }
        try{
          SmsManager.getDefault().sendTextMessage(telnumber,null,buf,null,null);
        }catch(Exception e){
          e.printStackTrace();
        }
        main.tasks.pop();
        break;
      default:
        switch(curpos){
          case 0:
            if(keymap.containsKey(code)){
              telnumber+=keymapTel.get(code);
            }
            break;
          case 1:
            if(code==KeyEvent.KEYCODE_STAR&&ccode!=null&&dakuon.containsKey(""+keymap.get(ccode).charAt(cindex))){
              if(dindex==null){
                dindex=1;
              }else{
                dindex=(dindex+1)%dakuon.get(""+keymap.get(ccode).charAt(cindex)).length();
              }
            }else if(keymap.containsKey(code)){
              if(ccode==null||code!=ccode||dindex!=null){
                if(ccode!=null){
                  if(dindex!=null){
                    buf+=dakuon.get(""+keymap.get(ccode).charAt(cindex)).charAt(dindex);
                  }else{
                    buf+=keymap.get(ccode).charAt(cindex);
                  }
                }
                ccode=code;
                cindex=0;
                dindex=null;
              }else{
                cindex=(cindex+1)%keymap.get(ccode).length();
              }
            }
            break;
        }
        break;
    }
  }
  @Override
  public String getAppName(){
    return "SMS";
  }
}
