package com.coral.mini.program.common.business.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.coral.mini.program.common.business.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.hibernate.annotations.Columns;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "t_file",
        indexes = {
            @Index(columnList = "fileId")
        }
)
@TableName("t_file")
@ApiModel(value = "文件")
public class File extends BaseEntity {


    @ApiModelProperty(value = "文件名称")
    private String name;

    @ApiModelProperty(value = "文件类型")
    private String type;

    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "文件大小")
    private long size;

    @ApiModelProperty(value = "文件url")
    private String url;

    @ApiModelProperty(value = "文件ID")
    @Column(length = 64)
    private String fileId;

    @ApiModelProperty(value = "文件内容")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="content", columnDefinition="longblob", nullable=true)
    private byte[] content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}