/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.common.extension.factory;

import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.ExtensionFactory;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AdaptiveExtensionFactory
 *
 * 所有 {@link ExtensionLoader} 实例默认的 bean factory
 *
 * {@link ExtensionFactory} 接口有三个实现类
 * 1、{@link AdaptiveExtensionFactory} 其他 {@link ExtensionFactory} 的总管，其中 {@link AdaptiveExtensionFactory#factories} 属性中包含了其他所有的实现
 *  1.1、目前来看 factories 中包含 SpiExtensionFactory & SpringExtensionFactory
 * 2、{@link SpiExtensionFactory} 从 {@link org.apache.dubbo.common.extension.SPI} 所有扩展点实现类中加载实现类对象
 * 3、{@link org.apache.dubbo.config.spring.extension.SpringExtensionFactory} 从 spring 容器中，根据 bean name 加载依赖对象
 *  3.1、dubbo SPI 实例化类能够完成自动依赖注入，其和 spring 容器的无缝对接，就是通过该类来实现 -- 漂亮的设计...
 *
 * {@link #getExtension} 方法返回就是指定名称的扩展点的实现，如果是从 spring 容器中加载的话，就是返回指定 name 的 spring bean
 *
 */
@Adaptive
public class AdaptiveExtensionFactory implements ExtensionFactory {

    private final List<ExtensionFactory> factories;

    public AdaptiveExtensionFactory() {
        // 返回的就是 AdaptiveExtensionFactory 自身的对象
        ExtensionLoader<ExtensionFactory> loader = ExtensionLoader.getExtensionLoader(ExtensionFactory.class);
        List<ExtensionFactory> list = new ArrayList<ExtensionFactory>();
        for (String name : loader.getSupportedExtensions()) {
            list.add(loader.getExtension(name));
        }
        factories = Collections.unmodifiableList(list);
    }

    /**
     * 只是循环调用其他的 ExtensionFactory#getExtension 来找到依赖对象
     */
    @Override
    public <T> T getExtension(Class<T> type, String name) {
        for (ExtensionFactory factory : factories) {
            T extension = factory.getExtension(type, name);
            if (extension != null) {
                return extension;
            }
        }
        return null;
    }

}
