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
	private final String TOKEN_FILE_NAME = "token_key"; //토큰 파일 이름
	
	@Resource(name="baseSecretPath") //기본 토큰파일이 저장될 경로
	private String baseSecretPath;
	
	@Override
	public String createToken(String tokenUserId) {
		String tokenStr = ""; //토큰 값이 저장될 변수
		String issure = "UserAuth4JWT"; //토큰 발급자
		String subject = "tokenData~~"; //토큰의 주제 (즉 토큰에 담길 내용)
		Date exDate = new Date(System.currentTimeMillis() + 60000); //토큰 만료 시간 (임시로 1분)
		Key tokenKey = MacProvider.generateKey(SignatureAlgorithm.HS256); //토큰의 서명 알고리즘
		tokenStr = Jwts.builder()
				.setIssuer(issure)
				.setSubject(subject)
				.setAudience(tokenUserId)
				.setId(SupportUtil.makeUUID2String())
				.setExpiration(exDate)
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, tokenKey)
				.compact(); //토큰 생성 
		makeHS512KeyFile(tokenKey, tokenUserId); //토큰 검증을 위해 키를 파일로 생성하고 저장한다.
		return tokenStr;
	}
	
	@Override
	public String validToken(String tokenStr, String userId) {
		String resultMsg = "";
		try {
			Jwts.parser().setSigningKey(getKeyObject4File(userId)).parseClaimsJws(tokenStr).getBody();
			resultMsg="Pass";
		}
		catch(ExpiredJwtException eje) { //토큰의 만료시간이 지난 경우
			resultMsg = "expiredTokenDate";
		}
		catch(SignatureException se) { //토큰의 서명 검증이 위조되거나 문제가 생긴 경우
			resultMsg = "wrongSign";
		}
		return resultMsg;
	}
	
	@Override
	public Map<String, Object> getTokenPayload(String tokenStr) {
		Map<String, Object> payloadMap = new HashMap<>();
		ObjectMapper om = new ObjectMapper();
		String encodedTokenPayload = tokenStr.split("\\.")[1]; //토큰의 바디 부분을 추린다. 
		String tokenPayload = new String(new Base64(true).decode(encodedTokenPayload)); //토큰의 바디를 디코딩한다.
		try{payloadMap=om.readValue(tokenPayload, new TypeReference<Map<String, Object>>(){});} //토큰의 값을 Map으로 객체화 시킨다.
		catch(Exception e){System.out.println("[getTokenPayload] + " + e.getMessage());}
		return payloadMap;
	}
	
	
	/**
	 * 토큰에 사용되는 Key 객체를 파일로 만들어주는 메서드
	 * @param keyObject 키 객체
	 * @param userId 키를 생성하게 된 사용자 ID
	 */
	private void makeHS512KeyFile(Key keyObject, String userId) {
		FileChannel fileChannel;
		String separatorStr = FileSystems.getDefault().getSeparator();
		String tokenDir = baseSecretPath+separatorStr+userId;
		String tokenPath = tokenDir+separatorStr+TOKEN_FILE_NAME;
		String keyObjStr= Base64Utils.encodeToString(keyObject.getEncoded());
		
		//토큰파일이 존재하는 경우 삭제해준다.
		if(Files.exists(Paths.get(tokenPath))) {
			try{Files.delete(Paths.get(tokenPath));}
			catch(Exception e){System.out.println("makeHS512KeyFile = " + e.getMessage());}
		}
		
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
		catch(IOException e) {System.out.println("makeHS512KeyFile = " + e.getMessage());}
	}
	
	
	/**
	 * 저장된 파일에서 Key 객체를 가져오는 메서드
	 * @param userID 사용자 Id
	 * @return 파일에서 받아온 키 객체를 반환한다.
	 */
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
