package com.cdkj.coin.enums;

import java.util.HashMap;
import java.util.Map;

import com.cdkj.coin.exception.BizException;

/**
 * @author: xieyj 
 * @since: 2016年11月11日 上午10:09:32 
 * @history:
 */
public enum ECaptchaType {
    C_REG("805041", "C端会员注册"), ADDRESS_ADD("625203", "地址新增");

    public static ECaptchaType getBizType(String code) {
        Map<String, ECaptchaType> map = getBizTypeMap();
        ECaptchaType result = map.get(code);
        if (result == null) {
            throw new BizException("XN0000", code + "对应的验证码类型不存在");
        }
        return result;
    }

    public static Map<String, ECaptchaType> getBizTypeMap() {
        Map<String, ECaptchaType> map = new HashMap<String, ECaptchaType>();
        for (ECaptchaType bizType : ECaptchaType.values()) {
            map.put(bizType.getCode(), bizType);
        }
        return map;
    }

    ECaptchaType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
