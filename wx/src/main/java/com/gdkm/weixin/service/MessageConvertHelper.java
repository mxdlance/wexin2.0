package com.gdkm.weixin.service;

import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXB;

import com.gdkm.weixin.domain.InMessage;
import com.gdkm.weixin.domain.event.EventInMessage;
import com.gdkm.weixin.domain.image.ImageInMessage;
import com.gdkm.weixin.domain.text.TextInMessage;

public class MessageConvertHelper {

	private static final Map<String, Class<? extends InMessage>> typeMap = new ConcurrentHashMap<>();

	static {
		typeMap.put("event", EventInMessage.class);
		typeMap.put("text", TextInMessage.class);
		typeMap.put("image", ImageInMessage.class);
	}

	public static Class<? extends InMessage> getClass(String xml) {

		// 获取消息类型
		String type = xml.substring(xml.indexOf("<MsgType><![CDATA[") + 18);
		type = type.substring(0, type.indexOf("]"));

		// 获取java
		Class<? extends InMessage> c = typeMap.get(type);
		return c;
	}

	// 传入XML转换成Java对象
	public static <T extends InMessage> T convert(String xml) {
		Class<? extends InMessage> c = getClass(xml);
		if (c == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		T msg = (T) JAXB.unmarshal(new StringReader(xml), c);
		return msg;
	}

}
