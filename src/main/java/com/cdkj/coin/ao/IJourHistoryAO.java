package com.cdkj.coin.ao;

import com.cdkj.coin.bo.base.Paginable;
import com.cdkj.coin.domain.Jour;

/** 
 * @author: xieyj 
 * @since: 2016年12月23日 下午9:05:07 
 * @history:
 */
public interface IJourHistoryAO {
    String DEFAULT_ORDER_COLUMN = "code";

    public Paginable<Jour> queryJourPage(int start, int limit, Jour condition);

    public Paginable<Jour> queryFrontJourPage(int start, int limit,
            Jour condition);

    public Jour getJour(String code, String systemCode);
}
