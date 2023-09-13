//package com.base.utils.common;
//
//import com.pojos.User;
//
//public class ThreadLocalUtil {
//    private final static ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();
//    public static void setUser(User user){
//        USER_THREAD_LOCAL.set(user);
//    }
//    public static User getUser(){
//        return USER_THREAD_LOCAL.get();
//    }
//    public static void clear(){
//        USER_THREAD_LOCAL.remove();
//    }
//}
