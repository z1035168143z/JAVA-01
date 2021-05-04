package io.zrzhao.shardingproxydemo.order.entity;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zrzhao
 * @date 2021/5/4
 */
@Data
@ToString
public class Order {

    private String orderNo;
    private Integer ownerId;
    private Integer productId;
    private Integer receiptAddressId;
    private BigDecimal orderAmount;
    private String status;
    private Long orderPayTime;
    private Integer deleteStatus;
    private Integer orderSnapshot;
    private Integer versionId;
    private Date createTime;
    private Date updateTime;

}
