package com.hp.wx.until;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;


public class ZipFileUtil {
	
	/**
	 * @param infos 文件列表
	 * @param name 文件名
	 * @return
	 * @throws IOException
	 * <p>压缩文件，下载文件</p>
	 */
	 public static  void zipFiles(List<File> files, ZipOutputStream zos) throws IOException {       
	      for (File f : files) {
		       zos.putNextEntry(new ZipEntry(f.getName()));     
		       BufferedInputStream fis = new BufferedInputStream(new FileInputStream(f));
		       byte[] buffer = new byte[fis.available()];     
		       int r = 0;     
		       while ((r = fis.read(buffer)) != -1) {     
		           zos.write(buffer, 0, r);     
		       }     
		       fis.close();   
	      }  
	 }

	/**
	 * @return zip名称
	 * <P>生成zip文件名，时间戳</p>
	 */
	public static String getZipName(){
		Calendar calendar = Calendar.getInstance();
		String extens = "" + calendar.get(Calendar.YEAR)
				+ calendar.get(Calendar.MONTH)
				+ calendar.get(Calendar.DATE)
				+ calendar.get(Calendar.HOUR)
				+ calendar.get(Calendar.MINUTE);
		String fileName = "batchdownload.zip";
		fileName = FilenameUtils.getBaseName(fileName)
				.concat("_" + extens + ".")
				.concat(FilenameUtils.getExtension(fileName));
		return fileName;
	}

}
