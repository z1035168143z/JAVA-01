package com.zrzhao.demo.database;

import com.zrzhao.demo.database.vo.User;
import com.zrzhao.demo.utils.JsonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zrzhao
 * @date 2021/2/9
 */
@RestController
@RequestMapping("/jdbc")
public class JDBCController {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;

    @GetMapping("/user")
    public JsonResult<List<User>> userList() {
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("select * from user");

            List<User> users = new ArrayList<>();
            User user;
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));

                users.add(user);
            }

            return JsonResult.success(users);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return JsonResult.fail(e.getMessage());
        }
    }

    @PutMapping("/user")
    public JsonResult<Object> updateUser(@RequestBody User user) {
        try {
            Statement statement = getStatement();

            String sql = "update user set user_name = " + user.getUserName() + " where id = " + user.getId();
            int i = statement.executeUpdate(sql);

            if (i != 1) {
                System.out.println(sql + ": update row :" + i);
                throw new RuntimeException("update failure");
            }

            return JsonResult.success(null);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return JsonResult.fail(e.getMessage());
        }
    }

    @DeleteMapping("/user/{id}")
    public JsonResult<Object> updateUser(@PathVariable("id") int id) {
        try {
            Statement statement = getStatement();

            String sql = "delete from user where id = " + id;
            int i = statement.executeUpdate(sql);

            if (i != 1) {
                System.out.println(sql + ": delete row :" + i);
                throw new RuntimeException("delete failure");
            }

            return JsonResult.success(null);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return JsonResult.fail(e.getMessage());
        }
    }


    @PostMapping("/user")
    public JsonResult<Object> saveUser(@RequestBody User user) {
        try {
            Statement statement = getStatement();

            String sql = "insert into user (user_name) values ('" + user.getUserName() + "')";
            boolean execute = statement.execute(sql);

            if (execute) {
                System.out.println(sql + ": insert failure");
                throw new RuntimeException("insert failure");
            }

            return JsonResult.success(null);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return JsonResult.fail(e.getMessage());
        }
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, userName, password);
    }

    private Statement getStatement() throws ClassNotFoundException, SQLException {
        return getConnection().createStatement();
    }

}
