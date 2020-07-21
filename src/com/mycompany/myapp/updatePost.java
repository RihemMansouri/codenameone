 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.entity.Post;
import com.services.Services;
import com.util.UserSession;
import java.io.IOException;
import javax.mail.Session;

/**
 *
 * @author gaston
 */
public class updatePost extends Form {
    
    public updatePost(Form previous , Post p){
        TextField nomev = new TextField("", "titre du poste: ");
        TextArea lieu = new TextField("", "contenu du poste: ");
        Button btnValider = new Button("update post");
        
        nomev.setText(p.getTitle());
        lieu.setText(p.getContent());
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ( (nomev.getText().length() == 0)&(lieu.getText().length() == 0) ) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                }
                
            
                
                else {
                   
                       
                        String lieuu =lieu.getText();
                        String nome = nomev.getText();
                        
                     Image img ;
                     
          
                
                        if (Services.getInstance().PutPost(new Post(p.getId(), nome, lieuu))) {

                            
                            
                             
                            Dialog.show("Success", "Post modified", new Command("OK"));
                            try {
                                new ListEvent(previous).show();
                                //previous.show();
                            } catch (IOException ex) {
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
