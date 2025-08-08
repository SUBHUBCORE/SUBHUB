package io.subHub.modules.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.subHub.modules.project.dao.ProjectDao;
import io.subHub.modules.project.dto.ProjectDTO;
import io.subHub.modules.project.service.ProjectService;
import io.subHub.common.service.impl.CrudServiceImpl;
import io.subHub.common.utils.ConvertUtils;
import io.subHub.modules.project.entity.ProjectEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-01-22
 */
@Service
public class ProjectServiceImpl extends CrudServiceImpl<ProjectDao, ProjectEntity, ProjectDTO> implements ProjectService {
    @Autowired
    private ProjectDao projectDao;

    @Override
    public QueryWrapper<ProjectEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<ProjectEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public ProjectDTO getProjectsInfoByUserId(Long user_id){
        QueryWrapper<ProjectEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("belongs_to_id",user_id);
        ProjectEntity project = baseDao.selectOne(wrapper);
        return ConvertUtils.sourceToTarget(project, ProjectDTO.class);
    }

    @Override
    public ProjectDTO getProjectsInfoByName(String name){
        QueryWrapper<ProjectEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        ProjectEntity project = baseDao.selectOne(wrapper);
        return ConvertUtils.sourceToTarget(project, ProjectDTO.class);
    }

    @Override
    public String getNftByUserId(Long userId){
        String nft = projectDao.getNftByUserId(userId);
        return nft;
    }
    @Override
    public ProjectDTO getProjectsInfoByNft(String nft){
        QueryWrapper<ProjectEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("nft",nft);
        ProjectEntity project = baseDao.selectOne(wrapper);
        return ConvertUtils.sourceToTarget(project, ProjectDTO.class);
    }

    @Override
    public Long getProjectCount(){
        QueryWrapper<ProjectEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("certifications",1);
        return baseDao.selectCount(wrapper);
    }

    @Override
    public ProjectDTO getByWalletAddress(String address){
        QueryWrapper<ProjectEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("wallet_address",address);
        ProjectEntity project = baseDao.selectOne(wrapper);
        return ConvertUtils.sourceToTarget(project, ProjectDTO.class);
    }
}