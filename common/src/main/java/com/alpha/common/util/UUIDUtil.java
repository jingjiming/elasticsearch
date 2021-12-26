package com.alpha.common.util;

import java.util.UUID;

/**
 * Copyright:  
 * Author: jiming.jing
 * Date: 2017年7月22日
 * Description:
 */
public class UUIDUtil {
	
	public static String randomUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

    public static void main(String[] args) {
        for(int i = 0; i < 18; i++) {
            System.out.println(randomUUID());
        }
    }
}
