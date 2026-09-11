package com.naikeri.sgw.network.layers.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.restcomm.protocols.ss7.cap.api.errors.CAPErrorCode;
import org.restcomm.protocols.ss7.cap.api.errors.CAPErrorMessage;
import org.restcomm.protocols.ss7.map.api.errors.MAPErrorMessage;

public class ProxyConstants {

  private ProxyConstants() {
  }

  /**
   * The message type indicating the primitive event
   */
  public static final String MESSAGE_TYPE = "messageType";
  /**
   * The MAP or CAP message event Object e.g. SendRoutingInformationRequest
   */
  public static final String MESSAGE = "message";
  /**
   * The Invoke Id
   */
  public static final String INVOKE_ID = "invokeId";
  public static final String DIALOG = "dialog";
  public static final String MAP_ERROR_MESSAGE = "mapErrorMessage";
  public static final String ON_ERROR_COMPONENT = "onErrorComponent";
  public static final String ON_REJECT_COMPONENT = "onRejectComponent";
  public static final String ON_INVOKE_TIMEOUT = "onInvokeTimeout";
  public static final String ON_MAP_MESSAGE = "onMAPMessage";
  public static final String MAP_MESSAGE = "mapmessage";
  public static final String PROBLEM = "problem";
  public static final String LOCAL_ORIGINATED = "localOriginated";
  public static final String MAP_EXTENSION_CONTAINER = "mapExtensionContainer";
  public static final String ON_DIALOG_CLOSE = "onDialogClose";
  public static final String CAP_ERROR_MESSAGE = "capErrorMessage";
  public static final String CAP_LAYER_NAME = "layerName";
  public static final String ON_CAP_MESSAGE = "on_CapMessage";
  public static final String GPRS_REF_NUM = "gprs-ref-num";
  public static final String ON_DIALOG_TIMEOUT = "onDialogTimeout";
  public static final String TCAP_MESSAGE_TYPE = "TCAP_EVENT_TYPE";
  public static final String TCAP_MESSAGE = "TCAP_MESSAGE";
  public static final String TCAP_MESSAGE_DIALOG = "TCAP_MESSAGE_DIALOG";

  private static final Map<Long, String> mapErrorNames;

  static {
    mapErrorNames = new HashMap<>();
    mapErrorNames.put(1L, "Unknown Subscriber");
    mapErrorNames.put(2L, "Unknown Base Station");
    mapErrorNames.put(3L, "Unknown MSC");
    mapErrorNames.put(5L, "Unidentified Subscriber");
    mapErrorNames.put(6L, "Absent Subscriber SM");
    mapErrorNames.put(7L, "Unknown Equipment");
    mapErrorNames.put(8L, "Roaming Not Allowed");
    mapErrorNames.put(9L, "Illegal Subscriber");
    mapErrorNames.put(10L, "Bearer Service Not Provisioned");
    mapErrorNames.put(11L, "Teleservice Not Provisioned");
    mapErrorNames.put(12L, "Illegal Equipment");
    mapErrorNames.put(13L, "Call Barred");
    mapErrorNames.put(14L, "Forwarding Violation");
    mapErrorNames.put(15L, "CUG Reject");
    mapErrorNames.put(16L, "Illegal SS Operation");
    mapErrorNames.put(17L, "SS Error Status");
    mapErrorNames.put(18L, "SS Not Available");
    mapErrorNames.put(19L, "SS Subscription Violation");
    mapErrorNames.put(20L, "SS Incompatibility");
    mapErrorNames.put(21L, "Facility Not Supported");
    mapErrorNames.put(22L, "Ongoing Group Call");
    mapErrorNames.put(23L, "Invalid Target Base Station");
    mapErrorNames.put(24L, "No Radio Resource Available");
    mapErrorNames.put(25L, "No Handover Number Available");
    mapErrorNames.put(26L, "Subsequent Handover Failure");
    mapErrorNames.put(27L, "Absent Subscriber");
    mapErrorNames.put(28L, "Incompatible Terminal");
    mapErrorNames.put(29L, "Short Term Denial");
    mapErrorNames.put(30L, "Long Term Denial");
    mapErrorNames.put(31L, "Subscriber Busy For MT SMS");
    mapErrorNames.put(32L, "SM Delivery Failure");
    mapErrorNames.put(33L, "Message Waiting List Full");
    mapErrorNames.put(34L, "System Failure");
    mapErrorNames.put(35L, "Data Missing");
    mapErrorNames.put(36L, "Unexpected Data Value");
    mapErrorNames.put(37L, "PW Registration Failure");
    mapErrorNames.put(38L, "Negative PW Check");
    mapErrorNames.put(39L, "No Roaming Number Available");
    mapErrorNames.put(40L, "Tracing Buffer Full");
    mapErrorNames.put(42L, "Target Cell Outside Group Call Area");
    mapErrorNames.put(43L, "Number Of PW Attempts Violation");
    mapErrorNames.put(44L, "Number Changed");
    mapErrorNames.put(45L, "Busy Subscriber");
    mapErrorNames.put(46L, "No Subscriber Reply");
    mapErrorNames.put(47L, "Forwarding Failed");
    mapErrorNames.put(48L, "OR Not Allowed");
    mapErrorNames.put(49L, "ATI Not Allowed");
    mapErrorNames.put(50L, "No Group Call Number Available");
    mapErrorNames.put(51L, "Resource Limitation");
    mapErrorNames.put(52L, "Unauthorized Requesting Network");
    mapErrorNames.put(53L, "Unauthorized LCS Client");
    mapErrorNames.put(54L, "Position Method Failure");
    mapErrorNames.put(58L, "Unknown or Unreachable LCS Client");
    mapErrorNames.put(59L, "MM Event Not Supported");
    mapErrorNames.put(60L, "ATSI Not Allowed");
    mapErrorNames.put(61L, "ATM Not Allowed");
    mapErrorNames.put(62L, "Information Not Available");
    mapErrorNames.put(71L, "Unknown Alphabet");
    mapErrorNames.put(72L, "USSD Busy");
  }

  private static final Map<Long, String> capErrorNames;

  static {
    capErrorNames = new HashMap<>();
    capErrorNames.put((long) CAPErrorCode.canceled, "Canceled");
    capErrorNames.put((long) CAPErrorCode.cancelFailed, "Cancel Failed");
    capErrorNames.put((long) CAPErrorCode.eTCFailed, "ETC Failed");
    capErrorNames.put((long) CAPErrorCode.improperCallerResponse, "Improper Caller Response");
    capErrorNames.put((long) CAPErrorCode.missingCustomerRecord, "Missing Customer Record");
    capErrorNames.put((long) CAPErrorCode.missingParameter, "Missing Parameter");
    capErrorNames.put((long) CAPErrorCode.parameterOutOfRange, "Parameter Out Of Range");
    capErrorNames.put((long) CAPErrorCode.requestedInfoError, "Requested Info Error");
    capErrorNames.put((long) CAPErrorCode.systemFailure, "System Failure");
    capErrorNames.put((long) CAPErrorCode.taskRefused, "Task Refused");
    capErrorNames.put((long) CAPErrorCode.unavailableResource, "Unavailable Resource");
    capErrorNames.put((long) CAPErrorCode.unexpectedComponentSequence, "Unexpected Component Sequence");
    capErrorNames.put((long) CAPErrorCode.unexpectedDataValue, "Unexpected Data Value");
    capErrorNames.put((long) CAPErrorCode.unexpectedParameter, "Unexpected Parameter");
    capErrorNames.put((long) CAPErrorCode.unknownLegID, "Unknown Leg ID");
    capErrorNames.put((long) CAPErrorCode.unknownPDPID, "Unknown PDP ID");
    capErrorNames.put((long) CAPErrorCode.unknownCSID, "Unknown CS ID");
  }

  public static String getMapErrorCodeToString(MAPErrorMessage mapErrorMessage) {
    return Optional.ofNullable(mapErrorMessage).map(MAPErrorMessage::getErrorCode)
        .map(code -> mapErrorNames.getOrDefault(code, "Unknown MAP Error " + code))
        .orElse("No MAP Error Code");
  }

  public static String getCapErrorCodeToString(CAPErrorMessage capErrorMessage) {
    return Optional.ofNullable(capErrorMessage).map(CAPErrorMessage::getErrorCode)
        .map(code -> capErrorNames.getOrDefault(code, "Unknown CAP Error " + code))
        .orElse("No CAP Error Code");
  }
}
