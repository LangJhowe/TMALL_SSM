package com.how2java.tmall.util;
/** 
* @author Jhowe
* @version 2019��3��10�� ����4:40:22
* tmall_ssm
*/

import org.springframework.web.multipart.MultipartFile;

public class UploadedImageFile {
	// image��������ע�룬��ҳ��ı�name image����һ��
	MultipartFile image;
	public MultipartFile getImage() {
		return image;
	}
	
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	
}
