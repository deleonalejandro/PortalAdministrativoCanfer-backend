package com.canfer.app.pdfExport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.canfer.app.model.Log;


@Component
public class DBThread implements Runnable {
	
	@Autowired
	private DbObserver dbObserver;
	private boolean doStop = false;
	private boolean syncPayments = true;
	
	
	public DBThread() {
		//Constructor
	}

    public synchronized void doStop() {
        this.doStop = true;
    }

    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }
    
    public synchronized boolean getStatus() {
    	return this.syncPayments;
    }
    
    public synchronized void stopSyncPayments() {
    	this.syncPayments = false;
    }
    
    public synchronized void runSyncPayments() {
    	this.syncPayments = true;
    }
	

	@Override
	public void run() {
		// Checking email every 5 minutes.
		while (keepRunning()) {
			
			if (syncPayments) {
				
				// Thread running externally
				System.out.println("Checare la Tabla de Pagos");
				dbObserver.checkPago();
				
			} else {
				
				System.out.println("Sincronizacion con tabla de pagos detenida.");
			}
			
			try {
				System.out.println("DB Thread running... ya me fui a dormir");
                Thread.sleep(60L * 1000L);
            } catch (InterruptedException e) {
            	Log.falla("Se interrumpio el thread que monitorea la base de datos.", "ERROR_CONNECTION");
                e.printStackTrace();
            }
			
		}
		
	}
  }