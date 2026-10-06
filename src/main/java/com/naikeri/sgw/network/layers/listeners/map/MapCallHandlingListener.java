package com.naikeri.sgw.network.layers.listeners.map;

import java.util.UUID;

import com.naikeri.sgw.network.layers.listeners.ProxyConstants;
import com.naikeri.sgw.api.chn.ChannelMessage;
import com.naikeri.sgw.api.chn.IChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.restcomm.protocols.ss7.map.api.MAPDialog;
import org.restcomm.protocols.ss7.map.api.MAPMessage;
import org.restcomm.protocols.ss7.map.api.errors.MAPErrorMessage;
import org.restcomm.protocols.ss7.map.api.service.callhandling.IstCommandRequest;
import org.restcomm.protocols.ss7.map.api.service.callhandling.IstCommandResponse;
import org.restcomm.protocols.ss7.map.api.service.callhandling.MAPServiceCallHandlingListener;
import org.restcomm.protocols.ss7.map.api.service.callhandling.ProvideRoamingNumberRequest;
import org.restcomm.protocols.ss7.map.api.service.callhandling.ProvideRoamingNumberResponse;
import org.restcomm.protocols.ss7.map.api.service.callhandling.SendRoutingInformationRequest;
import org.restcomm.protocols.ss7.map.api.service.callhandling.SendRoutingInformationResponse;
import org.restcomm.protocols.ss7.tcap.asn.comp.Problem;

public class MapCallHandlingListener implements MAPServiceCallHandlingListener {

  private final IChannelHandler channelHandler;
  private static final Logger logger = LoggerFactory.getLogger(MapCallHandlingListener.class);

  public MapCallHandlingListener(IChannelHandler channelHandler) {
    this.channelHandler = channelHandler;
    logger.debug("MapCallHandlingListener listening for events...");
  }

  private ChannelMessage getMessage(String primitive) {
    ChannelMessage channelMessage = new ChannelMessage(UUID.randomUUID().toString(), "Map");
    channelMessage.setParameter(ProxyConstants.MESSAGE_TYPE, primitive);
    return channelMessage;
  }

  @Override
  public void onMAPMessage(MAPMessage mapMessage) {
    ChannelMessage channelMessage = getMessage(ProxyConstants.ON_MAP_MESSAGE);
    channelMessage.setParameter(ProxyConstants.MAP_MESSAGE, mapMessage);
    channelHandler.receiveMessageRequest(channelMessage);
  }

  @Override
  public void onSendRoutingInformationRequest(SendRoutingInformationRequest sendRoutingInformationRequest) {
    sendToChannel(sendRoutingInformationRequest);
  }

  @Override
  public void onSendRoutingInformationResponse(SendRoutingInformationResponse sendRoutingInformationResponse) {
    sendToChannel(sendRoutingInformationResponse);
  }

  @Override
  public void onProvideRoamingNumberRequest(ProvideRoamingNumberRequest provideRoamingNumberRequest) {
    sendToChannel(provideRoamingNumberRequest);
  }

  @Override
  public void onProvideRoamingNumberResponse(ProvideRoamingNumberResponse provideRoamingNumberResponse) {
    sendToChannel(provideRoamingNumberResponse);
  }

  @Override
  public void onIstCommandRequest(IstCommandRequest istCommandRequest) {
    sendToChannel(istCommandRequest);
  }

  @Override
  public void onIstCommandResponse(IstCommandResponse istCommandResponse) {
    sendToChannel(istCommandResponse);
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
  public void onRejectComponent(MAPDialog mapDialog, Long invokeId, Problem problem, boolean isLocalOriginated) {
    ChannelMessage channelMessage = getMessage(ProxyConstants.ON_REJECT_COMPONENT);
    channelMessage.setParameter(ProxyConstants.DIALOG, mapDialog);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, invokeId);
    channelMessage.setParameter(ProxyConstants.PROBLEM, problem);
    channelMessage.setParameter(ProxyConstants.LOCAL_ORIGINATED, isLocalOriginated);
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
