package com.zrzhao.mysql.order.service.impl;

import com.zrzhao.mysql.batchinsert.bo.Order;
import com.zrzhao.mysql.configrution.CustomDataSource;
import com.zrzhao.mysql.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author zrzhao
 * @date 2021/3/6
 */
@Service
public class OrderServiceImpl implements OrderService  {

    @Autowired
    private DataSource dataSource;

    @Override
    public Order readOrderByOrderNum(String orderNum) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from t_order");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.print(resultSet.getString(1) + "\t");
                System.out.print(resultSet.getString(2) + "\t");
                System.out.print(resultSet.getString(3) + "\t");
                System.out.print(resultSet.getString(4) + "\t");
                System.out.println(resultSet.getString(5) + "\t");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order getOrderByOrderNum(String orderNum) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from t_order");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.print(resultSet.getString(1) + "\t");
                System.out.print(resultSet.getString(2) + "\t");
                System.out.print(resultSet.getString(3) + "\t");
                System.out.print(resultSet.getString(4) + "\t");
                System.out.println(resultSet.getString(5) + "\t");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
