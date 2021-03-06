/**
 * @Title XN625100.java 
 * @Package com.cdkj.coin.api.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年11月9日 下午7:00:49 
 * @version V1.0   
 */
package com.cdkj.coin.api.impl;

import com.cdkj.coin.ao.IEthTransactionAO;
import com.cdkj.coin.api.AProcessor;
import com.cdkj.coin.common.JsonUtil;
import com.cdkj.coin.core.ObjValidater;
import com.cdkj.coin.core.StringValidater;
import com.cdkj.coin.domain.EthTransaction;
import com.cdkj.coin.dto.req.XN625207Req;
import com.cdkj.coin.exception.BizException;
import com.cdkj.coin.exception.ParaException;
import com.cdkj.coin.spring.SpringContextHolder;

/** 
 * 分页查询广播记录
 * @author: haiqingzheng 
 * @since: 2017年11月9日 下午7:00:49 
 * @history:
 */
public class XN625207 extends AProcessor {

    private IEthTransactionAO ethTransactionAO = SpringContextHolder
        .getBean(IEthTransactionAO.class);

    private XN625207Req req = null;

    /** 
     * @see com.cdkj.coin.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        EthTransaction condition = new EthTransaction();
        condition.setHash(req.getHash());
        condition.setBlockHash(req.getBlockHash());
        condition.setBlockNumber(req.getBlockNumber());
        condition.setFrom(req.getFrom());
        condition.setTo(req.getTo());
        condition.setRefNo(req.getRefNo());
        condition.setCreatesStart(req.getDateStart());
        condition.setCreatesEnd(req.getDateEnd());
        condition.setAddress(req.getAddress());
        condition.setOrder("creates", "desc");
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return ethTransactionAO
            .queryEthTransactionPage(start, limit, condition);
    }

    /** 
     * @see com.cdkj.coin.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams, String operator) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN625207Req.class);
        ObjValidater.validateReq(req);
    }

}
