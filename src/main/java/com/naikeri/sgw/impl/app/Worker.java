package com.naikeri.sgw.impl.app;

import com.naikeri.sgw.api.chn.ChannelMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(Worker.class);

    private Application application;

    private Integer workerId;

    Worker(Integer workerId, Application application) {
        logger.info("Worker Id '" + workerId + "' instantiated for application '" + application.getApplicationSettings().getName() + "'");
        this.application = application;
        this.workerId = workerId;
    }

    public void run() {
        logger.info("Worker Id '" + this.workerId + "' started for application '" + this.application.getApplicationSettings().getName() + "'");
        while (this.application.getStatus() != Application.Status.Terminated) {
            try {
                if (this.application.getStatus() == Application.Status.Suspended) {
                    Thread.sleep(50);
                    continue;
                }
                ChannelMessage message = this.application.getNextMessage();
                this.application.processMessage(message);
            } catch (Exception e) {
                logger.error("Worker id '" + this.workerId + "' caught exception '" + e + "'");
                e.printStackTrace();
            }
        }
    }
}

