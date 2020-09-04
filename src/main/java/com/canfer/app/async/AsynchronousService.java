package com.canfer.app.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.canfer.app.mail.EmailThread;

@Service
public class AsynchronousService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TaskExecutor taskExecutor;

    public void executeAsynchronously() {

        EmailThread emailThread = applicationContext.getBean(EmailThread.class);
        taskExecutor.execute(emailThread);
    }
}