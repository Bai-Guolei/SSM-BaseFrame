package com.zcxa.share.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaskerService {
	private final Logger lg = LoggerFactory.getLogger(TaskerService.class);
	public void  findProduct() {
		lg.debug("定时任务成功执行");
	}
}
