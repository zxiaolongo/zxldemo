package com.demo.zxl.user.zxldemo.f10_sra;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.demo.zxl.user.zxldemo.R;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

public class RSAActivity extends Activity {

	private TextView	mTvResult;
	private boolean		isDesed;
	private boolean		isAesed;
	private boolean		isRsaed;
	private String		data	= "需要加密的数据";
	private String		key		= "%^&*()^&*(^&";
	private String		mDesEncryptResult;
	private String		mDesDecryptResult;
	private String		mAesEncryptResult;
	private String		mPublicKey;
	private String		mPrivateKey;
	private byte[]		mRsaEncryptByPrivateKeys;
	private RSAPublicKey pubKey;
	private RSAPrivateKey priKey;
	private boolean isEncrypt = true;//是否为加密
	String ming = "123456789";  
	String mi = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rsa);
		mTvResult = (TextView) findViewById(R.id.result);

		//获取公私钥
		initKey();
	}
	public void rsa(View view){
		if(isEncrypt){
			//加密后的密文  
			try {
				//加密方法(明文,公钥),返回加密后的字符串
				mi = RSAUtils.encryptByPublicKey(ming, pubKey);
				System.err.println(mi); 
				mTvResult.setText(mi);
			} catch (Exception e) {
				e.printStackTrace();
			}  
			isEncrypt = false;
		}else{
			//解密后的明文  
	        try {
				//将之前的密文进行解密操作(参数一:密文,参数二:私钥),得到明文
				ming = RSAUtils.decryptByPrivateKey(mi, priKey);
				System.err.println(ming);  
				mTvResult.setText(ming);
			} catch (Exception e) {
				e.printStackTrace();
			}  
	        isEncrypt = true;
		}
	}
	

	private void initKey() {
		HashMap<String, Object> map;
		
		try {
			map = RSAUtils.getKeys();
			//生成公钥和私钥  
	        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");  
	        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");  
	          
	        //模  
	        String modulus = publicKey.getModulus().toString();  
	        //公钥指数  
	        String public_exponent = publicKey.getPublicExponent().toString();  
	        //私钥指数  
	        String private_exponent = privateKey.getPrivateExponent().toString();  
	        
	        pubKey = RSAUtils.getPublicKey(modulus, public_exponent);  //公钥
	        priKey = RSAUtils.getPrivateKey(modulus, private_exponent);  //私钥
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}
}
