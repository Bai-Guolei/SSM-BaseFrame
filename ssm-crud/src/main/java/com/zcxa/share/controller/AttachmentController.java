package com.zcxa.share.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zcxa.share.constant.Constants;
import com.zcxa.share.service.AttachmentService;
import com.zcxa.share.vo.Attachment;
import com.zcxa.share.vo.User;

@RestController
@RequestMapping(path = "/base/file")
public class AttachmentController extends BaseController{
	
	@Autowired
	private AttachmentService service;
	
	@RequestMapping(path = "/upload")
	public Map<String, Object> upload(MultipartFile[] file, String dataId, String fileSource, String fileCode, HttpServletRequest req) {
		User su = new User(); 
		Attachment attachment = service.save(file[0], su, dataId, fileSource, fileCode);
		return success(Constants.UPLOAD_SUCCESS_MSG, attachment);
	}
}
