package com.cdkj.coin.api.impl;

import java.math.BigDecimal;

import com.cdkj.coin.ao.IChargeAO;
import com.cdkj.coin.api.AProcessor;
import com.cdkj.coin.common.JsonUtil;
import com.cdkj.coin.core.StringValidater;
import com.cdkj.coin.dto.req.XN802700Req;
import com.cdkj.coin.dto.res.PKCodeRes;
import com.cdkj.coin.exception.BizException;
import com.cdkj.coin.exception.ParaException;
import com.cdkj.coin.spring.SpringContextHolder;

/**
 * 线下充值申请
 * @author: myb858 
 * @since: 2017年5月3日 上午9:23:51 
 * @history:
 */
public class XN802700 extends AProcessor {
    private IChargeAO chargeAO = SpringContextHolder.getBean(IChargeAO.class);

    private XN802700Req req = null;

    /** 
    * @see com.xnjr.base.api.IProcessor#doBusiness()
    */
    @Override
    public synchronized Object doBusiness() throws BizException {
        BigDecimal amount = StringValidater.toBigDecimal(req.getAmount());
        String code = chargeAO.applyOrder(req.getAccountNumber(),
            req.getBizType(), amount, req.getPayCardInfo(), req.getPayCardNo(),
            req.getApplyUser(), req.getApplyNote());
        return new PKCodeRes(code);
    }

    /** 
    * @see com.xnjr.base.api.IProcessor#doCheck(java.lang.String)
    */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802700Req.class);
        StringValidater.validateBlank(req.getAccountNumber(),
            req.getApplyUser());
        StringValidater.validateAmount(req.getAmount());
    }
}
