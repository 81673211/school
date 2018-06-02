package com.school.util;

import com.school.constant.Constants;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/** 
 * @author  作者：方梁
 * @date 创建时间：2016年10月28日 下午3:51:51
 * @description   
 */
public class MD5PasswordEncoder implements PasswordEncoder{
	private org.springframework.security.authentication.encoding.Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
	private CharSequence salt = Constants.PASSWD_SALT;
	
	public MD5PasswordEncoder(){
	}
	
	public MD5PasswordEncoder(CharSequence salt){
		this.salt = salt;
	}

	@Override
	public String encode(CharSequence rawPassword) {
		return md5PasswordEncoder.encodePassword(rawPassword.toString(), salt);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return md5PasswordEncoder.isPasswordValid(encodedPassword, rawPassword.toString(), salt);
	}
}
