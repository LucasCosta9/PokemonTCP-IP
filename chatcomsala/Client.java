/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcomsala;

/**
 *
 * @author plrf1
 */


import java.awt.DisplayMode;
import java.io.*; 
import java.util.*; 
import java.net.*; 
import java.util.Scanner; 
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    
   static Images tela;
   //int acao; //Identificar qual ação vai precisar ser feita (Room do Server vai enviar)

    
    public static void main(String[] args) throws IOException {
    
        //Criar Janela
        System.out.println("Kk vo criar janela");
        DisplayMode dm = new DisplayMode(800,600,16,DisplayMode.REFRESH_RATE_UNKNOWN); //Resolução,meme de cor eu acho (8,16,32 quanto menor mais ligero),FPS)
        Images tela = new Images();
        tela.run(dm);

            
        // getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");

        // establish the connection
        Socket s = new Socket(ip, 1234);

        // obtaining input and out streams
        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        
     
        Thread sendMessage = new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    while (true) {

                        Scanner scn = new Scanner(System.in);
                    // read the message to deliver.
                        String msg = scn.nextLine();

                        out.writeUTF(msg);
                    }

                } catch (IOException ex) {

                }
            }

        });

        Thread readMessage = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    while (true) {
                        String msg;
                        msg = in.readUTF();
                        //99XX Evento
                        //RECEBER EVENTO 

                        //Tratar como evento caso possuir #
                        if(msg.contains("#")){ //Dps trocar pra while
                            System.out.println("???????????????chegou aqui pelo menos mizeraaaaaaaa");
                           // System.out.println(msg); //Ver a mensagem recebida 
                            //Enviando o evento pra tela
                            tela.mudarEvento(msg);
                            System.out.println(msg);
                            //Voltar a receber mensagem 
                           //  msg = in.readUTF();
                             }else{
                            System.out.println(msg);                           
                            }
          
                    }
                } catch (IOException ex) {

                 
                }

                System.out.println("asssssssssssssssssssssssssssssssssssssssssssssssssssssaa");
                System.exit(0);
            }

        }
        );

        sendMessage.start();
        readMessage.start();

    }
    
   //  public Images pegarTela(){
   //      return tela;
   //   }
}