package io.zrzhao.rpcprovider.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author zrzhao
 * @date 2021/3/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private Long userId;

    private String uerName;

}
