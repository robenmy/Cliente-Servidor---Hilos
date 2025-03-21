package socket_III_chat;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;


public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
		setBounds(600,300,280,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		}	
	
}

class LaminaMarcoCliente extends JPanel implements Runnable{
	
	
private JTextField campo1, nick, ip;

private JTextArea campochat;

private JButton miboton;
	
	public LaminaMarcoCliente(){
	
		nick = new JTextField(5); // area para nickname
		add(nick);
		
		//------------------------------------------------
		JLabel texto=new JLabel("-CHAT-"); //area de texto chat
		
		add(texto);
		//-----------------------------------------
		ip = new JTextField(8); //area de texto para ip
		
		add(ip);
		//---------------------------
		campochat = new JTextArea(12,20);
		
		add(campochat);
	
		campo1=new JTextField(20);
		
	
		add(campo1);		
	
		miboton=new JButton("Enviar");
		
		//-----Creamos el evento del action listener -----------------
		EnviaTexto mievento = new EnviaTexto();
		
		miboton.addActionListener(mievento);
		
		
		//-----------------------------------------------------------
		
		add(miboton);	
		
		Thread mihilo = new Thread(this); // corremos el hilos cuando inicie el contructor de la clase
		mihilo.start();
		
	}
	
	
	// clase que gestiona eventos
	private class EnviaTexto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//System.out.println(campo1.getText()); //conseguir el texto del cuadro
			
			// Creacion de socket----------------------------
			//-------------------------------------------
			//-------------------------------------------
			
			campochat.append("\n"+campo1.getText()); // para que aparesca lo que escribo en mi propio chat para enviar
			
			try {
				//1-crear sockect y puerto de comunicacion
				Socket misocket =  new Socket("127.0.0.1",9999);
				
				PaqueteEnvio datos = new PaqueteEnvio(); // clase para  nick, ip, texto
				
				
				// -----------------------------------------------------------------------------
				datos.setNick(nick.getText()); // almacenamos el nickname del jtext
				
				datos.setIp(ip.getText());
				
				datos.setMensaje(campo1.getText());
				//En estos campos se almacenan los datos a enviar
				//-------------------------------------------------------------------
				//para rnviar un objeto no se usa dataouputStream sino objectOuput Stream
				ObjectOutputStream paquete_datos = new ObjectOutputStream(misocket.getOutputStream());
				
				paquete_datos.writeObject(datos);
				
				misocket.close();
				
			} catch (UnknownHostException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			//-------------------------------------------
			
		}
		
	}
	
	
/// hilo para mantener puerto de red a la escucha
	@Override
	public void run() {
		try {
			
			ServerSocket servidor_cliente = new ServerSocket(9090);
			
			Socket cliente;
			
			PaqueteEnvio paqueteRecibido;
			
			while(true) {
				cliente = servidor_cliente.accept();  //acepta todas las conexiones entrantes
				
				ObjectInputStream flujoentrada = new ObjectInputStream(cliente.getInputStream());
				
				paqueteRecibido = (PaqueteEnvio) flujoentrada.readObject();
				
				campochat.append("\n"+paqueteRecibido.getNick()+": "+paqueteRecibido.getMensaje());
				
				
				
			}
			
		}catch(Exception e) {
			
		}
		
	}
	
}

//clase para enviar almacenar y enviar datos
class PaqueteEnvio implements Serializable{
	// es necesario serializar la clase para poder enviar los datos a traves de la red
	private String nick, ip, mensaje;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}