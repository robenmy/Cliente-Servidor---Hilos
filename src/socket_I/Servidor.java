package socket_I;


import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
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
			
			while(true) {
			//ahora le decimos que acepte conexiones del exterior
			Socket misocket = servidor.accept();//
			
			//ahora el socket debe ser capaz de recoger el flujo de datos que envia el cliente
			//esto se logra mediante el metodo DataInputStream
			DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream());//ahora estamos capturando los datos que envian
			
			//se debe de cambiar el flujo a datos que se puedan leer:
			String mensaje_texto = flujo_entrada.readUTF();
			
			areatexto.append("\n"+mensaje_texto);
			
			misocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}