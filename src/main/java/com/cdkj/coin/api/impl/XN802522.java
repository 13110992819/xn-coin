package com.cdkj.coin.api.impl;

import com.cdkj.coin.ao.IJourAO;
import com.cdkj.coin.api.AProcessor;
import com.cdkj.coin.common.JsonUtil;
import com.cdkj.coin.core.StringValidater;
import com.cdkj.coin.dto.req.XN802522Req;
import com.cdkj.coin.exception.BizException;
import com.cdkj.coin.exception.ParaException;
import com.cdkj.coin.spring.SpringContextHolder;

/**
 * 流水详情
 * @author: xieyj 
 * @since: 2016年12月24日 上午8:17:00 
 * @history:
 */
public class XN802522 extends AProcessor {

    private IJourAO jourAO = SpringContextHolder.getBean(IJourAO.class);

    private XN802522Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return jourAO.getJour(req.getCode(), req.getSystemCode());
    }

    @Override
    public void doCheck(String inputparams, String operator) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802522Req.class);
        StringValidater.validateBlank(req.getCode(), req.getSystemCode());
    }
}
