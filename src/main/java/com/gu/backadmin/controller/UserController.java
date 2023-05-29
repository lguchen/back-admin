package com.gu.backadmin.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gu.backadmin.common.Constants;
import com.gu.backadmin.common.Result;
import com.gu.backadmin.entity.UserDTO;
import com.gu.backadmin.entity.User;
import com.gu.backadmin.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;


/**
 * @Description
 * @Author: luo
 * @Date 2023年01月22日 01:04:05
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public Boolean save(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @GetMapping("/find/{id}")
    public User findById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeByIds(ids);
    }

    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address) {
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        queryWrapper.like("email", email);
        queryWrapper.like("address", address);
        return userService.page(page, queryWrapper);
    }

    /*导出接口*/
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        //从数据库查询出所有数据,如果需要获取其他的数据，就调用其他的方法
        List<User> list = userService.list();
        //通过工具类创建write 写出磁盘路径
        //ExcelWriter writer= ExcelUtil.getWriter(filesUpLoadPth + "/用户信息.xlsx");
        //这里的参数也可以设置绝对路径，本项目实现网页的下载，省略下载路径
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("username", "用户名");//用别名的方法，实现Excel文件的标题是中文的
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("created_time", "创建时间");
        //一次性写出list内部的对象到excel，强制输出标题
        writer.write(list, true);
        String filename = URLEncoder.encode("用户信息", "UTF-8");
        //设置浏览器弹出响应格式,输出xlsx格式，官网也可以查看输出xls格式的方法
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    /*excel导入*/
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception {  //注意方法名不能是import，这是一个关键字
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
        System.out.println(list);
        userService.saveBatch(list);//save是单条插入，saveBatch批量导入
        return true;
    }

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        } else {
            UserDTO dto = userService.login(userDTO);
            return Result.success(dto);
        }
    }
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        User existUser = userService.findByUsername(user.getUsername());
        if (existUser != null) {
            return Result.error();
        }
        userService.register(user);
        return Result.success();
    }
}