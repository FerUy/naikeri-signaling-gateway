package com.naikeri.sgw.network.layers.listeners.map;

import java.util.UUID;

import com.naikeri.sgw.api.chn.IChannelHandler;
import com.naikeri.sgw.network.layers.listeners.ProxyConstants;
import com.naikeri.sgw.api.chn.ChannelMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.restcomm.protocols.ss7.map.api.MAPDialog;
import org.restcomm.protocols.ss7.map.api.MAPMessage;
import org.restcomm.protocols.ss7.map.api.errors.MAPErrorMessage;
import org.restcomm.protocols.ss7.map.api.service.oam.ActivateTraceModeRequest_Oam;
import org.restcomm.protocols.ss7.map.api.service.oam.ActivateTraceModeResponse_Oam;
import org.restcomm.protocols.ss7.map.api.service.oam.MAPServiceOamListener;
import org.restcomm.protocols.ss7.map.api.service.oam.SendImsiRequest;
import org.restcomm.protocols.ss7.map.api.service.oam.SendImsiResponse;
import org.restcomm.protocols.ss7.tcap.asn.comp.Problem;

public class MapOamListener implements MAPServiceOamListener {

  private static final Logger logger = LoggerFactory.getLogger(MapOamListener.class);
  private final IChannelHandler channelHandler;

  public MapOamListener(IChannelHandler channelHandler) {
    this.channelHandler = channelHandler;
    logger.debug("MapOamListener listening...");
  }

  private ChannelMessage getMessage(String messagetype) {
    ChannelMessage channelMessage = new ChannelMessage(UUID.randomUUID().toString(), "Map");
    channelMessage.setParameter(ProxyConstants.MESSAGE_TYPE, messagetype);
    return channelMessage;
  }

  @Override
  public void onMAPMessage(MAPMessage mapMessage) {
    ChannelMessage channelMessage = getMessage(ProxyConstants.ON_MAP_MESSAGE);
    channelMessage.setParameter(ProxyConstants.MAP_MESSAGE, mapMessage);
    channelHandler.receiveMessageRequest(channelMessage);
  }

  @Override
  public void onActivateTraceModeRequest_Oam(ActivateTraceModeRequest_Oam activateTraceModeRequestoam) {
    sendToChannel(activateTraceModeRequestoam);
  }

  @Override
  public void onActivateTraceModeResponse_Oam(ActivateTraceModeResponse_Oam activateTraceModeResponseOam) {
    sendToChannel(activateTraceModeResponseOam);
  }

  @Override
  public void onSendImsiRequest(SendImsiRequest sendImsiRequest) {
    sendToChannel(sendImsiRequest);
  }

  @Override
  public void onSendImsiResponse(SendImsiResponse sendImsiResponse) {
    sendToChannel(sendImsiResponse);
  }

  @Override
  public void onErrorComponent(MAPDialog mapDialog, Long invokeId, MAPErrorMessage mapErrorMessage) {
    ChannelMessage channelMessage = getMessage(ProxyConstants.ON_ERROR_COMPONENT);
    channelMessage.setParameter(ProxyConstants.DIALOG, mapDialog);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, invokeId);
    channelMessage.setParameter(ProxyConstants.MAP_ERROR_MESSAGE, mapErrorMessage);
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  @Override
  public void onRejectComponent(MAPDialog mapDialog, Long invokeId, Problem problem, boolean b) {
    ChannelMessage channelMessage = getMessage(ProxyConstants.ON_REJECT_COMPONENT);
    channelMessage.setParameter(ProxyConstants.DIALOG, mapDialog);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, invokeId);
    channelMessage.setParameter(ProxyConstants.PROBLEM, problem);
    channelMessage.setParameter(ProxyConstants.LOCAL_ORIGINATED, b);
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  @Override
  public void onInvokeTimeout(MAPDialog mapDialog, Long invokeId) {
    ChannelMessage channelMessage = getMessage(ProxyConstants.ON_INVOKE_TIMEOUT);
    channelMessage.setParameter(ProxyConstants.DIALOG, mapDialog);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, invokeId);
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  private void sendToChannel(MAPMessage message) {
    ChannelMessage channelMessage = getMessage(message.getMessageType().toString());
    channelMessage.setParameter(ProxyConstants.MESSAGE, message);
    logger.debug("{}, {}, [ DialogId = '{}'] ", message, channelMessage,
        message.getMAPDialog().getLocalDialogId());
    channelHandler.receiveMessageRequest(channelMessage);
  }
}
