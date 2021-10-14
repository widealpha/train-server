package cn.widealpha.train.controller;

import cn.widealpha.train.pojo.dto.ResultEntity;
import cn.widealpha.train.util.StatusCode;
import cn.widealpha.train.service.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JwtUserService userService;

    /**
     * @api {POST} /user/register register
     * @apiVersion 1.0.0
     * @apiGroup User
     * @apiName register
     * @apiDescription 用户注册
     * @apiParam (请求参数) {String} username
     * @apiParam (请求参数) {String} password
     * @apiParamExample 请求参数示例
     * password=root&username=root
     * @apiSuccess (响应结果) {Number} code
     * @apiSuccess (响应结果) {String} message
     * @apiSuccess (响应结果) {Boolean} data
     * @apiSuccessExample {json} 响应结果示例
     * {"code":0,"message":"success","data":"true"}
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    ResultEntity register(@RequestParam String username, @RequestParam String password) {
        StatusCode code = userService.createUser(username, password);
        if (code == StatusCode.SUCCESS){
            return ResultEntity.success();
        }
        return ResultEntity.error(code);
    }

    /**
     * @api {POST} /user/login login
     * @apiVersion 1.0.0
     * @apiGroup User
     * @apiName login
     * @apiDescription 登录
     * @apiParam (请求参数) {String} username
     * @apiParam (请求参数) {String} password
     * @apiParamExample 请求参数示例
     * password=root&username=root
     * @apiSuccess (响应结果) {Number} code
     * @apiSuccess (响应结果) {String} message
     * @apiSuccess (响应结果) {String} data
     * @apiSuccessExample {json} 响应结果示例
     * {"code":0,"message":"success","data":"eyJhbGciOiJIUzI1NiJ9.eyJST0xFIjoiUk9MRV9BRE1JTiIsInN1YiI6InJvb3QiLCJleHAiOjE2MjEzMTQzOTgsImlhdCI6MTYyMDcwOTU5OH0.6dIqBqJ9HkEl9hvU7O-GIJmNkqF1VCf1PGiAUHQSj-c"}
     */
}
