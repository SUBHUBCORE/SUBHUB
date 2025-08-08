package io.subHub.modules.rssMedium.controller;


import io.subHub.modules.rssMedium.dto.RssMediumDTO;
import io.subHub.modules.rssMedium.utils.RssMediumDataSyncUtil;
import io.subHub.common.constant.CommonConstant;
import io.subHub.common.constant.Constant;
import io.subHub.common.page.PageData;
import io.subHub.common.redis.RedisUtils;
import io.subHub.common.utils.Result;
import io.subHub.modules.project.dto.ProjectDTO;
import io.subHub.modules.project.service.ProjectService;
import io.subHub.modules.rssMedium.service.RssMediumService;
import io.subHub.modules.security.user.SecurityUser;
import io.subHub.modules.security.user.UserDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.*;


@RestController
@RequestMapping("rss/medium")
@Api(tags="RssMedium")
public class RssMediumController {
    private Logger logger = LoggerFactory.getLogger(RssMediumController.class);


    @Resource
    private ProjectService projectsService;
    @Resource
    private RssMediumService rssMediumService;
    @Resource
    private RedisUtils redisUtils;


    @GetMapping("page")
    @ApiOperation("PageList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "Current page number, starting from 1", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "Number of records displayed per page", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "sort field", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = Constant.ORDER, value = "Sorting method, optional values (asc, desc)", paramType = "query", dataType="String")
    })
    public Result<PageData<RssMediumDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        UserDetail user = SecurityUser.getUser();
        if(user == null || (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getAddress()))){
            return new Result().error(-1);
        }

        ProjectDTO project = projectsService.getProjectsInfoByUserId(user.getId());
        if (project == null){
            return new Result().error("Project does not exist");
        }
        params.put("projectId",project.getId());
        if (StringUtils.isEmpty((String)params.get(Constant.ORDER_FIELD))){
            params.put(Constant.ORDER_FIELD,"content_pub_date");
        }
        if (StringUtils.isEmpty((String)params.get(Constant.ORDER))){
            params.put(Constant.ORDER,"desc");
        }
        PageData<RssMediumDTO> page = rssMediumService.page(params);
        return new Result<PageData<RssMediumDTO>>().ok(page);
    }

    @GetMapping("sync")
    @ApiOperation("sync")
    public Result sync(){
        UserDetail user = SecurityUser.getUser();
        if(user == null || (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getAddress()))){
            return new Result().error(-1);
        }
        ProjectDTO project = projectsService.getProjectsInfoByUserId(user.getId());
        if (project == null){
            return new Result().error("Project does not exist");
        }
        if (project.getCertifications() != 1 && project.getCertifications() != 5){
            return new Result().error(401,"Review failed");
        }

        if (StringUtils.isEmpty(project.getMedium()) || project.getMedium().indexOf("@") == -1){
            return new Result().error(506);
        }
        if (StringUtils.isEmpty(project.getMedium())){
            return new Result().error(506);
        }
        Object rssMedium = redisUtils.get(CommonConstant.RSS_MEDIUM+"_"+project.getId());
        if (rssMedium != null){
            return new Result().error(501,"Medium sync too frequent. Access paused for 5 minutes.");
        }
        Long redMediumCount = redisUtils.incrementCounter(CommonConstant.RSS_MEDIUM+"_count_"+project.getId());
        if(redMediumCount == 1){
            redisUtils.expire(CommonConstant.RSS_MEDIUM+"_count_"+project.getId(),10L);
        }
        if (redMediumCount != null && redMediumCount > 5) {
            redisUtils.set(CommonConstant.RSS_MEDIUM+"_"+project.getId(),1,redisUtils.MIN_FIVE_EXPIRE);
            redisUtils.delete(CommonConstant.RSS_MEDIUM+"_count_"+project.getId());
            return new Result().error(501,"Medium sync too frequent. Access paused for 5 minutes.");
        }
        String mediumName = project.getMedium().substring(project.getMedium().indexOf("@") + 1);
        Result result = RssMediumDataSyncUtil.dataSync(project.getId(),mediumName,rssMediumService);
        return result;
    }

}
