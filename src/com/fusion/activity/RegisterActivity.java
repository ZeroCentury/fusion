/**
 * 处理注册数据，通过计算，取得相关值，进行陌生识别
 */
package com.fusion.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fusion.db.Dao;
import com.fusion.db.Service;
import com.fusion.util.Algorithm;
import com.fusion.util.SaveFile;
import com.fusion.util.StrtoInt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class RegisterActivity extends Activity {

	private String name = "";
	private int[] max1 = new int[6];
	private int[] max2 = new int[6];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		name = intent.getStringExtra("name");

		getData();
		setperson();

		new AlertDialog.Builder(this).setTitle("注册成功").setIcon(android.R.drawable.ic_dialog_info).setCancelable(false)
				.setPositiveButton("返回主页面", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent();
						intent.setClass(RegisterActivity.this, MainActivity.class);
						startActivity(intent);
					}
				}).show();
	}

	// 取得该用户注册四次中两两比较后的数值
	private void getData() {
		Service data = new Dao(getBaseContext(), "data");
		List<Map<String, String>> list = data.listMaps(null, null);
		int size = list.size();
		int k = 0;
		int m=0;
		for (int i = size - 4; i < size - 1; i++) {
			Map<String, String> map = list.get(i);
			for (int j = i + 1; j < size; j++) {
				Map<String, String> map1 = list.get(j);
				max1[k] = Algorithm.operation(StrtoInt.toInteger(map.get("strWriteX1")),
						StrtoInt.toInteger(map.get("strWriteY1")), StrtoInt.toInteger(map1.get("strWriteX1")),
						StrtoInt.toInteger(map1.get("strWriteY1")));
				max2[k] = Algorithm.operation(StrtoInt.toInteger(map.get("strWriteX2")),
						StrtoInt.toInteger(map.get("strWriteY2")), StrtoInt.toInteger(map1.get("strWriteX2")),
						StrtoInt.toInteger(map1.get("strWriteY2")));
				k++;
			}
			SaveFile.saveFile("注册数据", name+ (++m) , map.toString());			
		}
		SaveFile.saveFile("注册数据", name+ (++m) , list.get(size-1).toString());
	}

	// 存储该用户的相关信息
	private void setperson() {
		String strMax1=getArrayList(max1).toString();
		String strMax2=getArrayList(max2).toString();
		Service person = new Dao(getBaseContext(), "person");
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("max1", strMax1);
		values.put("max2", strMax2);
		person.add(values);
		SaveFile.saveFile("注册处理", name,values.toString());
	}

	// 取得每次与其他组数比较的最大值
	public ArrayList getArrayList(int[] max) {
		ArrayList m = new ArrayList();
		m.add(getMax(max[0], max[1], max[2]));
		m.add(getMax(max[0], max[3], max[4]));
		m.add(getMax(max[1], max[3], max[5]));
		m.add(getMax(max[2], max[4], max[5]));
		return m;
	}

	public int getMax(int a, int b, int c) {
		return Math.max(Math.max(a, b), c);
	}
}
