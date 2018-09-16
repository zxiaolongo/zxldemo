package com.demo.zxl.user.zxldemo.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by beibei on 2017/11/22.
 */

public class AddressDbUtils {

    public static String getAddress(Context context,String number){
        String result = "未知";
        //数据库放到了 data/data/包名/files 目录下
        String path = context.getFilesDir().getAbsolutePath()+"/address.db";
        //由于数据库是准备好的 不是自己创建的 所以不需要使用SqliteOpenHelper
        //而是使用openDatabase 打开数据库 传入的参数
        // 参数1 数据库文件的路径 需要注意 这个路径一定是有权限能访问的
        //参数2  游标工厂
        //参数3 打开数据库的模式 OPEN_READONLY 只读方式打开
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        //首先判断号码是不是手机号
        //  1 35678 9位数字  正则表达式
        if(number.matches("^1[35678]\\d{9}$")){
            //手机号
            //先截取号码字符串的前7位
            String prefix = number.substring(0,7);
            //select cardtype from info where mobileprefix = 字符串的前7位
            Cursor cursor = db.query("info", new String[]{"cardtype"}, "mobileprefix = ?", new String[]{prefix}, null, null, null);
            if(cursor != null && cursor.moveToNext()){
              result = cursor.getString(0);
                cursor.close();
            }
        }else{
            int length = number.length();
            switch (length){
                case 3:
                    result = "紧急号码";
                    break;
                case 4:
                    result="模拟器";
                    break;
                case 5:
                    result = "服务号码";
                    break;
                case 7:
                case 8:
                    result = "本地固话";
                    break;
                case 11:
                    //有区号的固话
                    String prefix = number.substring(0, 3);
                    //select city from info where area = prefix
                    String sql ="select city from info where area = ?";
                    Cursor cursor = db.rawQuery(sql, new String[]{prefix});
                    if(cursor != null && cursor.moveToNext()){
                        result = cursor.getString(0);
                        cursor.close();
                        return"归属地:"+result;
                    }
                    prefix = number.substring(0, 4);
                     cursor = db.rawQuery(sql, new String[]{prefix});
                    if(cursor != null && cursor.moveToNext()){
                        result = cursor.getString(0);
                        return"归属地:"+result;
                    }
                    break;
            }

        }

        //座机
        //select city from info where area = 字符串的前3/4位
        db.close();
        return "归属地:"+result;
    }
}
