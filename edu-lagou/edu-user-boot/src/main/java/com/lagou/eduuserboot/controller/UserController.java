package com.lagou.eduuserboot.controller;

import com.lagou.eduuserboot.entity.FileSystem;
import com.lagou.eduuserboot.service.UserService;

import org.csource.common.IniFileReader;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


@RestController
@RequestMapping("userSetting")
@CrossOrigin //跨域
public class UserController {

    @Autowired
    private UserService userService;

    static String fastdfsip = null;
    static{
        Properties props = new Properties();
        InputStream in = IniFileReader.loadFromOsFileSystemOrClasspathAsStream("config/fastdfs-client.properties");
        if (in != null) {
            try {
                props.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fastdfsip = props.getProperty("fastdfs.tracker_servers").split(":")[0];
    }


    @PostMapping("upload")
    @ResponseBody
    public FileSystem upload(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("接收到：" +file);
        FileSystem fs = new FileSystem();

        //获得文件的原始名称
        String oldFileName = file.getOriginalFilename();
        //获得后缀名
        String hou = oldFileName.substring(oldFileName.lastIndexOf(".")+1);

        try {
            //加载配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");

            System.out.println("ip:" + fastdfsip );
            //创建tracker客户端
            TrackerClient tc = new TrackerClient();
            //根据tracker客户端创建连接
            TrackerServer ts = tc.getConnection();
            StorageServer ss = null;
            //定义storage客户端
            StorageClient1 client = new StorageClient1(ts, ss);
            //文件元信息
            NameValuePair[] list = new NameValuePair[1];
            list[0] = new NameValuePair("fileName", oldFileName);
            //上传，返回fileId
            String fileId = client.upload_file1(file.getBytes(), hou, list);
            System.out.println(fileId);
            ts.close();
            //封装数据对象，将路径保存到数据库（本次不写）
            fs.setFileId(fileId);
            fs.setFilePath(fileId);
            fs.setFileName(oldFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fs;
    }

    //修改昵称
    @GetMapping("updateUser")
    public void updateUser(Integer userid,String newName,String fileId) throws UnsupportedEncodingException {
        System.out.println("newName = " + newName);
        fileId = "http://"+fastdfsip+"/"+fileId;
        System.out.println("imgfileId = " + fileId);
        userService.updateUser(userid,newName, fileId);
    }

    //修改密码
    @GetMapping("updatePassword")
    public void updatePassword(Integer userid,String newPwd){
        System.out.println("userid = " + userid);
        System.out.println("newPwd = " + newPwd);
        userService.updatePassword(userid,newPwd);
    }
}

