package com.gdkm.weixin.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.gdkm.weixin.domain.InMessage;
import com.gdkm.weixin.service.MessageConvertHelper;

//如果需要返回到指定页面，则需要用 @Controller配合视图解析器InternalResourceViewResolver才行。
//若返回json等内容到页面，则需要加@ResponseBody注解  返回的是return里的内容

//@RestController是满足RESTful风格的一种控制器实现，实际上它还是@Controller。
//但是@RestController只是返回内容，不返回视图（JSP、HTML）。

//路径和类的映射关系
//<url-pattern> 用于映射URL和Servlet的关系
//如果多人共享一台服务器，把kemao_2改为姓名的拼音

@RestController//= @RequestBody+@Controller
@RequestMapping("/weixin/message/receiver")
public class MssageReceiverController {

	// 日志记录器
	private static final Logger LOG = LoggerFactory.getLogger(MssageReceiverController.class);

	@Autowired
	private XmlMapper xmlMapper;
	@Autowired
	private RedisTemplate<String, ? extends InMessage> inMessageTemplate;

	@GetMapping
	public String echo(//
			@RequestParam("signature") String signature, 
			@RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, 
			@RequestParam("echostr") String echostr) {
		return echostr;

	}

	@PostMapping
// @RequestBody注解表示把请求内容获取出来，并且转换为String传入给xml参数。

	public String onMessage(//
			@RequestParam("signature") String signature, //
			@RequestParam("timestamp") String timestamp, //
			@RequestParam("nonce") String nonce, //
			@RequestBody String xml) throws JsonParseException, JsonMappingException, IOException {
		// 收到消息
		// {}是占位符，第一个{}会把第二个参数的值自动填入
		// LOG.trace必须要求日志记录器的配置为trace级别才能输出
		
		LOG.trace("收到的消息原文：\n{}\n------------------------------", xml);
		
		// 转换消息
		InMessage inMessage = convert(xml);

		if (inMessage == null) {
			LOG.error("消息无法转换！原文：\n{}\n", xml);
			// 消息无法转换
			return "success";
		}

		LOG.debug("转换后的消息对象\n{}\n", inMessage);

		// 把消息丢入队列

		// 1
//		ByteArrayOutputStream bos =new ByteArrayOutputStream();
//		ObjectOutputStream out =new ObjectOutputStream(bos);
//		out.writeObject(inMessage);
//		byte[] data =bos.toByteArray();
		// 序列化之后的字节数组
		// 2

		// 直接把对象发送出去，调用ValueSerializer来实现对象的序列化和反序列
		String channel = "weixin_" + inMessage.getMsgType();
		inMessageTemplate.convertAndSend(channel, inMessage);

//		inMessageTemplate.execute(new RedisCallback<InMessage>() {
//
//			@Override
//			public InMessage doInRedis(RedisConnection connection) throws DataAccessException {
//				// 建议不同的人分开前缀
//				
//				String channel="weixin_"+inMessage.getClass();
//				connection.publish(channel.getBytes(), data);
//
//				return null;
//		}
//		});
		// 消费队列中的消息
		// 产生客服消息

		return "success";
	}

	private InMessage convert(String xml) throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		Class<? extends InMessage> c = MessageConvertHelper.getClass(xml);
		InMessage msg = xmlMapper.readValue(xml, c);
		return msg;
	}

}
