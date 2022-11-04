package com.naikeri.sgw.impl.chn;

import com.naikeri.sgw.impl.settings.ChannelSettings;

import com.naikeri.sgw.api.chn.IChannelHandler;
import com.naikeri.sgw.api.chn.ChannelMessage;

import com.naikeri.sgw.impl.queue.QueueInstancePool;
import com.naikeri.sgw.impl.queue.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ChannelHandler implements IChannelHandler {

    private static final Logger logger = LoggerFactory.getLogger(ChannelHandler.class);

    protected BlockingQueue queue;

    public ChannelHandler(ChannelSettings channelSettings) {
        queue = QueueInstancePool.getQueueInstance(channelSettings.getName());
    }

    public int sendMessageRequest(ChannelMessage channelMessage) {
        // send message to application throughout queue
        logger.debug(channelMessage.toString());
        queue.send(channelMessage);
        return 0;
    }

}