//esqueleto.java
import java.net.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
// javac client.java
//java programa
public class client{

  static String[][] tablero =  {
        {"A","C","G","H"},
        {"D","H","F","F"},
        {"A","E","B","D"},
        {"B","G","C","E"}
      };

  static String[][] Ptablero =  {
        {"A","C","G","H"},
        {"D","H","F","F"},
        {"A","E","B","D"},
        {"B","G","C","E"}
      };

    static void printTablero()
  {
   for(int i = 0; i < 4; i++)
    {
      for(int j = 0; j < 4; j++)
        System.out.print("'" + "X" + "'" );
      System.out.println("\n");
    }
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
     Scanner in = new Scanner(System.in);


     try { // PARTE DEL CLIENTE
      ////////////////////////////////////////////////////////////!\     PARTE DEL CLIENTE (J2)   /!\///////////////////////////////////////////////////////////////////////////////////////////////////

        Socket s = new Socket(nombreServidor, PUERTO2);
        System.out.println("SE HA ESTABLECIDO CONEXIoN CON: \r\n");
        System.out.println("Servidor: " + nombreServidor + "\nPuerto: " + PUERTO2 +"\r\n\n\n");

        ServerSocket servidor = new ServerSocket(PUERTO1);
        Socket cliente = servidor.accept();

        System.out.println("COMUNICACIoN BIDIRECIONAL HECHA");

        // ENVIAR MENSAJE
        DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
        dOut.writeByte(1);
        dOut.writeBoolean(turno);
        dOut.writeUTF("Cliente: Hola!!!!!");
        dOut.flush(); // Send off the data


        // LEER Y PRINTAR MENSAJE
        DataInputStream dIn = new DataInputStream(cliente.getInputStream());
        byte messageType = dIn.readByte();
        boolean _turno = dIn.readBoolean();
        turno = _turno;
        System.out.println(dIn.readUTF());
        System.out.println("\n\n//TABLERO CLIENTE//\n\n");
        String numero1, numero2;
        while(true)
        {
          if(turno)
          {
            clear();
            System.out.println("\n\n// <JUGADOR 2> //\n\n");
            _print("Tablero del jugador 1:");
            printTablero();
            _print("Tablero del jugador 2:");
            printTablero();
            _print("Dame tu primera jugada:");
            numero1 = in.nextLine();

            char[] ch = new char[numero1.length()];
            for (int i = 0; i < numero1.length(); i++) {
                ch[i] = numero1.charAt(i);
            }
  
              //Integer.parseInt(ch[0])
  
            _print("jugada1: " + ch[0] + " , " + ch[2] );
            numero2 = in.nextLine();

            char[] _ch = new char[numero2.length()];
            for (int i = 0; i < numero2.length(); i++) {
                _ch[i] = numero2.charAt(i);
            }
            _print("jugada2: " + _ch[0] + " , " + _ch[2] );

            dOut.writeByte(1);
            dOut.writeBoolean(turno);
            turno = !turno;
          }
          else {
            clear();
            _print("Espera a que el J1 haga su jugada...");
            messageType = dIn.readByte();
            turno = dIn.readBoolean();
          }
        }
      } catch(UnknownHostException e){
            System.out.print("Nombre del servidor desconocido \n"+ e +"\r\n");
      } 
      
      
      
      
      
      catch(IOException e){ // PARTE DEL SERVIDOR
      ////////////////////////////////////////////////////////////!\     PARTE DEL SERVIDOR (J1)   /!\///////////////////////////////////////////////////////////////////////////////////////////////////
        System.out.print("No es posible realizar la conexion, me preparo para ser el servidor\n"+ e +"\r\n");
        turno = false;
        ServerSocket servidor = new ServerSocket(PUERTO1);
        Socket cliente = servidor.accept(); 
        System.out.println("SE HA ESTABLECIDO CONEXION: \n\n\n");
        Socket s = new Socket(nombreServidor, PUERTO2);
        System.out.println("COMUNICACIoN BIDIRECIONAL HECHA");
        String numero1, numero2;

        // LEER MENSAJE
        DataInputStream dIn = new DataInputStream(cliente.getInputStream());
        byte messageType = dIn.readByte();
        boolean _turno = dIn.readBoolean();
        turno = _turno;
        System.out.println(dIn.readUTF());

      //comment
        // ENVIAR MENSAJE
        DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
        dOut.writeByte(1);
        dOut.writeBoolean(!turno);
        dOut.writeUTF("Servidor: Hola!!!!!");
        dOut.flush(); // Send off the data
        clear();
        while(true)
        {
          if(turno)
          {
            clear();
            System.out.println("\n\n// <JUGADOR 1> //\n\n");
            _print("Tablero del jugador 1:");
            printTablero();
            _print("Tablero del jugador 2:");
            printTablero();
            _print("Dame tu primera jugada:");
            numero1 = in.nextLine();

            char[] ch = new char[numero1.length()];
            for (int i = 0; i < numero1.length(); i++) {
                ch[i] = numero1.charAt(i);
            }
  
              //Integer.parseInt(ch[0])
  
            _print("jugada1: " + ch[0] + " , " + ch[2] );
            numero2 = in.nextLine();

            char[] _ch = new char[numero2.length()];
            for (int i = 0; i < numero2.length(); i++) {
                _ch[i] = numero2.charAt(i);
            }
            _print("jugada2: " + _ch[0] + " , " + _ch[2] );

            dOut.writeByte(1);
            dOut.writeBoolean(turno);
            turno = !turno;
          }
          else {
            clear();
            _print("Espera a que el J1 haga su jugada...");
            messageType = dIn.readByte();
            turno = dIn.readBoolean();
          }
        }
      }

  }
}

