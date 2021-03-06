package com.cdkj.coin.bo;

import java.util.List;

import com.cdkj.coin.bo.base.IPaginableBO;
import com.cdkj.coin.domain.Arbitrate;

public interface IArbitrateBO extends IPaginableBO<Arbitrate> {

    public String submit(String tradeOrderCode, String yuangao, String beigao,
            String reason, String attach);

    public int handle(Arbitrate arbitrate, String result, String updater,
            String remark);

    public List<Arbitrate> queryArbitrateList(Arbitrate condition);

    public Arbitrate getArbitrate(String code);

}
