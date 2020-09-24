package com.canfer.app.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ThreadAppRunner implements ApplicationRunner {

	
	@Autowired
	private AsynchronousService asyncService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		asyncService.executeAsynchronously();
	}
	
	

}
