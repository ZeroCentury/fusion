/**
 * 对数据库的操作
 */
package com.fusion.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Dao implements Service {

	private DbOpenHelper helper = null;
	private String tableName = null;

	public Dao(Context basecontext, String tableName) {
		helper = new DbOpenHelper(basecontext);
		this.tableName = tableName;
	}
//添加数据
	public boolean add(ContentValues values) {
		boolean flag = false;
		SQLiteDatabase database = null;
		long id = -1;
		try {
			database = helper.getWritableDatabase();
			Log.i("--->", values.toString());
			id = database.insert(tableName, null, values);
			flag = (id != -1 ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}
//删除数据
	@Override
	public boolean delete(String whereClause, String[] whereArgs) {
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			count = database.delete(tableName, whereClause, whereArgs);
			flag = (count > 0 ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}
//修改数据
	@Override
	public boolean update(ContentValues values, String whereClause, String[] whereArgs) {
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;// 影响数据库的行数
		try {
			database = helper.getWritableDatabase();
			count = database.update(tableName, values, whereClause, whereArgs);
			flag = (count > 0 ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}
//取出所有数据
	@Override
	public List<Map<String, String>> listMaps(String selection, String[] selectionArgs) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {
			database = helper.getReadableDatabase();
			cursor = database.query(true, tableName, null, selection, selectionArgs, null, null, null, null);
			int colums = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < colums; i++) {
					String clos_name = cursor.getColumnName(i);
					String clos_value = cursor.getString(cursor.getColumnIndex(clos_name));
					if (clos_value == null) {
						clos_value = "";
					}
					map.put(clos_name, clos_value);
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return list;
	}

}
