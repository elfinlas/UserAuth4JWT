package com.mhlab.userauth.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class EncryptUtil {

	public static final String AES_KEY_FILE_NAME = "aes_key";
	
	
	//////////////////////////
	//		RSA Method 
	//////////////////////////

	/**
	 * KeyPair를 리턴
	 * @param session 에러 파일 작성 시 정보를 위한 세션
	 * @return Base64로 인코딩된 암호화 문자열
	 */
	public static KeyPair generateKeyPair() {
		KeyPair keyPair = null;
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048);
			keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
		} catch (NoSuchAlgorithmException e) { //에러 발생 시 파일로 저장한다.
			e.printStackTrace();
		}
		return keyPair;
	}
	
	
	
	
	//////////////////////////////////////
	//		AES Method  
	//////////////////////////////////////

	/**
	 * 
	 * @param baseSecretPath
	 * @return
	 */
	public static boolean checkAESKeyFile(String baseSecretPath){
		boolean isExist = true;
		
		Path secretFilePath = Paths.get(baseSecretPath);
		
		if(Files.notExists(secretFilePath)){
			System.out.println("No Exist");
			isExist=false;
			makeAESKeyFile(baseSecretPath);
		}//AES Key 파일이 존재하지 않을 경우
		
		else {
			System.out.println("Exist");
		}
		
		return isExist;
	}
	
	/**
	 * 
	 * @param baseSecretPath
	 * @return
	 */
	public static boolean makeAESKeyFile(String baseSecretPath) {
		boolean isSuccess = true;
		FileChannel fileChannel;
		String uuid_key = SupportUtil.makeUUID2String();
		
		try {
			Files.createFile(Paths.get(baseSecretPath)); //파일을 먼저 생성한다.
			fileChannel = FileChannel.open(Paths.get(baseSecretPath), StandardOpenOption.WRITE); //생성한 파일의 체널을 연다.
			
			//
			ByteBuffer buffer = ByteBuffer.allocateDirect(uuid_key.length());
			Charset charset = Charset.forName("UTF-8");
			buffer = charset.encode(uuid_key);
			
			int byteCnt = fileChannel.write(buffer);
			System.out.println("byteCnt = " + byteCnt);
			fileChannel.close();
		}
		catch(IOException e){
			isSuccess=false;
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		System.out.println("21312cds");
		return isSuccess;
	}
	
	
	/**
	 * 
	 * @param baseSecretPath
	 * @return
	 */
	public static String getAESKeyString(String baseSecretPath){
		String keyStr = ""; 
		FileChannel fileChannel;
		
		try{
			fileChannel = FileChannel.open(Paths.get(baseSecretPath), StandardOpenOption.READ);
			ByteBuffer buffers = ByteBuffer.allocate(100);
			Charset charset = Charset.forName("UTF-8");
			
			int byteCnt = 0;
			while(true){
				byteCnt=fileChannel.read(buffers);
				if(byteCnt==-1)break;
				buffers.flip();
				keyStr += charset.decode(buffers).toString();
				buffers.clear();
			}
			fileChannel.close();
		}
		catch(IOException e){
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		
		return keyStr;
	}
	
	
	/**
	 * 
	 * @param baseSecretPath
	 * @param targetStr
	 * @return
	 */
	public static String encryptForAES(String baseSecretPath, String targetStr){
		String encryptStr = "";
		try {
			byte[] keyData4Byte = getAESKeyString(baseSecretPath).getBytes();
			
			SecretKey secretKey = new SecretKeySpec(keyData4Byte, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(keyData4Byte));
			
			byte[] encryptData4Byte = cipher.doFinal(targetStr.getBytes("UTF-8"));
			encryptStr = new String(Base64.encodeBase64(encryptData4Byte));
		}
		catch(IOException e){
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		catch(NoSuchPaddingException e) {
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		catch(NoSuchAlgorithmException e) {
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		catch(InvalidAlgorithmParameterException e) {
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		catch(InvalidKeyException e) {
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		catch(BadPaddingException e) {
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		catch(IllegalBlockSizeException e) {
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		
		return encryptStr;
	}
	
	/**
	 * 
	 * @param baseSecretPath
	 * @param targetStr
	 * @return
	 */
	public static String decryptForAES(String baseSecretPath, String targetStr){
		String decryptStr = "";
		
		try {
			byte[] keyData4Byte = getAESKeyString(baseSecretPath).getBytes();
			
			SecretKey secretKey = new SecretKeySpec(keyData4Byte, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(keyData4Byte));
			
			byte[] decryptData4Byte = Base64.decodeBase64(targetStr.getBytes());
			decryptStr = new String(cipher.doFinal(decryptData4Byte), "UTF-8");
		}
		catch(IOException e){
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		catch(NoSuchPaddingException e) {
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		catch(NoSuchAlgorithmException e) {
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		catch(InvalidAlgorithmParameterException e) {
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		catch(InvalidKeyException e) {
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		catch(BadPaddingException e) {
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		catch(IllegalBlockSizeException e) {
			System.out.println("[makeAESKeyFile] error : " + e.getMessage());
		}
		
		return decryptStr;
	}
}
