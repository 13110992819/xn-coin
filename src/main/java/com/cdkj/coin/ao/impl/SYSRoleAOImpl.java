package com.cdkj.coin.ao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.coin.ao.ISYSRoleAO;
import com.cdkj.coin.bo.ISYSMenuRoleBO;
import com.cdkj.coin.bo.ISYSRoleBO;
import com.cdkj.coin.bo.IUserBO;
import com.cdkj.coin.bo.base.Paginable;
import com.cdkj.coin.domain.SYSRole;
import com.cdkj.coin.domain.User;
import com.cdkj.coin.exception.BizException;

@Service
public class SYSRoleAOImpl implements ISYSRoleAO {

    @Autowired
    ISYSRoleBO sysRoleBO;

    @Autowired
    IUserBO userBO;

    @Autowired
    ISYSMenuRoleBO sysMenuRoleBO;

    @Override
    public String addSYSRole(SYSRole data) {
        if (data != null) {
            sysRoleBO.saveSYSRole(data);
        } else {
            throw new BizException("lh4000", "角色编号已经存在！");
        }
        return data.getCode();
    }

    @Override
    @Transactional
    public boolean dropSYSRole(String roleCode) {
        if (!sysRoleBO.isSYSRoleExist(roleCode)) {
            throw new BizException("lh4000", "角色编号不存在！");
        }
        User condition = new User();
        condition.setRoleCode(roleCode);
        List<User> list = userBO.queryUserList(condition);
        if (!CollectionUtils.sizeIsEmpty(list)) {
            throw new BizException("lh4000", "该角色已在使用，无法删除！");
        }
        // 删除角色和角色菜单关联表
        sysRoleBO.removeSYSRole(roleCode);
        sysMenuRoleBO.removeSYSMenuList(roleCode);
        return true;
    }

    @Override
    public boolean editSYSRole(SYSRole data) {
        if (data != null && sysRoleBO.isSYSRoleExist(data.getCode())) {
            sysRoleBO.refreshSYSRole(data);
        } else {
            throw new BizException("lh4000", "角色编号不存在！");
        }
        return true;
    }

    @Override
    public List<SYSRole> querySYSRoleList(SYSRole condition) {
        return sysRoleBO.querySYSRoleList(condition);
    }

    @Override
    public Paginable<SYSRole> querySYSRolePage(int start, int limit,
            SYSRole condition) {
        return sysRoleBO.getPaginable(start, limit, condition);
    }

    /** 
     * @see com.std.user.ao.ISYSRoleAO#getSYSRole(java.lang.String)
     */
    @Override
    public SYSRole getSYSRole(String code) {
        if (!sysRoleBO.isSYSRoleExist(code)) {
            throw new BizException("lh4000", "角色编号不存在！");
        }
        return sysRoleBO.getSYSRole(code);
    }
}
