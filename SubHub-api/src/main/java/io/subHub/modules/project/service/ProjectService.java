package io.subHub.modules.project.service;

import io.subHub.modules.project.dto.ProjectDTO;
import io.subHub.common.service.CrudService;
import io.subHub.modules.project.entity.ProjectEntity;
/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-01-22
 */
public interface ProjectService extends CrudService<ProjectEntity, ProjectDTO> {

    ProjectDTO getProjectsInfoByUserId(Long user_id);

    ProjectDTO getProjectsInfoByName(String name);

    ProjectDTO getProjectsInfoByNft(String name);

    String getNftByUserId(Long userId);

    Long getProjectCount();

    ProjectDTO getByWalletAddress(String address);

}