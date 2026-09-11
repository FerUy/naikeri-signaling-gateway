package com.naikeri.sgw.network.layers.listeners.map;

import java.util.UUID;

import com.naikeri.sgw.network.layers.listeners.ProxyConstants;
import com.naikeri.sgw.api.chn.ChannelMessage;
import com.naikeri.sgw.api.chn.IChannelHandler;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberInformation.AnyTimeModificationRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberInformation.AnyTimeModificationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.restcomm.protocols.ss7.map.api.MAPDialog;
import org.restcomm.protocols.ss7.map.api.MAPMessage;
import org.restcomm.protocols.ss7.map.api.errors.MAPErrorMessage;
import org.restcomm.protocols.ss7.map.api.service.mobility.MAPServiceMobilityListener;
import org.restcomm.protocols.ss7.map.api.service.mobility.authentication.AuthenticationFailureReportRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.authentication.AuthenticationFailureReportResponse;
import org.restcomm.protocols.ss7.map.api.service.mobility.authentication.SendAuthenticationInfoRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.authentication.SendAuthenticationInfoResponse;
import org.restcomm.protocols.ss7.map.api.service.mobility.faultRecovery.ForwardCheckSSIndicationRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.faultRecovery.ResetRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.faultRecovery.RestoreDataRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.faultRecovery.RestoreDataResponse;
import org.restcomm.protocols.ss7.map.api.service.mobility.imei.CheckImeiRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.imei.CheckImeiResponse;
import org.restcomm.protocols.ss7.map.api.service.mobility.locationManagement.CancelLocationRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.locationManagement.CancelLocationResponse;
import org.restcomm.protocols.ss7.map.api.service.mobility.locationManagement.PurgeMSRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.locationManagement.PurgeMSResponse;
import org.restcomm.protocols.ss7.map.api.service.mobility.locationManagement.SendIdentificationRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.locationManagement.SendIdentificationResponse;
import org.restcomm.protocols.ss7.map.api.service.mobility.locationManagement.UpdateGprsLocationRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.locationManagement.UpdateGprsLocationResponse;
import org.restcomm.protocols.ss7.map.api.service.mobility.locationManagement.UpdateLocationRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.locationManagement.UpdateLocationResponse;
import org.restcomm.protocols.ss7.map.api.service.mobility.oam.ActivateTraceModeRequest_Mobility;
import org.restcomm.protocols.ss7.map.api.service.mobility.oam.ActivateTraceModeResponse_Mobility;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberInformation.AnyTimeInterrogationRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberInformation.AnyTimeInterrogationResponse;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberInformation.AnyTimeSubscriptionInterrogationRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberInformation.AnyTimeSubscriptionInterrogationResponse;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberInformation.ProvideSubscriberInfoRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberInformation.ProvideSubscriberInfoResponse;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberManagement.DeleteSubscriberDataRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberManagement.DeleteSubscriberDataResponse;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberManagement.InsertSubscriberDataRequest;
import org.restcomm.protocols.ss7.map.api.service.mobility.subscriberManagement.InsertSubscriberDataResponse;
import org.restcomm.protocols.ss7.tcap.asn.comp.Problem;

public class MapServiceMobilityListener implements MAPServiceMobilityListener {

  private final IChannelHandler channelHandler;
  private static final Logger logger = LoggerFactory.getLogger(MapServiceMobilityListener.class);


  public MapServiceMobilityListener(IChannelHandler channelHandler) {
    logger.debug("MapServiceMobilityListener running ");
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
  public void onUpdateLocationRequest(UpdateLocationRequest updateLocationRequest) {
    sendToChannel(updateLocationRequest);
  }

  @Override
  public void onUpdateLocationResponse(UpdateLocationResponse updateLocationResponse) {
    sendToChannel(updateLocationResponse);
  }

  @Override
  public void onCancelLocationRequest(CancelLocationRequest cancelLocationRequest) {
    sendToChannel(cancelLocationRequest);
  }

  @Override
  public void onCancelLocationResponse(CancelLocationResponse cancelLocationResponse) {
    sendToChannel(cancelLocationResponse);
  }

  @Override
  public void onSendIdentificationRequest(SendIdentificationRequest sendIdentificationRequest) {
    sendToChannel(sendIdentificationRequest);
  }

  @Override
  public void onSendIdentificationResponse(SendIdentificationResponse sendIdentificationResponse) {
    sendToChannel(sendIdentificationResponse);
  }

  @Override
  public void onUpdateGprsLocationRequest(UpdateGprsLocationRequest updateGprsLocationRequest) {
    sendToChannel(updateGprsLocationRequest);
  }

  @Override
  public void onUpdateGprsLocationResponse(UpdateGprsLocationResponse updateGprsLocationResponse) {
    sendToChannel(updateGprsLocationResponse);
  }

  @Override
  public void onPurgeMSRequest(PurgeMSRequest purgeMSRequest) {
    sendToChannel(purgeMSRequest);
  }

  @Override
  public void onPurgeMSResponse(PurgeMSResponse purgeMSResponse) {
    sendToChannel(purgeMSResponse);
  }

  @Override
  public void onSendAuthenticationInfoRequest(SendAuthenticationInfoRequest sendAuthenticationInfoRequest) {
    sendToChannel(sendAuthenticationInfoRequest);
  }

  @Override
  public void onSendAuthenticationInfoResponse(SendAuthenticationInfoResponse sendAuthenticationInfoResponse) {
    sendToChannel(sendAuthenticationInfoResponse);
  }

  @Override
  public void onAuthenticationFailureReportRequest(AuthenticationFailureReportRequest authenticationFailureReportRequest) {
    sendToChannel(authenticationFailureReportRequest);
  }

  @Override
  public void onAuthenticationFailureReportResponse(AuthenticationFailureReportResponse authenticationFailureReportResponse) {
    sendToChannel(authenticationFailureReportResponse);
  }

  @Override
  public void onResetRequest(ResetRequest resetRequest) {
    sendToChannel(resetRequest);
  }

  @Override
  public void onForwardCheckSSIndicationRequest(ForwardCheckSSIndicationRequest forwardCheckSSIndicationRequest) {
    sendToChannel(forwardCheckSSIndicationRequest);
  }

  @Override
  public void onRestoreDataRequest(RestoreDataRequest restoreDataRequest) {
    sendToChannel(restoreDataRequest);
  }

  @Override
  public void onRestoreDataResponse(RestoreDataResponse restoreDataResponse) {
    sendToChannel(restoreDataResponse);
  }

  @Override
  public void onAnyTimeInterrogationRequest(AnyTimeInterrogationRequest anyTimeInterrogationRequest) {
    sendToChannel(anyTimeInterrogationRequest);
  }

  @Override
  public void onAnyTimeInterrogationResponse(AnyTimeInterrogationResponse anyTimeInterrogationResponse) {
    sendToChannel(anyTimeInterrogationResponse);
  }

  @Override
  public void onAnyTimeSubscriptionInterrogationRequest(AnyTimeSubscriptionInterrogationRequest anyTimeSubscriptionInterrogationRequest) {
    sendToChannel(anyTimeSubscriptionInterrogationRequest);
  }

  @Override
  public void onAnyTimeSubscriptionInterrogationResponse(AnyTimeSubscriptionInterrogationResponse anyTimeSubscriptionInterrogationResponse) {
    sendToChannel(anyTimeSubscriptionInterrogationResponse);
  }

  @Override
  public void onAnyTimeModificationRequest(AnyTimeModificationRequest anyTimeModificationRequest) {
    sendToChannel(anyTimeModificationRequest);
  }

  @Override
  public void onAnyTimeModificationResponse(AnyTimeModificationResponse anyTimeModificationResponse) {
    sendToChannel(anyTimeModificationResponse);
  }

  @Override
  public void onProvideSubscriberInfoRequest(ProvideSubscriberInfoRequest provideSubscriberInfoRequest) {
    sendToChannel(provideSubscriberInfoRequest);
  }

  @Override
  public void onProvideSubscriberInfoResponse(ProvideSubscriberInfoResponse provideSubscriberInfoResponse) {
    sendToChannel(provideSubscriberInfoResponse);
  }

  @Override
  public void onInsertSubscriberDataRequest(InsertSubscriberDataRequest insertSubscriberDataRequest) {
    sendToChannel(insertSubscriberDataRequest);
  }

  @Override
  public void onInsertSubscriberDataResponse(InsertSubscriberDataResponse insertSubscriberDataResponse) {
    sendToChannel(insertSubscriberDataResponse);
  }

  @Override
  public void onDeleteSubscriberDataRequest(DeleteSubscriberDataRequest deleteSubscriberDataRequest) {
    sendToChannel(deleteSubscriberDataRequest);
  }

  @Override
  public void onDeleteSubscriberDataResponse(DeleteSubscriberDataResponse deleteSubscriberDataResponse) {
    sendToChannel(deleteSubscriberDataResponse);
  }

  @Override
  public void onCheckImeiRequest(CheckImeiRequest checkImeiRequest) {
    sendToChannel(checkImeiRequest);
  }

  @Override
  public void onCheckImeiResponse(CheckImeiResponse checkImeiResponse) {
    sendToChannel(checkImeiResponse);
  }

  @Override
  public void onActivateTraceModeRequest_Mobility(ActivateTraceModeRequest_Mobility activateTraceModeRequestMobility) {
    sendToChannel(activateTraceModeRequestMobility);
  }

  @Override
  public void onActivateTraceModeResponse_Mobility(ActivateTraceModeResponse_Mobility activateTraceModeResponseMobility) {
    sendToChannel(activateTraceModeResponseMobility);
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
    channelMessage.setParameter(ProxyConstants.DIALOG, mapDialog);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, aLong);
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
