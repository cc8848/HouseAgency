package com.eroad.base.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.util.Base64;

/**
 * 工具类
 * 
 * @author skypan
 * 
 */
@SuppressLint("SimpleDateFormat")
public class CommonUtil {
	/**
	 * 字符拆分字符 返回list
	 * 
	 * @return list
	 */
	public static List<String> getListByString(String string, String speter) {
		List<String> list = new ArrayList<String>();
		if (string.isEmpty())
			return list;
		String[] s = string.split(speter);
		for (String temp : s) {
			list.add(temp);
		}
		return list;

	}

	/**
	 * 合并JSONArray
	 * 
	 * @param oldArray
	 *            原来的array
	 * @param tempArray
	 *            要加入的array
	 * @return
	 */
	public static JSONArray combineArray(JSONArray oldArray, JSONArray tempArray) {
		if (oldArray == null) {
			oldArray = new JSONArray();
		}
		if (tempArray.length() != 0) {
			for (int i = 0; i < tempArray.length(); i++) {
				oldArray.put(tempArray.opt(i));
			}
		}

		return oldArray;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            入参
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.length() <= 0 || "".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 文件转base64
	 * 
	 * @param path
	 *            路径
	 * @return
	 */
	public static String file2Base64(String path) {
		File file = new File(path);
		FileInputStream inputFile;
		byte[] buffer = null;
		try {
			inputFile = new FileInputStream(file);
			buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Base64.encodeToString(buffer, Base64.DEFAULT);
	}

	/**
	 * bitmap转base64
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String bitmap2Base64(Bitmap bitmap) {
		String result = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 检查摄像头 是否存在
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			// 摄像头存在

			return isCameraCanUse();
		} else {
			// 摄像头不存在
			return false;
		}
	}

	/**
	 * 测试当前摄像头能否被使用
	 * 
	 * @return
	 */
	public static boolean isCameraCanUse() {
		boolean canUse = true;
		Camera mCamera = null;
		try {
			// TODO camera驱动挂掉,处理??
			mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
		} catch (Exception e) {
			try {
				mCamera = Camera.open();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				canUse = false;
			}
		}
		if (canUse) {
			mCamera.release();
			mCamera = null;
		}

		return canUse;
	}

	public static boolean isMobile(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_.]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 密码由6- 20位 数字、英文或字符
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isSuitPassword(String passwrod) {
		if (passwrod.isEmpty()) {
			return false;
		}
		// String
		// str="^(?![0-9]*$)(?![a-zA-Z]*$)[a-zA-Z0-9~!@#$%^&*()_+;',./\\:\"<>?|]{6,18}$";
		String str = "^[a-zA-Z0-9~!@#$%^&*()_+;',./\\:\"<>?|]{6,18}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(passwrod);
		return m.matches();
	}

	/**
	 * dp转px
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * px转dp
	 * 
	 * @param context
	 * @param px
	 * @return
	 */
	public static int px2dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	/**
	 * JSONArry 删除 index 索引元素
	 * 
	 * @param arry
	 * @param index
	 * @return
	 */
	public static JSONArray removeJSONArray(JSONArray arry, int index) {
		try {
			JSONArray newArray = new JSONArray();
			for (int i = 0; i < arry.length(); i++) {
				if (i != index)
					newArray.put(arry.getJSONObject(i));
			}
			arry = newArray;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arry;
	}

	/**
	 * jsonarry删除 字段key == value的 jsonarry
	 * 
	 * @param arry
	 * @param key
	 * @param value
	 * @return
	 */
	public static JSONArray removeJSONArray(JSONArray arry, String key, String value) {
		try {
			JSONArray newArray = new JSONArray();
			for (int i = 0; i < arry.length(); i++) {
				if (!arry.getJSONObject(i).getString(key).equalsIgnoreCase(value))
					newArray.put(arry.getJSONObject(i));
			}
			arry = newArray;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arry;
	}

	/**
	 * 判断 jsonarry 是否存在该值
	 * 
	 * @param arry
	 * @param key
	 *            比较的字段
	 * @param value
	 * @return
	 */
	public static boolean containsJSONArray(JSONArray arry, String key, String value) {
		try {
			for (int i = 0; i < arry.length(); i++) {
				if (arry.getJSONObject(i).getString(key).equalsIgnoreCase(value))
					return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * 日期格式转换类
	 * @author skypan
	 *
	 */
	public static class Date {
		/**
		 * 转换成 2015-01－01格式
		 * @param time
		 * @return
		 */
		public static String toYMR(String time) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date;
			String str = null;
			try {
				date = sdf.parse(time);
				str = sdf.format(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return str;
		}
	}
}
