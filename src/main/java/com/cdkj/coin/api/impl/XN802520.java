package com.cdkj.coin.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.cdkj.coin.ao.IJourAO;
import com.cdkj.coin.api.AProcessor;
import com.cdkj.coin.common.DateUtil;
import com.cdkj.coin.common.JsonUtil;
import com.cdkj.coin.core.StringValidater;
import com.cdkj.coin.domain.Jour;
import com.cdkj.coin.dto.req.XN802520Req;
import com.cdkj.coin.exception.BizException;
import com.cdkj.coin.exception.ParaException;
import com.cdkj.coin.spring.SpringContextHolder;

/**
 * 流水分页查询(oss)
 * @author: xieyj 
 * @since: 2016年12月24日 上午7:59:19 
 * @history:
 */
public class XN802520 extends AProcessor {
    private IJourAO jourAO = SpringContextHolder.getBean(IJourAO.class);

    private XN802520Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Jour condition = new Jour();
        condition.setPayGroup(req.getPayGroup());
        condition.setRefNo(req.getRefNo());
        condition.setChannelType(req.getChannelType());
        condition.setChannelOrder(req.getChannelOrder());

        condition.setAccountNumber(req.getAccountNumber());
        condition.setUserId(req.getUserId());
        condition.setRealName(req.getRealName());
        condition.setType(req.getType());
        condition.setCurrency(req.getCurrency());

        condition.setBizType(req.getBizType());
        condition.setStatus(req.getStatus());
        condition.setCreateDatetimeStart(DateUtil.getFrontDate(
            req.getDateStart(), false));
        condition.setCreateDatetimeEnd(DateUtil.getFrontDate(req.getDateEnd(),
            true));
        condition.setWorkDate(req.getWorkDate());

        condition.setCheckUser(req.getCheckUser());
        condition.setAdjustUser(req.getAdjustUser());
        condition.setSystemCode(req.getSystemCode());
        condition.setCompanyCode(req.getCompanyCode());

        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = IJourAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return jourAO.queryJourPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802520Req.class);
        StringValidater.validateNumber(req.getStart(), req.getLimit());
        StringValidater
            .validateBlank(req.getSystemCode(), req.getCompanyCode());
    }
}
