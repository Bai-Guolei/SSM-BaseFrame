package com.zcxa.share.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zcxa.share.enums.FileSource;
import com.zcxa.share.enums.FlagEnum;
import com.zcxa.share.utils.MongoDbUtils;
import com.zcxa.share.vo.Attachment;
import com.zcxa.share.vo.User;
/**
 * 
 * @author BGL
 * @date 2018年8月29日
 */
@Service
public class AttachmentService {
	
	/**
	 * 附件存储
	 * @param multipartFile
	 * @param su
	 * @param dataId
	 * @param fileSource
	 * @param fileCode
	 * @return
	 * @throws Exception 
	 */
	public Attachment save(MultipartFile file, User su, String dataId, String fileSource, String fileCode) throws Exception {
		
		FileSource source = null;
		if(StringUtils.isNotBlank(fileSource)) {
			source = FileSource.findById(fileSource);
		}
		// 保存附件至Mogodb
		DBObject metaData = new BasicDBObject();
		metaData.put("type", source.getType());
		String fileId = MongoDbUtils.saveFile(file, null, metaData);

		// 保存本地记录
		Attachment attachment = new Attachment();
		attachment.setCreateTime(new Date());
		attachment.setCreateUerId(su.getId());
		// 如果DataID为空,则需要在保存相关数据是更新,默认为0
		attachment.setDataId(StringUtils.isNotBlank(dataId) ? Long.parseLong(dataId) : 0);
		attachment.setIsDel(FlagEnum.NO.getId());
		attachment.setFileId(fileId);
		attachment.setFileName(file.getOriginalFilename());
		attachment.setFileSize(file.getSize());
		attachment.setFileSource(source.getId());
		attachment.setFileCode(StringUtils.isNotBlank(fileCode) ? fileCode : "1");
//		long id = this.insertIntoTable(attachment);
//		attachment.setId(id);
		return attachment;
	}
	
	
}
