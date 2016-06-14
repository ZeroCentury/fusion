package com.fusion.util;

import java.io.File;
import java.io.FileOutputStream;

import android.os.Environment;

public class SaveFile {
	public static void saveFile(String str, String name, String data) {
		String filePath = null;
		boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (hasSDCard) {
			filePath = Environment.getExternalStorageDirectory().toString() + File.separator + str + File.separator
					+ name + ".txt";
		} else
			filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + "值" + File.separator
					+ name + ".txt";
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				File dir = new File(file.getParent());
				dir.mkdirs();
				file.createNewFile();
			}
			FileOutputStream outStream = new FileOutputStream(file);
			outStream.write(data.getBytes());
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
