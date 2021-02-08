package com.coral.mini.program.common.business.controller;

import cn.hutool.core.util.StrUtil;
import com.coral.mini.program.common.business.entity.File;
import com.coral.mini.program.common.business.service.FileService;
import com.coral.mini.program.common.common.utils.Base64DecodeMultipartFile;
import com.coral.mini.program.common.common.utils.ResultUtil;
import com.coral.mini.program.common.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author coral
 */
@Slf4j
@RestController
@Api(description = "文件上传接口")
@RequestMapping("/file")
@Transactional
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "文件上传")
    public Result<Object> upload(@RequestParam(required = false) MultipartFile file,
                                 @RequestParam(required = false) String base64,
                                 HttpServletRequest request) {
        File fileEntity = null;
        try {

            if (StrUtil.isNotBlank(base64)) {
                // base64上传
                file = Base64DecodeMultipartFile.base64Convert(base64);
            }


            InputStream inputStream = file.getInputStream();
            String fileName = file.getName();
            //保存至数据库
            fileEntity  = saveFile(fileName, inputStream);

           // fileEntity.setContent(null);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResultUtil<Object>().setErrorMsg(e.toString());
        }

        return new ResultUtil<Object>().setData(fileEntity.getUrl());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "文件服务器")
    public Result<Object> delete(@RequestParam(required = true) String fileId) {
        try {
            fileService.deleteByFileId(fileId);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResultUtil<Object>().setErrorMsg(e.toString());
        }

        return new ResultUtil<Object>().setData("OK");
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiOperation(value = "文件下载")
    public void download(@RequestParam(required = true) String fileId, HttpServletResponse response) {
        try {

           List<File> fileList  = fileService.findByFileId(fileId);
           if (fileList != null && fileList.size() > 0){
               response.setContentType("image/png;name=file.png");
               OutputStream outputStream = response.getOutputStream();
               outputStream.write(fileList.get(0).getContent());
               response.setStatus(200);
           } else {
               response.setStatus(404);
           }

        } catch (Exception e) {
            log.error(e.toString());

        }

    }

    private File saveFile(String fileName, InputStream inputStream) throws IOException {
        File file = new File();
        long fileSize = 0;
        String fileId = UUID.randomUUID().toString();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte []tmp = new byte[1024];
        int readSize = 0;
        while ((readSize = inputStream.read(tmp)) > 0){
            outputStream.write(tmp, 0, readSize);
            fileSize = fileSize + readSize;
        }
        file.setName(fileName);
        file.setFileId(fileId);
        file.setSize(fileSize);
        file.setCreateTime(new Date());
        file.setUpdateTime(new Date());
        file.setType("file");
        file.setUrl("/coral/file/download?fileId=" + fileId);
        file.setContent(outputStream.toByteArray());

        return fileService.save(file);
    }


}
