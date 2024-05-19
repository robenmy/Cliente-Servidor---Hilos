package usoThreads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BancoSincronizar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Banco b = new Banco();
		
		for(int i=0; i<100;i++){
			
			EjecucionTransferencia r = new EjecucionTransferencia(b,i,2000);
			
			Thread t = new Thread(r);
			
			t.start();
		}

	}

}


class Banco{
	
	private final double[] cuentas;
	//private Lock cierreBanco = new ReentrantLock();
	//private Condition saldoSuficiente;
	
	public Banco(){
		
		cuentas = new double[100];
		
		for(int i=0;i<cuentas.length; i++){
			
			cuentas[i]=2000;
		}
		
		//saldoSuficiente = cierreBanco.newCondition();// se le indica que el bloqueo cierre banco se establezca en base a una condicion
	}
	
	public synchronized void transferencia(int cuentaOrigen, int cuentaDestino, double cantidad) throws InterruptedException{
		
		//cierreBanco.lock(); //para cincronizar los hilos y solo un hilo a la vez entre a este codigo
		// ahora se utiliza synchronized para sincronizar hilos
		
		//try{
		
		while(cuentas[cuentaOrigen]<cantidad){// evalua que el saldo no es inferior a la transferencia
			
			//return;
			//saldoSuficiente.await(); /// espera hasta que se cumpla la condicion y el hilo que esta ya pueda transferir saldo
			wait();
		}
		
		System.out.println(Thread.currentThread());//para saber el hilo que esta transfireindo
		
		cuentas[cuentaOrigen]-=cantidad;
		
		System.out.printf("%10.2f de %d para %d", cantidad,cuentaOrigen,cuentaDestino);
		
		cuentas[cuentaDestino]+=cantidad;
		
		System.out.printf("Saldo total: %10.2f%n", getSaldoTotal());
		
		//saldoSuficiente.signalAll(); // envia una notificacion a los hilos que se encuentran en espera para verifica si ya pueden seguir con la ejecucuion del codigo
		notifyAll(); // inform a los hilos en espera
	
	}//finally{
			
			//cierreBanco.unlock();
		//}
		
	//}
	
	public double getSaldoTotal(){
		
		double suma_cuentas = 0;
		
		for(double a: cuentas){
			
			suma_cuentas +=a;
		}
		
		return suma_cuentas;
	}
	
	
}


class EjecucionTransferencia implements Runnable{
	
	private Banco banco;
	private int deLaCuenta;
	private double cantidadMax;
	
	public EjecucionTransferencia(Banco b,int de, double max){
		
		banco = b;
		deLaCuenta = de;
		cantidadMax =max;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
		while(true){
			
			int paraLaCuenta = (int)(100*Math.random());
			
			double cantidad = cantidadMax*Math.random();
			
			banco.transferencia(deLaCuenta, paraLaCuenta, cantidad);
			
			
				Thread.sleep((int)(Math.random()*10));
			
		}
		} catch (InterruptedException e) {}
		
	}
	
	
	
}