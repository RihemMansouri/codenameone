/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.RequestBuilder;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.entity.Comments;
import com.entity.Post;
import com.entity.User;
import com.util.UserSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author gaston
 */
public class Services {
    public ArrayList<Post> post_list;
    public List<User> ul;
        public ArrayList<Comments> comment_list;

     public boolean resultOK;
   // private ConnectionRequest req;
    public RequestBuilder delete;
     private ConnectionRequest req;
   public static Services instance = null;

    
    private Services() {
        req = new ConnectionRequest();
    }

    public static Services getInstance() {
        if (instance == null) {
            instance = new Services();
        }
        return instance;
    }
    
 

       public ArrayList<Post> getAllFeedbacks() {

       String url = "http://127.0.0.1:8080/allP";
               req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("GET");

       req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               // System.out.println(new String(req.getResponseData()));
                post_list = parseFeedbacks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return post_list;
    }
       
       
       
       
       
       /////////////////////////////////////////////////////////////////////////////////////
       
        public Boolean getuserauth(User u) {

       String url = "http://localhost:8080/log?nom="+u.getUserName()+"&password="+u.getPassword();
               req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("GET");

       req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               // System.out.println(new String(req.getResponseData()));
                ul = parselogin(new String(req.getResponseData()));
                
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        if(ul==null){
            System.out.println("err"+ul);
           
            return false;
        }else {
             UserSession.getInstace(ul.get(0).getUserName(), ul.get(0).getId());
        return true;
        }
    }
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       public ArrayList<Comments> getComs(int id) {

       String url = "http://127.0.0.1:8080/affComs/"+id;
               req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("GET");

       req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println(new String(req.getResponseData())+"///////////////\\\\\\\\\\\\\\");
                comment_list = parsecomment(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return comment_list;
    }
       
       
        public ArrayList<Post> parseFeedbacks(String jsonText) {
        try {
            System.out.println(jsonText);
            post_list = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> FeedbacksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) FeedbacksListJson.get("root");

            for (Map<String, Object> obj : list) {

                Post f = new Post();

                
                String idU =obj.get("id").toString();
                System.out.println("////////////////////////////////*/");
                System.out.println(idU);

                float id = Float.parseFloat(obj.get("id").toString());
                String title = obj.get("NomPost").toString();
                String contenu = obj.get("Contenu").toString();
                String datep = obj.get("DatePost").toString();
                String id_auth=obj.get("id_author").toString();
                f.setUserid(id_auth);
                f.setId((int) id);
                f.setContent(contenu);
               f.setTitle(title);
              // f.setUserid(idU);
               f.setDatep(datep);
                post_list.add(f);
            }

        } catch (IOException ex) {

        }
        return post_list;
    }

    ///////////////////////////////////////////parsePost/////////////
            public ArrayList<Comments> parsecomment(String jsonText) {
        try {
            System.out.println(jsonText);
            comment_list = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> FeedbacksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) FeedbacksListJson.get("root");

            for (Map<String, Object> obj : list) {

                Comments f = new Comments();

                
                String idU =obj.get("id").toString();
                System.out.println("////////////////////////////////*/");
                System.out.println(idU);

                float id = Float.parseFloat(obj.get("id").toString());
                String username = obj.get("nomUser").toString();
                System.out.println("username:"+username);
                String contenu = obj.get("contenu").toString();
                String datep = obj.get("dateCom").toString();

                f.setId((int) id);
                f.setContent(contenu);
               f.setUsername(username);
            
               f.setDatecom(datep);
                System.out.println("hani hna");
                comment_list.add(f);
            }

        } catch (IOException ex) {

        }
        return comment_list;
            }
            
    //////////////////////////////////////Remove//////////////////////////////////
             public boolean RemovePost(Post f) {
        String url = "http://127.0.0.1:8080/deleteP/" + f.getId();
        req.setUrl(url);
        req.setHttpMethod("DELETE");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
                       public boolean RemoveComment(Comments f) {
        String url = "http://127.0.0.1:8080/deleteC/" + f.getId();
        req.setUrl(url);
        req.setHttpMethod("DELETE");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
                       
                       ////////////////////////////////////////////////Ajouter///////////////
//                       public void onloginActionEvent(Post f) {
//                JSONObject json = new JSONObject();
//              try {
//                  ConnectionRequest post = new ConnectionRequest(){
//                       
//
//                        @Override
//                        protected void readResponse(InputStream input) throws IOException {
//                        }
//
//                        @Override
//                        protected void postResponse() {                             
//                        }
//                    };
//                    json.put("nom", f.getTitle());
//                    json.put("contenu", f.getContent());
//
//                  post.setUrl("http://localhost:8080/addP");
//                  post.setPost(true);
//                  post.setContentType("application/json");
//                  post.addArgument("nom", f.getTitle());
//                  post.addArgument("contenu", f.getContent());
//                  String bodyToString = json.toString();
//                  NetworkManager.getInstance().addToQueueAndWait(post);
//                  Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(post.getResponseData()), "UTF-8"));
//              } catch (Exception ex) {
//                  ex.printStackTrace();
//              }
//          }
                       
                       
                        public boolean addPost(Post f) {
                             Date dateobj = new Date();
             DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String url = "http://localhost:8080/addP/add?nom="+ 
                f.getTitle()+"&contenu="+
                f.getContent()+"&ide="+UserSession.getsession().getid()+"&dateP="+df.format(dateobj);
        req.setUrl(url);
        req.setHttpMethod("POST");

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
                        
        public boolean PutPost(Post f) {
            System.out.println("idooooo:"+f.getId()+"conentuuuuuuuuuuuuuuuuuuuuuuu:"+f.getContent());
        String url = "http://localhost:8080/updateP?id="+f.getId()+"&nom="+f.getTitle()+"&contenu="+f.getContent();
        req.setUrl(url);
        req.setHttpMethod("PUT");

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                System.out.println(resultOK);
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
            System.out.println("send");
        return resultOK;
    }
                        
                                         public boolean addCom(Comments f) {
                                             Date dateobj = new Date();
             DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                             
        String url = "http://localhost:8080/addC?nomU="+f.getUsername()+"&contenuP="+f.getContent()+"&idP="+f.getId_post()+"&idu="+UserSession.getsession().getid()+"&dateCom="+df.format(dateobj);
        req.setUrl(url);
        req.setHttpMethod("POST");

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<User> parselogin(String jsonText) {
        try {
            System.out.println(jsonText);
            ul = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> FeedbacksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) FeedbacksListJson.get("root");

            for (Map<String, Object> obj : list) {

               // Comments f = new Comments();
               User us = new User();
                
                String idU =obj.get("id").toString();
                System.out.println("////////////////////////////////*/");
                System.out.println(idU);

                float id = Float.parseFloat(obj.get("id").toString());
                String username = obj.get("nom").toString();
               // System.out.println("username:"+username);
                String contenu = obj.get("password").toString();
               // String datep = obj.get("dateCom").toString();
               us.setId((int)Float.parseFloat(obj.get("id").toString()));
               us.setUserName(username);
               us.setPassword(contenu);
              
                System.out.println("hani hna");
               this.ul.add(us);
            }

        } catch (IOException ex) {

        }
        return (ArrayList<User>) this.ul;
            }                                      
}
