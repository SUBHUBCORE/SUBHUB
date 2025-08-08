package io.subHub.modules.subscribeClassify.controller;

import io.subHub.modules.subscribeClassify.dto.SubscribeClassifyDTO;
import io.subHub.common.utils.Result;
import io.subHub.modules.subscribeClassify.service.SubscribeClassifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * SubscribeClassify
 *
 * @author by 
 * @since 1.0.0 2024-02-22
 */
@RestController
@RequestMapping("subscribeClassify")
@Api(tags="SubscribeClassify")
public class SubscribeClassifyController {
    @Autowired
    private SubscribeClassifyService subscribeClassifyService;


    @GetMapping("getAllSubscribeClassify")
    @ApiOperation("GetAllSubscribeClassify")
    public Result<List<SubscribeClassifyDTO>> getAllSubscribeClassify(){
        List<SubscribeClassifyDTO> dataList = subscribeClassifyService.getAllSubscribeClassify();
        return new Result<List<SubscribeClassifyDTO>>().ok(dataList);
    }
}
