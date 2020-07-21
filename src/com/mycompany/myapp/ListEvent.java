/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;

import com.codename1.components.FloatingActionButton;
import com.entity.Post;
import com.services.Services;
import com.util.UserSession;
import java.io.IOException;
import java.io.InputStream;




/**
 *
 * @author LQss
 */
public class ListEvent extends Form {

    Form current;

    public ListEvent(Form previous) throws IOException {
        current = this;
        current.setLayout(BoxLayout.y());

        setTitle("List Posts");
        Container xa = new Container(BoxLayout.x());
         Button btnaj = new Button("ajouter post");
       
               
               //Services.getInstance().addPost(new Post("aaaaaaaaaaaaa", "bb"));
                   btnaj.addActionListener(e -> new AjoutPost(current).show());
           
          xa.add(btnaj);
             add(xa);
        for (Post f : Services.getInstance().getAllFeedbacks()) {
            
            Container cnt1 = new Container(BoxLayout.y());
    
//            Label lbId = new Label(" iD= " + f.getId());

            SpanLabel lbDesc = new SpanLabel( "Title:"+f.getTitle());
            Label content = new Label("Content:"+f.getContent());
           // Label User = new Label(f.getUserid());
            Label DLabel=new Label("Date:"+f.getDatep());
            Button update = new Button("Modifier Post");
            update.setWidth(50);
            update.setHeight(50);
            update.addActionListener(e -> 
            {
                 String curida=String.valueOf(UserSession.getsession().getid())+".0";
               if (!(curida).equals(f.getUserid())){
                   try {
                       System.out.println(curida+"  id curr user");
                       System.out.println(f.getUserid()+"user id");
                       Dialog.show("Alert", "You are not allowed to update this", new Command("OK"));
                       new ListEvent(current).show();
                       return;
                   } catch (IOException ex) {
                   }
                     
                    }
                
                
                
                new updatePost(current,f).show();
            
            
            });
            
//lbDesc.setAutoSizeMode(true);
            //Label lbnbr = new Label(" nombre Participant= " + f.getNbrMaxParticipants());
            //Label lbDate = new Label(" Date= " + f.getDateEvenement());
            //Label lbRate = new Label(" Lieu= " + f.getLieuEvenement());
           Label a=new Label();
           a.setUIID("Separator");
            SpanLabel lbSeparator = new SpanLabel();

            Container cnt2 = new Container(BoxLayout.x());
            Button btnRemoveFeedback = new Button("Remove Post");

            Button btnaffCom = new Button("voir les commentaires");
            

            //affiche Image ------------
         
 
 
            // -----------------
             
              
              
            btnaffCom.addActionListener(e -> {
                try {
                    new ListCom(current, f).show();
                } catch (IOException ex) {
                
                }
            });
               
            
            if (f.getUserid().equals(String.valueOf(UserSession.getsession().getid()))){
                update.setHidden(true);
                btnRemoveFeedback.setHidden(true);
            
            
            
            }
            
            
            
            
            btnRemoveFeedback.addActionListener((e) -> {
                String curida=String.valueOf(UserSession.getsession().getid())+".0";
               if (!(curida).equals(f.getUserid())){
                   try {
                       System.out.println(curida+"  id curr user");
                       System.out.println(f.getUserid()+"user id");
                       Dialog.show("Alert", "You are not allowed to remove this", new Command("OK"));
                       new ListEvent(current).show();
                       return;
                   } catch (IOException ex) {
                   }
                     
                    }
                    
                try {
                    
                    
                    Post fb = new Post();
                    if (Services.getInstance().RemovePost(f)) {
                        Dialog.show("Success", "Posts  avec ID= " + f.getId() + " a ete supprimee avec succees", new Command("OK"));
                        new ListEvent(current).show();
                    } else {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                    }
                } catch (NumberFormatException err) {
                    Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                } catch (IOException ex) {
                  
                }
            });
            
            FloatingActionButton btninvit = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD_CIRCLE);
            FloatingActionButton btnlist = FloatingActionButton.createFAB(FontImage.MATERIAL_LIST);
            FloatingActionButton btnup = FloatingActionButton.createFAB(FontImage.MATERIAL_UPDATE);
               
            
            cnt1.addAll(/*lbId,*/ lbDesc,  lbSeparator,content,DLabel,a,btnaffCom,btnRemoveFeedback,update);
           // cnt2.addAll( btnaj);
//            btnup.addActionListener(e -> new UpdateEvent(current, f).show());
//            btninvit.addActionListener(e -> new AjoutInvite(current, f).show());
//            btnlist.addActionListener(e -> new ListeInvite(current,f).show());
          
                    //xa.add(btnaj);

            addAll(cnt1);
        }
     
//        if (previous.getTitle() == "Home") {
//            getToolbar().addMaterialCommandToLeftBar("Home", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
//        } else {
//           // getToolbar().addMaterialCommandToLeftBar("Home", FontImage.MATERIAL_ARROW_DROP_UP, e -> new HomeForm().show());
//            //getToolbar().addMaterialCommandToLeftBar(previous.getTitle(), FontImage.MATERIAL_ARROW_BACK,e -> previous.showBack());
//
//        }

    }
}
