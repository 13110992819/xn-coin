package com.cdkj.coin.bo;

import java.math.BigDecimal;
import java.util.List;

import com.cdkj.coin.bo.base.IPaginableBO;
import com.cdkj.coin.domain.Account;
import com.cdkj.coin.domain.EthAddress;
import com.cdkj.coin.domain.Withdraw;
import com.cdkj.coin.enums.EWithdrawStatus;

public interface IWithdrawBO extends IPaginableBO<Withdraw> {

    public void doCheckTimes(Account account);

    String applyOrder(Account account, BigDecimal amount, BigDecimal fee,
            String payCardInfo, String payCardNo, String applyUser,
            String applyNote);

    void approveOrder(Withdraw data, EWithdrawStatus status,
            String approveUser, String approveNote);

    void payOrder(Withdraw data, EWithdrawStatus status, String payUser,
            String payNote, String channelOrder, String payCode,
            BigDecimal payFee);

    void broadcastOrder(Withdraw data, String txHash, String updater);

    List<Withdraw> queryWithdrawList(Withdraw condition);

    Withdraw getWithdraw(String code, String systemCode);

    public Withdraw getWithdraw(String hash);

    public EthAddress getAddressUseInfo(String fromAddress);

    public BigDecimal getTotalWithdraw();
}
