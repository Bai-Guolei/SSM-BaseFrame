package com.zcxa.share.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.zcxa.share.service.AttachmentService;
import com.zcxa.share.vo.User;

@RestController
@RequestMapping(path = "/base/file")
public class AttachmentController extends BaseController{
	private Logger lg = LoggerFactory.getLogger(AttachmentController.class);
	@Autowired
	private AttachmentService service;
	
	@RequestMapping(value = "/upload", produces = "text/plain;charset=UTF-8")
	public String upload( @RequestParam(value = "file", required = false) MultipartFile file, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		if (request.getParameter("chunk") == null) {
            String realPath = request.getSession().getServletContext()
                    .getRealPath("/Upload/");
            String fileName = file.getOriginalFilename();

            File targetFile = new File(realPath, fileName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            file.transferTo(targetFile); // 小文件，直接拷贝

            return "";
        } else {
            int chunk = Integer.parseInt(request.getParameter("chunk")); // 当前分片
            int chunks = Integer.parseInt(request.getParameter("chunks")); // 分片总计

            String realPath = request.getSession().getServletContext()
                    .getRealPath("/Upload/");

            String Ogfilename = file.getOriginalFilename();

            File tempFile = new File(realPath, Ogfilename);
            OutputStream outputStream = new FileOutputStream(tempFile, true);
            InputStream inputStream = file.getInputStream();

            byte buffer[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            outputStream.close();

            return "";
        }
	}
	
	@RequestMapping("/uploadfile")
	public Object uploadFile(MultipartFile[] file, String dataId, String fileSource, 
			String fileCode, HttpServletRequest request, HttpServletResponse resp) throws Exception {
		String fileName = file[0].getOriginalFilename();
		User user = new User();
		service.save(file[0], user, dataId, fileSource, fileCode);
		return user;
	}
	
}
