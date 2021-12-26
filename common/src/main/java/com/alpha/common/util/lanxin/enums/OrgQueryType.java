package com.alpha.common.util.lanxin.enums;

public enum OrgQueryType {

	/**分支节点*/
	BRANCHNODE(0),
	/**成员节点*/
	MEMBERNODE(1),
	/**全部节点*/
	ALLNODE(-1);
	
	private int value;

	OrgQueryType(int value){
		this.value = value;
	}
	
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
}
