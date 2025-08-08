package io.subHub.modules.project.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DashboardInfoDTO {
    @ApiModelProperty(value = "SubscriberList")
    List<Map<String, Object>> subscriberList;

    @ApiModelProperty(value = "newWorkList")
    List<Map<String, Object>> newWorkList;

    @ApiModelProperty(value = "TotalMessagesPushed")
    private int totalMessagesPushed;
    @ApiModelProperty(value = "totalSubscribers")
    private int totalSubscribers;
    @ApiModelProperty(value = "totalTwentyFourHoursSubscribe")
    private int totalTwentyFourHoursSubscribe;

    @ApiModelProperty(value = "linkSubscriptionCount")
    private int linkSubscriptionCount;

    @ApiModelProperty(value = "linkSubscriptionPoints")
    private int linkSubscriptionPoints;
}
