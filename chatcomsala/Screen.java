/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcomsala;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Screen {
        
    private GraphicsDevice vc; //Monitor
    
    public Screen(){
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment(); //Esse env é importante pra fazer os memes
        vc = env.getDefaultScreenDevice(); //Acesso da tela (Video card do graphic card)
    }
    
    public void setScreen(DisplayMode dm, JFrame window){ //Dm é as caraceteristicas da tela, resolução fps e os krl
  
       // window.setUndecorated(true); //Tirar as barrinhas de scroll da tela 
        window.setResizable(false); //Não poder mudar o tamanho da resolução da tela
       // vc.setFullScreenWindow(window); //Criar janela fullscreen
       //Janela normal
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Faz matar o programa quando fecha a janela
        window.setSize(800,600); //Resolução da janela 
        window.setVisible(true); //Mostrar janela
        window.setTitle("Pokemon Melhorado"); //Titulo
        
       
        //Colocar Ícone
        Image icone;
        icone = new ImageIcon("H:\\UFPB\\Imagens\\icone.png").getImage();
        window.setIconImage(icone);

      
        if (dm !=null && vc.isDisplayChangeSupported()){ //DisplayChangeCoisinha vai ver se a tua tela pode mudar de tamanho, é necessario pra nao dar umas bugadas
            try{
                vc.setDisplayMode(dm);
                System.out.println("Mizera21");
            }catch(Exception ex){}
       
        }
    
    }
    
    
      public Window getFullScreenWindow(){ //Deixar a tela em janela (Pra não ficar em softlock)
            return vc.getFullScreenWindow();
            
      } 
      public void restoreScreen(){ //Deixa a tela em janela 
          Window w = vc.getFullScreenWindow();
          if (w != null){
                w.dispose(); //Forma entulhada de fechar janela, economiza recurso
            }
          vc.setFullScreenWindow(null); //Usar esse comando só quando nao tiver fullscreen
      }
     
    
}
