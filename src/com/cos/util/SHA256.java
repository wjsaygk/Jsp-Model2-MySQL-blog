package com.cos.util;

import java.security.MessageDigest;

//256bit 암호 => 해시 => 복호화X
public class SHA256 {
	// password를 암호화해서 리턴
	public static String getEncrypy(String rawPassword, String salt) {

		String result = "";

		byte[] b = (rawPassword + salt).getBytes();

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			md.update(b);
			
			byte[] bResult = md.digest();

			StringBuffer sb = new StringBuffer();
			
			for (byte data : bResult) {
				sb.append(Integer.toString(data & 0xff, 16));
			}
			
			result = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}


}
