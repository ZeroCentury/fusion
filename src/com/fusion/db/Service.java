package com.fusion.db;

import java.util.List;
import java.util.Map;

import android.content.ContentValues;

public interface Service {
	public boolean add(ContentValues values);

	public boolean delete(String whereClause, String[] whereArgs);

	public boolean update(ContentValues values, String whereClause, String[] whereArgs);

	public List<Map<String, String>> listMaps(String selection, String[] selectionArgs);

}
