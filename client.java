//esqueleto.java
import java.net.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
// javac client.java
//java programa
public class client{

  static char[][] P1tablero =  {
        {'Z','Z','Z','Z'},
        {'Z','Z','Z','Z'},
        {'Z','Z','Z','Z'},
        {'Z','Z','Z','Z'}
      };

      static boolean[][] b_P1tablero =  {
      {false,false,false,false},
      {false,false,false,false},
      {false,false,false,false},
      {false,false,false,false}
    };

  static char[][] P2tablero =  {
        {'Z','Z','Z','Z'},
        {'Z','Z','Z','Z'},
        {'Z','Z','Z','Z'},
        {'Z','Z','Z','Z'}
      };


      static boolean[][] b_P2tablero =  {
      {false,false,false,false},
      {false,false,false,false},
      {false,false,false,false},
      {false,false,false,false}
    };

  static char[] Letras = {'A','B','C','D','E','F','G','H'};

  static void printTablero(char[][] tablero, boolean[][] b_tablero)
  {
   for(int i = 0; i < 4; i++)
    {
      for(int j = 0; j < 4; j++)
      {
        if(b_tablero[i][j])
        System.out.print("'" + tablero[i][j] + "'" );
        else System.out.print("'" + "X" + "'" );

      }
          System.out.println();
    }
  }

  static void initTableros(char[][] tablero)
  {
    Scanner inn = new Scanner(System.in);

    for (int i = 0; i < Letras.length; i++) {
      int numi = 0;
      int numj = 0;
      int _numi = 0;
      int _numj = 0;

      while((numi == _numi && numj == _numj)){
          numi = getRandomNumber(0, 4);
          numj = getRandomNumber(0, 4);    

      while(!(tablero[numi][numj] == 'Z')){
          numi = getRandomNumber(0, 4);
          numj = getRandomNumber(0, 4);    
      }
      while(!(tablero[_numi][_numj] == 'Z' )){
          _numi = getRandomNumber(0, 4);
          _numj = getRandomNumber(0, 4);    
      }
      }
      tablero[numi][numj] = Letras[i];
      tablero[_numi][_numj] = Letras[i];
    }

  }

  public static int getRandomNumber(int min, int max) { // 0 y 4
    return (int) ((Math.random() * (max - min)) + min);
}

  static void clear() throws IOException
  {
    try{new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();} 
    catch(IOException ex){} 
    catch (InterruptedException ex){}    
  }

  static void _print(String cadena)
  {
    System.out.println(cadena);
  }

  public static void main(String[] args) throws UnknownHostException, IOException 
  {
     //comprobar el numero de argumentos
     if (args.length < 2){
       System.out.println("solo" + " args.length " + "argumento");
       System.out.println("Debes usar dos argumentos ");
       return;
     }
     //transformer un argumento en entero
     int PUERTO2 = Integer.parseInt(args[2]);
     String nombreServidor = args[1];
     int PUERTO1 = Integer.parseInt(args[0]);
     boolean playing = true;
     boolean turno = true;
     String inputText;

     try { // PARTE DEL CLIENTE
      ////////////////////////////////////////////////////////////!\     PARTE DEL CLIENTE (J2)   /!\///////////////////////////////////////////////////////////////////////////////////////////////////
        initTableros(P1tablero);
        initTableros(P2tablero);
        Socket s = new Socket(nombreServidor, PUERTO2);
        System.out.println("SE HA ESTABLECIDO CONEXIoN CON: \r\n");
        System.out.println("Servidor: " + s.getInetAddress().toString() +"\r\n\n\n");
        ServerSocket servidor = new ServerSocket(PUERTO1);
        Socket cliente = servidor.accept();

        String str = cliente.getInetAddress().toString().replace("/","");
        if(!s.isConnected()){
        s = new Socket(str, PUERTO2);
        }
        // COMUNICACIÓN BIDIRECCIONAL PREPARADA
        String numero1, numero2;
        Scanner in = new Scanner(System.in);
        byte messageType;
        // LEER MENSAJE
        DataInputStream dIn = new DataInputStream(cliente.getInputStream());
        // ENVIAR MENSAJE
        DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
        String[] texto = new String[8];
        Arrays.fill(texto,"nada");

        while(true)
        {
          if(turno)
          {
          clear();  
            System.out.println("\n// <JUGADOR 2> //\n");
              _print("Tablero del jugador 1:");
              printTablero(P1tablero, b_P1tablero);
              _print("Tablero del jugador 2:");
              printTablero(P2tablero, b_P2tablero);

            _print("Dame tu primera jugada:");
            numero1 = in.nextLine();
            char[] ch = new char[numero1.length()];
            for (int i = 0; i < numero1.length(); i++) {
                ch[i] = numero1.charAt(i);
            }
            _print("Tu letra es: " + P2tablero[Character.getNumericValue(ch[0])][Character.getNumericValue(ch[2])]);
            _print("Introduce tu segunda jugada");
            numero2 = in.nextLine();

            char[] _ch = new char[numero2.length()];
            for (int i = 0; i < numero2.length(); i++) {
                _ch[i] = numero2.charAt(i);
            }
            _print("Tu letra es: " + P2tablero[Character.getNumericValue(_ch[0])][Character.getNumericValue(_ch[2])]);
              _print("Pulsa intro para continuar");
            if(P2tablero[Character.getNumericValue(_ch[0])][Character.getNumericValue(_ch[2])] != P2tablero[Character.getNumericValue(ch[0])][Character.getNumericValue(ch[2])])
            {
              String enter = in.nextLine();
              dOut.writeByte(1);
              dOut.writeBoolean(turno);
              for(int i =0; i < 4; i++ ){
                  dOut.writeUTF(String.valueOf(P1tablero[i]));
                } 
                for(int i =0; i < 4; i++ ){
                  dOut.writeUTF(String.valueOf(P2tablero[i]));
                } 

                for(int i = 0; i < 4; i++ ){
                  for(int j = 0; j < 4; j++ ){
                  dOut.writeBoolean(b_P2tablero[i][j]);
                }
              } 
              turno = !turno;
            }
            else{
              b_P2tablero[Character.getNumericValue(_ch[0])][Character.getNumericValue(_ch[2])] = true;
              b_P2tablero[Character.getNumericValue(ch[0])][Character.getNumericValue(ch[2])] = true;
            }
          }
          else {
            clear();
            _print("Espera a que el J1 haga su jugada...");
            messageType = dIn.readByte();
            turno = dIn.readBoolean();
            
            for(int i = 0; i < 8; i++)
            texto[i] = dIn.readUTF();


        for (int i = 0; i < texto[0].length(); i++) {
            P1tablero[0][i]  = texto[0].charAt(i);
            P1tablero[1][i]  = texto[1].charAt(i);
            P1tablero[2][i]  = texto[2].charAt(i);
            P1tablero[3][i]  = texto[3].charAt(i); 
        }        
        for (int i = 0; i < texto[0].length(); i++) {
            P2tablero[0][i]  = texto[4].charAt(i);
            P2tablero[1][i]  = texto[5].charAt(i);
            P2tablero[2][i]  = texto[6].charAt(i);
            P2tablero[3][i]  = texto[7].charAt(i);
        } 

        for(int i =0; i < 4; i++ ){
          for(int j =0; j < 4; j++ ){
            b_P1tablero[i][j] = dIn.readBoolean();
          }
        } 

          }
        }
      } catch(UnknownHostException e){
            System.out.print("Nombre del servidor desconocido \n"+ e +"\r\n");
      } 
        
      catch(IOException e){ // PARTE DEL SERVIDOR
      ////////////////////////////////////////////////////////////!\     PARTE DEL SERVIDOR (J1)   /!\///////////////////////////////////////////////////////////////////////////////////////////////////
        System.out.print("Me preparo para ser el servidor\n"+ e +"\r\n");
        turno = false;
        ServerSocket servidor = new ServerSocket(PUERTO1);
        Socket cliente = servidor.accept(); 
        
        String ip=(((InetSocketAddress) cliente.getRemoteSocketAddress()).getAddress()).toString().replace("/","");

        Socket s = new Socket(ip, PUERTO2);
        String numero1, numero2;
        Scanner in = new Scanner(System.in);
        byte messageType;
        String[] texto = new String[8];
        System.out.println("Cliente: " + s.getRemoteSocketAddress().toString() +"\r\n\n\n");
        Arrays.fill(texto,"nada");

        // LEER MENSAJE
        DataInputStream dIn = new DataInputStream(cliente.getInputStream());
        // ENVIAR MENSAJE
        DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
        clear();
        while(true)
        {
          if(turno)
          {
          clear();  
            System.out.println("\n// <JUGADOR 1> //\n");
              _print("Tablero del jugador 1:");
              printTablero(P1tablero, b_P1tablero);
              _print("Tablero del jugador 2:");
              printTablero(P2tablero, b_P2tablero);

            _print("Dame tu primera jugada:");
            numero1 = in.nextLine();
            char[] ch = new char[numero1.length()];
            for (int i = 0; i < numero1.length(); i++) {
                ch[i] = numero1.charAt(i);
            }
            _print("Tu letra es: " + P1tablero[Character.getNumericValue(ch[0])][Character.getNumericValue(ch[2])]);
            _print("Introduce tu segunda jugada");
            numero2 = in.nextLine();

            char[] _ch = new char[numero2.length()];
            for (int i = 0; i < numero2.length(); i++) {
                _ch[i] = numero2.charAt(i);
            }
            _print("Tu letra es: " + P1tablero[Character.getNumericValue(_ch[0])][Character.getNumericValue(_ch[2])]);
              _print("Pulsa intro para continuar");
            if(P1tablero[Character.getNumericValue(_ch[0])][Character.getNumericValue(_ch[2])] != P1tablero[Character.getNumericValue(ch[0])][Character.getNumericValue(ch[2])])
            {
              String enter = in.nextLine();
              dOut.writeByte(1);
              dOut.writeBoolean(turno);
              for(int i =0; i < 4; i++ ){
                dOut.writeUTF(String.valueOf(P1tablero[i]));
              } 
              for(int i =0; i < 4; i++ ){
                dOut.writeUTF(String.valueOf(P2tablero[i]));
              } 

              for(int i = 0; i < 4; i++ ){
                for(int j = 0; j < 4; j++ ){
                dOut.writeBoolean(b_P1tablero[i][j]);
              }
              } 

              turno = !turno;
            }
            else{
              b_P1tablero[Character.getNumericValue(_ch[0])][Character.getNumericValue(_ch[2])] = true;
              b_P1tablero[Character.getNumericValue(ch[0])][Character.getNumericValue(ch[2])] = true;
            }
          }
          else {
            //clear();
            _print("Espera a que el J2 haga su jugada...");
            messageType = dIn.readByte();
            turno = dIn.readBoolean();
            
            for(int i = 0; i < 8; i++)
            texto[i] = dIn.readUTF();


        for (int i = 0; i < texto[0].length(); i++) {
            P1tablero[0][i]  = texto[0].charAt(i);
            P1tablero[1][i]  = texto[1].charAt(i);
            P1tablero[2][i]  = texto[2].charAt(i);
            P1tablero[3][i]  = texto[3].charAt(i); 
        }        
        for (int i = 0; i < texto[0].length(); i++) {
            P2tablero[0][i]  = texto[4].charAt(i);
            P2tablero[1][i]  = texto[5].charAt(i);
            P2tablero[2][i]  = texto[6].charAt(i);
            P2tablero[3][i]  = texto[7].charAt(i);
        } 

        for(int i =0; i < 4; i++ ){
          for(int j =0; j < 4; j++ ){
            b_P2tablero[i][j] = dIn.readBoolean();
          }
        } 

          }
        }
      }
    }
  }

