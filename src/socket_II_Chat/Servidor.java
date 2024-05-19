package socket_II_Chat;


import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame implements Runnable{
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);
		
		////-Creamos el hilo dentro del constructor del marco
		Thread miHilo = new Thread(this);
		miHilo.start();
		
		}
	
	private	JTextArea areatexto;
   ///el metodo que que siempre estara a la escucha estara aqui dentro
	@Override
	public void run() {
		System.out.println("Estoy a la escucha");
		// aqui creamos el servidor para que siempre este a la escucha
		try {
			ServerSocket servidor = new ServerSocket(9999);//le pasamos el puerto del cual aceptara conexiones
			
			String nick, ip, mensajes;
			//necesitamos una instancia  para almacenar las variables anteiorres
			PaqueteEnvio paquete_recibido;
			while(true) {
			//ahora le decimos que acepte conexiones del exterior
			Socket misocket = servidor.accept();//
			
			ObjectInputStream paquete_datos = new ObjectInputStream(misocket.getInputStream());
			
			paquete_recibido = (PaqueteEnvio) paquete_datos.readObject();
			
			
			nick = paquete_recibido.getNick();
			ip = paquete_recibido.getIp();
			mensajes = paquete_recibido.getMensaje();
			
			areatexto.append("\n"+nick+": "+mensajes+" para "+ip);
			
			misocket.close();
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}