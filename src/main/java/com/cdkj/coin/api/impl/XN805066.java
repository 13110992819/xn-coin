package com.cdkj.coin.api.impl;

import com.cdkj.coin.ao.IUserAO;
import com.cdkj.coin.api.AProcessor;
import com.cdkj.coin.common.JsonUtil;
import com.cdkj.coin.core.StringValidater;
import com.cdkj.coin.dto.req.XN805066Req;
import com.cdkj.coin.dto.res.BooleanRes;
import com.cdkj.coin.exception.BizException;
import com.cdkj.coin.exception.ParaException;
import com.cdkj.coin.spring.SpringContextHolder;

/**
 * 设置支付密码
 * @author: xieyj 
 * @since: 2017年7月16日 下午2:41:15 
 * @history:
 */
public class XN805066 extends AProcessor {
    private IUserAO userAO = SpringContextHolder.getBean(IUserAO.class);

    private XN805066Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        userAO.doSetTradePwd(req.getUserId(), req.getTradePwd(),
            req.getSmsCaptcha(), req.getGoogleCaptcha());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805066Req.class);
        StringValidater.validateBlank(req.getUserId(), req.getTradePwd(),
            req.getSmsCaptcha());
    }

}
