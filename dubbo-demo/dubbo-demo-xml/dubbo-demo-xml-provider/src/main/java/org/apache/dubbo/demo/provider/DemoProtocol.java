package org.apache.dubbo.demo.provider;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.RpcException;

/**
 * @author: liaozhicheng
 * @date: 2019/12/29 0029
 * @since 1.0
 */
public class DemoProtocol implements Protocol {

    /**
     * 本次要验证的就是这个 MyService 对象的实例能够自动注入
     * 注：MyService 实例是在 spring 容器中管理的
     */
    private MyService myService;

    /**
     * 注：dubbo SPI IOC 特性，该 set 方法不能少
     * @param myService
     */
    public void setMyService(MyService myService) {
        this.myService = myService;
    }

    /**
     * 调用 myService 对象上的方法
     * @return
     */
    @Override
    public int getDefaultPort() {
        return myService.getPort();
    }

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        return null;
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        return null;
    }

    @Override
    public void destroy() {

    }
}
