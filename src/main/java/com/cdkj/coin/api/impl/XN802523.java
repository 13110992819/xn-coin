package com.cdkj.coin.api.impl;

import com.cdkj.coin.ao.IJourAO;
import com.cdkj.coin.api.AProcessor;
import com.cdkj.coin.common.JsonUtil;
import com.cdkj.coin.core.StringValidater;
import com.cdkj.coin.domain.Jour;
import com.cdkj.coin.dto.req.XN802523Req;
import com.cdkj.coin.exception.BizException;
import com.cdkj.coin.exception.ParaException;
import com.cdkj.coin.spring.SpringContextHolder;

/**
 * 根据业务订单编号(refNo)查询所有关联流水(front/oss)
 * @author: xieyj 
 * @since: 2016年12月24日 上午8:17:00 
 * @history:
 */
public class XN802523 extends AProcessor {

    private IJourAO jourAO = SpringContextHolder.getBean(IJourAO.class);

    private XN802523Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Jour condition = new Jour();
        condition.setRefNo(req.getRefNo());
        condition.setSystemCode(req.getSystemCode());
        return jourAO.queryJourList(condition);
    }

    @Override
    public void doCheck(String inputparams, String operator) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802523Req.class);
        StringValidater.validateBlank(req.getRefNo(), req.getSystemCode());
    }
}
