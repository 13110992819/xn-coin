package com.cdkj.coin.ao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.coin.ao.IChargeAO;
import com.cdkj.coin.bo.IAccountBO;
import com.cdkj.coin.bo.IChargeBO;
import com.cdkj.coin.bo.IEthCollectionBO;
import com.cdkj.coin.bo.IEthTransactionBO;
import com.cdkj.coin.bo.IJourBO;
import com.cdkj.coin.bo.IUserBO;
import com.cdkj.coin.bo.base.Paginable;
import com.cdkj.coin.domain.Account;
import com.cdkj.coin.domain.Charge;
import com.cdkj.coin.domain.EthCollection;
import com.cdkj.coin.domain.EthTransaction;
import com.cdkj.coin.domain.Jour;
import com.cdkj.coin.domain.User;
import com.cdkj.coin.dto.res.XN802707Res;
import com.cdkj.coin.enums.EBoolean;
import com.cdkj.coin.enums.EChannelType;
import com.cdkj.coin.enums.EChargeStatus;
import com.cdkj.coin.enums.ECoin;
import com.cdkj.coin.enums.EJourBizTypeCold;
import com.cdkj.coin.enums.EJourBizTypeUser;
import com.cdkj.coin.enums.EJourKind;
import com.cdkj.coin.enums.ESystemAccount;
import com.cdkj.coin.enums.ESystemCode;
import com.cdkj.coin.exception.BizException;

@Service
public class ChargeAOImpl implements IChargeAO {
    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IChargeBO chargeBO;

    @Autowired
    private IJourBO jourBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IEthTransactionBO ethTransactionBO;

    @Autowired
    private IEthCollectionBO ethCollectionBO;

    @Override
    public String applyOrder(String accountNumber, BigDecimal amount,
            String payCardInfo, String payCardNo, String applyUser,
            String applyNote) {
        if (amount.compareTo(BigDecimal.ZERO) == 0
                || amount.compareTo(BigDecimal.ZERO) == -1) {
            throw new BizException("xn000000", "充值金额需大于零");
        }
        Account account = accountBO.getAccount(accountNumber);
        // 生成充值订单
        String code = chargeBO.applyOrderOffline(account,
            EJourBizTypeUser.AJ_CHARGE.getCode(), amount, payCardInfo,
            payCardNo, applyUser, applyNote);
        return code;
    }

    @Override
    @Transactional
    public void payOrder(String code, String payUser, String payResult,
            String payNote, String systemCode) {
        Charge data = chargeBO.getCharge(code, systemCode);
        if (!EChargeStatus.toPay.getCode().equals(data.getStatus())) {
            throw new BizException("xn000000", "申请记录状态不是待支付状态，无法支付");
        }
        if (EBoolean.YES.getCode().equals(payResult)) {
            payOrderYES(data, payUser, payNote);
        } else {
            payOrderNO(data, payUser, payNote);
        }
    }

    private void payOrderNO(Charge data, String payUser, String payNote) {
        chargeBO.payOrder(data, false, payUser, payNote);
    }

    private void payOrderYES(Charge data, String payUser, String payNote) {
        chargeBO.payOrder(data, true, payUser, payNote);
        // 用户账户加钱
        Account userAccount = accountBO.getAccount(data.getAccountNumber());
        userAccount = accountBO.changeAmount(userAccount, data.getAmount(),
            EChannelType.Offline, null, null, data.getCode(),
            EJourBizTypeUser.AJ_CHARGE.getCode(), "ETH线下充值");

        if (ECoin.ETH.getCode().equals(userAccount.getCurrency())) {
            Account coldAccount = accountBO
                .getAccount(ESystemAccount.SYS_ACOUNT_ETH_COLD.getCode());
            // 冷钱包加钱
            accountBO.changeAmount(coldAccount, data.getAmount(),
                EChannelType.Offline, null, null, data.getCode(),
                EJourBizTypeCold.AJ_INCOME.getCode(), "ETH线下充值");
        }
    }

    @Override
    public Paginable<Charge> queryChargePage(int start, int limit,
            Charge condition) {
        Paginable<Charge> page = chargeBO.getPaginable(start, limit, condition);
        if (CollectionUtils.isNotEmpty(page.getList())) {
            List<Charge> list = page.getList();
            for (Charge charge : list) {
                User user = userBO.getUser(charge.getApplyUser());
                charge.setUser(user);
            }
        }
        return page;
    }

    @Override
    public List<Charge> queryChargeList(Charge condition) {
        List<Charge> list = chargeBO.queryChargeList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            for (Charge charge : list) {
                User user = userBO.getUser(charge.getApplyUser());
                charge.setUser(user);
            }
        }
        return list;
    }

    @Override
    public Charge getCharge(String code, String systemCode) {
        Charge charge = chargeBO.getCharge(code, systemCode);
        User user = userBO.getUser(charge.getApplyUser());
        charge.setUser(user);
        return charge;
    }

    @Override
    public XN802707Res getChargeCheckInfo(String code) {
        XN802707Res res = new XN802707Res();

        // 充值订单详情
        Charge charge = chargeBO.getCharge(code, ESystemCode.COIN.getCode());

        // 充值对应流水记录
        Jour jour = new Jour();
        jour.setRefNo(charge.getCode());
        jour.setKind(EJourKind.BALANCE.getCode());
        List<Jour> jourList1 = jourBO.queryJourList(jour);

        // 充值对应广播记录
        EthTransaction ethTransaction = new EthTransaction();
        ethTransaction.setRefNo(charge.getCode());
        List<EthTransaction> resultList1 = ethTransactionBO
            .queryEthTransactionList(ethTransaction);

        EthCollection ethCollection = ethCollectionBO
            .getEthCollectionByRefNo(charge.getCode());
        // 如果有归集
        if (ethCollection != null) {
            // 归集对应流水
            jour.setRefNo(ethCollection.getCode());
            List<Jour> jourList2 = jourBO.queryJourList(jour);
            jourList1.addAll(jourList2);
            // 归集对应广播记录
            ethTransaction.setRefNo(ethCollection.getCode());
            List<EthTransaction> resultList2 = ethTransactionBO
                .queryEthTransactionList(ethTransaction);
            resultList1.addAll(resultList2);
        }

        res.setCharge(charge);
        res.setEthCollection(ethCollection);
        res.setJourList(jourList1);
        res.setTransList(resultList1);

        return res;
    }
}
