package com.alpha.elasticsearch.web;

import com.alpha.common.beans.response.ResultBean;
import com.alpha.elasticsearch.facade.model.SearchUserModel;
import com.alpha.elasticsearch.facade.model.UserModel;
import com.alpha.elasticsearch.facade.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiming.jing on 2020/4/30.
 */
@RequestMapping("/search/user")
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/search")
    public ResultBean search(SearchUserModel searchUserModel){
        try {
            return userService.search(searchUserModel);
        }catch (Exception e){
            logger.error("user search error:{}", e);
        }
        return ResultBean.badRequest();
    }

    @RequestMapping("add")
    public ResultBean add(UserModel userModel){
        try {
            return userService.add(userModel);
        }catch (Exception e){
            logger.error("user add error:{}", e);
        }
        return ResultBean.badRequest();
    }

    @RequestMapping("update")
    public ResultBean update(UserModel userModel){
        try {
            return userService.update(userModel);
        }catch (Exception e){
            logger.error("user udpate error:{}", e);
        }
        return ResultBean.badRequest();
    }

    @RequestMapping("delete")
    public ResultBean delete(UserModel userModel){
        try {
            return userService.delete(userModel);
        }catch (Exception e){
            logger.error("user delete error:{}", e);
        }
        return ResultBean.badRequest();
    }

    @RequestMapping("index/delete")
    public boolean deleteIndex(){
        try {
            return userService.deleteIndex();
        }catch (Exception e){
            logger.error("user index/delete error:{}", e);
        }
        return false;
    }

    @RequestMapping("index/add")
    public boolean indexAdd(){
        try {
            return userService.createIndex(1, 1);
        }catch (Exception e){
            logger.error("user index/add error:{}", e);
        }
        return false;
    }

    @RequestMapping("index/addmapping")
    public boolean indexAddMapping(){
        try {
            return userService.addMapping();
        }catch (Exception e){
            logger.error("user addmapping error:{}", e);
        }
        return false;
    }
}
