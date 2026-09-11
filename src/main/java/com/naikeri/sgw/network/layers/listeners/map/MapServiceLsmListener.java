package com.naikeri.sgw.network.layers.listeners.map;

import java.util.UUID;

import com.naikeri.sgw.api.chn.ChannelMessage;
import com.naikeri.sgw.api.chn.IChannelHandler;
import com.naikeri.sgw.network.layers.listeners.ProxyConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.restcomm.protocols.ss7.map.api.MAPDialog;
import org.restcomm.protocols.ss7.map.api.MAPMessage;
import org.restcomm.protocols.ss7.map.api.errors.MAPErrorMessage;
import org.restcomm.protocols.ss7.map.api.service.lsm.MAPServiceLsmListener;
import org.restcomm.protocols.ss7.map.api.service.lsm.ProvideSubscriberLocationRequest;
import org.restcomm.protocols.ss7.map.api.service.lsm.ProvideSubscriberLocationResponse;
import org.restcomm.protocols.ss7.map.api.service.lsm.SendRoutingInfoForLCSRequest;
import org.restcomm.protocols.ss7.map.api.service.lsm.SendRoutingInfoForLCSResponse;
import org.restcomm.protocols.ss7.map.api.service.lsm.SubscriberLocationReportRequest;
import org.restcomm.protocols.ss7.map.api.service.lsm.SubscriberLocationReportResponse;
import org.restcomm.protocols.ss7.tcap.asn.comp.Problem;

public class MapServiceLsmListener implements MAPServiceLsmListener {

  private static final Logger logger = LoggerFactory.getLogger(MapServiceLsmListener.class);
  private final IChannelHandler channelHandler;

  public MapServiceLsmListener(IChannelHandler channelHandler) {
    logger.debug("MapServiceLsmListener running");
    this.channelHandler = channelHandler;
  }

  private ChannelMessage getMessage(String messagetype) {
    ChannelMessage channelMessage = new ChannelMessage(UUID.randomUUID().toString(), "Map");
    channelMessage.setParameter(ProxyConstants.MESSAGE_TYPE, messagetype);
    return channelMessage;
  }

  @Override
  public void onMAPMessage(MAPMessage mapMessage) {
    ChannelMessage channelMessage = getMessage("onMAPMessage");
    channelMessage.setParameter("mapmessage", mapMessage);
    channelHandler.receiveMessageRequest(channelMessage);
  }

  @Override
  public void onProvideSubscriberLocationRequest(ProvideSubscriberLocationRequest provideSubscriberLocationRequest) {
    sendToChannel(provideSubscriberLocationRequest);
  }

  @Override
  public void onProvideSubscriberLocationResponse(ProvideSubscriberLocationResponse provideSubscriberLocationResponse) {
    sendToChannel(provideSubscriberLocationResponse);
  }

  @Override
  public void onSubscriberLocationReportRequest(SubscriberLocationReportRequest subscriberLocationReportRequest) {
    sendToChannel(subscriberLocationReportRequest);
  }

  @Override
  public void onSubscriberLocationReportResponse(SubscriberLocationReportResponse subscriberLocationReportResponse) {
    sendToChannel(subscriberLocationReportResponse);
  }

  @Override
  public void onSendRoutingInfoForLCSRequest(SendRoutingInfoForLCSRequest sendRoutingInfoForLCSRequest) {
    sendToChannel(sendRoutingInfoForLCSRequest);
  }

  @Override
  public void onSendRoutingInfoForLCSResponse(SendRoutingInfoForLCSResponse sendRoutingInfoForLCSResponse) {
    sendToChannel(sendRoutingInfoForLCSResponse);
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
  public void onInvokeTimeout(MAPDialog mapDialog, Long aLong) {
    ChannelMessage channelMessage = getMessage("onInvokeTimeout");
    channelMessage.setParameter("dialog", mapDialog);
    channelMessage.setParameter("invokeId", aLong);
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  private void sendToChannel(MAPMessage message) {
    ChannelMessage channelMessage = getMessage(message.getMessageType().toString());
    channelMessage.setParameter(ProxyConstants.MESSAGE, message);
    logger.info("{}, {}, [ DialogId = '{}'] ", message, channelMessage,
        message.getMAPDialog().getLocalDialogId());
    channelHandler.receiveMessageRequest(channelMessage);
  }
}
