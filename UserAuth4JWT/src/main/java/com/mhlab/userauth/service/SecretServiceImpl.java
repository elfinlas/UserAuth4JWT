package com.mhlab.userauth.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhlab.userauth.util.SupportUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Service
public class SecretServiceImpl implements SecretService {

	
	@Resource(name="baseSecretPath")
	private String baseSecretPath;
	
	private final String TOKEN_FILE_NAME = "token_key"; 
	
	
	@Override
	public String createToken(String tokenUserId) {
		String tokenStr = "";
		String issure = "UserAuth4JWT";
		String subject = "tokenData~~";
		Date exDate = new Date(System.currentTimeMillis() + 60000); //1분;
		Key tokenKey = MacProvider.generateKey(SignatureAlgorithm.HS256);
		
		tokenStr = Jwts.builder()
				.setIssuer(issure)
				.setSubject(subject)
				.setAudience(tokenUserId)
				.setId(SupportUtil.makeUUID2String())
				.setExpiration(exDate)
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, tokenKey)
				.compact();
		
		makeHS512KeyFile(tokenKey, tokenUserId); //키 파일을 생성
		
		return tokenStr;
	}
	
	
	public String validToken(String tokenStr, String userId) {
		String resultMsg = "";
		
		try {
			Jwts.parser().setSigningKey(getKeyObject4File(userId)).parseClaimsJws(tokenStr).getBody();
			resultMsg="Pass";
		}
		catch(ExpiredJwtException eje) {
			resultMsg = "expiredTokenDate";
		}
		catch(SignatureException se) {
			resultMsg = "wrongSign";
		}
		return resultMsg;
	}
	
	
	
	
	public Map<String, Object> getTokenPayload(String tokenStr) {
		Map<String, Object> payloadMap = new HashMap<>();
		ObjectMapper om = new ObjectMapper();
		String encodedTokenPayload = tokenStr.split("\\.")[1];
		String tokenPayload = new String(new Base64(true).decode(encodedTokenPayload));
		
		try{payloadMap=om.readValue(tokenPayload, new TypeReference<Map<String, Object>>(){});}
		catch(Exception e){
			System.out.println("[getTokenPayload] + " + e.getMessage());
		}
		
		return payloadMap;
	}
	
	
	
	private void makeHS512KeyFile(Key keyObject, String userId) {
		FileChannel fileChannel;
		String separatorStr = FileSystems.getDefault().getSeparator();
		String tokenDir = baseSecretPath+separatorStr+userId;
		String tokenPath = tokenDir+separatorStr+TOKEN_FILE_NAME;
		String keyObjStr= Base64Utils.encodeToString(keyObject.getEncoded());
		
		//토큰파일이 존재하는 경우 삭제해준다.
		if(Files.exists(Paths.get(tokenPath))) {
			System.out.println("Delete Old File~~");
			try{Files.delete(Paths.get(tokenPath));}
			catch(Exception e){System.out.println("makeHS512KeyFile = " + e.getMessage());}
		}
		
		/*
		 System.out.println("keyObject toString = " + keyObject.toString());
		System.out.println("keyObjStr = " + keyObjStr);
		Key tempKeyObj = new SecretKeySpec(Base64Utils.decodeFromString(keyObjStr), "HmacSHA256");
		System.out.println("file keyObject toString = " + tempKeyObj.toString());
		 */
		
		try {
			Files.createDirectories(Paths.get(tokenDir)); //디렉토리 생성
			Files.createFile(Paths.get(tokenPath)); //토큰 디렉토리에 토큰 키 파일을 생성
			fileChannel = FileChannel.open(Paths.get(tokenPath), StandardOpenOption.WRITE); //파일 쓰기
			
			ByteBuffer buffer = ByteBuffer.allocateDirect(keyObjStr.length());
			Charset charset = Charset.forName("UTF-8");
			buffer = charset.encode(keyObjStr);
			
			fileChannel.write(buffer);
			
			fileChannel.close();
		}
		catch(IOException e) {
			System.out.println("makeHS512KeyFile = " + e.getMessage());
		}
	}
	
	
	private Key getKeyObject4File(String userID) {
		FileChannel fileChannel;
		
		String keyStr = "";
		String separatorStr = FileSystems.getDefault().getSeparator();
		String tokenDir = baseSecretPath+separatorStr+userID;
		String tokenPath = tokenDir+separatorStr+TOKEN_FILE_NAME;
		
		try {
			fileChannel = FileChannel.open(Paths.get(tokenPath));
			ByteBuffer buffers = ByteBuffer.allocateDirect(100);
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
		catch(IOException e) {
			System.out.println("getKeyObject4File = " + e.getMessage());
		}
		
		return new SecretKeySpec(Base64Utils.decodeFromString(keyStr), "HmacSHA256");
	}
}
