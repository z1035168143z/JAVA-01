package io.zrzhao.rpcregister;

import io.zrzhao.rpcregister.server.RegisterServer;

public class RpcRegisterApplication {

    public static void main(String[] args) {
        try {
            new RegisterServer(8080).start();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
