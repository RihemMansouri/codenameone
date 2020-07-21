/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.l10n.ParseException;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.entity.Post;
import com.services.Services;
import java.io.IOException;


/**
 *
 * @author gaston
 */
public class AjoutPost extends Form{
    
    public AjoutPost(Form previous){
            TextField nomev = new TextField("", "titre du poste: ");
            TextArea lieu = new TextField("", "contenu du poste: ");
            
            
        
        Button btnValider = new Button("add post");
        
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ( (nomev.getText().length() == 0)&(lieu.getText().length() == 0) ) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                   
                       
                        String lieuu =lieu.getText();
                        String nome = nomev.getText();
                        
                     Image img ;
                     
          
                
                        if (Services.getInstance().addPost(new Post(nome, lieuu))) {

                            
                            
                             
                            Dialog.show("Success", "post added", new Command("OK"));
                            try {
                               // new ListEvent(previous).refreshTheme();
                                new ListEvent(previous).show();
                                
                            } catch (IOException ex) {
                                //Logger.getLogger(AjoutPost.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    
                        
                        
                  

                }

            }
        });
             addAll(lieu, nomev,btnValider);
  
    }

}
