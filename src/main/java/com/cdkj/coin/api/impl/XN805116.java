package com.cdkj.coin.api.impl;

import com.cdkj.coin.ao.IUserRelationAO;
import com.cdkj.coin.api.AProcessor;
import com.cdkj.coin.common.JsonUtil;
import com.cdkj.coin.core.StringValidater;
import com.cdkj.coin.dto.req.XN805116Req;
import com.cdkj.coin.enums.EUserReleationType;
import com.cdkj.coin.exception.BizException;
import com.cdkj.coin.exception.ParaException;
import com.cdkj.coin.spring.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;

/**
 * 关注人是否存在关联
 *
 * @author: xieyj
 * @since: 2016年8月31日 上午9:26:10
 * @history:
 */
public class XN805116 extends AProcessor {
    private IUserRelationAO userRelationAO = SpringContextHolder
            .getBean(IUserRelationAO.class);

    private XN805116Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        String type = req.getType();
        if (StringUtils.isBlank(type)) {
            type = EUserReleationType.TRUST.getCode();
        }
        return userRelationAO.isExistUserRelation(req.getUserId(),
                req.getToUser(), type);
    }

    @Override
    public void doCheck(String inputparams, String operator) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805116Req.class);
        StringValidater.validateBlank(req.getUserId(), req.getToUser());
    }
}
