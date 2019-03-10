package com.how2java.tmall.util;
/** 
* @author Jhowe
* @version 2019年3月10日 下午4:40:22
* tmall_ssm
*/

import org.springframework.web.multipart.MultipartFile;

public class UploadedImageFile {
	// image用来接收注入，与页面的表单name image保持一致
	MultipartFile image;
	public MultipartFile getImage() {
		return image;
	}
	
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	
}
