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


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {

    /**
     * @param args the command line arguments
     */

    static Vector<Cliente> clients = new Vector<>();
    // counter for clients
    static int i = 0;
    

    static Vector<Cliente> roomOne = new Vector<>();
    static Vector<Cliente> roomTwo = new Vector<>();
    static Vector<Cliente> roomThree = new Vector<>();

    static Vector<String> entries = new Vector<>();
    static Vector<String> jose = new Vector<>();
    static Room roomBraba, roomBraba2, roomBraba3;
    //Mudei aqui lel
   // static DataInputStream in;
   // static DataInputStream out;
        
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        // server is listening on port 1234
        ServerSocket ss = new ServerSocket(1234);

        Socket s;

        System.out.println("bora fiote");
        while (true) {

            s = ss.accept();
            Cliente clt = null;

            System.out.println("Novo cliente recebido : " + s);

            // obtain input and output streams
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            if (roomOne.size() < 2) {
                clt = new Cliente(s, "Cliente " + i, in, out, roomOne);
                System.out.println("Cliente alocado para a sala 1");
                roomOne.add(clt);

            } else if (roomTwo.size() < 2) {
                clt = new Cliente(s, "Cliente " + i, in, out, roomTwo);
                System.out.println("Cliente alocado para a sala 2");
                roomTwo.add(clt);
                
            } else if (roomThree.size() < 2) {
                clt = new Cliente(s, "Cliente " + i, in, out, roomThree);
                System.out.println("Cliente alocado para a sala 3");
                roomThree.add(clt);
            }


            if (clt != null) {
                Thread t = new Thread(clt);
                // add this client to active clients list
                clients.add(clt);

                t.start();
                i++;
            }
            //Criar Room() pros dois primeiros clientes
            if (roomOne.size() == 2){
             //  Cliente clt11 = roomOne.firstElement();
             //  Cliente clt22 = roomOne.lastElement();
              //  System.out.println("Entrou aqui aaaaaaaa");
                roomBraba = new Room(roomOne);
                Thread room;
                room= new Thread(roomBraba);
                room.start();
                
              
            }
            if (roomTwo.size() == 2){
             //  Cliente clt11 = roomOne.firstElement();
             //  Cliente clt22 = roomOne.lastElement();
               // System.out.println("Entrou aqui aaaaaaaa");
                roomBraba2 = new Room(roomTwo);
                Thread room2;
                room2= new Thread(roomBraba2);
                room2.start();
   
            }
              if (roomThree.size() == 2){
             //  Cliente clt11 = roomOne.firstElement();
             //  Cliente clt22 = roomOne.lastElement();
              //  System.out.println("Entrou aqui aaaaaaaa");
                roomBraba3 = new Room(roomThree);
                Thread room3;
                room3= new Thread(roomBraba3);
                room3.start();
   
            }
            // if (falaTino.equals("Bye")) break;
        }

        // ss.close();
        // s.close();

    }
}

class Cliente implements Runnable {

    private String name;
    final DataInputStream in;
    final DataOutputStream out;
    Socket s;
    boolean isloggedin;
    Vector<Cliente> room;
    String changeMsg; //Mensagem pra mandar pra room qual pokemon trocar

    
    int hp;
    Vector<Pokemon> hand = new Vector<>();
    volatile int turno = 0;
    volatile int acao = 0; //Açao escolhida 
    volatile int placebo3 = 1;
    int dano; //Dano que tu vai dar no prox turno
    volatile int prioridade =0; //1 Refresh com evento = 3, 2 = Só um refresh
    volatile int event =1; //Controlar alguns eventos pra mudar na tela (Tela de mudança de pokemon e tela que mostra ataques)
    //event = 1 tela normal, event = 2 mostrando ataques, event = 3 trocamento
    
    
    public Cliente(Socket s, String name, DataInputStream in, DataOutputStream out, Vector<Cliente> room) {

        this.s = s;
        this.name = name;
        this.in = in;
        this.out = out;
        this.room = room;

    }

    @Override
    public void run() {

        String recieved;
        int index = 0;
        //Randomizador de pokemons
        PokemonSelector seletor = new PokemonSelector();
        Pokemon pokemon1 = new Pokemon(seletor.getRandPoke());
        Pokemon pokemon2 = new Pokemon(seletor.getRandPoke());
        Pokemon pokemon3 = new Pokemon(seletor.getRandPoke());
        Pokemon pokemon4 = new Pokemon(seletor.getRandPoke());
        Pokemon pokemon5 = new Pokemon(seletor.getRandPoke());
        Pokemon pokemon6 = new Pokemon(seletor.getRandPoke());

        hand.add(pokemon1);
        hand.add(pokemon2);
        hand.add(pokemon3);
        hand.add(pokemon4);
        hand.add(pokemon5);
        hand.add(pokemon6);
        
        
        while (placebo3==1) {

            try {              
    
                recieved =""; //Inicializar pro compilador n reclamar
                //Recebendo acao;
                
                //=====================================================================//
                //SER OBRIGADO A TROCAR CASO TEU POKEMON TENHA FAINTADO
                //=====================================================================//
                while(hand.firstElement().hp==0){
               
                     
                // Troca se o pokemon em campo morrer
                int count = 0;
                int naoFaintados = 5;
                // Exibe os pokemons na tela
                out.writeUTF("[" + hand.firstElement().showName() + "] fainted");
                out.writeUTF("Troque imediatamente");
                
                for (Pokemon pok : hand) {
                    if (pok != hand.firstElement()) {
                        count += 1;
                        out.writeUTF(count + " - " + pok.showName());
                        if(pok.hp<=0){naoFaintados-=1;}
                    }
                }

                if (naoFaintados == 0){
                    System.out.println("Todos os outros pokemons tao fainted");
                    System.out.println("Tu se lascou");
                    while(true){
                    //DERROTA    
                    }
                    
                }else{
                    event = 3; //Evento de trocar de pokemon 
                    prioridade = 1; //Refrescar a tela //Com evento = 3
                    changeMsg = in.readUTF();
                     while((Integer.parseInt(changeMsg)>5) || (hand.get(Integer.parseInt(changeMsg)).hp<=0)){
                    //Integer.parseInt(changeMsg)>(count)
                     System.out.println("Essa opcao nao eh valida");
                     changeMsg = in.readUTF(); 
           
                }
                     //Fazer o trocamento aqui mesmo 
                     int changeIndex = Integer.parseInt(changeMsg);
                        Pokemon atual = hand.firstElement();
                        Pokemon troca = hand.get(changeIndex);

                        hand.set(0, troca);
                        hand.set(changeIndex, atual);
                      event = 1;
                      prioridade = 2; //Refrescar a tela com evento normal 
                    // turno = 1; Isso nao vai comer o turno dele 
                     
                    //Voltar pro turno normal 
                }
                
              
                
                  
                    
               }
                //=====================================================================//
                //SER OBRIGADO A TROCAR CASO TEU POKEMON TENHA FAINTADO
                //=====================================================================//
                
                
                
                if (turno == 0){ //Só considerar o primeiro ataque enviado
                System.out.print("Digite sua acao rapaz");
                recieved = in.readUTF();
                }
                
                System.out.print("Sua acao foi recebida");
                //System.out.println(recieved);                                
                //System.out.print("Recebeu uma mensagem");
                out.writeUTF("Recebeu um numerozim");
                
                //SISTEMA DE TURNOS
                turno = 1;
                        
                        //============Trocar pokemon se possivel====================
                        while (recieved.equals("5")&& (acao!=5)) { //&& (turno!=2)
                            int count = 0;
                            // Exibe os pokemons na tela
                            int naoFaintados = 5; //Considerando que seus outros 5 tão vivos
                            out.writeUTF("Digite o pokemon que vc queira trocar por [" + hand.firstElement().showName()
                                    + "]");
                            for (Pokemon pok : hand) {
                                
                                    if (pok != hand.firstElement()) { //Sair passando por todos os pokemons
                                        count += 1;
                                        out.writeUTF(count + " - " + pok.showName());
                                     if (pok.hp <= 0){naoFaintados -= 1;}

                                }
                            }
                            if (naoFaintados == 0){
                                System.out.println("Todos os outros pokemons tao fainted");
                                recieved = in.readUTF();
                            }else{
                                 event = 3; //Evento de trocar de pokemon 
                                 //System.out.println("Valor do count: "+count);
                                 acao = 5;
                                 changeMsg = in.readUTF();//Receber o pokemon que quer trocar
                              //   System.out.println("Valor de menagem passada"+Integer.parseInt(changeMsg));
                            while((Integer.parseInt(changeMsg)>5) || (hand.get(Integer.parseInt(changeMsg)).hp<=0)){
                                //Integer.parseInt(changeMsg)>(count)
                                 System.out.println("Essa opcao nao eh valida");
                                 changeMsg = in.readUTF(); 
                            }
                            
                            }
                        }
                        //==============================================================================
                    

                //Fim do for doido         
                if (recieved.equals("1")) {
                          // System.out.print("!!!!!!!!!!!!!!!!!!!!!!!");
                           event = 2; //Trocar tela para a que mostra os ataques
                           //System.out.print("Escolha seu ataque");
                           out.writeUTF("Escolha seus ataques");
                           //System.out.print("zz!!!!!!!!!!!!!!!!!!!!!!!");
                           recieved = in.readUTF();
                           while (acao==0){ //Enquanto a acao 
                                
                                switch(Integer.parseInt(recieved)){
                                    case 1: //Ataque 1;
                                    acao = 1;
                                    break;

                                    case 2: //Ataque 2;
                                    acao = 2;
                                    break;

                                    case 3://Ataque 3;
                                    acao = 3;
                                    break;

                                    case 4://Ataque 4;
                                    acao = 4;
                                    break;

                                    default:
                                    out.writeUTF("Digite um ataque valido");
                                    recieved = in.readUTF();
                                    break;
                                }
                           }
                        }
                
                turno = 2;
              //  out.writeUTF("Meu turno virou "+turno);
                //System.out.println("Meu entulho="+turno);
                while(turno == 2){  //Espernado room ver que os dois tao com turno = 2 pra mudar pra zero dnv
                  //   x += 0;  
                  Thread.sleep(100);
                }
                System.out.println("Meu turno agora eh:"+turno);
     
               // x=0;
               
               
               


            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            } /*catch (IOException e) {
                
                for(Cliente cl : room){
                  
                 if (cl.s == this.s){ room.remove(index); break; }
                 else { index++; }
                  
                 }
                
                try {
                    in.close();
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
               
            }*/

        }

    }

}

class Room implements Runnable {
    
    Cliente client1;
    Cliente client2;
    //Images tela1;
    //Images tala2;
    volatile int placebo=1;
    int dano; //Dano do ataque dado
    volatile int event1 = 1; //Evento do player 1 (Para mudanaças na tela)
    volatile int event2 = 1; //Evento do player 2 (Para mudanaças na tela)
    boolean darRefresh = true; //Quando tiver true, a tela vai dar uma atualizada
    boolean darRefresh1 = false; //Dar refresh soh no 1
    boolean darRefresh2 = false; //Dar refresh soh no 2
    int buff = 0; //Valor do buff
    int tipo; //Tipo kkoi
    int hp1, hp2; //Hp dos dois pokemons pra tentar resolver uns bugs
    Pokemon pok1;
    Pokemon pok2;

    
    public Room(Vector<Cliente> vectClients){
        // System.out.println("tttttttttt");
        this.client1 = vectClients.firstElement();
        this.client2 = vectClients.lastElement();
    }
    
    @Override
    public void run(){
      //  System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
        pok1 = client1.hand.firstElement(); 
        pok2 = client2.hand.firstElement(); 
        while (placebo == 1){  
        //pok1 = client1.hand.firstElement();
        //pok2 = client2.hand.firstElement();
        
            //TELA DE MOSTRAR OS POKEMONS QUE QUER TROCAR 
            
            if((client1.event == 3)){
                event1 =3; //Passar o evento para o evento da room
                darRefresh1 = true;
                client1.event =1;//Deixa o evento de volta ao normal
            }
            //Cliente 2
             if((client2.event == 3)){
                event2 =3;//Passar o evento para o evento da room
                darRefresh2 = true;
                client2.event =1; //Deixa o evento de volta ao normal
            }
            
            
            
            //TELA DE MOSTRAR OS ATAQUES
            //Cliente 1
            if((client1.event == 2)){
                event1 =2;//Passar o evento para o evento da room
                darRefresh1 = true;
                client1.event =1;//Deixa o evento de volta ao normal
                
            }
            //Cliente 2
             if((client2.event == 2)){
                event2 =2;//Passar o evento para o evento da room
                darRefresh2 = true;
                client2.event =1; //Deixa o evento de volta ao normal
               
            }
        
        
        
        
            //===============------------TURNOS-----------=======================
            if((client1.turno ==2) && (client2.turno ==2)){  
                
            
              //=======TROCAS DE POKEMON=======
              //=====CLIENTE 1======
                    if (client1.acao == 5) {
                        System.out.println("kk trocou o poketulio");
                        int changeIndex = Integer.parseInt(client1.changeMsg);
                        Pokemon atual = client1.hand.firstElement();
                        Pokemon troca = client1.hand.get(changeIndex);

                        client1.hand.set(0, troca);
                        client1.hand.set(changeIndex, atual);

                        client1.acao = 0;
                        System.out.println("Trocou os pokemons Primeiro/Segundo"+client1.hand.firstElement().showID()+"/"+client2.hand.firstElement().showID());
                    }
            
             //=====CLIENTE 2======
             if (client2.acao == 5) {
                        int changeIndex = Integer.parseInt(client2.changeMsg);
                        Pokemon atual = client2.hand.firstElement();
                        Pokemon troca = client2.hand.get(changeIndex);

                        client2.hand.set(0, troca);
                        client2.hand.set(changeIndex, atual);

                        client2.acao = 0;
                        System.out.println("Trocou os pokemons Primeiro/Segundo"+client1.hand.firstElement().showID()+"/"+client2.hand.firstElement().showID());
                    }

            //=========================ACOES CLIENTE 1====================================== 
            //=======ATAQUE 1=======
            if (client1.acao == 1) {
                
                dano = client1.hand.firstElement().attack1(); // Dano do ataque 1

                // Verifica se o ataque eh super efetivo
                Boolean superEffectiveAttack = client1.hand.firstElement().counterVerifyAttack(client2.hand.firstElement().type);
                Boolean superEffectiveDefense = client2.hand.firstElement().counterVerifyDefense(client1.hand.firstElement().type);

                if (superEffectiveAttack){ //Caso for super efetivo
                    dano *= 2;
                }else if (superEffectiveDefense){//Caso nao for efetivo
                    dano = dano / 2;
                }

                System.out.println("Tipo do primeiro: " + client1.hand.firstElement().showType()); 
                System.out.println("Tipo do segundo: " + client2.hand.firstElement().showType());
                System.out.println("Cliente 1 deu " + client1.hand.firstElement().showAttack1() + " com o "
                        + client1.hand.firstElement().name + " de " + dano + " de dano");
                System.out.print("Hp do Cliente 2 foi de " + client2.hand.firstElement().hp);
                
                client2.hand.firstElement().hp -= dano; // Diminuir hp do cliente 2

                if(client1.hand.firstElement().name.equals("Bulbasaur")) client2.hand.firstElement().poisonThis(); //Envenena se for bulbassauro

                System.out.println(" para " + client2.hand.firstElement().hp);
                client1.acao = 0;
                System.out.println();   //Pra dar uns espacinho entre um ataque e outro
            }
            //=======ATAQUE 2=======
            if (client1.acao == 2) {
                        dano = client1.hand.firstElement().attack2(); // Dano do ataque 2

                        // Verifica se o ataque eh super efetivo
                        Boolean superEffectiveAttack = client1.hand.firstElement().counterVerifyAttack(client2.hand.firstElement().type);
                        Boolean superEffectiveDefense = client2.hand.firstElement().counterVerifyDefense(client1.hand.firstElement().type);
                        
                        if (superEffectiveAttack){
                            dano *= 2;
                        }
                        if (superEffectiveDefense){
                            dano = dano/2;
                        }

                        System.out.println("Tipo do primeiro: " + client1.hand.firstElement().showType());
                        System.out.println("Tipo do segundo: " + client2.hand.firstElement().showType());

                        System.out.println("Cliente 1 deu " + client1.hand.firstElement().showAttack2() + " com o "
                                + client1.hand.firstElement().name + " de " + dano + " de dano");

                        System.out.print("Hp do Cliente 2 foi de " + client2.hand.firstElement().hp);
                        client2.hand.firstElement().hp -= dano; // Diminuir hp do cliente 2
                        System.out.println(" para " + client2.hand.firstElement().hp);

                        client1.acao = 0;
                        System.out.println();   //Pra dar uns espacinho entre um ataque e outro
                    }
                    //=======ATAQUE 3 (Buff de ataque)=======
                    if (client1.acao == 3) {

                        client1.hand.firstElement().attack3(); 

                        System.out.println("Tipo do primeiro: " + client1.hand.firstElement().showType());

                        System.out.println("Cliente 1 usou " + client1.hand.firstElement().showAttack3() + " com o "
                                + client1.hand.firstElement().name + " aumentando " + buff + " de damage");

                        //System.out.print("Hp do Cliente 2 foi de " + client2.hand.firstElement().hp);
                        //client2.hand.firstElement().hp -= dano; // Diminuir hp do cliente 2
                        //System.out.println(" para " + client2.hand.firstElement().hp);

                        client1.acao = 0;
                        System.out.println();   //Pra dar uns espacinho entre um ataque e outro
                    }
                    //=======ATAQUE 4 (Buff de defesa)=======
                    if (client1.acao == 4) {

                        client1.hand.firstElement().attack4(); 

                        System.out.println("Tipo do primeiro: " + client1.hand.firstElement().showType());

                        System.out.println("Cliente 1 usou " + client1.hand.firstElement().showAttack4() + " com o "
                                + client1.hand.firstElement().name + " aumentando " + buff + " de defense");

                        client1.acao = 0;
                        System.out.println();   //Pra dar uns espacinho entre um ataque e outro
                    }

                 
            //=========================ACOES CLIENTE 2======================================         
            //=======ATAQUE 1=======
            if (client2.acao == 1) {

                        dano = client2.hand.firstElement().attack1();

                        Boolean superEffectiveAttack = client2.hand.firstElement().counterVerifyAttack(client1.hand.firstElement().type);
                        Boolean superEffectiveDefense = client1.hand.firstElement().counterVerifyDefense(client2.hand.firstElement().type);

                        if (superEffectiveAttack) {
                            dano *= 2;
                        }
                        if (superEffectiveDefense) {
                            dano = dano / 2;
                        }

                        System.out.println("Tipo do primeiro: " + client1.hand.firstElement().showType());
                        System.out.println("Tipo do segundo: " + client2.hand.firstElement().showType());

                        System.out.println("Cliente 2 deu " + client2.hand.firstElement().showAttack1() + " com o "
                                + client2.hand.firstElement().name + " de " + dano + " de dano");

                        System.out.print("Hp do Cliente 1 foi de " + client1.hand.firstElement().hp);
                        client1.hand.firstElement().hp -= dano; // Diminuir hp do cliente 2
                        System.out.println(" para " + client1.hand.firstElement().hp);

                        client2.acao = 0;
                        System.out.println();   //Pra dar uns espacinho entre um ataque e outro
                    }
            //=======ATAQUE 2=======
            if (client2.acao == 2) {

                        dano = client2.hand.firstElement().attack2();

                        Boolean superEffectiveAttack = client2.hand.firstElement().counterVerifyAttack(client1.hand.firstElement().type);
                        Boolean superEffectiveDefense = client1.hand.firstElement().counterVerifyDefense(client2.hand.firstElement().type);

                        if (superEffectiveAttack) {
                            dano *= 1.5;
                        }
                        if (superEffectiveDefense) {
                            dano = dano*10 / 20;
                        }

                        System.out.println("Tipo do primeiro: " + client1.hand.firstElement().showType());
                        System.out.println("Tipo do segundo: " + client2.hand.firstElement().showType());

                        System.out.println("Cliente 2 deu " + client2.hand.firstElement().showAttack2() + " com o "
                                + client2.hand.firstElement().name + " de " + dano + " de dano");

                        System.out.print("Hp do Cliente 1 foi de " + client1.hand.firstElement().hp);
                        client1.hand.firstElement().hp -= dano; // Diminuir hp do cliente 2
                        System.out.println(" para " + client1.hand.firstElement().hp);

                        client2.acao = 0;
                        System.out.println();   //Pra dar uns espacinho entre um ataque e outro
                    }
            //=======ATAQUE 3 (Buff de ataque)=======
            if (client2.acao == 3) {

                        client2.hand.firstElement().attack3(); 

                        System.out.println("Tipo do primeiro: " + client1.hand.firstElement().showType());
                        System.out.println("Tipo do segundo: " + client2.hand.firstElement().showType());

                        System.out.println("Cliente 2 usou " + client2.hand.firstElement().showAttack3() + " com o "
                                + client2.hand.firstElement().name + " aumentando " + buff + " de damage");

                        //System.out.print("Hp do Cliente 2 foi de " + client2.hand.firstElement().hp);
                        //client2.hand.firstElement().hp -= dano; // Diminuir hp do cliente 2
                        //System.out.println(" para " + client2.hand.firstElement().hp);

                        client2.acao = 0;
                        System.out.println();   //Pra dar uns espacinho entre um ataque e outro
                    }
            //=======ATAQUE 4 (Buff de defesa)=======
             if (client2.acao == 3) {

                        client2.hand.firstElement().attack4(); 
                        

                        System.out.println("Tipo do primeiro: " + client1.hand.firstElement().showType());
                        System.out.println("Tipo do segundo: " + client2.hand.firstElement().showType());

                        System.out.println("Cliente 2 usou " + client2.hand.firstElement().showAttack4() + " com o "
                                + client2.hand.firstElement().name + " aumentando " + buff + " de defense");

                        //System.out.print("Hp do Cliente 2 foi de " + client2.hand.firstElement().hp);
                        //client2.hand.firstElement().hp -= dano; // Diminuir hp do cliente 2
                        //System.out.println(" para " + client2.hand.firstElement().hp);

                        client2.acao = 0;
                        System.out.println();   //Pra dar uns espacinho entre um ataque e outro
                    }
            
             //======DANO DE VENENO=======
             if (client2.hand.firstElement().poisoned) {
                        System.out.println("Cliente 2 recebeu 10 de dano por veneno");
                        client2.hand.firstElement().isPoisoned();
                        client2.hand.firstElement().hp -= 10;
                    }
            
            //==============================FIM DE TURNO====================================
            client1.turno =0;
            client2.turno =0;
            System.out.println("Fim do turno");
            pok1 = client1.hand.firstElement();
            pok2 = client2.hand.firstElement();
            event1 = 1;//Fazer voltar pra tela normal
            event2 = 1;//Fazer voltar pra tela normal
            
            //Deixar HP igual a 0 se ele ficar com HP negativo
            if(client1.hand.firstElement().hp <= 0){client1.hand.firstElement().hp=0;}
            if(client2.hand.firstElement().hp <= 0){client2.hand.firstElement().hp=0;}
            darRefresh=true;
          
    }
        
            //=================----------ATUALIZAÇÃO DA TELA--------------======================
            //Enviar as informações em uma string, pra depois ser transformado em um array no client
            //pra mandar isso pra tela, e conseguir atualizaer
           
            if (darRefresh == true){
            try {
                System.out.println("kk Atualizou");
             
             
              
             //Mandamento de msg brabo
             
             
              
                //PRINTAR A MAO DOS DOIS 
               // System.out.println("SERVER Cliente 1 ="+client1.hand.firstElement().showID()+"Cliente 2 ="+client2.hand.firstElement().showID());
              
                
                //INFORMACOES PARA O CLIENTE 1 
                //"10"(Event)(pok1)(hppok1)(poken)(kppoken)(pok2)(hppok2)(pok3)(hppok3)(pok4)(hppok4)(pok5)(hppok5)(pok6)(hppok6)
                client1.out.writeUTF("10#"+event1+"#"+client1.hand.firstElement().showID()+"#"+client1.hand.firstElement().hp+"#" //Seu pok principal
                        +client2.hand.firstElement().showID()+"#"+client2.hand.firstElement().hp+"#" //Pok principal do inimigo
                        +client1.hand.get(1).showID()+"#"+client1.hand.get(1).hp+"#" //Pok 2
                        +client1.hand.get(2).showID()+"#"+client1.hand.get(2).hp+"#" //Pok 3 
                        +client1.hand.get(3).showID()+"#"+client1.hand.get(3).hp+"#" //Pok 4 
                        +client1.hand.get(4).showID()+"#"+client1.hand.get(4).hp+"#" //Pok 5
                        +client1.hand.get(5).showID()+"#"+client1.hand.get(5).hp);   //Pok 6
                        
                
                //INFORMACOES PARA O CLIENTE 2 
                //"10"(Event)(pok1)(hppok1)(poken)(kppoken)(pok2)(hppok2)(pok3)(hppok3)(pok4)(hppok4)(pok5)(hppok5)(pok6)(hppok6)
                client2.out.writeUTF("10#"+event2+"#"+client2.hand.firstElement().showID()+"#"+client2.hand.firstElement().hp+"#"
                        +client1.hand.firstElement().showID()+"#"+client1.hand.firstElement().hp+"#"
                        +client2.hand.get(1).showID()+"#"+client2.hand.get(1).hp+"#" //Pok 2
                        +client2.hand.get(2).showID()+"#"+client2.hand.get(2).hp+"#" //Pok 3
                        +client2.hand.get(3).showID()+"#"+client2.hand.get(3).hp+"#" //Pok 4 
                        +client2.hand.get(4).showID()+"#"+client2.hand.get(4).hp+"#" //Pok 5
                        +client2.hand.get(5).showID()+"#"+client2.hand.get(5).hp);   //Pok 6
                 
                
            //    System.out.println("SERVER Cliente 1 ="+client1.hand.firstElement().showID()+"Cliente 2 ="+client2.hand.firstElement().showID());
                event1 = 1;//Resetar o evento dps de enviar, pra na próxima chamada garantir que vai voltar pra tela certa
                event2 = 1;//Resetar o evento dps de enviar, pra na proxima chamada garantir que vai voltar pra tela certa
            } catch (IOException ex) {
                Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);         
                } 
            darRefresh=false;
            }
          //Refresh soh no client 1  
          if(darRefresh1 == true){
                try {
                    //"10"(Event)(pok1)(hppok1)(poken)(kppoken)(pok2)(hppok2)(pok3)(hppok3)(pok4)(hppok4)(pok5)(hppok5)(pok6)(hppok6)
                      
                    //Prioridade = 1 = Refresh com evento = 3, Prioridade = 1 = refresh normal 
                    if(client1.prioridade ==1){
                    event1 = 3;    
                    client1.prioridade =0;
                    }else if(client1.prioridade ==2){
                    client1.prioridade =0;
                    }
                    
                    client1.out.writeUTF("10#"+event1+"#"+client1.hand.firstElement().showID()+"#"+client1.hand.firstElement().hp+"#" //Seu pok principal
                        +client2.hand.firstElement().showID()+"#"+client2.hand.firstElement().hp+"#" //Pok principal do inimigo
                        +client1.hand.get(1).showID()+"#"+client1.hand.get(1).hp+"#" //Pok 2
                        +client1.hand.get(2).showID()+"#"+client1.hand.get(2).hp+"#" //Pok 3 
                        +client1.hand.get(3).showID()+"#"+client1.hand.get(3).hp+"#" //Pok 4 
                        +client1.hand.get(4).showID()+"#"+client1.hand.get(4).hp+"#" //Pok 5
                        +client1.hand.get(5).showID()+"#"+client1.hand.get(5).hp);   //Pok 6
                } catch (IOException ex) {
                    Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
                }
          event1 = 1;   
          darRefresh1 = false;
          }
          //Refresh soh no client 2
          if(darRefresh2 == true){
                try {
                    if(client2.prioridade ==1){
                    event2 = 3;    
                    client2.prioridade =0;
                    }else if(client2.prioridade ==2){
                    client2.prioridade =0;
                }
                   client2.out.writeUTF("10#"+event2+"#"+client2.hand.firstElement().showID()+"#"+client2.hand.firstElement().hp+"#"
                        +client1.hand.firstElement().showID()+"#"+client1.hand.firstElement().hp+"#"
                        +client2.hand.get(1).showID()+"#"+client2.hand.get(1).hp+"#" //Pok 2
                        +client2.hand.get(2).showID()+"#"+client2.hand.get(2).hp+"#" //Pok 3
                        +client2.hand.get(3).showID()+"#"+client2.hand.get(3).hp+"#" //Pok 4 
                        +client2.hand.get(4).showID()+"#"+client2.hand.get(4).hp+"#" //Pok 5
                        +client2.hand.get(5).showID()+"#"+client2.hand.get(5).hp);   //Pok 6  
                } catch (IOException ex) {
                    Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
                }
          event2 = 1;    
          darRefresh2 = false;
          }
            //Tratamento das prioridades
          if (client1.prioridade > 0){
          darRefresh1 = true;
          }
          if (client2.prioridade > 0){
          darRefresh2 = true;
          }
          
          // prioridade = 1; //Refrescar a tela //Com evento = 3
          // prioridade = 2; //Refrescar a tela //Com evento normal
          
    }
    }
}


//Classe pra randomizar os pokemons iniciais
class PokemonSelector{
    ArrayList<String> pokemons;

    public PokemonSelector() {
        pokemons = new ArrayList<>(Arrays.asList("Charmander", "Squirtle", "Bulbasaur", "Caterpie", "Weedle",
                "Butterfree", "Pidgey", "Rattata", "Spearow", "Ekans", "Pikachu", "Sandshrew", "Nidoran"));
    }

    public String getRandPoke() {
       

        Random rand = new Random();
        int indiceEscolhido = rand.nextInt(pokemons.size());
        String pokemonEscolhido = pokemons.get(indiceEscolhido);        
        pokemons.remove(indiceEscolhido);

        return pokemonEscolhido;


    }
}

class Pokemon {

    String name;  
    int hp = 100;
    int damage = 10;
    int damage2 = 10;
    int defense = 4;
    int var1 =3;
    int var2 = 5;
    int acc1 = 90;
    int acc2 = 75;
    String type;
    Boolean poisoned = false; //Aumenta o dano que o caba d�
    int poisonTime;
    // String attack1;
    // String attack2;

    public Pokemon(String name) {

        this.name = name;

        String[] water = { "Squirtle" };
        String[] fire = { "Charmander" };
        String[] grass = { "Bulbasaur" };
        String[] bug = { "Caterpie", "Weedle", "Butterfree"};
        String[] normal = { "Pidgey", "Rattata", "Spearow" };
        String[] poison = { "Ekans", "Nidoran" }; 
        String[] electric = { "Pikachu" }; 
        String[] ground = { "Sandshrew" };
        
        
        for (String pok : water) {
            if (name == pok) {
                type = "Water";
            }
        }

        for (String pok : fire) {
            if (name == pok) {
                type = "Fire";
            }
        }

        for (String pok : grass) {
            if (name == pok) {
                type = "Grass";
            }
        }
        
        for (String pok : bug) {
            if (name == pok) {
                type = "Bug";
            }
        }
        
        for (String pok : normal) {
            if (name == pok) {
                type = "Normal";
            }
        }
        
        for (String pok : poison) {
            if (name == pok) {
                type = "Poison";
            }
        }
        
        for (String pok : electric) {
            if (name == pok) {
                type = "Electric";
            }
        }
        
        for (String pok : ground) {
            if (name == pok) {
                type = "Ground";
            }
        }
        setarDano(showID());
    }

    public String showName() {
        return this.name;
    }

    public int showID() {

        int count = 1;
        String[] pokemons = { "Charmander", "Squirtle", "Bulbasaur", "Caterpie", "Metapod", "Butterfree",
                              "Pidgey", "Rattata", "Spearow", "Ekans", "Pikachu", "Sandshrew", "Nidoran" };

        for (String pok : pokemons) {

            if (name == pok) {
                return (count);
            }

            count += 1;
        }

        return 10;

    }    

    /* public String getRandPoke() {
        String[] pokemons = { "Charmander", "Squirtle", "Bulbasaur", "Caterpie", "Metapod", "Butterfree", "Pidgey",
                "Rattata", "Spearow", "Ekans", "Pikachu", "Sandshrew", "Nidoran" };

        Random rand = new Random();
        int escolhido = rand.nextInt(pokemons.length - 1);

        return pokemons[escolhido];
    } */

    public String showType() {

        return (type);

    }

    public Boolean counterVerifyAttack(String testador){
        
        if (this.type.equals("Fire")){
            if (testador.equals("Grass") || testador.equals("Bug")){
                return (true);
            }
        }
        if (this.type.equals("Water")){
            if (testador.equals("Fire") || testador.equals("Ground")){
                return (true);
            }
        }
        if (this.type.equals("Grass")){
            if (testador.equals("Water") || testador.equals("Ground")){
                return (true);
            }
        }        
        if (this.type.equals("Bug")){
            if (testador.equals("Grass")){
                return (true);
            }
        }
        if (this.type.equals("Poison")){
            if (testador.equals("Grass")){
                return (true);
            }
        }
        if (this.type.equals("Electric")){
            if (testador.equals("Water")){
                return (true);
            }
        }
        if (this.type.equals("Ground")){
            if (testador.equals("Fire") || testador.equals("Electric") || testador.equals("Poison")){
                return (true);
            }
        }
        
        
        return (false);
    }

    public Boolean counterVerifyDefense(String testador){
        
        if (this.type.equals("Fire")){
            if (testador.equals("Grass")){
                return (true);
            }
        }
        if (this.type.equals("Water")){
            if (testador.equals("Fire")){
                return (true);
            }
        }
        if (this.type.equals("Grass")){
            if (testador.equals("Water")){
                return (true);
            }
        }
        
        return (false);
    }
    
    public String showAttack1() {

        if (name.equals("Charmander")) {
            return ("Blaze");
        }
        if (name.equals("Squirtle")) {
            return ("Torrent");
        }
        if (name.equals("Bulbasaur")) {
            return ("Overgrow");
        }	
        if (name.equals("Caterpie")) {
            return ("Omnislash");
        }
        if (name.equals("Weedle")) {
            return ("Tail atack");
        }
        if (name.equals("Buterfree")) {
            return ("Confusion");
        }
        if (name.equals("Pidgey")) {
            return ("Lapada segura");
        }
        if (name.equals("Rattata")) {
            return ("Bite");
        }
        if (name.equals("Spearow")) {
            return ("Peck");
        }
        if (name.equals("Ekans")) {
            return ("Bite");
        }
	if (name.equals("Pikachu")) {
            return ("Thundershock");
        }
        if (name.equals("Sandshrew")) {
            return ("Sand Attack");
        }
         if (name.equals("Nidoran")) {
            return ("Gyro Ball");
        }
        return ("nothing");
    }

    public String showAttack2() {

         if (name.equals("Charmander")) {
            return ("Solar Power");
        }

        if (name.equals("Squirtle")) {
            return ("Rain Dish");
        }

        if (name.equals("Bulbasaur")) {
            return ("Chlorophyll");
        }
        if (name.equals("Caterpie")) {
            return ("Gaea Rage");
        }
        if (name.equals("Weedle")) {
            return ("Poison sting");
        }
        if (name.equals("Buterfree")) {
            return ("Whirlwind");
        }
        if (name.equals("Pidgey")) {
            return ("Voada");
        }
        if (name.equals("Rattata")) {
            return ("Tail atack");
        }
        if (name.equals("Spearow")) {
            return ("Fury Attack");
        }
         if (name.equals("Ekans")) {
            return ("Tail Attack");
        }
	if (name.equals("Pikachu")) {
            return ("Lapada de rabo");
        }
        if (name.equals("Sandshrew")) {
            return ("Spin Attack");
        }
         if (name.equals("Nidoran")) {
            return ("Bite");
        }

        return ("nothing");

    }   

    public String showAttack3() {

         if (name.equals("Charmander")) {
            return ("Dragon Dance");
        }

        if (name.equals("Squirtle")) {
            return ("Energy Drink");
        }

        if (name.equals("Bulbasaur")) {
            return ("Growth");
        }
        if (name.equals("Caterpie")) {
            return ("Focus");
        }
        if (name.equals("Weedle")) {
            return ("Fertilizer ");
        }
        if (name.equals("Buterfree")) {
            return ("Dar o gas");
        }
        if (name.equals("Pidgey")) {
            return ("Comer barro");
        }
        if (name.equals("Rattata")) {
            return ("Tarukaja");
        }
        if (name.equals("Spearow")) {
            return ("Growth");
        }
        if (name.equals("Ekans")) {
            return ("Comer semente");
        }
	if (name.equals("Pikachu")) {
            return ("Pilha duracell");
        }
        if (name.equals("Sandshrew")) {
            return ("Comer areia");
        }
         if (name.equals("Nidoran")) {
            return ("Comer cebola");
        }

        return ("nothing");

    }

    public String showAttack4() {

          if (name.equals("Charmander")) {
            return ("Firewall");
        }

        if (name.equals("Squirtle")) {
            return ("Water Wall");
        }

        if (name.equals("Bulbasaur")) {
            return ("Seed Wall");
        }
        if (name.equals("Caterpie")) {
            return ("Kaclang");
        }
        if (name.equals("Weedle")) {
            return ("Bug Wall");
        }
        if (name.equals("Buterfree")) {
            return ("Endurecer");
        }
        if (name.equals("Pidgey")) {
            return ("Placebo");
        }
        if (name.equals("Rattata")) {
            return ("Rakukaja");
        }
        if (name.equals("Spearow")) {
            return ("Pombo Wall");
        }
        if (name.equals("Ekans")) {
            return ("Cobra Wall");
        }
	if (name.equals("Pikachu")) {
            return ("Rato Wall");
        }
        if (name.equals("Sandshrew")) {
            return ("Castelim dareia");
        }
        if (name.equals("Nidoran")) {
            return ("Dar o gas");
        }

        return ("nothing");

    }
    
    public void setarDano(int numero){
        switch (numero){
            case 1: //Charmander
            damage =10;
            damage2=15;
            var1=3;
            var2=10;
            acc1=90;
            acc2=75;
            defense=4;
            break;   
          
            case 2://Squirtle
            damage =10;
            damage2=15;
            var1=3;
            var2=10;
            acc1=90;
            acc2=75;
            defense=4;   
            break;
            
            case 3: //Bulbassauro
            damage =10;
            damage2=10;
            var1=3;
            var2=5;
            acc1=90;
            acc2=75;
            defense=4;    
            break;
            
            case 4://Caterpie
            damage = 180;
            damage2 = 30;
            var1= 5;
            var2 = 20;
            acc1 = 100;
            acc2 = 40;
            defense =2;
            break;
            
            case 5://Weedle
            damage =15;
            damage2=10;
            var1=3;
            var2=8;
            acc1=80;
            acc2=65;
            defense=3;
            break;
            
            case 6: //Borboleta
            damage =15;
            damage2=15;
            var1=3;
            var2=5;
            acc1=80;
            acc2=70;
            defense=3;
            break;
            
            case 7: //Pidey
            damage =20;
            damage2=50;
            var1=3;
            var2=10;
            acc1=90;
            acc2=30;
            defense=4;
            break;
            
            case 8: //Rattata
            damage =10;
            damage2=15;
            var1=4;
            var2=10;
            acc1=95;
            acc2=65;
            defense=6;
            break;
            
            case 9://Spearow
            damage =10;
            damage2=15;
            var1=3;
            var2=10;
            acc1=90;
            acc2=75;
            defense=4;
            break;
            
            case 10: //Ekans
            damage =15;
            damage2=15;
            var1=5;
            var2=10;
            acc1=90;
            acc2=75;
            defense=4;
            break;
            
            case 11: //Pikachu
            damage =10;
            damage2=15;
            var1=3;
            var2=10;
            acc1=90;
            acc2=75;
            defense=4;
            break;
            
            case 12: //AreiaShrew
            damage =10;
            damage2=15;
            var1=3;
            var2=10;
            acc1=90;
            acc2=75;
            defense=4;
            break;
            
            case 13: //Coelho rosa
            damage =10;
            damage2=25;
            var1=3;
            var2=20;
            acc1=90;
            acc2=55;
            defense=4;
            break;    
        
        }
    }

    public int attack1() {

        // Aletoriaedade do attack
        Random rand = new Random();
        int errou = rand.nextInt(100);
        int n = rand.nextInt(var1); // Adicional do dano
        int x = rand.nextInt(3); // Controle de subida ou descida de dano;

        if (errou > acc1) {
            return 0;
        }
        if (x == 0) {
            return damage + n;
        } else if (x == 1) {
            return damage - n ;
        } else {
            return damage;
        }
    }

    public int attack2() {

        // Aletoriaedade do attack
        Random rand = new Random();
        int errou = rand.nextInt(100);
        int n = rand.nextInt(var2); // Adicional do dano
        int x = rand.nextInt(3); // Controle de subida ou descida de dano;

        if (errou > acc2) {
            return 0;
        }
        if (x == 0) {
            return damage2 + (2 * n);
        }  else if (x == 1) {
            return (damage2 - n);
        }  else {
            return (damage2);
        }
    }

    public void attack3() {

        damage = damage + 10;
        damage2 = damage2 +10;

    }

    public void attack4() {

        defense = defense + 5;

    }

    public void poisonThis(){
        this.poisoned = true;
    }
    
    public void isPoisoned(){
        
        if (poisonTime <= 4){
            poisonTime += 1;
            
        }
        else {
            poisonTime = 0;
            this.poisoned = false;
        }
    }

    //Uns getterzinho se for precisar
    public double getDefense(){
        return defense;
    }

    public double getDamage(){
        return damage;
    }

    



}
