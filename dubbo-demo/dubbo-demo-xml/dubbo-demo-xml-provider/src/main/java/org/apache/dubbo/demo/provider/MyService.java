package org.apache.dubbo.demo.provider;

/**
 * @author: liaozhicheng
 * @date: 2019/12/29 0029
 * @since 1.0
 */
public class MyService {

    private int port;

    public int getPort() {
        System.out.println("myService#getPort");
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
