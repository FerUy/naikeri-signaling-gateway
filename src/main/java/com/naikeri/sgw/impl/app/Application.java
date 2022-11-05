package com.naikeri.sgw.impl.app;

import com.naikeri.sgw.impl.chn.ChannelHandler;
import com.naikeri.sgw.impl.queue.QueueInstancePool;
import com.naikeri.sgw.api.app.IApplication;
import com.naikeri.sgw.api.app.QueueInterface;
import com.naikeri.sgw.api.chn.ChannelMessage;
import com.naikeri.sgw.impl.settings.ApplicationSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class Application implements IApplication {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    enum Status {
        Initialized, Running, Suspended, Terminated;
    }

    private Status status = Status.Initialized;

    private ApplicationSettings applicationSettings;

    private List<Thread> workerThreads = new ArrayList<>();

    private QueueInterface queue;

    ChannelHandler channelHandler;

    Status getStatus() {
        return this.status;
    }

    ApplicationSettings getApplicationSettings() {
        return this.applicationSettings;
    }

    ChannelMessage getNextMessage() {
        return this.queue.receive();
    }

    ChannelHandler getChannelHandler() {
        return this.channelHandler;
    }

    public void setChannelHandler(ChannelHandler channelHandler) {
        this.channelHandler = channelHandler;
    }

    protected Application(ApplicationSettings applicationSettings) {
        this.applicationSettings = applicationSettings;

        this.queue = QueueInstancePool.getQueueInstance(applicationSettings.getChannelName());
        logger.debug("Application '" + applicationSettings.getName() + "' using queue '" +
            applicationSettings.getChannelName() + "' with '" + applicationSettings.getWorkers() + "' workers.");

        Integer integer1, integer2;
        for (Integer workerThreadId = Integer.valueOf(0); workerThreadId.intValue() < applicationSettings.getWorkers(); integer1 = workerThreadId, integer2 = workerThreadId = Integer.valueOf(workerThreadId.intValue() + 1)) {
            this.workerThreads.add(new Worker(workerThreadId, this));
        }
    }

    public void start() {
        this.status = Status.Running;
        for (Thread worker : this.workerThreads)
            worker.start();
    }

    void suspend(Boolean suspendApplication) {
        this.status = suspendApplication.booleanValue() ? Status.Suspended : Status.Running;
    }

    void stop() {
        this.status = Status.Terminated;
    }

}
