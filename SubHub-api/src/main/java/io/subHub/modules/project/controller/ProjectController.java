package io.subHub.modules.project.controller;

import io.subHub.modules.inviteCode.dto.InviteCodeRecordDTO;
import io.subHub.modules.project.dto.DashboardInfoDTO;
import io.subHub.modules.project.dto.ProjectDTO;
import io.subHub.modules.project.util.CreatePidUtil;
import io.subHub.modules.security.user.SecurityUser;
import io.subHub.modules.security.user.UserDetail;
import io.subHub.common.configBean.ConfigBean;
import io.subHub.common.constant.CommonConstant;
import io.subHub.common.redis.RedisUtils;
import io.subHub.common.utils.ConvertUtils;
import io.subHub.common.utils.CreateUuidUtil;
import io.subHub.common.utils.Result;
import io.subHub.common.validator.ValidatorUtils;
import io.subHub.common.validator.group.AddGroup;
import io.subHub.common.validator.group.DefaultGroup;
import io.subHub.common.validator.group.UpdateGroup;
import io.subHub.modules.inviteCode.service.InviteCodeRecordService;
import io.subHub.modules.project.service.ProjectService;
import io.subHub.modules.subscription.dto.SubscriptionDTO;
import io.subHub.modules.subscription.service.SubscriptionService;
import io.subHub.modules.subscription.util.*;
import io.subHub.modules.walletsAgent.dto.WalletsAgentDTO;
import io.subHub.modules.walletsAgent.entity.WalletsAgentEntity;
import io.subHub.modules.walletsAgent.service.WalletsAgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


/**
 * 
 *
 * @author by 
 * @since 1.0.0 2024-01-22
 */
@RestController
@RequestMapping("project")
@Api(tags="Project")
public class ProjectController {
    private Logger logger = LoggerFactory.getLogger(ProjectController.class);
    @Resource
    private ProjectService projectsService;
    @Resource
    private SubscriptionService subscriptionService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ConfigBean configBean;
    @Resource
    private WalletsAgentService walletsAgentService;
    @Resource
    public InviteCodeRecordService inviteCodeRecordService;


    @GetMapping("getProjectInfo")
    @ApiOperation("GetProjectInfo")
    public Result<ProjectDTO> getProjectInfo(){
        UserDetail user = SecurityUser.getUser();
        if(user == null || (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getAddress()))){
            return new Result().error(-1);
        }
        ProjectDTO project = projectsService.getProjectsInfoByUserId(user.getId());
        return new Result<ProjectDTO>().ok(project);
    }

    @GetMapping("getSubscriberByDay/{day}")
    @ApiOperation("GetSubscriberByDay")
    public Result<DashboardInfoDTO> getSubscriberByDay(@PathVariable("day") int day){
        if (day<=0){
            return new Result().error("Parameter abnormality");
        }
        if (day>90){
            return new Result().error("Cannot exceed 90 days");
        }
        UserDetail user = SecurityUser.getUser();
        if(user == null || (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getAddress()))){
            return new Result().error(-1);
        }

        ProjectDTO project = projectsService.getProjectsInfoByUserId(user.getId());
        if (project == null ){
            return new Result().error("Project does not exist");
        }
        Object dashboardRedis= redisUtils.get(CommonConstant.DASHBOARD_DATA+"_"+project.getId());
        if (dashboardRedis !=null){
            DashboardInfoDTO dashboard = ConvertUtils.sourceToTarget(dashboardRedis, DashboardInfoDTO.class);
            if (dashboard!=null){
                return new Result<DashboardInfoDTO>().ok(dashboard);
            }
        }
        DashboardInfoDTO dashboardInfoDTO = new DashboardInfoDTO();
        try {
            ExecutorService executor = Executors.newFixedThreadPool(5); // Create a thread pool
            Future<Integer> messCount = executor.submit(new QueryMessageCountThread(messageService,project.getId())); // Submit Task
            Future<Integer> totalSubscribers = executor.submit(new QuerySubCountThread(subscriptionService,project.getId())); // Submit Task
            Future<Integer> totalTwentyFourHoursSubscribe = executor.submit(new QueryThisDaySubCountTread(subscriptionService,project.getId())); // Submit Task
            Future<Integer> linkSubscriptionCount = executor.submit(new QueryInviteSubCountThread(subscriptionService,project.getId())); // Submit Task
            Future<List<Map<String, Object>>> subscriberCountDayList = executor.submit(new QuerySubCountByDayThread(subscriptionService,project.getId(),day)); // Submit Task
            Future<List<Map<String, Object>>> networkCountList = executor.submit(new QuerySubNetworkCountThread(subscriptionService,project.getId())); // Submit Task

            dashboardInfoDTO.setTotalMessagesPushed(messCount.get());
            dashboardInfoDTO.setTotalSubscribers(totalSubscribers.get());
            dashboardInfoDTO.setTotalTwentyFourHoursSubscribe(totalTwentyFourHoursSubscribe.get());
            dashboardInfoDTO.setLinkSubscriptionCount(linkSubscriptionCount.get());
            dashboardInfoDTO.setLinkSubscriptionPoints(dashboardInfoDTO.getLinkSubscriptionCount()*100);
            dashboardInfoDTO.setSubscriberList(subscriberCountDayList.get());
            dashboardInfoDTO.setNewWorkList(networkCountList.get());
            executor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        redisUtils.set(CommonConstant.DASHBOARD_DATA+"_"+project.getId(),dashboardInfoDTO,redisUtils.MIN_FIVE_EXPIRE);
        return new Result<DashboardInfoDTO>().ok(dashboardInfoDTO);
    }


    @PostMapping("createProfile")
    @ApiOperation("createProfile")
    @Transactional(rollbackFor = Exception.class)
    public Result saveProfile(@RequestBody ProjectDTO dto){
        //Validation data
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
        UserDetail user = SecurityUser.getUser();
        if(user == null || (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getAddress()))){
            return new Result().error(-1);
        }
        UserDTO userEntity = usersService.get(user.getId());
        if (userEntity == null){
            return new Result().error(-1);
        }
        ProjectDTO projectDTO = projectsService.getProjectsInfoByName(dto.getName());
        if (projectDTO!=null){
            return new Result().error("The project name is already in use");
        }
        ProjectDTO project = projectsService.getProjectsInfoByUserId(userEntity.getId());
        if (project!=null){
            return new Result().error("You have created the project");
        }
        Object twitterUrl = redisUtils.get("twitter-"+user.getId());
        Object twitterId = redisUtils.get("twitter_id_"+user.getId());
        if (twitterUrl == null || twitterId == null){
            return new Result().error("twitter verify cache failed,Please revalidate Twitter");
        }
        dto.setHashedPassword(CreateUuidUtil.get32UUID());
        dto.setTwitter(String.valueOf(twitterUrl));
        dto.setTwitterId(String.valueOf(twitterId));
        dto.setBelongsToId(user.getId());
        dto.setCertifications(3);
        dto.setNft(null);
        dto.setWalletAddress(null);
        dto.setUpdateDt(new Date());
        InviteCodeRecordDTO inviteCodeRecord = inviteCodeRecordService.getByUserId(userEntity.getId());
        if (inviteCodeRecord!=null){
            inviteCodeRecord.setUseStatus(1);
            inviteCodeRecord.setActivationDt(new Date());
            inviteCodeRecord.setUpdateDt(new Date());
            inviteCodeRecordService.update(inviteCodeRecord);
        }
        userEntity.setInviteCode(inviteCodeRecord.getInviteCode());
        userEntity.setUpdateDt(new Date());
        dto.setInviteCode(inviteCodeRecord.getInviteCode());
        projectsService.save(dto);
        usersService.update(userEntity);

        return new Result();
    }

    @PostMapping("upProfile")
    @ApiOperation("UpProfile")
    @Transactional(rollbackFor = Exception.class)
    public Result upProfile(@RequestBody ProjectDTO dto){
        //Validation data
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        UserDetail user = SecurityUser.getUser();
        if(user == null || (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getAddress()))){
            return new Result().error(-1);
        }
        ProjectDTO entity = projectsService.getProjectsInfoByUserId(user.getId());
        if (entity == null ){
            return new Result().error("Project does not exist");
        }
        if(StringUtils.isNotEmpty(entity.getName())){
            if (!entity.getName().equals(dto.getName())){
                ProjectDTO projectDTO = projectsService.getProjectsInfoByName(dto.getName());
                if (projectDTO!=null){
                    return new Result().error("The project name is already in use");
                }
            }
            entity.setName(dto.getName());
        }
        if (StringUtils.isNotEmpty(dto.getLogo())){
            if (!dto.getLogo().equals(entity.getLogo())){
                WalletsAgentEntity walletsAgent = walletsAgentService.getUserPidByAddress(entity.getWalletAddress());
                if (walletsAgent!=null){
                    Result web3Result = ProjectLogoToWeb3Util.logoToWeb3(walletsAgent.getDstAddress(),dto.getLogo());
                    if (web3Result.getCode() !=0){
                        return new Result().error(web3Result.getMsg());
                    }
                }
            }
            entity.setLogo(dto.getLogo());
        }
        if(StringUtils.isNotEmpty(dto.getType())){
            entity.setType(dto.getType());
        }
        if (StringUtils.isNotEmpty(dto.getDescription())){
            entity.setDescription(dto.getDescription());
        }
        if (StringUtils.isNotEmpty(dto.getWebsite())){
            entity.setWebsite(dto.getWebsite());
        }
        Object twitterUrl = redisUtils.get("twitter-"+user.getId());
        Object twitterId = redisUtils.get("twitter_id_"+user.getId());
        if (twitterUrl != null && twitterId != null){
            entity.setTwitter(String.valueOf(twitterUrl));
            entity.setTwitterId(String.valueOf(twitterId));
        }
        if (StringUtils.isNotEmpty(dto.getDiscord())){
            entity.setDiscord(dto.getDiscord());
        }
        if (StringUtils.isNotEmpty(dto.getTelegram())){
            entity.setTelegram(dto.getTelegram());
        }
        if (StringUtils.isNotEmpty(dto.getMedium())){
            entity.setMedium(dto.getMedium());
        }
        if (StringUtils.isNotEmpty(dto.getCustomProperties())){
            entity.setCustomProperties(dto.getCustomProperties());
        }
        entity.setUpdateDt(new Date());
        projectsService.update(entity);
//        ProjectDTO projectDTO = new ProjectDTO();
//        projectDTO.setId(entity.getId());
//        projectDTO.setCustomProperties(dto.getCustomProperties());
//        projectDTO.setDescription(dto.getDescription());
//        projectDTO.setDiscord(dto.getDiscord());
//        projectDTO.setLogo(dto.getLogo());
//        projectDTO.setName(dto.getName());
//        projectDTO.setTelegram(dto.getTelegram());
//        if (StringUtils.isNotEmpty(dto.getMedium())){
//            projectDTO.setMedium(dto.getMedium());
//        }
//
//
//        projectDTO.setWebsite(dto.getWebsite());
//        projectDTO.setUpdateDt(new Date());
//        projectsService.update(projectDTO);
        return new Result();
    }

    @PostMapping("upAccount")
    @ApiOperation("UpAccount")
    public Result upAccount(@RequestBody ProjectDTO dto){
        if (StringUtils.isEmpty(dto.getNft())){
            return new Result().error("nft cannot be empty");
        }
        UserDetail user = SecurityUser.getUser();
        if(user == null || (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getAddress()))){
            return new Result().error(-1);
        }
        String walletAddress = "";
        if (user.getUserType() > 0){
            walletAddress = user.getAddress();
            if (StringUtils.isEmpty(dto.getEmail())){
                return new Result().error("email cannot be empty");
            }
            if (StringUtils.isEmpty(dto.getEmailVerifyCode())){
                return new Result().error("email Verify code cannot be empty");
            }
            Object code = redisUtils.get(CommonConstant.ACCOUNT_BIND_EMAIL_CODE+"_"+dto.getEmail());
            if (code == null){
                return new Result().error("The verification code has expired");
            }
            if (!code.equals(dto.getEmailVerifyCode())){
                return new Result().error("Verification code error");
            }
            UserDTO userDTO = usersService.get(user.getId());
            userDTO.setEmail(dto.getEmail());
            userDTO.setUpdateDt(new Date());
            usersService.update(userDTO);
        }else {
            if (StringUtils.isEmpty(dto.getWalletAddress())){
                return new Result().error("walletAddress cannot be empty");
            }
            walletAddress = dto.getWalletAddress();
        }
        ProjectDTO projectDTO = projectsService.getByWalletAddress(walletAddress);
        if (projectDTO!=null){
            return new Result().error("The current wallet address is bound");
        }
        projectDTO = projectsService.getProjectsInfoByUserId(user.getId());
        if (projectDTO == null){
            return new Result().error("Project does not exist");
        }
        ProjectDTO projectNft = projectsService.getProjectsInfoByNft(dto.getNft());
        if (StringUtils.isNotEmpty(projectDTO.getWalletAddress())){
            return new Result().error("The walletAddress cannot be modified");
        }
        if (StringUtils.isNotEmpty(projectDTO.getNft())){
            return new Result().error("The NFT cannot be modified");
        }
        String pid = CreatePidUtil.createPidByAddress(walletAddress,configBean);
        if (pid == null){
            return new Result().error("create pid with theme failed");
        }
        WalletsAgentDTO walletsAgentDTO = null;
        try {
            WalletsAgentEntity walletsAgent = walletsAgentService.getUserPidByAddress(walletAddress);
            if (walletsAgent == null){
                walletsAgentDTO = new WalletsAgentDTO();
                walletsAgentDTO.setWalletName("Metamask");
                walletsAgentDTO.setSrcAddress(walletAddress);
                walletsAgentDTO.setDstAddress(pid);
                walletsAgentDTO.setCreateTime(new Date());
                walletsAgentService.save(walletsAgentDTO);
            }
            projectDTO.setWalletAddress(walletAddress);
            projectDTO.setNft(dto.getNft());
            projectDTO.setPid(pid);
            projectsService.update(projectDTO);

        }catch (Exception e){
            if (walletsAgentDTO!=null){
                walletsAgentService.deleteById(walletsAgentDTO.getId());
            }
            projectDTO.setWalletAddress("");
            projectDTO.setNft("");
            projectsService.update(projectDTO);
            return new Result().error();
        }
        return new Result();
    }


    @GetMapping("external/ProjectInfo/{nftName}")
    @ApiOperation("External GetProjectInfo")
    public Result<ProjectDTO> externalProjectInfo(@PathVariable("nftName") String nftName){
        if (StringUtils.isBlank(nftName)){
            return new Result().error("Parameter error");
        }
        ProjectDTO project = projectsService.getProjectsInfoByNft(nftName);
        return new Result<ProjectDTO>().ok(project);
    }

    @PostMapping("external/ProjectSubscribe")
    @ApiOperation("External ProjectSubscribe")
    public Result projectSubscribe(@ApiIgnore @RequestBody Map<String, Object> params){
        String address = (String) params.get("address");
        String appId = (String) params.get("appId");
        String networkName = (String) params.get("networkName");
        if (StringUtils.isBlank(address) || StringUtils.isBlank(appId) || StringUtils.isBlank(networkName)){
            return new Result().error("Parameter abnormality");
        }
        SubscriptionDTO dto = subscriptionService.getSubscriptionByAddressAndProjectId(params);
        if (dto == null){
            dto = new SubscriptionDTO();
            dto.setBelongsToId(Integer.parseInt(appId));
            dto.setAddress(address);
            dto.setNetworkName(networkName);
            dto.setSubscribeSource(1);
            dto.setSubscribeStatus(0);
            dto.setCreateAt(new Date());
            subscriptionService.save(dto);
        }else if (dto.getSubscribeStatus() == 1){
            dto.setSubscribeStatus(1);
            dto.setSubscribeStatus(0);
            dto.setCreateAt(new Date());
            subscriptionService.update(dto);
        }else if(dto.getSubscribeStatus() == 0){
            return new Result().error("You have already subscribed to this project");
        }
        return new Result();
    }
}
