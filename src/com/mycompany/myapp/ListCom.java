/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp ;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;

import com.codename1.components.FloatingActionButton;
import com.entity.Comments;
import com.entity.Post;
import com.services.Services;
import java.io.IOException;

/**
 *
 * @author Moez
 */
public class ListCom extends Form {
       Form current;

    public ListCom(Form previous, Post p) throws IOException {
        current = this;
        Container xa = new Container();
        setTitle("List Commentaires  "+p.getTitle());
         Button btnaj = new Button("ajouter un commentaire");
       
               
               //Services.getInstance().addPost(new Post("aaaaaaaaaaaaa", "bb"));
                   btnaj.addActionListener(e -> new AjoutCom(current,p).show());
           
          xa.add(btnaj);
             add(xa);
        
     
        System.out.println("ghassa"+p.getId());
 
        for (Comments f : Services.getInstance().getComs(p.getId())) {

            Container cnt1 = new Container(BoxLayout.y());
    
//            Label lbId = new Label(" iD= " + f.getId());

            SpanLabel lbDesc = new SpanLabel( f.getUsername());
            Label content = new Label(f.getContent());
            Label User = new Label(f.getDatecom());
          
//lbDesc.setAutoSizeMode(true);
            //Label lbnbr = new Label(" nombre Participant= " + f.getNbrMaxParticipants());
            //Label lbDate = new Label(" Date= " + f.getDateEvenement());
            //Label lbRate = new Label(" Lieu= " + f.getLieuEvenement());
           Label a=new Label("----------------");
            SpanLabel lbSeparator = new SpanLabel();

            Container cnt2 = new Container(BoxLayout.x());
            //Button btnRemoveFeedback = new Button("Remove Event");
            FloatingActionButton btnRemoveFeedback = FloatingActionButton.createFAB(FontImage.MATERIAL_DELETE);
            Button btnsupp = new Button("suuprimer cette commentaire");
            

            //affiche Image ------------
         
 
 
            // -----------------
                btnsupp.addActionListener((e) -> {
                try {
                    Post fb = new Post();
                    if (Services.getInstance().RemoveComment(f)) {
                        Dialog.show("Success", "Comments  avec ID= " + f.getId() + " a ete supprimee avec succees", new Command("OK"));
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
               
            
            cnt1.addAll(/*lbId,*/ User,lbDesc,  lbSeparator,content,a,btnsupp);
            //cnt2.addAll( btnRemoveFeedback,btninvit,btnlist,btnup);
//            btnup.addActionListener(e -> new UpdateEvent(current, f).show());
//            btninvit.addActionListener(e -> new AjoutInvite(current, f).show());
//            btnlist.addActionListener(e -> new ListeInvite(current,f).show());
          

            addAll(cnt1);
        }

    }
}
