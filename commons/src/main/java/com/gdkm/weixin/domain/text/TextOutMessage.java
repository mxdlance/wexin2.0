package com.gdkm.weixin.domain.text;

import com.gdkm.weixin.domain.OutMessage;

public class TextOutMessage extends OutMessage {
	private String text;

	public TextOutMessage(String toUser, String text) {
		super(toUser, "text");
		this.text = text;
		// TODO Auto-generated constructor stub
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
