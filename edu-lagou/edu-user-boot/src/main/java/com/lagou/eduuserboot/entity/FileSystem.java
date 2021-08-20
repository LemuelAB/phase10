package com.lagou.eduuserboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileSystem {
    private String fileId;
    private String filePath;
    private String fileName;
}