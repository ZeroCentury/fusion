/**
 * 添加注册者身份，同时判断是否为已注册用户
 * 若上一次未注册成功，删除多余数据
 */
package com.fusion.activity;

import java.util.List;
import java.util.Map;

import com.fusion.R;
import com.fusion.db.Dao;
import com.fusion.db.Service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterNameActivity extends Activity implements OnClickListener {

	private Button btnRegister = null;
	private Button back = null;
	private EditText name = null;
	private String personName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		initView();

		// 判断是否注册成功
		Log.i("是否多余", registerSuccess() + "");
		btnRegister.setOnClickListener(this);
		back.setOnClickListener(this);
	}

	public void initView() {
		btnRegister = (Button) this.findViewById(R.id.btnRegister);
		back = (Button) this.findViewById(R.id.back);
		name = (EditText) this.findViewById(R.id.name);
	}

	public void onClick(View v) {
		if (v.getId() == R.id.btnRegister) {
			judgmentRepetition();
		} else if (v.getId() == R.id.back) {
			finish();
		}
	}
//判断注册用户是否有效
	private void judgmentRepetition() {
		personName = name.getText().toString();
		boolean repeat = false;
		if (personName != null && personName.equals("")) {
			Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Service person = new Dao(getBaseContext(), "person");
		List<Map<String, String>> list = person.listMaps(null, null);
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get("name").equals(personName)) {
				repeat = true;
				break;
			}
		}
		if (repeat) {
			Toast.makeText(this, "该用户名已存在，请重新输入用户名", Toast.LENGTH_SHORT).show();
		} else {
			Intent intent = new Intent();
			intent.putExtra("flag", true);
			intent.putExtra("name", personName);
			intent.setClass(RegisterNameActivity.this, CollectDataActivity.class);
			startActivity(intent);
		}
	}
//判断上一次是否注册成功
	private boolean registerSuccess() {
		Service person = new Dao(getBaseContext(), "person");
		Service data = new Dao(getBaseContext(), "data");
		List<Map<String, String>> list1 = person.listMaps(null, null);
		List<Map<String, String>> list2 = data.listMaps(null, null);
		if (4 * list1.size() != list2.size()) {
			for (int i = list2.size(); i > 4 * list1.size(); i--) {
				data.delete(" id = ? ", new String[] { list2.get(i - 1).get("id") });
			}
			return false;
		} else
			return true;
	}
}
