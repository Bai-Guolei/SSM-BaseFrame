package com.zcxa.share.utils;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

public class MongoDbUtils {

	@Autowired
	static GridFsTemplate gridFs;

	/**
	 * 保存附件
	 * 
	 * @param file
	 * @param id
	 * @param metaData
	 * @return
	 * @throws Exception
	 */
	public static String saveFile(MultipartFile file, String id, DBObject metaData) throws Exception {
		String fileId;
		if (StringUtils.isNotBlank(id)) {
			deleteFile(id);
		}

		InputStream is = null;
		try {
			is = file.getInputStream();
			fileId = gridFs.store(is, file.getOriginalFilename(), file.getContentType(), metaData).getId().toString();
		} finally {
			IOUtils.closeQuietly(is);
		}

		return fileId;
	}
	
	/**
	 * 删除附件
	 * @param id
	 * @throws Exception
	 */
	public static void deleteFile(String id) throws Exception {
		gridFs.delete(new Query(Criteria.where("_id").is(id)));
	}
	
	/**
	 * 加载附件
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static GridFSDBFile loadFile(String id) throws Exception {
		if (StringUtils.isNotBlank(id)) {
			return gridFs.findOne(new Query(Criteria.where("_id").is(id)));
		}

		return null;
	}
}
