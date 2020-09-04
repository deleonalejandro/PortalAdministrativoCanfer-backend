package com.canfer.app.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailThread implements Runnable {
	
	@Autowired
	private EmailReceiver emailReceiver;
	private boolean doStop = false;
	
	
	public EmailThread() {
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
			System.out.println("Llamare al metodo");
			emailReceiver.downloadEmails(true);
			
			try {
				System.out.println("Thread running... ya me fui a dormir");
                Thread.sleep(60L * 1000L);
            } catch (InterruptedException e) {
            	System.out.println("Se interrumpio el thread");
                e.printStackTrace();
                // Restore interrupted state...
                Thread.currentThread().interrupt();
            }
			
		}
		
	}
  }