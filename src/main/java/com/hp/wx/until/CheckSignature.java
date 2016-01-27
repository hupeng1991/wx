package com.hp.wx.until;

import com.github.sd4324530.fastweixin.message.aes.SHA1;

public class CheckSignature {

	private static final String token="phu";
	
	public static boolean check(String timestamp,String nonce,String signature,String encrypt) throws Exception{
		return signature.equals(SHA1.getSHA1(token, timestamp, nonce, ""));
	}
	
}
