/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.entity.Comments;
import com.entity.Post;
import com.services.Services;
import com.util.UserSession;
import java.io.IOException;


/**
 *
 * @author gaston
 */
public class AjoutCom extends Form{
    
    public AjoutCom(Form previous,Post c){
        System.out.println("aaaaaabbbghass"+c.getId());
               TextField nomev = new TextField("", "nom user: ");
               nomev.setText(UserSession.getsession().getUserName());
        TextArea lieu = new TextField("", "contenu du commentaire: ");
        Button btnValider = new Button("add comment");
        
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ( (nomev.getText().length() == 0)&(lieu.getText().length() == 0) ) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                   
                       
                        String lieuu =lieu.getText();
                        String nome = nomev.getText();
                        
                     Image img ;
                     
          
                
                        if (Services.getInstance().addCom(new Comments(c.getId(), nome, lieuu))) {
                            try {
                                Message m = new Message("Body of message");
                                m.getAttachments().put("testtest", "text/plain");
                                
                                Display.getInstance().sendMessage(new String[] {"rihem.mansouri@esprit.tn"}, "Subject of message", m);
                                
                                
                                
                                
                                Dialog.show("Success", "comment added", new Command("OK"));
                                new ListCom(previous, c).show();
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
