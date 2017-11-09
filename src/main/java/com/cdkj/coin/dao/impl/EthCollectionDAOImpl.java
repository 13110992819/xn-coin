package com.cdkj.coin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cdkj.coin.dao.IEthCollectionDAO;
import com.cdkj.coin.dao.base.support.AMybatisTemplate;
import com.cdkj.coin.domain.EthCollection;

@Repository("ethCollectionDAOImpl")
public class EthCollectionDAOImpl extends AMybatisTemplate implements
        IEthCollectionDAO {

    @Override
    public int insert(EthCollection data) {
        return super.insert(NAMESPACE.concat("insert_ethCollection"), data);
    }

    @Override
    public int delete(EthCollection data) {
        return super.delete(NAMESPACE.concat("delete_ethCollection"), data);
    }

    @Override
    public EthCollection select(EthCollection condition) {
        return super.select(NAMESPACE.concat("select_ethCollection"),
            condition, EthCollection.class);
    }

    @Override
    public long selectTotalCount(EthCollection condition) {
        return super.selectTotalCount(
            NAMESPACE.concat("select_ethCollection_count"), condition);
    }

    @Override
    public List<EthCollection> selectList(EthCollection condition) {
        return super.selectList(NAMESPACE.concat("select_ethCollection"),
            condition, EthCollection.class);
    }

    @Override
    public List<EthCollection> selectList(EthCollection condition, int start,
            int count) {
        return super.selectList(NAMESPACE.concat("select_ethCollection"),
            start, count, condition, EthCollection.class);
    }

    @Override
    public int updateNotice(EthCollection data) {
        return super.update(NAMESPACE.concat("update_notice"), data);
    }

}
