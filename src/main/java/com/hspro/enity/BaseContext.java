package com.hspro.enity;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/9/30
 */
public class BaseContext {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setId(Long id){
        threadLocal.set(id);
    }

    public static Long getId(){
        return threadLocal.get();
    }
}
