package io.subHub.modules.annex.controller;

import io.subHub.common.AwsS3.Awss3FileUploadResultDTO;
import io.subHub.common.AwsS3.Awss3FileUtil;
import io.subHub.common.configBean.ConfigBean;
import io.subHub.modules.annex.dto.ImgUploadDTO;
import io.subHub.common.utils.Result;
import io.subHub.common.validator.ValidatorUtils;
import io.subHub.common.validator.group.AddGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("annex/file")
@Api(tags="annexUpload")
public class AnnexFileController {

    @Resource
    private ConfigBean configBean;

    @PostMapping("uploadImg")
    @ApiOperation("uploadImg")
    public Result uploadImg(@RequestBody ImgUploadDTO dto){
        //Validation data
        ValidatorUtils.validateEntity(dto, AddGroup.class);
        Awss3FileUploadResultDTO AwsResultDTO = Awss3FileUtil.uploadBase64(dto.getContent(),configBean);
        if (AwsResultDTO == null){
            return new Result().error("Upload error");
        }
//        return UploadBase64Util.uploadImgBase64(dto);
        return new Result().ok(AwsResultDTO.getFileUrl());
    }
}
