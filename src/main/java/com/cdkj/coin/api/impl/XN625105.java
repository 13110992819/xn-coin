/**
 * @Title XN625100.java 
 * @Package com.cdkj.coin.api.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年11月9日 下午7:00:49 
 * @version V1.0   
 */
package com.cdkj.coin.api.impl;

import com.cdkj.coin.ao.IEthCollectionAO;
import com.cdkj.coin.api.AProcessor;
import com.cdkj.coin.common.JsonUtil;
import com.cdkj.coin.core.ObjValidater;
import com.cdkj.coin.core.StringValidater;
import com.cdkj.coin.domain.EthCollection;
import com.cdkj.coin.dto.req.XN625105Req;
import com.cdkj.coin.exception.BizException;
import com.cdkj.coin.exception.ParaException;
import com.cdkj.coin.spring.SpringContextHolder;

/** 
 * 分页查询归集订单
 * @author: haiqingzheng 
 * @since: 2017年11月9日 下午7:00:49 
 * @history:
 */
public class XN625105 extends AProcessor {

    private IEthCollectionAO ethCollectionAO = SpringContextHolder
        .getBean(IEthCollectionAO.class);

    private XN625105Req req = null;

    /** 
     * @see com.cdkj.coin.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        EthCollection condition = new EthCollection();
        condition.setCodeForQuery(req.getCode());
        condition.setFromAddress(req.getFromAddress());
        condition.setToAddress(req.getToAddress());
        condition.setStatus(req.getStatus());
        condition.setTxHash(req.getTxHash());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return ethCollectionAO.queryEthCollectionPage(start, limit, condition);
    }

    /** 
     * @see com.cdkj.coin.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN625105Req.class);
        ObjValidater.validateReq(req);
    }

}
