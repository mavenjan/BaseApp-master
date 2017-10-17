package com.cauc.mavenj.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author Maven Jan
 */

public class StreamTool {

	public static byte[] read(InputStream inStream) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
		byte[] buffer = new byte[1024];
		int len;
		while ((len = inStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		inStream.close();
		return outputStream.toByteArray();
	}

}
