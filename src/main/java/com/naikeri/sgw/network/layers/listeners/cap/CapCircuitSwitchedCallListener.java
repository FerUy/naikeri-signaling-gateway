package com.naikeri.sgw.network.layers.listeners.cap;

import com.naikeri.sgw.api.chn.ChannelMessage;
import com.naikeri.sgw.api.chn.IChannelHandler;
import com.naikeri.sgw.network.layers.listeners.ProxyConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.restcomm.protocols.ss7.cap.api.CAPDialog;
import org.restcomm.protocols.ss7.cap.api.CAPMessage;
import org.restcomm.protocols.ss7.cap.api.errors.CAPErrorMessage;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.ActivityTestRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.ActivityTestResponse;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.ApplyChargingReportRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.ApplyChargingRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.AssistRequestInstructionsRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.CAPServiceCircuitSwitchedCallListener;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.CallGapRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.CallInformationReportRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.CallInformationRequestRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.CancelRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.CollectInformationRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.ConnectRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.ConnectToResourceRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.ContinueRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.ContinueWithArgumentRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.DisconnectForwardConnectionRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.DisconnectForwardConnectionWithArgumentRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.DisconnectLegRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.DisconnectLegResponse;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.EstablishTemporaryConnectionRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.EventReportBCSMRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.FurnishChargingInformationRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.InitialDPRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.InitiateCallAttemptRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.InitiateCallAttemptResponse;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.MoveLegRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.MoveLegResponse;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.PlayAnnouncementRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.PromptAndCollectUserInformationRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.PromptAndCollectUserInformationResponse;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.ReleaseCallRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.RequestReportBCSMEventRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.ResetTimerRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.SendChargingInformationRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.SpecializedResourceReportRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.SplitLegRequest;
import org.restcomm.protocols.ss7.cap.api.service.circuitSwitchedCall.SplitLegResponse;
import org.restcomm.protocols.ss7.tcap.asn.comp.Problem;

import java.util.UUID;

public class CapCircuitSwitchedCallListener implements CAPServiceCircuitSwitchedCallListener {

  private static final Logger logger = LoggerFactory.getLogger(CapCircuitSwitchedCallListener.class);
  private final String layerName;
  private final IChannelHandler channelHandler;

  public CapCircuitSwitchedCallListener(IChannelHandler channelHandler, String layerName) {
    this.channelHandler = channelHandler;
    this.layerName = layerName;
    logger.debug("CapCircuitSwitchedCallListener listener started.....");
  }

  private ChannelMessage getMessage(String message) {
    // dialogid + invokeid + salt
    ChannelMessage channelMessage = new ChannelMessage(UUID.randomUUID().toString(), "Cap");
    channelMessage.setParameter(ProxyConstants.MESSAGE_TYPE, message);
    channelMessage.setParameter(ProxyConstants.CAP_LAYER_NAME, this.layerName);
    return channelMessage;
  }

  @Override
  public void onCAPMessage(CAPMessage capMessage) {
    sendToChannel(ProxyConstants.ON_CAP_MESSAGE, "capMessage", capMessage);
  }

  @Override
  public void onInitialDPRequest(InitialDPRequest initialDPRequest) {
    sendToChannel(initialDPRequest);
  }

  @Override
  public void onRequestReportBCSMEventRequest(RequestReportBCSMEventRequest requestReportBCSMEventRequest) {
    sendToChannel(requestReportBCSMEventRequest);
  }

  @Override
  public void onApplyChargingRequest(ApplyChargingRequest applyChargingRequest) {
    sendToChannel(applyChargingRequest);
  }

  @Override
  public void onEventReportBCSMRequest(EventReportBCSMRequest eventReportBCSMRequest) {
    sendToChannel(eventReportBCSMRequest);
  }

  @Override
  public void onContinueRequest(ContinueRequest continueRequest) {
    sendToChannel(continueRequest);
  }

  @Override
  public void onContinueWithArgumentRequest(ContinueWithArgumentRequest continueWithArgumentRequest) {
    sendToChannel(continueWithArgumentRequest);
  }

  @Override
  public void onApplyChargingReportRequest(ApplyChargingReportRequest applyChargingReportRequest) {
    sendToChannel(applyChargingReportRequest);
  }

  @Override
  public void onReleaseCallRequest(ReleaseCallRequest releaseCallRequest) {
    sendToChannel(releaseCallRequest);
  }

  @Override
  public void onConnectRequest(ConnectRequest connectRequest) {
    sendToChannel(connectRequest);
  }

  @Override
  public void onCallInformationRequestRequest(CallInformationRequestRequest callInformationRequestRequest) {
    sendToChannel(callInformationRequestRequest);
  }

  @Override
  public void onCallInformationReportRequest(CallInformationReportRequest callInformationReportRequest) {
    sendToChannel(callInformationReportRequest);
  }

  @Override
  public void onActivityTestRequest(ActivityTestRequest activityTestRequest) {
    sendToChannel(activityTestRequest);
  }

  @Override
  public void onActivityTestResponse(ActivityTestResponse activityTestResponse) {
    sendToChannel(activityTestResponse);
  }

  @Override
  public void onAssistRequestInstructionsRequest(AssistRequestInstructionsRequest assistRequestInstructionsRequest) {
    sendToChannel(assistRequestInstructionsRequest);
  }

  @Override
  public void onEstablishTemporaryConnectionRequest(EstablishTemporaryConnectionRequest establishTemporaryConnectionRequest) {
    sendToChannel(establishTemporaryConnectionRequest);
  }

  @Override
  public void onDisconnectForwardConnectionRequest(DisconnectForwardConnectionRequest disconnectForwardConnectionRequest) {
    sendToChannel(disconnectForwardConnectionRequest);
  }

  @Override
  public void onDisconnectLegRequest(DisconnectLegRequest disconnectLegRequest) {
    sendToChannel(disconnectLegRequest);
  }

  @Override
  public void onDisconnectLegResponse(DisconnectLegResponse disconnectLegResponse) {
    sendToChannel(disconnectLegResponse);
  }

  @Override
  public void onDisconnectForwardConnectionWithArgumentRequest(DisconnectForwardConnectionWithArgumentRequest disconnectForwardConnectionWithArgumentRequest) {
    sendToChannel(disconnectForwardConnectionWithArgumentRequest);
  }

  @Override
  public void onConnectToResourceRequest(ConnectToResourceRequest connectToResourceRequest) {
    sendToChannel(connectToResourceRequest);
  }

  @Override
  public void onResetTimerRequest(ResetTimerRequest resetTimerRequest) {
    sendToChannel(resetTimerRequest);
  }

  @Override
  public void onFurnishChargingInformationRequest(FurnishChargingInformationRequest furnishChargingInformationRequest) {
    sendToChannel(furnishChargingInformationRequest);
  }

  @Override
  public void onSendChargingInformationRequest(SendChargingInformationRequest sendChargingInformationRequest) {
    sendToChannel(sendChargingInformationRequest);
  }

  @Override
  public void onSpecializedResourceReportRequest(SpecializedResourceReportRequest specializedResourceReportRequest) {
    sendToChannel(specializedResourceReportRequest);
  }

  @Override
  public void onPlayAnnouncementRequest(PlayAnnouncementRequest playAnnouncementRequest) {
    sendToChannel(playAnnouncementRequest);
  }

  @Override
  public void onPromptAndCollectUserInformationRequest(PromptAndCollectUserInformationRequest promptAndCollectUserInformationRequest) {
    sendToChannel(promptAndCollectUserInformationRequest);
  }

  @Override
  public void onPromptAndCollectUserInformationResponse(PromptAndCollectUserInformationResponse promptAndCollectUserInformationResponse) {
    sendToChannel(promptAndCollectUserInformationResponse);
  }

  @Override
  public void onCancelRequest(CancelRequest cancelRequest) {
    sendToChannel(cancelRequest);
  }

  @Override
  public void onInitiateCallAttemptRequest(InitiateCallAttemptRequest initiateCallAttemptRequest) {
    sendToChannel(initiateCallAttemptRequest);
  }

  @Override
  public void onInitiateCallAttemptResponse(InitiateCallAttemptResponse initiateCallAttemptResponse) {
    sendToChannel(initiateCallAttemptResponse);
  }

  @Override
  public void onMoveLegRequest(MoveLegRequest moveLegRequest) {
    sendToChannel(moveLegRequest);
  }

  @Override
  public void onMoveLegResponse(MoveLegResponse moveLegResponse) {
    sendToChannel(moveLegResponse);
  }

  @Override
  public void onCollectInformationRequest(CollectInformationRequest collectInformationRequest) {
    sendToChannel(collectInformationRequest);
  }

  @Override
  public void onSplitLegRequest(SplitLegRequest splitLegRequest) {
    sendToChannel(splitLegRequest);
  }

  @Override
  public void onSplitLegResponse(SplitLegResponse splitLegResponse) {
    sendToChannel(splitLegResponse);
  }

  @Override
  public void onCallGapRequest(CallGapRequest callGapRequest) {
    sendToChannel(callGapRequest);
  }

  @Override
  public void onErrorComponent(CAPDialog capDialog, Long invokeId, CAPErrorMessage capErrorMessage) {
    ChannelMessage channelMessage = getMessage(ProxyConstants.ON_ERROR_COMPONENT);
    channelMessage.setParameter(ProxyConstants.DIALOG, capDialog);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, invokeId);
    channelMessage.setParameter(ProxyConstants.CAP_ERROR_MESSAGE, capErrorMessage);
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  @Override
  public void onRejectComponent(CAPDialog capDialog, Long invokeId, Problem problem, boolean isLocalOriginated) {
    ChannelMessage channelMessage = getMessage("onRejectComponent");
    channelMessage.setParameter(ProxyConstants.DIALOG, capDialog);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, invokeId);
    channelMessage.setParameter("problem", problem);
    channelMessage.setParameter("isLocalOriginated", isLocalOriginated);
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  @Override
  public void onInvokeTimeout(CAPDialog capDialog, Long invokeId) {
    ChannelMessage channelMessage = getMessage("onInvokeTimeout");
    channelMessage.setParameter(ProxyConstants.DIALOG, capDialog);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, invokeId);
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  private void sendToChannel(CAPMessage message) {
    sendToChannel(message.getMessageType().toString(), ProxyConstants.MESSAGE, message);
  }

  private void sendToChannel(String messageType, String messageKey, CAPMessage message) {
    ChannelMessage channelMessage = getMessage(messageType);
    channelMessage.setParameter(messageKey, message);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, message.getInvokeId());
    logger.debug("{}, {}, [ DialogId = '{}'] ", message, channelMessage, message.getCAPDialog().getLocalDialogId());
    channelHandler.receiveMessageRequest(channelMessage);
  }
}
