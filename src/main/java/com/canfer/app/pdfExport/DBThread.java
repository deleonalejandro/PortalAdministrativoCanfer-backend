package com.canfer.app.pdfExport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DBThread implements Runnable {
	
	@Autowired
	private DbObserver dbObserver;
	private boolean doStop = true;
	
	
	public DBThread() {
		//Constructor
	}

    public synchronized void doStop() {
        this.doStop = true;
    }

    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }
	

	@Override
	public void run() {
		// Checking email every 5 minutes.
		while (keepRunning()) {
			
			// Thread running externally
			System.out.println("Checare la Tabla de Pagos");
			dbObserver.checkPago();
			
			try {
				System.out.println("DB Thread running... ya me fui a dormir");
                Thread.sleep(60L * 1000L);
            } catch (InterruptedException e) {
            	System.out.println("Se interrumpio el thread DB");
                e.printStackTrace();
                // Restore interrupted state...
                Thread.currentThread().interrupt();
            }
			
		}
		
	}
  }