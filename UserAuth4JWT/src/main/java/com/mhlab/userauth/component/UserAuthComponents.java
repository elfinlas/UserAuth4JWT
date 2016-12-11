package com.mhlab.userauth.component;

import java.nio.file.FileSystems;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.mhlab.userauth.util.EncryptUtil;

@Component
public class UserAuthComponents implements ApplicationListener<ContextRefreshedEvent> {

	@Resource(name="baseSecretPath")
	private String baseSecretPath;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		//System.out.println("21`212");
		//EncryptUtil.checkAESKeyFile(baseSecretPath+FileSystems.getDefault().getSeparator()+EncryptUtil.AES_KEY_FILE_NAME);
	}

	
}
