package com.naikeri.sgw.network.layers.listeners.cap;

import com.naikeri.sgw.api.chn.ChannelMessage;
import com.naikeri.sgw.api.chn.IChannelHandler;
import com.naikeri.sgw.network.layers.listeners.ProxyConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.restcomm.protocols.ss7.cap.api.CAPDialog;
import org.restcomm.protocols.ss7.cap.api.CAPMessage;
import org.restcomm.protocols.ss7.cap.api.errors.CAPErrorMessage;
import org.restcomm.protocols.ss7.cap.api.service.sms.CAPServiceSmsListener;
import org.restcomm.protocols.ss7.cap.api.service.sms.ConnectSMSRequest;
import org.restcomm.protocols.ss7.cap.api.service.sms.ContinueSMSRequest;
import org.restcomm.protocols.ss7.cap.api.service.sms.EventReportSMSRequest;
import org.restcomm.protocols.ss7.cap.api.service.sms.FurnishChargingInformationSMSRequest;
import org.restcomm.protocols.ss7.cap.api.service.sms.InitialDPSMSRequest;
import org.restcomm.protocols.ss7.cap.api.service.sms.ReleaseSMSRequest;
import org.restcomm.protocols.ss7.cap.api.service.sms.RequestReportSMSEventRequest;
import org.restcomm.protocols.ss7.cap.api.service.sms.ResetTimerSMSRequest;
import org.restcomm.protocols.ss7.tcap.asn.comp.Problem;

import java.util.UUID;

public class CapSmsListener implements CAPServiceSmsListener {

  private static final Logger logger = LoggerFactory.getLogger(CapSmsListener.class);
  private final String layerName;
  private final IChannelHandler channelHandler;

  public CapSmsListener(IChannelHandler channelHandler, String layerName) {
    this.channelHandler = channelHandler;
    this.layerName = layerName;
    logger.info("CapSmsListener listener started.....");
  }

  private ChannelMessage getMessage(String message) {
    ChannelMessage channelMessage = new ChannelMessage(UUID.randomUUID().toString(), "Cap");
    channelMessage.setParameter("messageType", message);
    channelMessage.setParameter("layerName", this.layerName);
    return channelMessage;
  }

  @Override
  public void onCAPMessage(CAPMessage capMessage) {
    ChannelMessage channelMessage = getMessage(capMessage.getMessageType().toString());
    channelMessage.setParameter("capMessage", capMessage);
    channelMessage.setParameter("invokeId", capMessage.getInvokeId());
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  @Override
  public void onConnectSMSRequest(ConnectSMSRequest connectSMSRequest) {
    sendToChannel(connectSMSRequest);
  }

  @Override
  public void onEventReportSMSRequest(EventReportSMSRequest eventReportSMSRequest) {
    sendToChannel(eventReportSMSRequest);
  }

  @Override
  public void onFurnishChargingInformationSMSRequest(FurnishChargingInformationSMSRequest furnishChargingInformationSMSRequest) {
    sendToChannel(furnishChargingInformationSMSRequest);
  }

  @Override
  public void onInitialDPSMSRequest(InitialDPSMSRequest initialDPSMSRequest) {
    sendToChannel(initialDPSMSRequest);
  }

  @Override
  public void onReleaseSMSRequest(ReleaseSMSRequest releaseSMSRequest) {
    sendToChannel(releaseSMSRequest);
  }

  @Override
  public void onRequestReportSMSEventRequest(RequestReportSMSEventRequest requestReportSMSEventRequest) {
    sendToChannel(requestReportSMSEventRequest);
  }

  @Override
  public void onResetTimerSMSRequest(ResetTimerSMSRequest resetTimerSMSRequest) {
    sendToChannel(resetTimerSMSRequest);
  }

  @Override
  public void onContinueSMSRequest(ContinueSMSRequest continueSMSRequest) {
    sendToChannel(continueSMSRequest);
  }

  @Override
  public void onErrorComponent(CAPDialog capDialog, Long invokeId, CAPErrorMessage capErrorMessage) {
    ChannelMessage channelMessage = getMessage("onErrorComponent");
    channelMessage.setParameter("dialog", capDialog);
    channelMessage.setParameter("invokeId", invokeId);
    channelMessage.setParameter("errorCode", capErrorMessage == null ? -1 : capErrorMessage.getErrorCode());
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  @Override
  public void onRejectComponent(CAPDialog capDialog, Long invokeId, Problem problem, boolean isLocalOriginated) {
    ChannelMessage channelMessage = getMessage("onRejectComponent");
    channelMessage.setParameter("dialog", capDialog);
    channelMessage.setParameter("invokeId", invokeId);
    channelMessage.setParameter("problem", problem);
    channelMessage.setParameter("isLocalOriginated", isLocalOriginated);
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  @Override
  public void onInvokeTimeout(CAPDialog capDialog, Long invokeId) {
    ChannelMessage channelMessage = getMessage("onInvokeTimeout");
    channelMessage.setParameter("dialog", capDialog);
    channelMessage.setParameter("invokeId", invokeId);
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  private void sendToChannel(CAPMessage message) {
    ChannelMessage channelMessage = new ChannelMessage(UUID.randomUUID().toString(), "Cap");
    channelMessage.setParameter(ProxyConstants.MESSAGE, message);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, message.getInvokeId());
    channelHandler.receiveMessageRequest(channelMessage);
  }
}
