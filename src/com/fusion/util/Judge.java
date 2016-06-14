package com.fusion.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.text.format.DateFormat;
import android.util.Log;

public class Judge {

	public static int take(List<Map<String, String>> list, List<Map<String, String>> list1, ArrayList loginWX1, ArrayList loginWY1, ArrayList loginWX2, ArrayList loginWY2) {

		long time=System.currentTimeMillis();
		int size=list.size();

		Log.i("Test", "--->>" + size);

		int[] tempW1;
		boolean[] tempboolW1;
		int[] tempW2;
		boolean[] tempboolW2;
		int result=-1;
		if(size<400){
			tempW1=new int[400];
			tempboolW1=new boolean[400];
			tempW2=new int[400];
			tempboolW2=new boolean[400];
		}else{
			tempW1=new int[4000];
			tempboolW1=new boolean[4000];
			tempW2=new int[4000];
			tempboolW2=new boolean[4000];
		}
		double min=Double.POSITIVE_INFINITY;
		int k=0;
		
		String str1="";
		String str2="";
		
		for (int i = 0; i < size;i++ ) {
			Map<String, String> map = list.get(i);
			tempW1[i]=Algorithm.operation(StrtoInt.toInteger(map.get("strWriteX1")),
					StrtoInt.toInteger(map.get("strWriteY1")),loginWX1,loginWY1);
			
			tempW2[i]=Algorithm.operation(StrtoInt.toInteger(map.get("strWriteX2")),
					StrtoInt.toInteger(map.get("strWriteY2")),loginWX2,loginWY2);	
		}
		
		for(int i=0;i<list1.size();i++){
			Map<String, String> map = list1.get(i);
			ArrayList max1=StrtoInt.toInteger(map.get("max1"));
			ArrayList max2=StrtoInt.toInteger(map.get("max2"));
			for(int j=0;j<max1.size();j++){
				if(tempW1[k]<(Integer)max1.get(j))
					tempboolW1[k]=true;
				else
					tempboolW1[k]=false;

				str1+=tempW1[k]+" "+tempboolW1[k]+" "+max1.get(j)+"\n";
				Log.i("-->>", tempW1[k]+" "+tempboolW1[k]);
				
				if(tempW2[k]<(Integer)max2.get(j))
					tempboolW2[k]=true;
				else
					tempboolW2[k]=false;
				
				str2+=tempW2[k]+" "+tempboolW2[k]+" "+max2.get(j)+"\n";
				Log.i("-->>", tempW2[k]+" "+tempboolW2[k]);
				
				k++;
			}
		}
		
		
		for(int i=0;i<size;i++){
			if(tempboolW1[i]&tempboolW2[i]&min>tempW1[i]+tempW2[i]){
				min=tempW1[i]+tempW2[i];
				result=i;
			}
		}
		
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("MM-dd HH:mm:ss");
		String stime=format.format(date);
		SaveFile.saveFile("登陆数据", stime, "姓\n"+str1+"\n"+"名\n"+ str2+"\n运算时间"+(System.currentTimeMillis()-time)+"毫秒");
		
		if(result<0)
			return result;
		else
			return result/4;
	}
}
