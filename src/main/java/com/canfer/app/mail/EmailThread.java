package com.canfer.app.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.canfer.app.model.Log;

@Component
public class EmailThread implements Runnable {
	
	@Autowired
	private EmailReceiver emailReceiver;
	private boolean doStop = false;
	private boolean checkingMail = true;
	
	
	public EmailThread() {
		//Constructor
	}

    public synchronized void doStop() {
        this.doStop = true;
    }
    
    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }
    
    public synchronized boolean getStatus() {
    	return this.checkingMail;
    }
    
    public synchronized void stopCheckingMail() {
    	this.checkingMail = false;
    }
    
    public synchronized void runCheckingMail() {
    	this.checkingMail = true;
    }
	
	@Override
	public void run() {
		// Checking email every 5 minutes.
		while (keepRunning()) {

			if (checkingMail) {

				// Thread running externally
				System.out.println("Llamare al metodo");
				emailReceiver.downloadEmails();

			} else {
				
				// Thread running externally
				System.out.println("Estoy detenido, no llamare al metodo");

			}

			try {
				
				System.out.println("Thread running... ya me fui a dormir");
				Thread.sleep(60L * 1000L);
				
			} catch (InterruptedException e) {
				System.out.println("Se interrumpio el thread");
				e.printStackTrace();
				Log.falla("Se interrumpió el thread del E-mail.", "ERROR_CONNECTION");
				// Restore interrupted state...
				Thread.currentThread().interrupt();
			}

		}

	}
}