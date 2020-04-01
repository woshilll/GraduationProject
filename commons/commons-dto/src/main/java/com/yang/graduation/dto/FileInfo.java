package com.yang.graduation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/2 00:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
    /**
     * 文件路径
     */
    private String path;
}
