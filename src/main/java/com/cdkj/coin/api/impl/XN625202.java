/**
 * @Title XN625200.java 
 * @Package com.cdkj.coin.api.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年11月8日 下午3:12:21 
 * @version V1.0   
 */
package com.cdkj.coin.api.impl;

import com.cdkj.coin.ao.IEthAddressAO;
import com.cdkj.coin.api.AProcessor;
import com.cdkj.coin.common.JsonUtil;
import com.cdkj.coin.core.ObjValidater;
import com.cdkj.coin.dto.req.XN625202Req;
import com.cdkj.coin.dto.res.BooleanRes;
import com.cdkj.coin.exception.BizException;
import com.cdkj.coin.exception.ParaException;
import com.cdkj.coin.spring.SpringContextHolder;

/** 
 * 弃用
 * @author: haiqingzheng 
 * @since: 2017年11月8日 下午3:12:21 
 * @history:
 */
public class XN625202 extends AProcessor {

    private IEthAddressAO ethAddressAO = SpringContextHolder
        .getBean(IEthAddressAO.class);

    private XN625202Req req = null;

    /** 
     * @see com.cdkj.coin.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        ethAddressAO.abandonAddress(req.getCode());
        return new BooleanRes(true);
    }

    /** 
     * @see com.cdkj.coin.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams, String operator) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN625202Req.class);
        ObjValidater.validateReq(req);
    }

}
