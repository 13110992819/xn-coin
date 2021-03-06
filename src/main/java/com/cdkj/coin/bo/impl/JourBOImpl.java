/**
 * @Title AJourBOImpl.java 
 * @Package com.ibis.account.bo.impl 
 * @Description 
 * @author miyb  
 * @date 2015-3-15 下午3:22:07 
 * @version V1.0   
 */
package com.cdkj.coin.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.coin.bo.IJourBO;
import com.cdkj.coin.bo.base.PaginableBOImpl;
import com.cdkj.coin.common.DateUtil;
import com.cdkj.coin.core.OrderNoGenerater;
import com.cdkj.coin.dao.IJourDAO;
import com.cdkj.coin.domain.Account;
import com.cdkj.coin.domain.HLOrder;
import com.cdkj.coin.domain.Jour;
import com.cdkj.coin.enums.EBoolean;
import com.cdkj.coin.enums.EChannelType;
import com.cdkj.coin.enums.EGeneratePrefix;
import com.cdkj.coin.enums.EJourKind;
import com.cdkj.coin.enums.EJourStatus;
import com.cdkj.coin.exception.BizException;

/** 
 * @author: miyb 
 * @since: 2015-3-15 下午3:22:07 
 * @history:
 */
@Component
public class JourBOImpl extends PaginableBOImpl<Jour> implements IJourBO {
    @Autowired
    private IJourDAO jourDAO;

    @Override
    public String addJour(Account dbAccount, EChannelType channelType,
            String channelOrder, String payGroup, String refNo, String bizType,
            String bizNote, BigDecimal transAmount) {
        if (!EChannelType.Offline.getCode().equals(channelType.getCode())
                && !EChannelType.NBZ.getCode().equals(channelType.getCode())) {// 线下和内部帐可为空，线上必须有
            if (StringUtils.isBlank(payGroup)) {// 必须要有的判断。每一次流水新增，必有有对应业务分组
                throw new BizException("xn000000", "新增流水业务分组不能为空");
            }
        }
        if (StringUtils.isBlank(refNo)) {// 必须要有的判断。每一次流水新增，必有有对应流水分组
            throw new BizException("xn000000", "新增流水流水分组不能为空");
        }
        if (transAmount.compareTo(BigDecimal.ZERO) == 0) {
            throw new BizException("xn000000", "新增流水变动金额不能为0");
        }
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.AJour.getCode());

        Jour data = new Jour();
        data.setCode(code);
        data.setKind(EJourKind.BALANCE.getCode());
        data.setPayGroup(payGroup);
        data.setRefNo(refNo);
        data.setChannelOrder(channelOrder);// 内部转账时为空，外部转账时必定有
        data.setAccountNumber(dbAccount.getAccountNumber());
        data.setTransAmount(transAmount);

        data.setUserId(dbAccount.getUserId());
        data.setRealName(dbAccount.getRealName());
        data.setType(dbAccount.getType());
        data.setCurrency(dbAccount.getCurrency());
        data.setBizType(bizType);

        data.setBizNote(bizNote);
        data.setPreAmount(dbAccount.getAmount());
        data.setPostAmount(dbAccount.getAmount().add(transAmount));
        data.setStatus(EJourStatus.todoCheck.getCode());
        data.setRemark("记得对账哦");

        data.setCreateDatetime(new Date());
        data.setWorkDate(DateUtil.dateToStr(new Date(),
            DateUtil.DB_DATE_FORMAT_STRING));
        data.setChannelType(channelType.getCode());
        data.setSystemCode(dbAccount.getSystemCode());
        data.setCompanyCode(dbAccount.getCompanyCode());
        jourDAO.insert(data);
        return code;
    }

    @Override
    public String addFrozenJour(Account dbAccount, EChannelType channelType,
            String channelOrder, String payGroup, String refNo, String bizType,
            String bizNote, BigDecimal transAmount) {
        if (!EChannelType.Offline.getCode().equals(channelType.getCode())
                && !EChannelType.NBZ.getCode().equals(channelType.getCode())) {// 线下和内部帐可为空，线上必须有
            if (StringUtils.isBlank(payGroup)) {// 必须要有的判断。每一次流水新增，必有有对应业务分组
                throw new BizException("xn000000", "新增流水业务分组不能为空");
            }
        }
        if (StringUtils.isBlank(refNo)) {// 必须要有的判断。每一次流水新增，必有有对应流水分组
            throw new BizException("xn000000", "新增流水流水分组不能为空");
        }
        if (transAmount.compareTo(BigDecimal.ZERO) == 0) {
            throw new BizException("xn000000", "新增流水变动金额不能为0");
        }
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.AJour.getCode());

        Jour data = new Jour();
        data.setCode(code);
        data.setKind(EJourKind.FROZEN.getCode());
        data.setPayGroup(payGroup);
        data.setRefNo(refNo);
        data.setChannelOrder(channelOrder);// 内部转账时为空，外部转账时必定有
        data.setAccountNumber(dbAccount.getAccountNumber());
        data.setTransAmount(transAmount);

        data.setUserId(dbAccount.getUserId());
        data.setRealName(dbAccount.getRealName());
        data.setType(dbAccount.getType());
        data.setCurrency(dbAccount.getCurrency());
        data.setBizType(bizType);

        data.setBizNote(bizNote);
        data.setPreAmount(dbAccount.getFrozenAmount());
        data.setPostAmount(dbAccount.getFrozenAmount().add(transAmount));
        data.setStatus(EJourStatus.todoCheck.getCode());
        data.setRemark("记得对账哦");

        data.setCreateDatetime(new Date());
        data.setWorkDate(DateUtil.dateToStr(new Date(),
            DateUtil.DB_DATE_FORMAT_STRING));
        data.setChannelType(channelType.getCode());
        data.setSystemCode(dbAccount.getSystemCode());
        data.setCompanyCode(dbAccount.getCompanyCode());
        jourDAO.insert(data);
        return code;
    }

    @Override
    public String addJourForHL(Account dbAccount, HLOrder order, String bizType) {
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.AJour.getCode());

        Jour data = new Jour();
        data.setCode(code);
        data.setAccountNumber(dbAccount.getAccountNumber());
        data.setUserId(dbAccount.getUserId());
        data.setRealName(dbAccount.getRealName());
        data.setType(dbAccount.getType());
        data.setCurrency(dbAccount.getCurrency());
        data.setChannelType(EChannelType.NBZ.getCode());

        data.setRefNo(order.getCode());
        data.setBizType(bizType);
        data.setBizNote("根据红蓝订单《" + order.getCode() + "》变动资金");
        data.setTransAmount(order.getAmount());
        data.setPreAmount(dbAccount.getAmount());

        data.setPostAmount(dbAccount.getAmount().add(order.getAmount()));
        data.setStatus(EJourStatus.noAdjust.getCode());
        data.setCreateDatetime(new Date());
        data.setWorkDate(null);
        data.setSystemCode(dbAccount.getSystemCode());
        data.setCompanyCode(dbAccount.getCompanyCode());
        jourDAO.insert(data);
        return code;

    }

    @Override
    public void doCheckJour(Jour jour, EBoolean checkResult,
            BigDecimal checkAmount, String checkUser, String checkNote) {
        Jour data = new Jour();
        data.setCode(jour.getCode());
        EJourStatus eJourStatus = EJourStatus.Checked_YES;
        if (EBoolean.NO.equals(checkResult)) {
            eJourStatus = EJourStatus.Checked_NO;
        }
        data.setStatus(eJourStatus.getCode());
        data.setCheckUser(checkUser);
        data.setCheckNote(checkNote + ":调整金额" + checkAmount.toString());
        data.setCheckDatetime(new Date());
        jourDAO.checkJour(data);
    }

    @Override
    public void adjustJourNO(Jour jour, String adjustUser, String adjustNote) {
        Jour data = new Jour();
        data.setCode(jour.getCode());
        data.setStatus(EJourStatus.Checked_YES.getCode());
        data.setAdjustUser(adjustUser);
        data.setAdjustNote(adjustNote);
        data.setAdjustDatetime(new Date());
        jourDAO.adjustJour(data);
    }

    @Override
    public void adjustJourYES(Jour jour, String adjustUser, String adjustNote) {
        Jour data = new Jour();
        data.setCode(jour.getCode());
        data.setStatus(EJourStatus.Adjusted.getCode());
        data.setAdjustUser(adjustUser);
        data.setAdjustNote(adjustNote);
        data.setAdjustDatetime(new Date());
        jourDAO.adjustJour(data);
    }

    @Override
    public Jour getJour(String code, String systemCode) {
        Jour data = null;
        if (StringUtils.isNotBlank(code)) {
            Jour condition = new Jour();
            condition.setCode(code);
            condition.setSystemCode(systemCode);
            data = jourDAO.select(condition);
            if (data == null) {
                throw new BizException("xn000000", "单号不存在");
            }
        }
        return data;
    }

    @Override
    public Jour getJourNotException(String code, String systemCode) {
        Jour data = null;
        if (StringUtils.isNotBlank(code)) {
            Jour condition = new Jour();
            condition.setCode(code);
            condition.setSystemCode(systemCode);
            data = jourDAO.select(condition);
        }
        return data;
    }

    @Override
    public List<Jour> queryJourList(Jour condition) {
        return jourDAO.selectList(condition);
    }

    @Override
    public BigDecimal getTotalAmount(String bizType, String channelType,
            String accountNumber, String dateStart, String dateEnd) {
        Jour jour = new Jour();
        jour.setKind(EJourKind.BALANCE.getCode());
        jour.setBizType(bizType);
        jour.setChannelType(channelType);
        jour.setAccountNumber(accountNumber);
        jour.setCreateDatetimeStart(DateUtil.getFrontDate(dateStart, false));
        jour.setCreateDatetimeEnd(DateUtil.getFrontDate(dateEnd, true));
        BigDecimal a = jourDAO.selectTotalAmount(jour);
        return a;
    }
}
