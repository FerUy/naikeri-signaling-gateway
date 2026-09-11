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
import org.restcomm.protocols.ss7.map.api.service.sms.AlertServiceCentreRequest;
import org.restcomm.protocols.ss7.map.api.service.sms.AlertServiceCentreResponse;
import org.restcomm.protocols.ss7.map.api.service.sms.ForwardShortMessageRequest;
import org.restcomm.protocols.ss7.map.api.service.sms.ForwardShortMessageResponse;
import org.restcomm.protocols.ss7.map.api.service.sms.InformServiceCentreRequest;
import org.restcomm.protocols.ss7.map.api.service.sms.MAPServiceSmsListener;
import org.restcomm.protocols.ss7.map.api.service.sms.MoForwardShortMessageRequest;
import org.restcomm.protocols.ss7.map.api.service.sms.MoForwardShortMessageResponse;
import org.restcomm.protocols.ss7.map.api.service.sms.MtForwardShortMessageRequest;
import org.restcomm.protocols.ss7.map.api.service.sms.MtForwardShortMessageResponse;
import org.restcomm.protocols.ss7.map.api.service.sms.NoteSubscriberPresentRequest;
import org.restcomm.protocols.ss7.map.api.service.sms.ReadyForSMRequest;
import org.restcomm.protocols.ss7.map.api.service.sms.ReadyForSMResponse;
import org.restcomm.protocols.ss7.map.api.service.sms.ReportSMDeliveryStatusRequest;
import org.restcomm.protocols.ss7.map.api.service.sms.ReportSMDeliveryStatusResponse;
import org.restcomm.protocols.ss7.map.api.service.sms.SendRoutingInfoForSMRequest;
import org.restcomm.protocols.ss7.map.api.service.sms.SendRoutingInfoForSMResponse;
import org.restcomm.protocols.ss7.tcap.asn.comp.Problem;

public class MapSmsListener implements MAPServiceSmsListener {

  IChannelHandler channelHandler;

  private static final Logger logger = LoggerFactory.getLogger(MapSmsListener.class);

  public MapSmsListener(IChannelHandler channelHandler) {
    this.channelHandler = channelHandler;
    logger.debug("MapSmsListener listening for events...");
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
  public void onForwardShortMessageRequest(ForwardShortMessageRequest forwardShortMessageRequest) {
    sendToChannel(forwardShortMessageRequest);
  }

  @Override
  public void onForwardShortMessageResponse(ForwardShortMessageResponse forwardShortMessageResponse) {
    sendToChannel(forwardShortMessageResponse);
  }

  @Override
  public void onMoForwardShortMessageRequest(MoForwardShortMessageRequest moForwardShortMessageRequest) {
    sendToChannel(moForwardShortMessageRequest);
  }

  @Override
  public void onMoForwardShortMessageResponse(MoForwardShortMessageResponse moForwardShortMessageResponse) {
    sendToChannel(moForwardShortMessageResponse);
  }

  @Override
  public void onMtForwardShortMessageRequest(MtForwardShortMessageRequest mtForwardShortMessageRequest) {
    sendToChannel(mtForwardShortMessageRequest);
  }

  @Override
  public void onMtForwardShortMessageResponse(MtForwardShortMessageResponse mtForwardShortMessageResponse) {
    sendToChannel(mtForwardShortMessageResponse);
  }

  @Override
  public void onSendRoutingInfoForSMRequest(SendRoutingInfoForSMRequest sendRoutingInfoForSMRequest) {
    sendToChannel(sendRoutingInfoForSMRequest);
  }

  @Override
  public void onSendRoutingInfoForSMResponse(SendRoutingInfoForSMResponse sendRoutingInfoForSMResponse) {
    sendToChannel(sendRoutingInfoForSMResponse);
  }

  @Override
  public void onReportSMDeliveryStatusRequest(ReportSMDeliveryStatusRequest reportSMDeliveryStatusRequest) {
    sendToChannel(reportSMDeliveryStatusRequest);
  }

  @Override
  public void onReportSMDeliveryStatusResponse(ReportSMDeliveryStatusResponse reportSMDeliveryStatusResponse) {
    sendToChannel(reportSMDeliveryStatusResponse);
  }

  @Override
  public void onInformServiceCentreRequest(InformServiceCentreRequest informServiceCentreRequest) {
    sendToChannel(informServiceCentreRequest);
  }

  @Override
  public void onAlertServiceCentreRequest(AlertServiceCentreRequest alertServiceCentreRequest) {
    sendToChannel(alertServiceCentreRequest);
  }

  @Override
  public void onAlertServiceCentreResponse(AlertServiceCentreResponse alertServiceCentreResponse) {
    sendToChannel(alertServiceCentreResponse);
  }

  @Override
  public void onReadyForSMRequest(ReadyForSMRequest readyForSMRequest) {
    sendToChannel(readyForSMRequest);
  }

  @Override
  public void onReadyForSMResponse(ReadyForSMResponse readyForSMResponse) {
    sendToChannel(readyForSMResponse);
  }

  @Override
  public void onNoteSubscriberPresentRequest(NoteSubscriberPresentRequest noteSubscriberPresentRequest) {
    sendToChannel(noteSubscriberPresentRequest);
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
    ChannelMessage channelMessage = getMessage("onInvokeTimeout");
    channelMessage.setParameter(ProxyConstants.DIALOG, mapDialog);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, invokeId);
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
