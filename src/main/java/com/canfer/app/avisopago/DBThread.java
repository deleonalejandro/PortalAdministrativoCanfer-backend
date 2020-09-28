package com.canfer.app.avisopago;


import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRException;


@Component
public class DBThread implements Runnable {
	
	private boolean doStop = false;
	@Autowired
	private PagoService pagoService; 
	
	
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
		// Checking DB Table every 5 minutes.
		while (keepRunning()) {
			
			// Thread running externally
			System.out.println("Checare DB");
			pagoService.checkTable();
			try {
				try {
					pagoService.makePdfPago();
					System.out.println("PDF done!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (JRException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			
			try {
				System.out.println("DB Checada... ya me fui a dormir");
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
