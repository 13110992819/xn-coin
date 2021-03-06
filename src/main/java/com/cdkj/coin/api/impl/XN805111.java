package com.cdkj.coin.api.impl;

import com.cdkj.coin.ao.IUserRelationAO;
import com.cdkj.coin.api.AProcessor;
import com.cdkj.coin.common.JsonUtil;
import com.cdkj.coin.core.StringValidater;
import com.cdkj.coin.dto.req.XN805111Req;
import com.cdkj.coin.dto.res.BooleanRes;
import com.cdkj.coin.enums.EUserReleationType;
import com.cdkj.coin.exception.BizException;
import com.cdkj.coin.exception.ParaException;
import com.cdkj.coin.spring.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;

/**
 * 解除关注关系
 * @author: xieyj 
 * @since: 2016年8月31日 上午9:26:10 
 * @history:
 */
public class XN805111 extends AProcessor {
    private IUserRelationAO userRelationAO = SpringContextHolder
        .getBean(IUserRelationAO.class);

    private XN805111Req req = null;

    @Override
    public Object doBusiness() throws BizException {

        String type = req.getType();
        if (StringUtils.isBlank(type)) {
            type = EUserReleationType.TRUST.getCode();
        }

        userRelationAO.unfollowUser(req.getUserId(), req.getToUser(),type);
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805111Req.class);
        StringValidater.validateBlank(req.getUserId(), req.getToUser());
    }
}
