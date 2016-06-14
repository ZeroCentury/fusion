/**
 * 创建数据库
 */
package com.fusion.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {
	private static String name = "mydb.db";
	private static int version = 1;

	public DbOpenHelper(Context context) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//记录姓名、多个最大值（该次与其他三次的计算取得最大值）
		String sql1 = "create table person(id integer primary key autoincrement,name varchar(64),max1 varchar(64),max2 varchar(64))";
		db.execSQL(sql1);
		//记录注册数据
		String sql2 = "create table data(id integer primary key autoincrement,strWriteX1 varchar(2000),strWriteY1 varchar(2000),strWriteX2 varchar(2000),strWriteY2 varchar(2000))";
		db.execSQL(sql2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
