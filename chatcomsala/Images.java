/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcomsala;

//Desenhador de imagens 
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.ImageIcon; //Negocim pra carregar imagens
import javax.swing.JFrame;
        
public class Images extends JFrame {
    public static void main (String[] args){
        DisplayMode dm = new DisplayMode(800,600,16,DisplayMode.REFRESH_RATE_UNKNOWN); //Resolução,meme de cor eu acho (8,16,32 quanto menor mais ligero),FPS)
        Images i = new Images();
        i.run(dm);
    }
    private Screen s; //Tem que botar fora do run pra geral ter acesso 
    private Image bg; //Background
    private Image pic;
    private Image pk1; //Seu pokemon principal
    private Image pk2; //Pokemon do adversario
    private Image pok1_1,pok2_1,pok3_1,pok4_1,pok5_1,pok6_1,pok7_1,pok8_1,pok9_1,pok10_1,pok11_1,pok12_1,pok13_1;
    private Image pok1_2,pok2_2,pok3_2,pok4_2,pok5_2,pok6_2,pok7_2,pok8_2,pok9_2,pok10_2,pok11_2,pok12_2,pok13_2;
    private Image n0,n1,n2,n3,n4,n5,n6,n7,n8,n9;//Carregar os numeros
    private Image la,lb,lc,ld,le,lf,lg,lh,li,lj,lk,ll,lm,ln,lo,lp,lq,lr,ls,lt,lu,lv,lw,lx,ly,lz,lnull ;//Letras pretas
    private Image la2,lb2,lc2,ld2,le2,lf2,lg2,lh2,li2,lj2,lk2,ll2,lm2,ln2,lo2,lp2,lq2,lr2,ls2,lt2,lu2,lv2,lw2,lx2,ly2,lz2,lnull2,lint2;//Letras brancas
    private Image la3,lb3,lc3,ld3,le3,lf3,lg3,lh3,li3,lj3,lk3,ll3,lm3,ln3,lo3,lp3,lq3,lr3,ls3,lt3,lu3,lv3,lw3,lx3,ly3,lz3,lnull3,l30,l31,l32,l33,l34,l35,l36,l37,l38,l39;    
    private Image pkp1,pkp2,pkp3,pkp4,pkp5,pkp6,pkp7,pkp8,pkp9,pkp10,pkp11,pkp12,pkp13; //Imagem das miniaturas dos pokemons
    private Image pkp1f,pkp2f,pkp3f,pkp4f,pkp5f,pkp6f,pkp7f,pkp8f,pkp9f,pkp10f,pkp11f,pkp12f,pkp13f; //Imagem das miniaturas dos pokemons soh que fainted
    private Image hp1gr,hp1ye,hp1rd,hp2gr,hp2ye,hp2rd,hp3gr,hp3ye,hp3rd,hp4gr,hp4ye,hp4rd; //Os 4 tipos de barras de hp
    private Image hp1c,hp1d,hp1u; //Centena, dezena e unidade do HP do seu pokemon
    private Image hp1Hud,hp2Hud; //Barra de HP pro teu pokemon e pro pokemon do inimigo
    private Image hpb,hpb2; //Barra do hp (Do seu pokemon e do adversário)
    String pokemon1Nome,pokemon2Nome; //Nome do seu pokemon e do adversario
   // String pokemonAux; //Nome de pokemon auxiliar
    String textoAux; //Texto auxiliar 
    
    String ultimaEntradaRecebida; //Ultima entrada recebida
    
    private boolean loaded; //Saber se uma imagem carregou ou n
    Vector<Integer> eventos = new Vector<>(); //Array que vai dizer o que vai precisar mostrar na tela (vai ser alterado pelo server)   
    Vector<Integer> eventosAnterior = new Vector<>(); //Array que vai guardar o evento anterior
    boolean eventoPreenchido = false; //Fica true quando receber um string de eventos pela primeira vez

    
    public void run (DisplayMode dm){
        setBackground(Color.BLACK);

        loaded = false; //Nada ainda carregou, por isso que é falso
        
        s = new Screen(); //Agora da pra usar todas as paradinhas que criamos na classe Screen
        try{
           s.setScreen(dm,this);
           loadpics(); //Funcao pra carregar imagnes
           System.out.println("Carregou imagens");
        }
        catch(Exception ex){}
        
    }
    public void mudarEvento(String eventoRecebido){
    System.out.println("kk mudei o evento"); //Ta chegando aqui
    System.out.println("Evento recebido 4 real ="+eventoRecebido);
    ultimaEntradaRecebida = eventoRecebido;
    eventos.clear();
    //Transformar em array de string
    String[] arrayStr = eventoRecebido.split("#"); //Cria uma array com as info separado em cada index, em strings

    //Transformar em vetor de strings
    Vector<String> vetorStr = new Vector<String>(Arrays.asList(arrayStr));
    
    //Passar o vetor de strings para o vetor eventos 
    for(String s : vetorStr) {
    eventos.add(Integer.parseInt(s));
    }
    eventoPreenchido =true;
    
    //System.out.println("STRING RECEBIDA DO SERVER");
    //System.out.println("Meme = "+eventos.get(0));
    //System.out.println("Evento = "+eventos.get(1));
    //System.out.println("Pok1 = "+eventos.get(2));
    //System.out.println("HpPok1 = "+eventos.get(3));
    //System.out.println("Pok2 = "+eventos.get(4));
    //System.out.println("HpPok2 = "+eventos.get(5));
    
    //if((eventos.get(0)==0) || (eventos.get(1)==0)|| (eventos.get(2)==0) || (eventos.get(3)==0) ||(eventos.get(4)==0) || (eventos.get(5)==0)){
    //System.out.println("Bugou e recebeu uma informacao valendo 0, nao vai carregar imagens");    
    //}else{
   // }
    //"10"(Event)(pok1)(hppok1)(pok2)(hppok2)(pok1_2) (pok1_3)
    //"10"(  1  )(  2 )(   3  )(  4 )(   5  )(  6   ) (  7   )
    
    //REMAKE
    //"10"(Event)(pok1)(hppok1)(poken)(kppoken)(pok2)(hppok2)(pok3)(hppok3)(pok4)(hppok4)(pok5)(hppok5)(pok6)(hppok6)
    //"10"(  1  )( 2  )(  3   )(  4  )(   5   )(  6 )(  7   )(  8 )(  9  )(  10 )(  11 )(  12 )(  13 )( 14 )(   15  )
    loadpics();  //loadpics() ja tem um repaint()
    
    }
    
    //load pingtures
    public void loadpics(){  //Carregar as imagens que vao ser utilizadas  
        
      if (eventoPreenchido ==false){
      bg = new ImageIcon("X:\\UFPB\\Imagens\\logo.png").getImage(); //Menu de espera
      //Carregar skin dos numeros
      n0 = new ImageIcon("X:\\UFPB\\Imagens\\n0.png").getImage(); //Carregar numero 0
      n1 = new ImageIcon("X:\\UFPB\\Imagens\\n1.png").getImage(); //Carregar numero 1
      n2 = new ImageIcon("X:\\UFPB\\Imagens\\n2.png").getImage(); //Carregar numero 2
      n3 = new ImageIcon("X:\\UFPB\\Imagens\\n3.png").getImage(); //Carregar numero 3
      n4 = new ImageIcon("X:\\UFPB\\Imagens\\n4.png").getImage(); //Carregar numero 4
      n5 = new ImageIcon("X:\\UFPB\\Imagens\\n5.png").getImage(); //Carregar numero 5
      n6 = new ImageIcon("X:\\UFPB\\Imagens\\n6.png").getImage(); //Carregar numero 6
      n7 = new ImageIcon("X:\\UFPB\\Imagens\\n7.png").getImage(); //Carregar numero 7
      n8 = new ImageIcon("X:\\UFPB\\Imagens\\n8.png").getImage(); //Carregar numero 8
      n9 = new ImageIcon("X:\\UFPB\\Imagens\\n9.png").getImage(); //Carregar numero 9
      //Carregar skin das letras
      //Ltras pretas 
      la = new ImageIcon("X:\\UFPB\\Imagens\\la.png").getImage(); //Carregar letra a
      lb = new ImageIcon("X:\\UFPB\\Imagens\\lb.png").getImage(); //Carregar letra b
      lc = new ImageIcon("X:\\UFPB\\Imagens\\lc.png").getImage(); //Carregar letra c
      ld = new ImageIcon("X:\\UFPB\\Imagens\\ld.png").getImage(); //Carregar letra d
      le = new ImageIcon("X:\\UFPB\\Imagens\\le.png").getImage(); //Carregar letra e
      lf = new ImageIcon("X:\\UFPB\\Imagens\\lf.png").getImage(); //Carregar letra f
      lg = new ImageIcon("X:\\UFPB\\Imagens\\lg.png").getImage(); //Carregar letra g
      lh = new ImageIcon("X:\\UFPB\\Imagens\\lh.png").getImage(); //Carregar letra h
      li = new ImageIcon("X:\\UFPB\\Imagens\\li.png").getImage(); //Carregar letra i
      lj = new ImageIcon("X:\\UFPB\\Imagens\\lj.png").getImage(); //Carregar letra j
      lk = new ImageIcon("X:\\UFPB\\Imagens\\lk.png").getImage(); //Carregar letra k
      ll = new ImageIcon("X:\\UFPB\\Imagens\\ll.png").getImage(); //Carregar letra l
      lm = new ImageIcon("X:\\UFPB\\Imagens\\lm.png").getImage(); //Carregar letra m
      ln = new ImageIcon("X:\\UFPB\\Imagens\\ln.png").getImage(); //Carregar letra n
      lo = new ImageIcon("X:\\UFPB\\Imagens\\lo.png").getImage(); //Carregar letra o
      lp = new ImageIcon("X:\\UFPB\\Imagens\\lp.png").getImage(); //Carregar letra p
      lq = new ImageIcon("X:\\UFPB\\Imagens\\lq.png").getImage(); //Carregar letra q
      lr = new ImageIcon("X:\\UFPB\\Imagens\\lr.png").getImage(); //Carregar letra r
      ls = new ImageIcon("X:\\UFPB\\Imagens\\ls.png").getImage(); //Carregar letra s
      lt = new ImageIcon("X:\\UFPB\\Imagens\\lt.png").getImage(); //Carregar letra t
      lu = new ImageIcon("X:\\UFPB\\Imagens\\lu.png").getImage(); //Carregar letra u
      lv = new ImageIcon("X:\\UFPB\\Imagens\\lv.png").getImage(); //Carregar letra v
      lx = new ImageIcon("X:\\UFPB\\Imagens\\lx.png").getImage(); //Carregar letra x
      ly = new ImageIcon("X:\\UFPB\\Imagens\\ly.png").getImage(); //Carregar letra y
      lw = new ImageIcon("X:\\UFPB\\Imagens\\lw.png").getImage(); //Carregar letra w
      lz = new ImageIcon("X:\\UFPB\\Imagens\\lz.png").getImage(); //Carregar letra z
      lnull = new ImageIcon("X:\\UFPB\\Imagens\\lnull.png").getImage(); //Carregar espaaco em branco
      //Letras brncas
      la2 = new ImageIcon("X:\\UFPB\\Imagens\\l2a.png").getImage(); //Carregar letra a
      lb2 = new ImageIcon("X:\\UFPB\\Imagens\\l2b.png").getImage(); //Carregar letra b
      lc2 = new ImageIcon("X:\\UFPB\\Imagens\\l2c.png").getImage(); //Carregar letra c
      ld2 = new ImageIcon("X:\\UFPB\\Imagens\\l2d.png").getImage(); //Carregar letra d
      le2 = new ImageIcon("X:\\UFPB\\Imagens\\l2e.png").getImage(); //Carregar letra e
      lf2 = new ImageIcon("X:\\UFPB\\Imagens\\l2f.png").getImage(); //Carregar letra f
      lg2 = new ImageIcon("X:\\UFPB\\Imagens\\l2g.png").getImage(); //Carregar letra g
      lh2 = new ImageIcon("X:\\UFPB\\Imagens\\l2h.png").getImage(); //Carregar letra h
      li2 = new ImageIcon("X:\\UFPB\\Imagens\\l2i.png").getImage(); //Carregar letra i
      lj2 = new ImageIcon("X:\\UFPB\\Imagens\\l2j.png").getImage(); //Carregar letra j
      lk2 = new ImageIcon("X:\\UFPB\\Imagens\\l2k.png").getImage(); //Carregar letra k
      ll2 = new ImageIcon("X:\\UFPB\\Imagens\\l2l.png").getImage(); //Carregar letra l
      lm2 = new ImageIcon("X:\\UFPB\\Imagens\\l2m.png").getImage(); //Carregar letra m
      ln2 = new ImageIcon("X:\\UFPB\\Imagens\\l2n.png").getImage(); //Carregar letra n
      lo2 = new ImageIcon("X:\\UFPB\\Imagens\\l2o.png").getImage(); //Carregar letra o
      lp2 = new ImageIcon("X:\\UFPB\\Imagens\\l2p.png").getImage(); //Carregar letra p
      lq2 = new ImageIcon("X:\\UFPB\\Imagens\\l2q.png").getImage(); //Carregar letra q
      lr2 = new ImageIcon("X:\\UFPB\\Imagens\\l2r.png").getImage(); //Carregar letra r
      ls2 = new ImageIcon("X:\\UFPB\\Imagens\\l2s.png").getImage(); //Carregar letra s
      lt2 = new ImageIcon("X:\\UFPB\\Imagens\\l2t.png").getImage(); //Carregar letra t
      lu2 = new ImageIcon("X:\\UFPB\\Imagens\\l2u.png").getImage(); //Carregar letra u
      lv2 = new ImageIcon("X:\\UFPB\\Imagens\\l2v.png").getImage(); //Carregar letra v
      lx2 = new ImageIcon("X:\\UFPB\\Imagens\\l2x.png").getImage(); //Carregar letra x
      ly2 = new ImageIcon("X:\\UFPB\\Imagens\\l2y.png").getImage(); //Carregar letra y
      lw2 = new ImageIcon("X:\\UFPB\\Imagens\\l2w.png").getImage(); //Carregar letra w
      lz2 = new ImageIcon("X:\\UFPB\\Imagens\\l2z.png").getImage(); //Carregar letra z
      lnull2 = new ImageIcon("X:\\UFPB\\Imagens\\l2null.png").getImage(); //Carregar espaaco em branco
      lint2 = new ImageIcon("X:\\UFPB\\Imagens\\l2int.png").getImage(); //Carregar interrogacao
      
      //Letras da tela de troca de pokemons 
      la3 = new ImageIcon("X:\\UFPB\\Imagens\\l3a.png").getImage(); //Carregar letra a
      lb3 = new ImageIcon("X:\\UFPB\\Imagens\\l3b.png").getImage(); //Carregar letra b
      lc3 = new ImageIcon("X:\\UFPB\\Imagens\\l3c.png").getImage(); //Carregar letra c
      ld3 = new ImageIcon("X:\\UFPB\\Imagens\\l3d.png").getImage(); //Carregar letra d
      le3 = new ImageIcon("X:\\UFPB\\Imagens\\l3e.png").getImage(); //Carregar letra e
      lf3 = new ImageIcon("X:\\UFPB\\Imagens\\l3f.png").getImage(); //Carregar letra f
      lg3 = new ImageIcon("X:\\UFPB\\Imagens\\l3g.png").getImage(); //Carregar letra g
      lh3 = new ImageIcon("X:\\UFPB\\Imagens\\l3h.png").getImage(); //Carregar letra h
      li3 = new ImageIcon("X:\\UFPB\\Imagens\\l3i.png").getImage(); //Carregar letra i
      lj3 = new ImageIcon("X:\\UFPB\\Imagens\\l3j.png").getImage(); //Carregar letra j
      lk3 = new ImageIcon("X:\\UFPB\\Imagens\\l3k.png").getImage(); //Carregar letra k
      ll3 = new ImageIcon("X:\\UFPB\\Imagens\\l3l.png").getImage(); //Carregar letra l
      lm3 = new ImageIcon("X:\\UFPB\\Imagens\\l3m.png").getImage(); //Carregar letra m
      ln3 = new ImageIcon("X:\\UFPB\\Imagens\\l3n.png").getImage(); //Carregar letra n
      lo3 = new ImageIcon("X:\\UFPB\\Imagens\\l3o.png").getImage(); //Carregar letra o
      lp3 = new ImageIcon("X:\\UFPB\\Imagens\\l3p.png").getImage(); //Carregar letra p
      lq3 = new ImageIcon("X:\\UFPB\\Imagens\\l3q.png").getImage(); //Carregar letra q
      lr3 = new ImageIcon("X:\\UFPB\\Imagens\\l3r.png").getImage(); //Carregar letra r
      ls3 = new ImageIcon("X:\\UFPB\\Imagens\\l3s.png").getImage(); //Carregar letra s
      lt3 = new ImageIcon("X:\\UFPB\\Imagens\\l3t.png").getImage(); //Carregar letra t
      lu3 = new ImageIcon("X:\\UFPB\\Imagens\\l3u.png").getImage(); //Carregar letra u
      lv3 = new ImageIcon("X:\\UFPB\\Imagens\\l3v.png").getImage(); //Carregar letra v
      lx3 = new ImageIcon("X:\\UFPB\\Imagens\\l3x.png").getImage(); //Carregar letra x
      ly3 = new ImageIcon("X:\\UFPB\\Imagens\\l3y.png").getImage(); //Carregar letra y
      lw3 = new ImageIcon("X:\\UFPB\\Imagens\\l3w.png").getImage(); //Carregar letra w
      lz3 = new ImageIcon("X:\\UFPB\\Imagens\\l3z.png").getImage(); //Carregar letra z
      lnull3 = new ImageIcon("X:\\UFPB\\Imagens\\l3null.png").getImage(); //Carregar espaaco em branco
      //Numeros de troca de pokemon
      l30 = new ImageIcon("X:\\UFPB\\Imagens\\l30.png").getImage(); //Carregar numero 0
      l31 = new ImageIcon("X:\\UFPB\\Imagens\\l31.png").getImage(); //Carregar numero 1
      l32 = new ImageIcon("X:\\UFPB\\Imagens\\l32.png").getImage(); //Carregar numero 2
      l33 = new ImageIcon("X:\\UFPB\\Imagens\\l33.png").getImage(); //Carregar numero 3
      l34 = new ImageIcon("X:\\UFPB\\Imagens\\l34.png").getImage(); //Carregar numero 4
      l35 = new ImageIcon("X:\\UFPB\\Imagens\\l35.png").getImage(); //Carregar numero 5
      l36 = new ImageIcon("X:\\UFPB\\Imagens\\l36.png").getImage(); //Carregar numero 6
      l37 = new ImageIcon("X:\\UFPB\\Imagens\\l37.png").getImage(); //Carregar numero 7
      l38 = new ImageIcon("X:\\UFPB\\Imagens\\l38.png").getImage(); //Carregar numero 8
      l39 = new ImageIcon("X:\\UFPB\\Imagens\\l39.png").getImage(); //Carregar numero 9
      
      //CARREGAR TODOS OS POKEMONS (POSICAO 1)
      pok1_1 = new ImageIcon("X:\\UFPB\\Imagens\\pok1_1.png").getImage();
      pok2_1 = new ImageIcon("X:\\UFPB\\Imagens\\pok2_1.png").getImage();
      pok3_1 = new ImageIcon("X:\\UFPB\\Imagens\\pok3_1.png").getImage();
      pok4_1 = new ImageIcon("X:\\UFPB\\Imagens\\pok4_1.png").getImage();
      pok5_1 = new ImageIcon("X:\\UFPB\\Imagens\\pok5_1.png").getImage();
      pok6_1 = new ImageIcon("X:\\UFPB\\Imagens\\pok6_1.png").getImage();
      pok7_1 = new ImageIcon("X:\\UFPB\\Imagens\\pok7_1.png").getImage();
      pok8_1 = new ImageIcon("X:\\UFPB\\Imagens\\pok8_1.png").getImage();
      pok9_1 = new ImageIcon("X:\\UFPB\\Imagens\\pok9_1.png").getImage();
      pok10_1 = new ImageIcon("X:\\UFPB\\Imagens\\pok10_1.png").getImage();
      pok11_1 = new ImageIcon("X:\\UFPB\\Imagens\\pok11_1.png").getImage();
      pok12_1 = new ImageIcon("X:\\UFPB\\Imagens\\pok12_1.png").getImage();
      pok13_1 = new ImageIcon("X:\\UFPB\\Imagens\\pok13_1.png").getImage();
      
      
      //CARREGAR TODOS OS POKEMONS (POSICAO 2)
      pok1_2 = new ImageIcon("X:\\UFPB\\Imagens\\pok1_2.png").getImage();
      pok2_2 = new ImageIcon("X:\\UFPB\\Imagens\\pok2_2.png").getImage();
      pok3_2 = new ImageIcon("X:\\UFPB\\Imagens\\pok3_2.png").getImage();
      pok4_2 = new ImageIcon("X:\\UFPB\\Imagens\\pok4_2.png").getImage();
      pok5_2 = new ImageIcon("X:\\UFPB\\Imagens\\pok5_2.png").getImage();
      pok6_2 = new ImageIcon("X:\\UFPB\\Imagens\\pok6_2.png").getImage();
      pok7_2 = new ImageIcon("X:\\UFPB\\Imagens\\pok7_2.png").getImage();
      pok8_2 = new ImageIcon("X:\\UFPB\\Imagens\\pok8_2.png").getImage();
      pok9_2 = new ImageIcon("X:\\UFPB\\Imagens\\pok9_2.png").getImage();
      pok10_2 = new ImageIcon("X:\\UFPB\\Imagens\\pok10_2.png").getImage();
      pok11_2 = new ImageIcon("X:\\UFPB\\Imagens\\pok11_2.png").getImage();
      pok12_2 = new ImageIcon("X:\\UFPB\\Imagens\\pok12_2.png").getImage();
      pok13_2 = new ImageIcon("X:\\UFPB\\Imagens\\pok13_2.png").getImage();
      
      
      //CARREGAR MINIATURA DOS POKEMONS (
      pkp1 = new ImageIcon("X:\\UFPB\\Imagens\\pokp1.png").getImage(); //Carregar pok 1
      pkp2 = new ImageIcon("X:\\UFPB\\Imagens\\pokp2.png").getImage(); //Carregar pok 2
      pkp3 = new ImageIcon("X:\\UFPB\\Imagens\\pokp3.png").getImage(); //Carregar pok 3
      pkp4 = new ImageIcon("X:\\UFPB\\Imagens\\pokp4.png").getImage(); //Carregar pok 4
      pkp5 = new ImageIcon("X:\\UFPB\\Imagens\\pokp5.png").getImage(); //Carregar pok 5
      pkp6 = new ImageIcon("X:\\UFPB\\Imagens\\pokp6.png").getImage(); //Carregar pok 6
      pkp7 = new ImageIcon("X:\\UFPB\\Imagens\\pokp7.png").getImage(); //Carregar pok 7
      pkp8 = new ImageIcon("X:\\UFPB\\Imagens\\pokp8.png").getImage(); //Carregar pok 8
      pkp9 = new ImageIcon("X:\\UFPB\\Imagens\\pokp9.png").getImage(); //Carregar pok 9
      pkp10 = new ImageIcon("X:\\UFPB\\Imagens\\pokp10.png").getImage(); //Carregar pok 10
      pkp11= new ImageIcon("X:\\UFPB\\Imagens\\pokp11.png").getImage(); //Carregar pok 11
      pkp12 = new ImageIcon("X:\\UFPB\\Imagens\\pokp12.png").getImage(); //Carregar pok 12
      pkp13 = new ImageIcon("X:\\UFPB\\Imagens\\pokp13.png").getImage(); //Carregar pok 13
      
      //CARREGAR MINIATURAS FAINTEDS
      pkp1f = new ImageIcon("X:\\UFPB\\Imagens\\pokp1f.png").getImage(); //Carregar pok 1
      pkp2f = new ImageIcon("X:\\UFPB\\Imagens\\pokp2f.png").getImage(); //Carregar pok 2
      pkp3f = new ImageIcon("X:\\UFPB\\Imagens\\pokp3f.png").getImage(); //Carregar pok 3
      pkp4f = new ImageIcon("X:\\UFPB\\Imagens\\pokp4f.png").getImage(); //Carregar pok 4
      pkp5f = new ImageIcon("X:\\UFPB\\Imagens\\pokp5f.png").getImage(); //Carregar pok 5
      pkp6f = new ImageIcon("X:\\UFPB\\Imagens\\pokp6f.png").getImage(); //Carregar pok 6
      pkp7f = new ImageIcon("X:\\UFPB\\Imagens\\pokp7f.png").getImage(); //Carregar pok 7
      pkp8f = new ImageIcon("X:\\UFPB\\Imagens\\pokp8f.png").getImage(); //Carregar pok 8
      pkp9f = new ImageIcon("X:\\UFPB\\Imagens\\pokp9f.png").getImage(); //Carregar pok 9
      pkp10f = new ImageIcon("X:\\UFPB\\Imagens\\pokp10f.png").getImage(); //Carregar pok 10
      pkp11f= new ImageIcon("X:\\UFPB\\Imagens\\pokp11f.png").getImage(); //Carregar pok 11
      pkp12f = new ImageIcon("X:\\UFPB\\Imagens\\pokp12f.png").getImage(); //Carregar pok 12
      pkp13f = new ImageIcon("X:\\UFPB\\Imagens\\pokp13f.png").getImage(); //Carregar pok 13
      
      //CARREGAR AS 4 DIFERENTES BARRAS DE HP
      //Barras verdes
      hp1gr =  new ImageIcon("X:\\UFPB\\Imagens\\HpVerde.png").getImage(); 
      hp2gr =  new ImageIcon("X:\\UFPB\\Imagens\\HpVerde2.png").getImage(); 
      hp3gr =  new ImageIcon("X:\\UFPB\\Imagens\\HpVerde3.png").getImage(); 
      hp4gr =  new ImageIcon("X:\\UFPB\\Imagens\\HpVerde4.png").getImage(); 
     
      //Barras amarelas
      hp1ye =  new ImageIcon("X:\\UFPB\\Imagens\\HpAmarelo.png").getImage(); 
      hp2ye =  new ImageIcon("X:\\UFPB\\Imagens\\HpAmarelo2.png").getImage(); 
      hp3ye =  new ImageIcon("X:\\UFPB\\Imagens\\HpAmarelo3.png").getImage(); 
      hp4ye =  new ImageIcon("X:\\UFPB\\Imagens\\HpAmarelo4.png").getImage(); 
      
      //Barras vermelhas 
      hp1rd =  new ImageIcon("X:\\UFPB\\Imagens\\HpVermelho.png").getImage(); 
      hp2rd =  new ImageIcon("X:\\UFPB\\Imagens\\HpVermelho2.png").getImage();
      hp3rd =  new ImageIcon("X:\\UFPB\\Imagens\\HpVermelho3.png").getImage();
      hp4rd =  new ImageIcon("X:\\UFPB\\Imagens\\HpVermelho4.png").getImage();
      
      }
      //bg = new ImageIcon("bgbattle.png").getImage(); //bg da batalha
       if (eventoPreenchido==true){ 
           //====Atualizar Background
            if(eventos.get(1)==1){ //Assim que os clientes se conectarem
                bg = new ImageIcon("X:\\UFPB\\Imagens\\bgbattle.png").getImage(); //Carregar bg da batalha
            }else if(eventos.get(1)==2){//Tela de mostrar os ataques do seu pokemon
                bg = new ImageIcon("X:\\UFPB\\Imagens\\bgbattle2.png").getImage(); //Carregar bg que mostra ataques
            }else if(eventos.get(1)==3){//Tela de mostrar o pokemon que quer trocar
                bg = new ImageIcon("X:\\UFPB\\Imagens\\bgbattle3.png").getImage(); //Carregar bg que mostra o trocamento
            }
            //===CARREGAR POKEMON 1
            pk1 = idPraPokImgP1(eventos.get(2));
            //===CARREGAR POKEMON 2 
            pk2 = idPraPokImgP2(eventos.get(4));
          
            ////////Carregar o hp do seu pokemon///////////////////////////
            //Casa das centenas 
            int numeroAux; //Numero das centenas 
            numeroAux = eventos.get(3)/100;
            if(numeroAux ==0){ //Caso a centena for zero
                hp1c = new ImageIcon("X:\\UFPB\\Imagens\\nnull.png").getImage();    
            }else{
                hp1c = retornarNumeroSkin(numeroAux);    
            }
           // System.out.println("Centena: "+numeroAux);
            //Casa das dezenas
            numeroAux = eventos.get(3)-((eventos.get(3)/100)*100);
            numeroAux = numeroAux/10;
           // System.out.println("Dezena: "+numeroAux);
            hp1d = retornarNumeroSkin(numeroAux);
            //Casa das unidades
            numeroAux = eventos.get(3) - ((eventos.get(3)/100)*100) - numeroAux*10 ; //Pega hp do pokemon 1, remove as centenas e dezenas
           // System.out.println("Unidade: "+numeroAux);
            hp1u = retornarNumeroSkin(numeroAux); 
            
            ///////////////////////////////////////////////////////////////
            //Carregar a cor das barras de vida 
            //Barra de vida do seu pokemon
            
            hpb = retornaBarra(1,eventos.get(3));
            hpb2 = retornaBarra(2,eventos.get(5));
            
  
      }
   
        loaded =true; //Dizer que carregou as imagens
        repaint(); //Chamar o paint() dnv pra atualizar as imagens
    }
    
    public void paint(Graphics g){ //O JFRAME ATIVA O paint() automaticmente, mesmo sem botar pra chamar no códiogo **********************
                                   //Pra usar manualmente tem que usar repaint()
                                   //Graphics G no paint chama um Graphics2D na real
       
        
                                   
              
      g.drawImage(bg,0,0,null);  //Desenhar background, ele vai ser alterado de acordo com a situação no loadpics()
      
      
      
      if (eventoPreenchido==true){ 
          
      //==============TELA DE TROCA DE POKEMON======================================    
     if(eventos.get(1) == 3){
     
     //"10"(Event)(pok1)(hppok1)(poken)(kppoken)(pok2)(hppok2)(pok3)(hppok3)(pok4)(hppok4)(pok5)(hppok5)(pok6)(hppok6)
    //"10"(  1  )( 2  )(  3   )(  4  )(   5   )(  6 )(  7   )(  8 )(  9  )(  10 )(  11 )(  12 )(  13 )( 14 )(   15  )
          
    //IMAGEM DOS SEUS 6 POKEMONS
     System.out.println("kk vo desenhar");
         
     g.drawImage(idPraAnao(eventos.get(2),eventos.get(3)),12,94,null); //Pok 1
     g.drawImage(idPraAnao(eventos.get(6),eventos.get(7)),284,40,null); //Pok 2
     g.drawImage(idPraAnao(eventos.get(8),eventos.get(9)),284,120,null); //Pok 3
     g.drawImage(idPraAnao(eventos.get(10),eventos.get(11)),284,200,null); //Pok 4
     g.drawImage(idPraAnao(eventos.get(12),eventos.get(13)),284,280,null); //Pok 5
     g.drawImage(idPraAnao(eventos.get(14),eventos.get(15)),284,360,null); //Pok 6
     System.out.println("kk desenhei");
     
     //NOME DOS SEUS 6 POKEMONS
     
     String pokemonAux;
     //Pokemon 1
     pokemonAux = idPraNome(eventos.get(2)); //
     for (int i = 0;pokemonAux.length()>i ;i++){ //Iterar em cada letra e transformar em imagem
                g.drawImage(retornarLetraSkin3(pokemonAux.charAt(i)),97+i*20,134,null);
           }
     
     //Pokemon 2 
    
     pokemonAux  = idPraNome(eventos.get(6)); //
     for (int i = 0;pokemonAux .length()>i ;i++){ //Iterar em cada letra e transformar em imagem
                g.drawImage(retornarLetraSkin3(pokemonAux.charAt(i)),377+i*20,72,null);
           }
     
     //Pokemon 3 
     
     pokemonAux = idPraNome(eventos.get(8)); //
     for (int i = 0;pokemonAux .length()>i ;i++){ //Iterar em cada letra e transformar em imagem
                g.drawImage(retornarLetraSkin3(pokemonAux.charAt(i)),377+i*20,152,null);
           }
      //Pokemon 4 
     
     pokemonAux = idPraNome(eventos.get(10)); //
     for (int i = 0;pokemonAux .length()>i ;i++){ //Iterar em cada letra e transformar em imagem
                g.drawImage(retornarLetraSkin3(pokemonAux.charAt(i)),377+i*20,232,null);
           }
      //Pokemon 5 
     
     pokemonAux  = idPraNome(eventos.get(12)); //
     for (int i = 0;pokemonAux .length()>i ;i++){ //Iterar em cada letra e transformar em imagem
                g.drawImage(retornarLetraSkin3(pokemonAux.charAt(i)),377+i*20,312,null);
           }
      //Pokemon 6 
     
     pokemonAux  = idPraNome(eventos.get(14)); //
     for (int i = 0;pokemonAux .length()>i ;i++){ //Iterar em cada letra e transformar em imagem
                g.drawImage(retornarLetraSkin3(pokemonAux.charAt(i)),377+i*20,392,null);
           }
     
     //================HP DOS SEUS 6 POKEMONS (NUMEROS) ================================
     Image imagemAux;
     //--------------------POKEMON 1----------------------------
     //Centenas 
     int numeroAux;
     numeroAux = eventos.get(3)/100;  
     imagemAux = retornarNumeroSkin3(numeroAux);
     if (numeroAux == 0){imagemAux = lnull3;}
     g.drawImage(imagemAux,134,244,null);
     
     //Dezenas
     numeroAux = eventos.get(3)-((eventos.get(3)/100)*100);
     numeroAux = numeroAux/10;
     imagemAux = retornarNumeroSkin3(numeroAux);
     g.drawImage(imagemAux,154,244,null);
     
     //Unidades
     numeroAux = eventos.get(3) - ((eventos.get(3)/100)*100) - numeroAux*10 ;
     imagemAux = retornarNumeroSkin3(numeroAux);
     g.drawImage(imagemAux,174,244,null);
     
     //--------------------POKEMON 2----------------------------
    
     //Centenas 
     numeroAux = eventos.get(7)/100;  
     imagemAux = retornarNumeroSkin3(numeroAux);
     if (numeroAux == 0){imagemAux = lnull3;}
     g.drawImage(imagemAux,640,107,null);
     
     //Dezenas
     numeroAux = eventos.get(7)-((eventos.get(7)/100)*100);
     numeroAux = numeroAux/10;
     imagemAux = retornarNumeroSkin3(numeroAux);
     g.drawImage(imagemAux,660,107,null);
     
     //Unidades
     numeroAux = eventos.get(7) - ((eventos.get(7)/100)*100) - numeroAux*10 ;
     imagemAux = retornarNumeroSkin3(numeroAux);
     g.drawImage(imagemAux,680,107,null);
     
     //--------------------POKEMON 3----------------------------
     
     //Centenas 
     numeroAux = eventos.get(9)/100;  
     imagemAux = retornarNumeroSkin3(numeroAux);
     if (numeroAux == 0){imagemAux = lnull3;}
     g.drawImage(imagemAux,640,187,null);
     
     //Dezenas
     numeroAux = eventos.get(9)-((eventos.get(9)/100)*100);
     numeroAux = numeroAux/10;
     imagemAux = retornarNumeroSkin3(numeroAux);
     g.drawImage(imagemAux,660,187,null);
     
     //Unidades
     numeroAux = eventos.get(9) - ((eventos.get(9)/100)*100) - numeroAux*10 ;
     imagemAux = retornarNumeroSkin3(numeroAux);
     g.drawImage(imagemAux,680,187,null);
     
     //--------------------POKEMON 4----------------------------
     
     //Centenas 
     numeroAux = eventos.get(11)/100;  
     imagemAux = retornarNumeroSkin3(numeroAux);
      if (numeroAux == 0){imagemAux = lnull3;}
     g.drawImage(imagemAux,640,267,null);
    
     //Dezenas
     numeroAux = eventos.get(11)-((eventos.get(11)/100)*100);
     numeroAux = numeroAux/10;
     imagemAux = retornarNumeroSkin3(numeroAux);
     g.drawImage(imagemAux,660,267,null);
     
     //Unidades
     numeroAux = eventos.get(11) - ((eventos.get(11)/100)*100) - numeroAux*10 ;
     imagemAux = retornarNumeroSkin3(numeroAux);
     g.drawImage(imagemAux,680,267,null);
     
    //--------------------POKEMON 5----------------------------
     
     //Centenas 
     numeroAux = eventos.get(13)/100;  
     imagemAux = retornarNumeroSkin3(numeroAux);
     if (numeroAux == 0){imagemAux = lnull3;}
     g.drawImage(imagemAux,640,347,null);
     
     //Dezenas
     numeroAux = eventos.get(13)-((eventos.get(13)/100)*100);
     numeroAux = numeroAux/10;
     imagemAux = retornarNumeroSkin3(numeroAux);
     g.drawImage(imagemAux,660,347,null);
     
     //Unidades
     numeroAux = eventos.get(13) - ((eventos.get(13)/100)*100) - numeroAux*10 ;
     imagemAux = retornarNumeroSkin3(numeroAux);
     g.drawImage(imagemAux,680,347,null);
     
     //--------------------POKEMON 6----------------------------
     
     //Centenas 
     numeroAux = eventos.get(15)/100;  
     imagemAux = retornarNumeroSkin3(numeroAux);
     if (numeroAux == 0){imagemAux = lnull3;}
     g.drawImage(imagemAux,640,427,null);
     
     //Dezenas
     numeroAux = eventos.get(15)-((eventos.get(15)/100)*100);
     numeroAux = numeroAux/10;
     imagemAux = retornarNumeroSkin3(numeroAux);
     g.drawImage(imagemAux,660,427,null);
     
     //Unidades
     numeroAux = eventos.get(15) - ((eventos.get(15)/100)*100) - numeroAux*10 ;
     imagemAux = retornarNumeroSkin3(numeroAux);
     g.drawImage(imagemAux,680,427,null);
     



    //=====BARRA DE HP DOS SEUS 6 POKEMONS======================
      Image imageAux;
      //3 = 157  10
      //4 = 162  10
      imageAux = retornaBarra(4,eventos.get(3)); 
      g.drawImage(imageAux,104,227,(162*eventos.get(3)/100),10,null); //Barra pok 1
      //g.drawImage(hpb,580,368,(192*eventos.get(3)/100),11,null); //Barra do seu pokemon
      
      imageAux =retornaBarra(3,eventos.get(7));
      g.drawImage(imageAux,612,90,(157*eventos.get(7)/100),10,null); //Barra pok 2
      
      imageAux =retornaBarra(3,eventos.get(9));
      g.drawImage(imageAux,612,170,(157*eventos.get(9)/100),10,null); //Barra pok 3
      
      imageAux =retornaBarra(3,eventos.get(11));
      g.drawImage(imageAux,612,249,(157*eventos.get(11)/100),10,null); //Barra pok 4
      
      imageAux =retornaBarra(3,eventos.get(13));
      g.drawImage(imageAux,612,332,(157*eventos.get(13)/100),10,null); //Barra pok 5
      
      imageAux =retornaBarra(3,eventos.get(15));
      g.drawImage(imageAux,612,410,(157*eventos.get(15)/100),10,null); //Barra pok 6
      
      //MENSAGEM NA CAIXA DE TEXTO 
      String mensagem;
      mensagem ="escolhe o pokemon ai meu";
      for (int i = 0;mensagem.length()>i ;i++){ //Iterar em cada letra e transformar em imagem
                g.drawImage(retornarLetraSkin(mensagem.charAt(i)),30+i*20,489,null);
           }
      
      //=============================================================================
      //=============================================================================
      //=============================================================================
      //=============================================================================
      //=============================================================================
      }else{
          
      //============TELA DE TRETA NORMAL=========================================        
           g.drawImage(pk1,100,235,null); //Desenhar o seu pokemon
           g.drawImage(pk2,450,100,null); //Desenhar o pokemon do adversário
           //Desenhar hp do seu pokemon 
           g.drawImage(hp1c,616,391,null);
           g.drawImage(hp1d,635,391,null);
           g.drawImage(hp1u,655,391,null);
           
           //Desenhar Barra de Hp baseada na porcentagem da vida 
           g.drawImage(hpb,580,368,(192*eventos.get(3)/100),11,null); //Barra do seu pokemon
           g.drawImage(hpb2,127,94,(144*eventos.get(5)/100),9,null);//Barra do pokemon inimigo
          
           
           //Desenhar o nome do seu pokemon com letra bonita
           pokemon1Nome = idPraNome(eventos.get(2)); //Nome do pokemon 1 em string
           for (int i = 0;pokemon1Nome.length()>i ;i++){ //Iterar em cada letra e transformar em imagem
                g.drawImage(retornarLetraSkin(pokemon1Nome.charAt(i)),450+i*20,319,null);
           }
           
           //Desenhar o nome do pokemon adversario com letra bonita 
           pokemon2Nome = idPraNome(eventos.get(4)); //Nome do pokemon 1 em string       
           for (int i = 0;pokemon2Nome.length()>i ;i++){ //Iterar em cada letra e transformar em imagem
                g.drawImage(retornarLetraSkin(pokemon2Nome.charAt(i)),30+i*15,60,15,24,null);
           }
       //===============================================================================    
           //===================TEXTO DO MEME DE BATALHA=================================
           //Mostrar os ataques event = 2;
           if (eventos.get(1)==2){ //MOSTRAR OS 4 ATAQUES
               
           //ATAQUE 1     
           textoAux = idPraAtaque1(eventos.get(2)); 
           for (int i = 0;textoAux.length()>i ;i++){ //Iterar em cada letra e transformar em imagem
                     g.drawImage(retornarLetraSkin(textoAux.charAt(i)),32+i*15,497,15,24,null);
           }
           
           //ATAQUE 2 
           textoAux = idPraAtaque2(eventos.get(2)); 
           for (int i = 0;textoAux.length()>i ;i++){ //Iterar em cada letra e transformar em imagem
                     g.drawImage(retornarLetraSkin(textoAux.charAt(i)),300+i*15,497,15,24,null);
           }
           
           //ATAQUE 3
           textoAux = idPraAtaque3(eventos.get(2)); 
           for (int i = 0;textoAux.length()>i ;i++){ //Iterar em cada letra e transformar em imagem
                     g.drawImage(retornarLetraSkin(textoAux.charAt(i)),32+i*15,540,15,24,null);
           }    
           
           //ATAQUE 4
           textoAux = idPraAtaque4(eventos.get(2)); 
           for (int i = 0;textoAux.length()>i ;i++){ //Iterar em cada letra e transformar em imagem
                     g.drawImage(retornarLetraSkin(textoAux.charAt(i)),300+i*15,540,15,24,null);
           }
           //===    
           }
           else{//"What will pokemon will do"      
                //Textos da batalha com as letras brnacas
                textoAux = "what will";
                for (int i = 0;textoAux.length()>i ;i++){ //Iterar em cada letra e transformar em imagem
                     g.drawImage(retornarLetraSkin2(textoAux.charAt(i)),32+i*15,497,15,24,null);
                }
                textoAux = pokemon1Nome+" do?";
                for (int i = 0;textoAux.length()>i ;i++){ //Iterar em cada letra e transformar em imagem
                     g.drawImage(retornarLetraSkin2(textoAux.charAt(i)),32+i*15,540,15,24,null);
                }
           }
           
    }
    }
   }
    
     public Image retornarNumeroSkin(int numero){
            switch(numero){
                case 0:
                return n0;
                
                case 1:
                return n1;
                
                case 2:
                return n2;
                
                case 3:
                return n3;    
                
                case 4:
                return n4; 
                
                case 5:
                return n5; 
                
                case 6:
                return n6; 
                
                case 7:
                return n7; 
                
                case 8:
                return n8; 
                
                case 9:
                return n9; 
                             
                default:
                return lnull3;
                    
            }
     
    }
     
      public Image retornarNumeroSkin3(int numero){
            switch(numero){
                case 0:
                return l30;
                
                case 1:
                return l31;
                
                case 2:
                return l32;
                
                case 3:
                return l33;    
                
                case 4:
                return l34; 
                
                case 5:
                return l35; 
                
                case 6:
                return l36; 
                
                case 7:
                return l37; 
                
                case 8:
                return l38; 
                
                case 9:
                return l39; 
                             
                default:
                return lnull3;
                    
            }
     
    }
     
     public Image retornarLetraSkin (char letra){
            if (letra=='a'){return la;} //Retornar letra a
            if (letra=='b'){return lb;} //Retornar letra b
            if (letra=='c'){return lc;} //Retornar letra c
            if (letra=='d'){return ld;} //Retornar letra d
            if (letra=='e'){return le;} //Retornar letra e
            if (letra=='f'){return lf;} //Retornar letra f
            if (letra=='g'){return lg;} //Retornar letra g
            if (letra=='h'){return lh;} //Retornar letra h
            if (letra=='i'){return li;} //Retornar letra i
            if (letra=='j'){return lj;} //Retornar letra j
            if (letra=='k'){return lk;} //Retornar letra k
            if (letra=='l'){return ll;} //Retornar letra l
            if (letra=='m'){return lm;} //Retornar letra m
            if (letra=='n'){return ln;} //Retornar letra n
            if (letra=='o'){return lo;} //Retornar letra o
            if (letra=='p'){return lp;} //Retornar letra p
            if (letra=='q'){return lq;} //Retornar letra q
            if (letra=='r'){return lr;} //Retornar letra r
            if (letra=='s'){return ls;} //Retornar letra s
            if (letra=='t'){return lt;} //Retornar letra t
            if (letra=='u'){return lu;} //Retornar letra u
            if (letra=='v'){return lv;} //Retornar letra v
            if (letra=='w'){return lw;} //Retornar letra w
            if (letra=='x'){return lx;} //Retornar letra x
            if (letra=='y'){return ly;} //Retornar letra y
            if (letra=='z'){return lz;} //Retornar letra z
            
            return lnull;//Retornar espaco em branco caso nao seja nenhuma letra                

    }
     
       public Image retornarLetraSkin2 (char letra){
            if (letra=='a'){return la2;} //Retornar letra a
            if (letra=='b'){return lb2;} //Retornar letra b
            if (letra=='c'){return lc2;} //Retornar letra c
            if (letra=='d'){return ld2;} //Retornar letra d
            if (letra=='e'){return le2;} //Retornar letra e
            if (letra=='f'){return lf2;} //Retornar letra f
            if (letra=='g'){return lg2;} //Retornar letra g
            if (letra=='h'){return lh2;} //Retornar letra h
            if (letra=='i'){return li2;} //Retornar letra i
            if (letra=='j'){return lj2;} //Retornar letra j
            if (letra=='k'){return lk2;} //Retornar letra k
            if (letra=='l'){return ll2;} //Retornar letra l
            if (letra=='m'){return lm2;} //Retornar letra m
            if (letra=='n'){return ln2;} //Retornar letra n
            if (letra=='o'){return lo2;} //Retornar letra o
            if (letra=='p'){return lp2;} //Retornar letra p
            if (letra=='q'){return lq2;} //Retornar letra q
            if (letra=='r'){return lr2;} //Retornar letra r
            if (letra=='s'){return ls2;} //Retornar letra s
            if (letra=='t'){return lt2;} //Retornar letra t
            if (letra=='u'){return lu2;} //Retornar letra u
            if (letra=='v'){return lv2;} //Retornar letra v
            if (letra=='w'){return lw2;} //Retornar letra w
            if (letra=='x'){return lx2;} //Retornar letra x
            if (letra=='y'){return ly2;} //Retornar letra y
            if (letra=='z'){return lz2;} //Retornar letra z
            if (letra == '?'){return lint2;} //Retornar interrogacao
       
            return lnull2;//Retornar espaco em branco caso nao seja nenhuma letra               

    }
    
    
        
         public Image retornarLetraSkin3 (char letra){
            if (letra=='a'){return la3;} //Retornar letra a
            if (letra=='b'){return lb3;} //Retornar letra b
            if (letra=='c'){return lc3;} //Retornar letra c
            if (letra=='d'){return ld3;} //Retornar letra d
            if (letra=='e'){return le3;} //Retornar letra e
            if (letra=='f'){return lf3;} //Retornar letra f
            if (letra=='g'){return lg3;} //Retornar letra g
            if (letra=='h'){return lh3;} //Retornar letra h
            if (letra=='i'){return li3;} //Retornar letra i
            if (letra=='j'){return lj3;} //Retornar letra j
            if (letra=='k'){return lk3;} //Retornar letra k
            if (letra=='l'){return ll3;} //Retornar letra l
            if (letra=='m'){return lm3;} //Retornar letra m
            if (letra=='n'){return ln3;} //Retornar letra n
            if (letra=='o'){return lo3;} //Retornar letra o
            if (letra=='p'){return lp3;} //Retornar letra p
            if (letra=='q'){return lq3;} //Retornar letra q
            if (letra=='r'){return lr3;} //Retornar letra r
            if (letra=='s'){return ls3;} //Retornar letra s
            if (letra=='t'){return lt3;} //Retornar letra t
            if (letra=='u'){return lu3;} //Retornar letra u
            if (letra=='v'){return lv3;} //Retornar letra v
            if (letra=='w'){return lw3;} //Retornar letra w
            if (letra=='x'){return lx3;} //Retornar letra x
            if (letra=='y'){return ly3;} //Retornar letra y
            if (letra=='z'){return lz3;} //Retornar letra z

            
            return lnull3;//Retornar espaco em branco caso nao seja nenhuma letra               

    }
      
       
     /* Funcao antiga usando String
      public Image retornarLetraSkin (String letra){
            if (letra.contains("a")){return la;} //Retornar letra a
            if (letra.contains("b")){return lb;} //Retornar letra b
            if (letra.contains("c")){return lc;} //Retornar letra c
            if (letra.contains("d")){return ld;} //Retornar letra d
            if (letra.contains("e")){return le;} //Retornar letra e
            if (letra.contains("f")){return lf;} //Retornar letra f
            if (letra.contains("g")){return lg;} //Retornar letra g
            if (letra.contains("h")){return lh;} //Retornar letra h
            if (letra.contains("i")){return li;} //Retornar letra i
            if (letra.contains("j")){return lj;} //Retornar letra j
            if (letra.contains("k")){return lk;} //Retornar letra k
            if (letra.contains("l")){return ll;} //Retornar letra l
            if (letra.contains("m")){return lm;} //Retornar letra m
            if (letra.contains("n")){return ln;} //Retornar letra n
            if (letra.contains("o")){return lo;} //Retornar letra o
            if (letra.contains("p")){return lp;} //Retornar letra p
            if (letra.contains("q")){return lq;} //Retornar letra q
            if (letra.contains("r")){return lr;} //Retornar letra r
            if (letra.contains("s")){return ls;} //Retornar letra s
            if (letra.contains("t")){return lt;} //Retornar letra t
            if (letra.contains("u")){return lu;} //Retornar letra u
            if (letra.contains("v")){return lv;} //Retornar letra v
            if (letra.contains("w")){return lw;} //Retornar letra w
            if (letra.contains("x")){return lx;} //Retornar letra x
            if (letra.contains("y")){return ly;} //Retornar letra y
            if (letra.contains("z")){return lz;} //Retornar letra z
      
            return la;//Retornar a caso nao retornar nenhum das de cima por algum motivo                

    }
     */
       
    //===================IMAGEM DA POKEMONZADA ============================   
       
     public Image idPraAnao (int numero, int hp){
         switch (numero){
        
                case 1:
                if(hp<=0){return pkp1f;}
                return pkp1;
                
                case 2:
                if(hp<=0){return pkp2f;}    
                return pkp2;
                
                case 3:
                if(hp<=0){return pkp3f;}    
                return pkp3;    
                
                case 4:
                if(hp<=0){return pkp4f;}    
                return pkp4; 
                
                case 5:
                if(hp<=0){return pkp5f;}    
                return pkp5; 
                
                case 6:
                if(hp<=0){return pkp6f;}    
                return pkp6; 
                
                case 7:
                if(hp<=0){return pkp7f;}    
                return pkp7; 
                
                case 8:
                if(hp<=0){return pkp8f;}    
                return pkp8; 
                
                case 9:
                if(hp<=0){return pkp9f;}    
                return pkp9; 
                
                case 10:
                if(hp<=0){return pkp10f;}    
                return pkp10;
                
                case 11:
                if(hp<=0){return pkp11f;}    
                return pkp11; 
                
                case 12:
                if(hp<=0){return pkp12f;}    
                return pkp12;
                
                case 13:
                if(hp<=0){return pkp13f;}    
                return pkp13;
                             
                default:
                return pkp11;
             
         }
                
    }
     public Image retornaBarra(int tipo, int hp){ //De acordo com o tipo e qtd de hp, vai retornar a barra certa
         switch(tipo){
             case 1: 
             if(hp>60){return hp1gr;}
             if(hp>30){return hp1ye;}
             return hp1rd;
             
             case 2:
             if(hp>60){return hp2gr;}
             if(hp>30){return hp2ye;}
             return hp2rd;    
             
             case 3:
             if(hp>60){return hp3gr;}
             if(hp>30){return hp3ye;}
             return hp3rd;    
             
             case 4:
             if(hp>60){return hp4gr;}
             if(hp>30){return hp4ye;}
             return hp4rd;    
         }
         return hp1gr;
     }
     
     

     //"Charmander", "Squirtle", "Bulbasaur", "Caterpie", "Weedle", "Butterfree",
     //                         "Pidgey", "Rattata", "Spearow", "Ekans", "Pikachu", "Sandshrew", "Nidoran" };
     
     public String idPraNome(int i){ //Manda id e retorna nome 
     //Nome máximo = 12 letras
        if (i == 1){return "charmano";}
        if (i == 2){return "squirtle";}  
        if (i == 3){return "bulbasaur";}
        if (i == 4){return "caterpie";}
        if (i == 5){return "weedle";}
        if (i == 6){return "butterfree";}
        if (i == 7){return "pidgey";}
        if (i == 8){return "rattata";}
        if (i == 9){return "spearow";}
        if (i == 10){return "ekans";}
        if (i == 11){return "pikachu";}
        if (i == 12){return "sandshrew";}
        if (i == 13){return "nidoran";}
     return "cebola";   
    }

     //Pegar ataque 1 
     public String idPraAtaque1(int i){
        if (i == 1){return "blaze";}
        if (i == 2){return "torrent";}  
        if (i == 3){return "overgrow";}
        if (i == 4){return "ominislash";}
        if (i == 5){return "tail atack";}
        if (i == 6){return "confusion";}
        if (i == 7){return "lapada segura";}
        if (i == 8){return "bite";}
        if (i == 9){return "peck";}
        if (i == 10){return "bite";}
        if (i == 11){return "thundershock";}
        if (i == 12){return "sand attack";}
        if (i == 13){return "gyro ball";}
        
     return "cebola";
     }
     
     //Pegar ataque 2
     public String idPraAtaque2(int i){
        if (i == 1){return "solar power";}
        if (i == 2){return "rain dish";}  
        if (i == 3){return "chlorophyll";}
        if (i == 4){return "gaea rage";}
        if (i == 5){return "poison sting";}
        if (i == 6){return "whirlwind";}
        if (i == 7){return "voada";}
        if (i == 8){return "tail atack";}
        if (i == 9){return "fury atack";}
        if (i == 10){return "tail atack";}
        if (i == 11){return "lapada de rabo";}
        if (i == 12){return "spin attack";}
        if (i == 13){return "bite";}
        
     return "cebola";
     }

     //Pegar ataque 3
     public String idPraAtaque3(int i){
        if (i == 1){return "dragon dance";}
        if (i == 2){return "energy drink";}  
        if (i == 3){return "growth";}
        if (i == 4){return "focus";}
        if (i == 5){return "fertilizer";}
        if (i == 6){return "dar o gas";}
        if (i == 7){return "comer barro";}
        if (i == 8){return "tarukaja";}
        if (i == 9){return "growth";}
        if (i == 10){return "comer semente";}
        if (i == 11){return "pilha duracell";}
        if (i == 12){return "comer areia";}
        if (i == 13){return "comer cebola";}
        
     return "cebola";
     }

     //Pegar ataque 4
     public String idPraAtaque4(int i){
       if (i == 1){return "fire wall";}
        if (i == 2){return "water wall";}  
        if (i == 3){return "seed wall";}
        if (i == 4){return "kaclang";}
        if (i == 5){return "bug wall";}
        if (i == 6){return "endurecer";}
        if (i == 7){return "placebo";}
        if (i == 8){return "rakukaja";}
        if (i == 9){return "pombo wall";}
        if (i == 10){return "cobra wall";}
        if (i == 11){return "rato wall";}
        if (i == 12){return "sand castle";}
        if (i == 13){return "dar o gas";}
        
     return "cebola";
     }
     
     
     public Image idPraPokImgP1(int numero){
                switch(numero){ //Carregar sprite do seu pokemon
                case 1:  //Se o pokemon principal for o Charmander
                return pok1_1;     
                
                case 2:  //Se o pokemon principal for o Squirtle
                return pok2_1;
                
                case 3:  //Se o pokemon principal for o Bulbasaur
                return pok3_1;
                
                case 4:  //Se o pokemon principal for o Caterpie
                return pok4_1; 
                
                case 5:  //Se o pokemon principal for o Weedle
                return pok5_1;
                
                case 6:  //Se o pokemon principal for o Butterfree
                return pok6_1;
                
                case 7:  //Se o pokemon principal for o Pidgey
                return pok7_1;
                
                case 8:  //Se o pokemon principal for o Rattata
                return pok8_1;
                
                case 9:  //Se o pokemon principal for o Spearow
                return pok9_1;
                
                case 10:  //Se o pokemon principal for o Ekans
                return pok10_1;
                
                case 11:  //Se o pokemon principal for o Pikachu
                return pok11_1;
                
                case 12:  //Se o pokemon principal for o Sandshrew
                return pok12_1;
                
                case 13:  //Se o pokemon principal for o Nidoran
                return pok13_1; 
                                    
                default:
                return pok1_1;    
                
            }
     }
            public Image idPraPokImgP2(int numero){
          
           switch(numero){ //Carregar sprite do seu pokemon
                case 1:  //Se o pokemon principal for o Charmander
                return pok1_2;     
                
                case 2:  //Se o pokemon principal for o Squirtle
                return pok2_2;
                
                case 3:  //Se o pokemon principal for o Bulbasaur
                return pok3_2;
                
                case 4:  //Se o pokemon principal for o Caterpie
                return pok4_2; 
                
                case 5:  //Se o pokemon principal for o Weedle
                return pok5_2;
                
                case 6:  //Se o pokemon principal for o Butterfree
                return pok6_2;
                
                case 7:  //Se o pokemon principal for o Pidgey
                return pok7_2;
                
                case 8:  //Se o pokemon principal for o Rattata
                return pok8_2;
                
                case 9:  //Se o pokemon principal for o Spearow
                return pok9_2;
                
                case 10:  //Se o pokemon principal for o Ekans
                return pok10_2;
                
                case 11:  //Se o pokemon principal for o Pikachu
                return pok11_2;
                
                case 12:  //Se o pokemon principal for o Sandshrew
                return pok12_2;
                
                case 13:  //Se o pokemon principal for o Nidoran
                return pok13_2; 
                                    
                default:
                return pok1_2;    
                
            }
     
     }
     
}
     
     
     
     
   
    

        
   