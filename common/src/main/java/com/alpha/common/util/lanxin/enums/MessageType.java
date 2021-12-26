package com.alpha.common.util.lanxin.enums;

/**
 * 消息类型枚举
 */
public enum MessageType {
	TEXT("text"),
	ATTACHMENT("attachment"),
	LINK("link"),
	NEWS("news"),
	MAIL("mail"),
	CARDTEXT("cardText");

	private String type;

	MessageType(String type){
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	@Override
	public String toString(){
		return this.getType();
	}
}
