package io.subHub.modules.inviteCode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.subHub.common.service.impl.CrudServiceImpl;
import io.subHub.common.utils.ConvertUtils;
import io.subHub.modules.inviteCode.dao.InviteCodeRecordDao;
import io.subHub.modules.inviteCode.dto.InviteCodeRecordDTO;
import io.subHub.modules.inviteCode.entity.InviteCodeRecordEntity;
import io.subHub.modules.inviteCode.service.InviteCodeRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Invitation Code Record Form
 *
 * @author by 
 * @since 1.0.0 2024-01-22
 */
@Service
public class InviteCodeRecordServiceImpl extends CrudServiceImpl<InviteCodeRecordDao, InviteCodeRecordEntity, InviteCodeRecordDTO> implements InviteCodeRecordService {

    @Override
    public QueryWrapper<InviteCodeRecordEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<InviteCodeRecordEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public InviteCodeRecordEntity getByInviteCode(String code){
        QueryWrapper<InviteCodeRecordEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("invite_code",code);
        return baseDao.selectOne(wrapper);
    }

    @Override
    public InviteCodeRecordDTO getByUserId(Long userId){
        QueryWrapper<InviteCodeRecordEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("use_user_id",userId);
        return ConvertUtils.sourceToTarget(baseDao.selectOne(wrapper), InviteCodeRecordDTO.class);
    }
    @Override
    public void updateByUseUserId(Long useUserId){
        UpdateWrapper<InviteCodeRecordEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("use_user_id",useUserId);
        updateWrapper.set("use_user_id",null);
        updateWrapper.set("use_status",0);
        updateWrapper.set("activation_dt",null);
        baseDao.update(new InviteCodeRecordEntity(),updateWrapper);
    }
}