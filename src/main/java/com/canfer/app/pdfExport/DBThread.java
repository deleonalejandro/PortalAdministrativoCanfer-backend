package com.canfer.app.pdfExport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.canfer.app.model.Log;
import com.crystaldecisions12.reports.common.RenderingHintSetter;


@Component
public class DBThread implements Runnable {
	
	@Autowired
	private DbObserver dbObserver;
	private boolean doStop = false;
	private boolean syncPayments = true;
	private boolean syncRsBit = false;
	private boolean syncSapSuppliers = false;
	
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
	
    public synchronized void stopSyncRsBit() {
    	this.syncRsBit = false;
    }
    
    public synchronized void runSyncRsBit() {
    	this.syncRsBit = true;
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
				
				Log.general("Sincronizacion con tabla de pagos detenida.");
			}
			
			
			
			if (syncRsBit) {
				
				System.out.println("Checare la tabla de SAP para actualizar Bit RS");
				
				dbObserver.checkSapBitRs();
				
			} else {
				
				Log.general("Sincronizacion de revisado Sap detenida.");
				
			}
			
			if (syncSapSuppliers) {
				
				System.out.println("Checare la tabla de SAP para sincronizar proveedores.");
				
				dbObserver.checkSapSuppliers();
				
			} else {

				Log.general("Sincronizacion de tabla proveedores Sap detenida.");
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