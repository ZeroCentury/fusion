/**
 * 采集笔迹数据，并根据请求选择注册或者登陆
 */
package com.fusion.activity;

import java.util.ArrayList;
import com.fusion.R;
import com.fusion.db.Dao;
import com.fusion.db.Service;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CollectDataActivity extends Activity implements OnTouchListener, OnClickListener {

	private ImageView image;
	private Button save;//保存采集数据
	private Button delete;//清除采集数据
	private Bitmap baseBitmap;
	private Canvas canvas;
	private Paint paint;

	// 屏幕大小
	private int width;
	private int height;

	// 开始坐标
	private int startX = 0;
	private int startY = 0;
	private int tempX = 0;
	private int tempY = 0;
	private int minX = Integer.MAX_VALUE;
	private int minY = Integer.MAX_VALUE;
	private boolean first = false;
	
	// 标记是登录或注册
	private boolean flag = false;
	private String name = "";

	private ArrayList strWriteX = new ArrayList();
	private ArrayList strWriteY = new ArrayList();

	private ArrayList strWriteX1;
	private ArrayList strWriteY1;

	private int COUNT = 4;
	private int dian = 400;
	private Service data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collectdata);
		initView();

		Intent intent = getIntent();
		flag = intent.getBooleanExtra("flag", false);
		if (flag) {
			data = new Dao(getBaseContext(), "data");
			name = intent.getStringExtra("name");
			
		}else{
			Toast.makeText(this,"请分别输入姓名前两个字", Toast.LENGTH_LONG).show();
		}

		paint();
		
		image.setOnTouchListener(this);
		save.setOnClickListener(this);
		delete.setOnClickListener(this);
	}
//初始化相关数据
	public void initView() {
		WindowManager wm = this.getWindowManager();
		width = wm.getDefaultDisplay().getWidth();
		height = wm.getDefaultDisplay().getHeight();

		image = (ImageView) this.findViewById(R.id.image);
		save = (Button) this.findViewById(R.id.save);
		delete = (Button) this.findViewById(R.id.delete);
	}
//处理点击事件
	@Override
	public void onClick(View v) {
		if (strWriteX.isEmpty()) {
			Toast.makeText(this, "请写入内容", Toast.LENGTH_SHORT).show();
			return;
		}
		switch (v.getId()) {
		case R.id.save:
			for (int i = 0; i < strWriteX.size(); i++) {
				strWriteX.set(i, (Integer) strWriteX.get(i) - minX);
				strWriteY.set(i, (Integer) strWriteY.get(i) - minY);
			}
			if (first) {
				first = false;
				skip();
			}
			else{
				first = true;
				strWriteX1 = new ArrayList(strWriteX);
				strWriteY1 = new ArrayList(strWriteY);
			}				
			break;
		case R.id.delete:
			break;
		default:
			break;
		}
		init();

	}
//处理触摸事件
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (dian > 0) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				tempX = (int) event.getX();
				tempY = (int) event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				startX = (int) event.getX();
				startY = (int) event.getY();
				if (startX < minX)
					minX = startX;
				if (startY < minY)
					minY = startY;
					strWriteX.add(startX);
					strWriteY.add(startY);
					canvas.drawLine(tempX, tempY, startX, startY, paint);
					image.setImageBitmap(baseBitmap);
					dian--;
				tempX = startX;
				tempY = startY;
			}
		}
		return true;
	}
//初始化画布
	public void paint() {
		// 创建一张空白图片
		baseBitmap = Bitmap.createBitmap(width, height*7/8, Bitmap.Config.ARGB_8888);
		// 创建一张画布
		canvas = new Canvas(baseBitmap);
		// 画布背景为灰色
		canvas.drawColor(Color.alpha(0));
		// 创建画笔
		paint = new Paint();
		// 画笔颜色为红色
		paint.setColor(Color.RED);
		// 宽度5个像素
		paint.setStrokeWidth(5);
		// 先将灰色背景画上
		canvas.drawBitmap(baseBitmap, new Matrix(), paint);
		image.setImageBitmap(baseBitmap);
	}

	public void skip() {
		if (flag) {
			save();
			COUNT--;
			if (COUNT == 0)
				registerManage();
			else
				Toast.makeText(this, "剩余注册次数"+COUNT, Toast.LENGTH_SHORT).show();
		} else
			loginManage();
		strWriteX1.clear();
		strWriteY1.clear();
	}
//注册跳转
	public void registerManage() {
		Intent intent = new Intent();
		intent.putExtra("name", name);
		intent.setClass(CollectDataActivity.this, RegisterActivity.class);
		startActivity(intent);
	}
//登陆跳转
	public void loginManage() {
		Intent intent = new Intent();
		intent.putIntegerArrayListExtra("strWriteX1", strWriteX1);
		intent.putIntegerArrayListExtra("strWriteY1", strWriteY1);
		intent.putIntegerArrayListExtra("strWriteX2", strWriteX);
		intent.putIntegerArrayListExtra("strWriteY2", strWriteY);
		intent.setClass(CollectDataActivity.this, LoginActivity.class);
		startActivity(intent);
	}
//保存数据
	public void save() {
		
		ContentValues contentValues = new ContentValues();
		contentValues.put("strWriteX1", strWriteX1.toString());
		contentValues.put("strWriteY1", strWriteY1.toString());
		contentValues.put("strWriteX2", strWriteX.toString());
		contentValues.put("strWriteY2", strWriteY.toString());
		Log.i("-->>", data.add(contentValues)+"");
		contentValues.clear();

	}
//初始化相关数据
	public void init() {
		strWriteX.clear();
		strWriteY.clear();
		dian = 400;
		baseBitmap.recycle();
		paint();
	}
}
