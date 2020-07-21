/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.entity.Post;
import java.util.ArrayList;

/**
 *
 * @author gaston
 */
public class TestService {
    public static void main(String[] args) {
        
        ArrayList<Post> ar = new ArrayList<>();
        ar=Services.getInstance().getAllFeedbacks();
        System.out.println(ar.size());
    }
}
