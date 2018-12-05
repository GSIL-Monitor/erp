package com.stosz.tms.external.aramex;

public class Service_1_0Proxy implements com.stosz.tms.external.aramex.Service_1_0_PortType {
  private String _endpoint = null;
  private com.stosz.tms.external.aramex.Service_1_0_PortType service_1_0_PortType = null;

  public Service_1_0Proxy() {
    _initService_1_0Proxy();
  }

  public Service_1_0Proxy(String endpoint) {
    _endpoint = endpoint;
    _initService_1_0Proxy();
  }

  private void _initService_1_0Proxy() {
    try {
      service_1_0_PortType = (new com.stosz.tms.external.aramex.Service_1_0_ServiceLocator()).getBasicHttpBinding_Service_1_0();
      if (service_1_0_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)service_1_0_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)service_1_0_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (service_1_0_PortType != null)
      ((javax.xml.rpc.Stub)service_1_0_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public com.stosz.tms.external.aramex.Service_1_0_PortType getService_1_0_PortType() {
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType;
  }

  public com.stosz.tms.external.aramex.ShipmentCreationResponse createShipments(com.stosz.tms.external.aramex.ShipmentCreationRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.createShipments(parameters);
  }

  public com.stosz.tms.external.aramex.LabelPrintingResponse printLabel(com.stosz.tms.external.aramex.LabelPrintingRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.printLabel(parameters);
  }

  public com.stosz.tms.external.aramex.PickupCreationResponse createPickup(com.stosz.tms.external.aramex.PickupCreationRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.createPickup(parameters);
  }

  public com.stosz.tms.external.aramex.PickupCancelationResponse cancelPickup(com.stosz.tms.external.aramex.PickupCancelationRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.cancelPickup(parameters);
  }

  public com.stosz.tms.external.aramex.ReserveRangeResponse reserveShipmentNumberRange(com.stosz.tms.external.aramex.ReserveRangeRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.reserveShipmentNumberRange(parameters);
  }

  public com.stosz.tms.external.aramex.LastReservedShipmentNumberRangeResponse getLastShipmentsNumbersRange(com.stosz.tms.external.aramex.LastReservedShipmentNumberRangeRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.getLastShipmentsNumbersRange(parameters);
  }

  public com.stosz.tms.external.aramex.ScheduledDeliveryResponse scheduleDelivery(com.stosz.tms.external.aramex.ScheduledDeliveryRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.scheduleDelivery(parameters);
  }

  public com.stosz.tms.external.aramex.HoldCreationResponse holdShipments(com.stosz.tms.external.aramex.HoldCreationRequest parameters) throws java.rmi.RemoteException{
    if (service_1_0_PortType == null)
      _initService_1_0Proxy();
    return service_1_0_PortType.holdShipments(parameters);
  }


}