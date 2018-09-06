/**
 * Copyright © 2016, Forp Co., LTD
 *
 * All Rights Reserved.
 */
package com.zcxa.share.controller;
import com.mongodb.gridfs.GridFSDBFile;
import com.zcxa.share.utils.MongoDbUtils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 第三方组件Controller
 *
 * @author  Bruce
 * @version 2016-8-11 17:32
 */
@RestController
public class ThirdController extends BaseController
{
	/**
	 * Log4j logger
	 */
	private static final Logger lg = LoggerFactory.getLogger(ThirdController.class);
	
	@Autowired
	GridFsTemplate gridFs;

	/**
	 * 下载附件
	 *
	 * @param id    文件编号
	 * @param req   请求参数
	 * @param rsp   请求回应对象
	 */
	@RequestMapping({"/third/mongodb/{id}", "/third/mongodb-download/{id}"})
	public ModelAndView downloadMongoDBAttachement(@PathVariable String id, HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		if (StringUtils.isNotBlank(id))
		{
			GridFSDBFile file = gridFs.findOne(new Query(Criteria.where("_id").is(id)));
			if (null != file)
			{
				rsp.setContentType("application/octet-stream");
				rsp.setHeader("charset", "utf-8");
				rsp.addHeader("Content-Length", String.valueOf(file.getLength()));

				// 文件名称：优先使用请求参数中的
				String fileName;
				if (StringUtils.isNotBlank(req.getParameter("fileName")))
				{
					fileName = URLEncoder.encode(req.getParameter("fileName"), "UTF-8");
				}
				else
				{
					fileName = URLEncoder.encode(file.getFilename(), "UTF-8");
				}

				// Safari浏览器文件名称兼容性适配
				String userAgent = req.getHeader("User-Agent");
				if (StringUtils.isNotBlank(userAgent) && userAgent.toLowerCase().contains("safari"))
				{
					fileName = new String(URLDecoder.decode(fileName, "UTF-8").getBytes("UTF-8"), "ISO8859-1");
				}

				rsp.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\";filename*=UTF-8''" + fileName);
				file.writeTo(rsp.getOutputStream());

				return null;
			}
			else
				return null;
			//返回错误的页面
//				return errorView("无效的附件编号：" + id, req);
		}
		else
			return null;
//			return errorView("无效的附件编号！", req);
	}

	/**
	 * 在线预览跳转
	 *
	 * @param id        文件编号
	 * @param req       请求参数
	 * @param rsp       请求回应对象
	 */
	@RequestMapping(path = "/third/mongodb/preview/{id}", method = RequestMethod.GET)
	public ModelAndView preview(@PathVariable String id, HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		if (StringUtils.isNotBlank(id))
		{	
			//从mongodb下载附件
			GridFSDBFile file = MongoDbUtils.loadFile(id);
			if (null != file)
			{
				// 文件名称：优先使用请求参数中的
				String fileName = StringUtils.isBlank(req.getParameter("fileName")) ? file.getFilename() : req.getParameter("fileName");
				String fileExtension = FilenameUtils.getExtension(fileName).toLowerCase();

				if ("pdf".equals(fileExtension))
				{
					// Office || PDF
					req.setAttribute("id", id);
					req.setAttribute("isPDF", "pdf".equals(fileExtension));
					req.setAttribute("fileName", fileName);
					return new ModelAndView("/common/preview.jsp");
				}
				else
				{
					lg.info("普通文件格式，直接下载");

					// 其它格式文档：下载模式
					rsp.setContentType(file.getContentType());
					file.writeTo(rsp.getOutputStream());
				}

				return null;
			}
			else
			{	
				return null;
//				return errorView("无效的附件编号：" + id, req);
			}
		}
		else
		{	
			return null;
//			return errorView("空的附件编号！", req);
		}
	}

	/**
	 * Office文档在线预览
	 *
	 * @param id    文件编号
	 * @param req   请求参数
	 * @param rsp   请求回应对象
	 */
	@RequestMapping(path = "/third/mongodb/preview/office/{id}", method = RequestMethod.POST)
	public ModelAndView previewOffice(@PathVariable String id, HttpServletRequest req, HttpServletResponse rsp) throws Exception
	{
		if (StringUtils.isNotBlank(id))
		{
			GridFSDBFile file = MongoDbUtils.loadFile(id);
			if (null != file)
			{
				// 文件名称：优先使用请求参数中的
				String fileName = StringUtils.isBlank(req.getParameter("fileName")) ? file.getFilename() : req.getParameter("fileName");
				String fileExtension = FilenameUtils.getExtension(fileName).toLowerCase();
//				if (OfficeUtil.EXTENSIONS.contains(fileExtension))
//				{
//					// Office文档
//					OfficeUtil.toHtml(file, fileExtension, rsp.getOutputStream());
//				}
//				else
//				{
//					lg.warn("无效的Office文件，忽略预览动作！");
//				}

				return null;
			}
			else
			{
//				return errorView("无效的Office附件编号：" + id, req);
			}
		}
		else
		{
//			return errorView("空的Office附件编号！", req);
		}
		return null;
	}

	//=================================================================
	//		corpper用户头像 - avatar
	//=================================================================

}