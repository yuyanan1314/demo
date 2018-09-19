package com.htsc.cams.controller;

import com.htsc.cams.model.Test;
import com.htsc.cams.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Swagger 配置
 */
@Api(value = "Swagger Rest风格", description = "配置验证")
@RestController
@RequestMapping("api/swagger")
public class SwaggerTestController {
    @Autowired
    private TestService testService;

    @ApiOperation(value="获取用户列表")
    @GetMapping("/getList")
    public List<Test> getList() {
        List<Test> r = testService.findAll();
        return r;
    }

    @ApiOperation(value="创建用户")
//    @ApiImplicitParam(name = "test", value = "用户详细实体user", required = true, dataType = "Test")
    @PostMapping
    public String save(@ApiParam(value = "用户实体类", required = true) @RequestBody Test test) {
        testService.save(test);
        return "success";
    }

    @ApiOperation(value="修改用户")
//    @ApiImplicitParam(name = "test", value = "用户详细实体user", required = true, dataType = "Test")
    @PutMapping
    public String edit(@ApiParam(value = "用户实体类", required = true) @RequestBody Test test) {
        testService.edit(test);
        return "success";
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
//    @ApiImplicitParam(name = "id",value = "用户Id", required = true, paramType = "query", dataType = "integer")
    @GetMapping("/{id}")
    public Test getOne(@PathVariable Integer id) {
        return testService.findOne(id);
    }


    @ApiOperation(value="删除用户")
//    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "integer")
    @DeleteMapping("/{id}")
    public String delete(@ApiParam(value = "用户Id", required = true) @PathVariable Integer id) {
        testService.delete(id);
        return "success";
    }

}
