package com.naikeri.sgw.api.chn;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChannelMessage {

    private String transactionId;

    private String originId;

    private Map<String, Object> payloadParameters = new HashMap<>();

    public String getTransactionId() {
        return this.transactionId;
    }

    public ChannelMessage(String originId) {
        this.transactionId = UUID.randomUUID().toString();
        this.originId = originId;
    }

    public ChannelMessage(String transactionId, String originId) {
        this.transactionId = transactionId;
        this.originId = originId;
    }

    public ChannelMessage(String transactionId, String originId, Map<String, Object> payloadParameters) {
        this.payloadParameters = payloadParameters;
        this.transactionId = transactionId;
        this.originId = originId;
    }

    public void setParameter(String name, Object value) {
        if (this.payloadParameters.containsKey(name)) {
            this.payloadParameters.replace(name, value);
        } else {
            this.payloadParameters.put(name, value);
        }
    }

    public Object getParameter(String name) {
        return this.payloadParameters.get(name);
    }

    public String getOriginId() {
        return this.originId;
    }

    public String toString() {
        return String.format("[tid = %s, origin = %s]", new Object[] { this.transactionId, this.originId });
    }

}

