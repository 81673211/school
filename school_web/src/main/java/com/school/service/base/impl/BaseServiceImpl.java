package com.school.service.base.impl;


import com.school.exception.ExpressStatusException;
import com.school.service.base.BaseService;
import com.school.vo.BaseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>Title: BaseServiceImpl</p>
 * <p>Description: Service基础实现类，实现单表的增删该查</p>
 *
 * @param <M> 表对应的实体
 * @param <T> 表对应的mapper接口（xxMapper.xml中的命名空间）
 * @author liliang
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BaseServiceImpl<M, T> implements BaseService<M, T> {

    /**
     * 将vo对象转换为可持久化po对象
     *
     * @param vo
     * @return
     */
    protected M converterVo2Po(BaseVo vo, Class<M> po) throws IllegalAccessException, InstantiationException {
        M m = po.newInstance();
        BeanUtils.copyProperties(vo, m);
        return m;
    }

    /**
     * 将可持久化po对象转换为vo对象
     *
     * @param vo
     * @return
     */
    protected <T extends BaseVo> T converterPo2Vo(M po, T vo) throws IllegalAccessException, InstantiationException {
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

    /**
     * 快件状态检查，检查状态是否遵循定义的流程，如果按照非流程处理则抛出异常
     * 比如：修改快件状态时，需要去提前检查快件当前状态是否满足修改的条件。
     *
     * @param expressId     快件id
     * @param expressType   快件类型，0寄件，1收件
     * @param forward       检查方向：true（以expressStatus值向前检查），false（以expressStatus值向后检查）
     * @param expressStatus 将要作为检查的状态
     */
    protected void expressStatusCheck(Long expressId, int expressType, boolean forward, int expressStatus) throws ExpressStatusException {
        try {
            //todo
            return;
        } catch (Exception e) {
            throw new ExpressStatusException("快件类型:" + expressType + ",快件id:" + expressId + ",forward:" + forward + ",快件状态:" + expressStatus);
        }
    }
}
