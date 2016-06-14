/**
 * 获取登录信息，与已有信息进行计算比较，判断该用户的信息
 */
package com.fusion.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fusion.db.Dao;
import com.fusion.db.Service;
import com.fusion.util.Algorithm;
import com.fusion.util.Judge;
import com.fusion.util.StrtoInt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;

public class LoginActivity extends Activity {

	private ArrayList loginWX1=new ArrayList();
	private ArrayList loginWY1=new ArrayList();
	private ArrayList loginWX2=new ArrayList();
	private ArrayList loginWY2=new ArrayList();
	
	private List<Map<String, String>> list1;
	
	private int id=-1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent=getIntent();
		loginWX1=intent.getIntegerArrayListExtra("strWriteX1");
		loginWY1=intent.getIntegerArrayListExtra("strWriteY1");
		loginWX2=intent.getIntegerArrayListExtra("strWriteX2");
		loginWY2=intent.getIntegerArrayListExtra("strWriteY2");
		Log.i("名字", loginWX1.toString()+"  "+loginWX2.toString());
		
		Service person = new Dao(getBaseContext(), "person");
		list1 = person.listMaps(null, null);
		
		if (list1.size() > 0) {
			Log.i("名字", list1.toString());
			Service data = new Dao(getBaseContext(), "data");
			List<Map<String, String>> list = data.listMaps(null, null);
			id=Judge.take(list,list1,loginWX1,loginWY1,loginWX2,loginWY2);

			Log.i("名字", id+"");
			new Thread() {
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(0);
			}
		}.start();
		}
		else
			showDialog();		
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			showDialog();
		}
	};
	
	public void showDialog(){
		String name="";
		if (id != -1) {
			name = list1.get(id).get("name");
		}
		else
			name="陌生人";
		new AlertDialog.Builder(LoginActivity.this).setTitle(name).setIcon(android.R.drawable.ic_dialog_info)
				.setCancelable(false).setPositiveButton("返回主页面", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				startActivity(intent);
			}
		}).show();
	}	
}
