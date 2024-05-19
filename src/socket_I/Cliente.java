package socket_I;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
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
		
		setBounds(1200,600,560,700);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		}	
	
}

class LaminaMarcoCliente extends JPanel{
	
	public LaminaMarcoCliente(){
	
		JLabel texto=new JLabel("CLIENTE");
		
		
		add(texto);
	
		campo1=new JTextField(20);
		
	
		add(campo1);		
	
		miboton=new JButton("Enviar");
		
		//-----Creamos el evento del action listener -----------------
		EnviaTexto mievento = new EnviaTexto();
		
		miboton.addActionListener(mievento);
		
		
		//-----------------------------------------------------------
		
		add(miboton);	
		
	}
	
	
	// clase que gestiona eventos
	private class EnviaTexto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//System.out.println(campo1.getText()); //conseguir el texto del cuadro
			
			// Creacion de socket----------------------------
			//-------------------------------------------
			//-------------------------------------------
			
			try {
				//1-crear sockect y puerto de comunicacion
				Socket misocket =  new Socket("127.0.0.1",9999);
				//2- crear un flujo de salida de datos
				DataOutputStream flujo_salida = new DataOutputStream(misocket.getOutputStream());
				// se le indica al flujo de salida que circulara por el socket
				//3- se le indica que circulara por el flujo de salida
				flujo_salida.writeUTF(campo1.getText());
				flujo_salida.close();
			} catch (UnknownHostException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			//-------------------------------------------
			
		}
		
	}
	
	
	
		
		
		
	private JTextField campo1;
	
	private JButton miboton;
	
}