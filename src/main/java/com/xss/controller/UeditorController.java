package com.xss.controller;

import com.baidu.ueditor.ActionEnter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author XSS
 * @date 2020/7/24
 * @desc
 */
@RestController
public class UeditorController {

    @Value("${path1}")
    private String path;


    @Value("${realPath}")
    private String realPath;


    @RequestMapping(value = "doUeditor",produces = "text/html;charset=utf-8")
    public String doUeditor(String action, HttpServletRequest request, MultipartFile upfile){
        String result = null;

        if ("config".equals(action)){
            System.out.println("前端请求获取后台配置json对象啦......");

            result = new ActionEnter( request, request.getContextPath() ).exec();
        }else if("uploadimage".equals(action)){
            System.out.println("前端请求ajax图片上传处理啦.......");

            String originalFilename = upfile.getOriginalFilename();
            String type = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID()+type;

            try {
                upfile.transferTo(new File(realPath,fileName));
                Map<String, Object> success = resultMap("SUCCESS", originalFilename, upfile.getSize(), fileName, type, path+fileName);

                result = new JSONObject(success).toString();
            } catch (IOException e) {
                result = new JSONObject(resultMap("FAIL",null,0,null,null,null)).toString();
                e.printStackTrace();
            }

        }else if("uploadfile".equals(action)){
            System.out.println("前端请求ajax文件上传处理啦......");
        }

        return result;
    }

    //{"state": "SUCCESS","original": "111.jpg","size": "124147","title": "1535961757878095151.jpg","type": ".jpg","url": "/1535961757878095151.jpg"}
    private Map<String,Object> resultMap(String state, String original, long size, String title,String type,  String url){
        Map<String ,Object> result = new HashMap<>();
        result.put("state",state);
        result.put("original",original);
        result.put("size",size);
        result.put("title",title);
        result.put("type",type);
        result.put("url", url);
        return result;
    }
}
