package com.cdkj.coin.ao;

import java.util.List;

import com.cdkj.coin.bo.base.Paginable;
import com.cdkj.coin.domain.HLOrder;
import com.cdkj.coin.spring.ServiceModule;

@ServiceModule
public interface IHLOrderAO {
    String DEFAULT_ORDER_COLUMN = "code";

    // 审批订单
    public void approveOrder(String code, String adjustResult,
            String approveUser, String approveNote, String systemCode);

    public Paginable<HLOrder> queryHLOrderPage(int start, int limit,
            HLOrder condition);

    public List<HLOrder> queryHLOrderList(HLOrder condition);

    public HLOrder getHLOrder(String code, String systemCode);

}
