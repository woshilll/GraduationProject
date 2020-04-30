package com.yang.graduation.oss.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.yang.graduation.commons.secret.AliSecret;
import com.yang.graduation.dto.FileInfo;
import com.yang.graduation.dto.ResponseResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传controller
 *
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/2 00:28
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    /**
     * 文件上传
     *
     * @param multipartFile @{code MultipartFile}
     * @return {@link ResponseResult<FileInfo>} 文件上传路径
     */
    @PostMapping(value = "")
    public ResponseResult<FileInfo> upload(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newName = UUID.randomUUID() + "." + suffix;
        OSS client = new OSSClientBuilder().build(AliSecret.OSS_ENDPOINT.getValue(), AliSecret.ACCESS_KEY_ID.getValue(), AliSecret.ACCESS_KEY_SECRET.getValue());
        try {
            client.putObject(new PutObjectRequest(AliSecret.OSS_BUCKET_NAME.getValue(), newName, new ByteArrayInputStream(multipartFile.getBytes())));
            // 上传文件路径 = http://BUCKET_NAME.ENDPOINT/自定义路径/fileName
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "文件上传成功", new FileInfo("http://" + AliSecret.OSS_BUCKET_NAME.getValue() + "." + AliSecret.OSS_ENDPOINT.getValue() + "/" + newName));
        } catch (IOException e) {
            return new ResponseResult<>(ResponseResult.CodeStatus.UPLOAD_FAIL, "文件上传失败，请重试");
        } finally {
            client.shutdown();
        }
    }

}
