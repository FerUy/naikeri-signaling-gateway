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
import org.restcomm.protocols.ss7.map.api.service.supplementary.ActivateSSRequest;
import org.restcomm.protocols.ss7.map.api.service.supplementary.ActivateSSResponse;
import org.restcomm.protocols.ss7.map.api.service.supplementary.DeactivateSSRequest;
import org.restcomm.protocols.ss7.map.api.service.supplementary.DeactivateSSResponse;
import org.restcomm.protocols.ss7.map.api.service.supplementary.EraseSSRequest;
import org.restcomm.protocols.ss7.map.api.service.supplementary.EraseSSResponse;
import org.restcomm.protocols.ss7.map.api.service.supplementary.GetPasswordRequest;
import org.restcomm.protocols.ss7.map.api.service.supplementary.GetPasswordResponse;
import org.restcomm.protocols.ss7.map.api.service.supplementary.InterrogateSSRequest;
import org.restcomm.protocols.ss7.map.api.service.supplementary.InterrogateSSResponse;
import org.restcomm.protocols.ss7.map.api.service.supplementary.MAPServiceSupplementaryListener;
import org.restcomm.protocols.ss7.map.api.service.supplementary.ProcessUnstructuredSSRequest;
import org.restcomm.protocols.ss7.map.api.service.supplementary.ProcessUnstructuredSSResponse;
import org.restcomm.protocols.ss7.map.api.service.supplementary.RegisterPasswordRequest;
import org.restcomm.protocols.ss7.map.api.service.supplementary.RegisterPasswordResponse;
import org.restcomm.protocols.ss7.map.api.service.supplementary.RegisterSSRequest;
import org.restcomm.protocols.ss7.map.api.service.supplementary.RegisterSSResponse;
import org.restcomm.protocols.ss7.map.api.service.supplementary.UnstructuredSSNotifyRequest;
import org.restcomm.protocols.ss7.map.api.service.supplementary.UnstructuredSSNotifyResponse;
import org.restcomm.protocols.ss7.map.api.service.supplementary.UnstructuredSSRequest;
import org.restcomm.protocols.ss7.map.api.service.supplementary.UnstructuredSSResponse;
import org.restcomm.protocols.ss7.tcap.asn.comp.Problem;

public class MapSupplementaryServiceListener implements MAPServiceSupplementaryListener {

  private static final Logger logger = LoggerFactory.getLogger(MapSupplementaryServiceListener.class);
  private final IChannelHandler channelHandler;

  public MapSupplementaryServiceListener(IChannelHandler channelHandler) {
    this.channelHandler = channelHandler;
    logger.debug("MapSupplementaryServiceListener listening for events...");
  }

  private ChannelMessage getMessage(String messagetype) {
    ChannelMessage channelMessage = new ChannelMessage(UUID.randomUUID().toString(), "Map");
    channelMessage.setParameter("messageType", messagetype);
    return channelMessage;
  }

  @Override
  public void onMAPMessage(MAPMessage mapMessage) {
    ChannelMessage channelMessage = getMessage("onMAPMessage");
    channelMessage.setParameter("mapmessage", mapMessage);
    channelHandler.receiveMessageRequest(channelMessage);
  }

  @Override
  public void onRegisterSSRequest(RegisterSSRequest registerSSRequest) {
    sendToChannel(registerSSRequest);
  }

  @Override
  public void onRegisterSSResponse(RegisterSSResponse registerSSResponse) {
    sendToChannel(registerSSResponse);
  }

  @Override
  public void onEraseSSRequest(EraseSSRequest eraseSSRequest) {
    sendToChannel(eraseSSRequest);
  }

  @Override
  public void onEraseSSResponse(EraseSSResponse eraseSSResponse) {
    sendToChannel(eraseSSResponse);
  }

  @Override
  public void onActivateSSRequest(ActivateSSRequest activateSSRequest) {
    sendToChannel(activateSSRequest);
  }

  @Override
  public void onActivateSSResponse(ActivateSSResponse activateSSResponse) {
    sendToChannel(activateSSResponse);
  }

  @Override
  public void onDeactivateSSRequest(DeactivateSSRequest deactivateSSRequest) {
    sendToChannel(deactivateSSRequest);
  }

  @Override
  public void onDeactivateSSResponse(DeactivateSSResponse deactivateSSResponse) {
    sendToChannel(deactivateSSResponse);
  }

  @Override
  public void onInterrogateSSRequest(InterrogateSSRequest interrogateSSRequest) {
    sendToChannel(interrogateSSRequest);
  }

  @Override
  public void onInterrogateSSResponse(InterrogateSSResponse interrogateSSResponse) {
    sendToChannel(interrogateSSResponse);
  }

  @Override
  public void onGetPasswordRequest(GetPasswordRequest getPasswordRequest) {
    sendToChannel(getPasswordRequest);
  }

  @Override
  public void onGetPasswordResponse(GetPasswordResponse getPasswordResponse) {
    sendToChannel(getPasswordResponse);
  }

  @Override
  public void onRegisterPasswordRequest(RegisterPasswordRequest registerPasswordRequest) {
    sendToChannel(registerPasswordRequest);
  }

  @Override
  public void onRegisterPasswordResponse(RegisterPasswordResponse registerPasswordResponse) {
    sendToChannel(registerPasswordResponse);
  }

  @Override
  public void onProcessUnstructuredSSRequest(ProcessUnstructuredSSRequest processUnstructuredSSRequest) {
    sendToChannel(processUnstructuredSSRequest);
  }

  @Override
  public void onProcessUnstructuredSSResponse(ProcessUnstructuredSSResponse processUnstructuredSSResponse) {
    sendToChannel(processUnstructuredSSResponse);
  }

  @Override
  public void onUnstructuredSSRequest(UnstructuredSSRequest unstructuredSSRequest) {
    sendToChannel(unstructuredSSRequest);
  }

  @Override
  public void onUnstructuredSSResponse(UnstructuredSSResponse unstructuredSSResponse) {
    sendToChannel(unstructuredSSResponse);
  }

  @Override
  public void onUnstructuredSSNotifyRequest(UnstructuredSSNotifyRequest unstructuredSSNotifyRequest) {
    sendToChannel(unstructuredSSNotifyRequest);
  }

  @Override
  public void onUnstructuredSSNotifyResponse(UnstructuredSSNotifyResponse unstructuredSSNotifyResponse) {
    sendToChannel(unstructuredSSNotifyResponse);
  }

  @Override
  public void onErrorComponent(MAPDialog mapDialog, Long aLong, MAPErrorMessage mapErrorMessage) {
    ChannelMessage channelMessage = getMessage("onErrorComponent");
    channelMessage.setParameter(ProxyConstants.DIALOG, mapDialog);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, aLong);
    this.channelHandler.receiveMessageRequest(channelMessage);
  }

  @Override
  public void onRejectComponent(MAPDialog mapDialog, Long aLong, Problem problem, boolean b) {
    ChannelMessage channelMessage = getMessage("onRejectComponent");
    channelMessage.setParameter(ProxyConstants.DIALOG, mapDialog);
    channelMessage.setParameter(ProxyConstants.INVOKE_ID, aLong);
    channelMessage.setParameter("problem", problem);
    channelMessage.setParameter("boolean", b);
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
