package generate.scripe.generate.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import com.google.gson.Gson;

import generate.scripe.bean.BaseBean;

public class SnakeUtil<T> {
	
	/**
	 * 读文件
	 * @param fileName
	 * @return
	 */
	public static String readFile(String fileName) {
		String encoding = "gbk";
		File file = new File(fileName);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 写文件
	 * @param fileName
	 * @param value
	 */
	public static void  writeFile(String fileName,String value) {
		File file = new File(fileName);
		 FileOutputStream fop;
		try {
			fop = new FileOutputStream(file);
			OutputStreamWriter writer = new OutputStreamWriter(fop, "gbk");
			writer.write(value);
			writer.close();
			fop.close();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String a=readFile("F:\\work\\sparkTest\\target\\classes\\generate\\scripe\\generate\\engine\\template\\SparkSqltemplatePython.py");
		writeFile("f:/a.txt", "a");
		System.out.println(a);
	}
	
	
	public  static < T extends BaseBean> Object  getBean (String json, Type typeOfSrc) {
		Gson gson = new Gson();
		T t= gson.fromJson(json, typeOfSrc);
		t.setConfigJson(json);
		return t;
	}
}
