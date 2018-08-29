package com.zcxa.share.controller;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author BGL
 * @date 2018年8月29日
 */
public abstract class BaseController {
	public Map<String, Object> success(String msg, Object object)
	  {
	    Map<String, Object> resaultMap = new HashMap<String, Object>();
	    resaultMap.put("success", "true");
	    resaultMap.put("msg", msg);
	    resaultMap.put("data", object);
	    return resaultMap;
	  }

	  public static String error(String msg)
	  {
	    return "{\"success\": false, \"msg\": \"" + msg + "\"}";
	  }
}
