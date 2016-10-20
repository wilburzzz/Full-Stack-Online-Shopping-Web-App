package com.jinmengzhu.groupdining.util;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

public class DESede  {
		
		public static final String KEY ="7w3jT0OwXcSAddU93yYLUdVRSl3+7BxU";
	
	    public static final String ALGORITHM = "PBEWITHMD5andDES";  
	    /** 
	     * 密钥算法 
	     * */  
	    public static final String KEY_ALGORITHM="DESede";  
	      
	    /** 
	     * 加密/解密算法/工作模式/填充方式 
	     * */  
	    public static final String CIPHER_ALGORITHM="DESede/ECB/PKCS5Padding";  
	      
	    /** 
	     *  
	     * 生成密钥 
	     * @return byte[] 二进制密钥 
	     * */  
	    public static byte[] initkey() throws Exception{  
	          
	        //实例化密钥生成器  
	        KeyGenerator kg=KeyGenerator.getInstance(KEY_ALGORITHM);  
	        //初始化密钥生成器  
	        kg.init(168);  
	        //生成密钥  
	        SecretKey secretKey=kg.generateKey();  
	        //获取二进制密钥编码形式  
	        return secretKey.getEncoded();  
	    }  
	    /** 
	     * 转换密钥 
	     * @param key 二进制密钥 
	     * @return Key 密钥 
	     * */  
	    public static Key toKey(byte[] key) throws Exception{  
	        //实例化Des密钥  
	        DESedeKeySpec dks=new DESedeKeySpec(key);  
	        //实例化密钥工厂  
	        SecretKeyFactory keyFactory=SecretKeyFactory.getInstance(KEY_ALGORITHM);  
	        //生成密钥  
	        SecretKey secretKey=keyFactory.generateSecret(dks);  
	        return secretKey;  
	    }  
	      
	    /** 
	     * 加密数据 
	     * @param data 待加密数据 
	     * @param key 密钥 
	     * @return byte[] 加密后的数据 
	     * */  
	    public static byte[] encrypt(byte[] data,byte[] key) throws Exception{  
	        //还原密钥  
	        Key k=toKey(key);  
	        //实例化  
	        Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);  
	        //初始化，设置为加密模式  
	        cipher.init(Cipher.ENCRYPT_MODE, k);  
	        //执行操作  
	        return cipher.doFinal(data);  
	    }  
	    /** 
	     * 解密数据 
	     * @param data 待解密数据 
	     * @param key 密钥 
	     * @return byte[] 解密后的数据 
	     * */  
	    public static byte[] decrypt(byte[] data,byte[] key) throws Exception{  
	        //欢迎密钥  
	        Key k =toKey(key);  
	        //实例化  
	        Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);  
	        //初始化，设置为解密模式  
	        cipher.init(Cipher.DECRYPT_MODE, k);  
	        //执行操作  
	        return cipher.doFinal(data);  
	    }  
	    /** 
	     * 进行加解密的测试 
	     * @throws Exception  
	     */  
	    public static void main(String[] args) throws Exception {          

	    	String str="DESede";  	        
	        //初始化密钥  
	        byte[] key=DESede.initkey();  	      
	        //加密数据  
	        byte[] data=DESede.encrypt(str.getBytes(), key); 	       
	        //解密数据  
	        data=DESede.decrypt(data, key);  
	         
	    } 
	    
	    /**
	     * 加密数据
	     * @param value
	     * @return
	     */
	    public static String encode(String value) {
	    	String result = "";
	    	try {
	    		String str = value;  
		        byte[] key= Base64.decodeBase64(KEY) ;  
		        byte[] data=DESede.encrypt(str.getBytes(), key);  
		        return Base64.encodeBase64String(data);
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	return result;
	    }
	    
	    /**
	     * 解密数据
	     * @param value
	     * @return
	     */
	    public static String decode(String value) {
	    	String result = "";
	    	try {
	    		byte[] str = Base64.decodeBase64(value);  
		        byte[] key= Base64.decodeBase64(KEY) ;  
		        byte[] data=DESede.decrypt(str, key);  
		        return new String(data);
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	return result;
	    }
	    
}
