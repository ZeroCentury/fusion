/**
 * 用户根据需要选择注册或登陆
 */
package com.fusion.activity;

import com.fusion.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	private Button login = null;//登录按钮
	private Button register = null;//注册按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		login.setOnClickListener(this);
		register.setOnClickListener(this);
	}
	public void initView(){
		login = (Button) this.findViewById(R.id.login);
		register = (Button) this.findViewById(R.id.register);		
	}

	/**
	 * 响应点击事件
	 */
	public void onClick(View v) {
		Intent intent = new Intent();
		if (v.getId() == R.id.login) {
			intent.setClass(MainActivity.this, CollectDataActivity.class);
		} else if (v.getId() == R.id.register) {
			intent.setClass(MainActivity.this, RegisterNameActivity.class);
		}
		startActivity(intent);
	}
}
