package com.zrzhao.demo.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zrzhao.demo.database.vo.User;
import com.zrzhao.demo.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zrzhao
 * @date 2021/2/9
 */
@RestController
@RequestMapping("/hikari")
public class HikariController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/user")
    public JsonResult<List<User>> userList() {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user");
            ResultSet resultSet = preparedStatement.executeQuery();

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
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @PutMapping("/user")
    public JsonResult<Object> updateUser(@RequestBody User user) {
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = "update user set user_name = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setInt(2, user.getId());

            int i = preparedStatement.executeUpdate();

            if (i != 1) {
                System.out.println(sql + ": update row :" + i);
                throw new RuntimeException("update failure");
            }

            return JsonResult.success(null);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return JsonResult.fail(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @DeleteMapping("/user/{id}")
    public JsonResult<Object> updateUser(@PathVariable("id") int id) {
        try {
            String sql = "delete from user where id = ?";

            PreparedStatement preparedStatement = this.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int i = preparedStatement.executeUpdate();

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
        Connection connection = null;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);

            String sql = "insert into user (user_name) values (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            boolean execute = preparedStatement.execute();

            if (execute) {
                System.out.println(sql + ": insert failure");
                throw new RuntimeException("insert failure");
            }

            connection.commit();
            connection.setAutoCommit(true);
            return JsonResult.success(null);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            return JsonResult.fail(e.getMessage());
        }
    }
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        return connection;
    }

}
