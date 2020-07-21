/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

/**
 *
 * @author gaston
 */
 public final class UserSession {

    public static UserSession instance;

    private String userName;
    private int id ;

    private UserSession(String userName, int id) {
        this.userName = userName;
        this.id = id;
    }

    public static UserSession getsession(){
                return instance;

    }
    
    public static UserSession getInstace(String userName, int id) {
        if(instance == null) {
            System.out.println("d5al");
            instance = new UserSession(userName, id);
        }
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public int getid() {
        return this.id;
    }

    public void cleanUserSession() {
        userName = "";// or null
        this.id = 0;// or null
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + userName + '\'' +
                ", privileges=" + id +
                '}';
    }
}
