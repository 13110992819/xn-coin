/**
 * @Title XN625205.java 
 * @Package com.cdkj.coin.api.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年11月8日 下午3:16:17 
 * @version V1.0   
 */
package com.cdkj.coin.api.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.cdkj.coin.ao.IEthAddressAO;
import com.cdkj.coin.api.AProcessor;
import com.cdkj.coin.common.DateUtil;
import com.cdkj.coin.common.JsonUtil;
import com.cdkj.coin.core.ObjValidater;
import com.cdkj.coin.core.StringValidater;
import com.cdkj.coin.domain.EthAddress;
import com.cdkj.coin.dto.req.XN625205Req;
import com.cdkj.coin.exception.BizException;
import com.cdkj.coin.exception.ParaException;
import com.cdkj.coin.spring.SpringContextHolder;

/** 
 * 分页查询地址
 * @author: haiqingzheng 
 * @since: 2017年11月8日 下午3:16:17 
 * @history:
 */
public class XN625205 extends AProcessor {

    private IEthAddressAO ethAddressAO = SpringContextHolder
        .getBean(IEthAddressAO.class);

    private XN625205Req req = null;

    /** 
     * @see com.cdkj.coin.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        EthAddress condition = new EthAddress();
        Date startDate = DateUtil.getStartDatetime(req
            .getAvailableDatetimeStart());
        Date endDate = DateUtil.getEndDatetime(req.getAvailableDatetimeEnd());
        condition.setStatusList(req.getStatusList());
        condition.setType(req.getType());
        condition.setAddressForQuery(req.getAddress());
        condition.setUserId(req.getUserId());
        condition.setAvailableDatetimeStart(startDate);
        condition.setAvailableDatetimeEnd(endDate);
        condition.setStatus(req.getStatus());
        if (StringUtils.isNotBlank(req.getBalanceStart())) {
            condition.setBalanceStart(StringValidater.toBigDecimal(req
                .getBalanceStart()));
        }
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return ethAddressAO.queryEthAddressPage(start, limit, condition);
    }

    /** 
     * @see com.cdkj.coin.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN625205Req.class);
        ObjValidater.validateReq(req);
    }

}
