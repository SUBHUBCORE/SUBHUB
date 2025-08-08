package io.subHub.modules.subscription.controller;

import io.subHub.modules.project.dto.ProjectDTO;
import io.subHub.modules.project.service.ProjectService;
import io.subHub.modules.security.user.SecurityUser;
import io.subHub.modules.security.user.UserDetail;
import io.subHub.common.constant.Constant;
import io.subHub.common.page.PageData;
import io.subHub.common.utils.Result;
import io.subHub.modules.subscription.dto.SubscriptionDTO;
import io.subHub.modules.subscription.service.SubscriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import java.util.Map;


/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-01-24
 */
@RestController
@RequestMapping("subscription")
@Api(tags="Subscriber Management")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private ProjectService projectsService;

    @GetMapping("page")
    @ApiOperation("Page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "Current page number, starting from 1", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "Number of records displayed per page", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "sort field", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = Constant.ORDER, value = "Sorting method, optional values (asc, desc)", paramType = "query", dataType="String")
    })
    public Result<PageData<SubscriptionDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        UserDetail user = SecurityUser.getUser();
        if(user == null || (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getAddress()))){
            return new Result().error(-1);
        }
        ProjectDTO project = projectsService.getProjectsInfoByUserId(user.getId());
        if (project == null){
            return new Result().error("Project does not exist");
        }
        params.put("projectId",project.getId());
        PageData<SubscriptionDTO> page = subscriptionService.page(params);
        return new Result<PageData<SubscriptionDTO>>().ok(page);
    }
}
