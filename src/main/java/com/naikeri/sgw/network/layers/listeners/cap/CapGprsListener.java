package com.naikeri.sgw.network.layers.listeners.cap;

import com.naikeri.sgw.api.chn.ChannelMessage;
import com.naikeri.sgw.api.chn.IChannelHandler;
import com.naikeri.sgw.network.layers.listeners.ProxyConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.restcomm.protocols.ss7.cap.api.CAPDialog;
import org.restcomm.protocols.ss7.cap.api.CAPMessage;
import org.restcomm.protocols.ss7.cap.api.errors.CAPErrorMessage;
import org.restcomm.protocols.ss7.cap.api.service.gprs.ActivityTestGPRSRequest;
import org.restcomm.protocols.ss7.cap.api.service.gprs.ActivityTestGPRSResponse;
import org.restcomm.protocols.ss7.cap.api.service.gprs.ApplyChargingGPRSRequest;
import org.restcomm.protocols.ss7.cap.api.service.gprs.ApplyChargingReportGPRSRequest;
import org.restcomm.protocols.ss7.cap.api.service.gprs.ApplyChargingReportGPRSResponse;
import org.restcomm.protocols.ss7.cap.api.service.gprs.CAPServiceGprsListener;
import org.restcomm.protocols.ss7.cap.api.service.gprs.CancelGPRSRequest;
import org.restcomm.protocols.ss7.cap.api.service.gprs.ConnectGPRSRequest;
import org.restcomm.protocols.ss7.cap.api.service.gprs.ContinueGPRSRequest;
import org.restcomm.protocols.ss7.cap.api.service.gprs.EntityReleasedGPRSRequest;
import org.restcomm.protocols.ss7.cap.api.service.gprs.EntityReleasedGPRSResponse;
import org.restcomm.protocols.ss7.cap.api.service.gprs.EventReportGPRSRequest;
import org.restcomm.protocols.ss7.cap.api.service.gprs.EventReportGPRSResponse;
import org.restcomm.protocols.ss7.cap.api.service.gprs.FurnishChargingInformationGPRSRequest;
import org.restcomm.protocols.ss7.cap.api.service.gprs.InitialDpGprsRequest;
import org.restcomm.protocols.ss7.cap.api.service.gprs.ReleaseGPRSRequest;
import org.restcomm.protocols.ss7.cap.api.service.gprs.RequestReportGPRSEventRequest;
import org.restcomm.protocols.ss7.cap.api.service.gprs.ResetTimerGPRSRequest;
import org.restcomm.protocols.ss7.cap.api.service.gprs.SendChargingInformationGPRSRequest;
import org.restcomm.protocols.ss7.tcap.asn.comp.Problem;

import java.util.UUID;

public class CapGprsListener implements CAPServiceGprsListener {

  private static final Logger logger = LoggerFactory.getLogger(CapGprsListener.class);

  private final IChannelHandler channelHandler;
  private final String layerName;

  public CapGprsListener(IChannelHandler channelHandler, String layerName) {
    this.channelHandler = channelHandler;
    this.layerName = layerName;
    logger.info("CapGprsListener started.....");
  }

  private ChannelMessage getMessage(String message) {
    ChannelMessage channelMessage = new ChannelMessage(UUID.randomUUID().toString(), "Cap");
    channelMessage.setParameter(ProxyConstants.MESSAGE_TYPE, message);
    channelMessage.setParameter("layerName", this.layerName);
    return channelMessage;
  }

  @Override
  public void onCAPMessage(CAPMessage capMessage) {
    ChannelMessage channelMessage = getMessage(capMessage.getMessageType().toString());
    channelMessage.setParameter("capMessage", capMessage);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, capMessage.getInvokeId());
    channelMessage.setParameter(ProxyConstants.GPRS_REF_NUM, capMessage.getCAPDialog().getGprsReferenceNumber());
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  @Override
  public void onInitialDpGprsRequest(InitialDpGprsRequest initialDpGprsRequest) {
    sendToChannel(initialDpGprsRequest);
  }

  @Override
  public void onRequestReportGPRSEventRequest(RequestReportGPRSEventRequest requestReportGPRSEventRequest) {
    sendToChannel(requestReportGPRSEventRequest);
  }

  @Override
  public void onApplyChargingGPRSRequest(ApplyChargingGPRSRequest applyChargingGPRSRequest) {
    sendToChannel(applyChargingGPRSRequest);
  }

  @Override
  public void onEntityReleasedGPRSRequest(EntityReleasedGPRSRequest entityReleasedGPRSRequest) {
    sendToChannel(entityReleasedGPRSRequest);
  }

  @Override
  public void onEntityReleasedGPRSResponse(EntityReleasedGPRSResponse entityReleasedGPRSResponse) {
    sendToChannel(entityReleasedGPRSResponse);
  }

  @Override
  public void onConnectGPRSRequest(ConnectGPRSRequest connectGPRSRequest) {
    sendToChannel(connectGPRSRequest);
  }

  @Override
  public void onContinueGPRSRequest(ContinueGPRSRequest continueGPRSRequest) {
    sendToChannel(continueGPRSRequest);
  }

  @Override
  public void onReleaseGPRSRequest(ReleaseGPRSRequest releaseGPRSRequest) {
    sendToChannel(releaseGPRSRequest);
  }

  @Override
  public void onResetTimerGPRSRequest(ResetTimerGPRSRequest resetTimerGPRSRequest) {
    sendToChannel(resetTimerGPRSRequest);
  }

  @Override
  public void onFurnishChargingInformationGPRSRequest(FurnishChargingInformationGPRSRequest furnishChargingInformationGPRSRequest) {
    sendToChannel(furnishChargingInformationGPRSRequest);
  }

  @Override
  public void onCancelGPRSRequest(CancelGPRSRequest cancelGPRSRequest) {
    sendToChannel(cancelGPRSRequest);
  }

  @Override
  public void onSendChargingInformationGPRSRequest(SendChargingInformationGPRSRequest sendChargingInformationGPRSRequest) {
    sendToChannel(sendChargingInformationGPRSRequest);
  }

  @Override
  public void onApplyChargingReportGPRSRequest(ApplyChargingReportGPRSRequest applyChargingReportGPRSRequest) {
    sendToChannel(applyChargingReportGPRSRequest);
  }

  @Override
  public void onApplyChargingReportGPRSResponse(ApplyChargingReportGPRSResponse applyChargingReportGPRSResponse) {
    sendToChannel(applyChargingReportGPRSResponse);
  }

  @Override
  public void onEventReportGPRSRequest(EventReportGPRSRequest eventReportGPRSRequest) {
    sendToChannel(eventReportGPRSRequest);
  }

  @Override
  public void onEventReportGPRSResponse(EventReportGPRSResponse eventReportGPRSResponse) {
    sendToChannel(eventReportGPRSResponse);
  }

  @Override
  public void onActivityTestGPRSRequest(ActivityTestGPRSRequest activityTestGPRSRequest) {
    sendToChannel(activityTestGPRSRequest);
  }

  @Override
  public void onActivityTestGPRSResponse(ActivityTestGPRSResponse activityTestGPRSResponse) {
    sendToChannel(activityTestGPRSResponse);
  }

  @Override
  public void onErrorComponent(CAPDialog capDialog, Long invokeId, CAPErrorMessage capErrorMessage) {
    ChannelMessage channelMessage = getMessage("onErrorComponent");
    channelMessage.setParameter(ProxyConstants.DIALOG, capDialog);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, invokeId);
    channelMessage.setParameter("errorCode", capErrorMessage == null ? -1 : capErrorMessage.getErrorCode());
    channelMessage.setParameter(ProxyConstants.GPRS_REF_NUM, capDialog.getGprsReferenceNumber());
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  @Override
  public void onRejectComponent(CAPDialog capDialog, Long invokeId, Problem problem, boolean isLocalOriginated) {
    ChannelMessage channelMessage = getMessage("onRejectComponent");
    channelMessage.setParameter(ProxyConstants.DIALOG, capDialog);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, invokeId);
    channelMessage.setParameter("problem", problem);
    channelMessage.setParameter("isLocalOriginated", isLocalOriginated);
    channelMessage.setParameter(ProxyConstants.GPRS_REF_NUM, capDialog.getGprsReferenceNumber());
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  @Override
  public void onInvokeTimeout(CAPDialog capDialog, Long invokeId) {
    ChannelMessage channelMessage = getMessage("onInvokeTimeout");
    channelMessage.setParameter(ProxyConstants.DIALOG, capDialog);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, invokeId);
    channelMessage.setParameter(ProxyConstants.GPRS_REF_NUM, capDialog.getGprsReferenceNumber());
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  private void sendToChannel(CAPMessage message) {
    ChannelMessage channelMessage = new ChannelMessage(UUID.randomUUID().toString(), "Cap");
    channelMessage.setParameter(ProxyConstants.MESSAGE, message);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, message.getInvokeId());
    channelMessage.setParameter(ProxyConstants.GPRS_REF_NUM, message.getCAPDialog().getGprsReferenceNumber());
    channelHandler.receiveMessageRequest(channelMessage);
  }
}
