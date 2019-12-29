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
package org.apache.dubbo.common.utils;

import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * Helper Class for hold a value.
 */
public class Holder<T> {

    /**
     * 这里这个 value 为啥要用 volatile 修饰？
     * 因为 Holder 对象本身是放在 ConcurrentMap 中，获取对象本身是不加锁访问的
     * 对应的访问 Holder 对象中的 value 对象（{@link #get()} 的调用）也是不加锁的，如果不用 volatile 修饰就要悲剧了
     *
     * @see ExtensionLoader#getExtension(java.lang.String) line 431
     */
    private volatile T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

}