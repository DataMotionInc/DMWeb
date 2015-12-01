package com.certifiedmail.ssl.CMv4;

public class CMv4SoapProxy implements com.certifiedmail.ssl.CMv4.CMv4Soap {
  private String _endpoint = null;
  private com.certifiedmail.ssl.CMv4.CMv4Soap cMv4Soap = null;
  
  public CMv4SoapProxy() {
    _initCMv4SoapProxy();
  }
  
  public CMv4SoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initCMv4SoapProxy();
  }
  
  private void _initCMv4SoapProxy() {
    try {
      cMv4Soap = (new com.certifiedmail.ssl.CMv4.CMv4Locator()).getCMv4Soap();
      if (cMv4Soap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)cMv4Soap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)cMv4Soap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (cMv4Soap != null)
      ((javax.xml.rpc.Stub)cMv4Soap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.certifiedmail.ssl.CMv4.CMv4Soap getCMv4Soap() {
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    return cMv4Soap;
  }
  
  public java.lang.String logon(java.lang.String userIDorEmail, java.lang.String password) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    return cMv4Soap.logon(userIDorEmail, password);
  }
  
  public int[] getInboxMIDs(java.lang.String sessionKey, int folderID, int filter, boolean mustHaveAttachments, com.certifiedmail.ssl.CMv4.holders.ArrayOfIntHolder msIDs) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    return cMv4Soap.getInboxMIDs(sessionKey, folderID, filter, mustHaveAttachments, msIDs);
  }
  
  public java.lang.String getInboxMIMEMessage(java.lang.String sessionKey, int MID) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    return cMv4Soap.getInboxMIMEMessage(sessionKey, MID);
  }
  
  public java.lang.String getInboxMIMEMessageEncoded(java.lang.String sessionKey, int MID) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    return cMv4Soap.getInboxMIMEMessageEncoded(sessionKey, MID);
  }
  
  public java.lang.String sendMIMEMessage(java.lang.String sessionKey, java.lang.String message) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    return cMv4Soap.sendMIMEMessage(sessionKey, message);
  }
  
  public java.lang.String sendMIMEMessageEncoded(java.lang.String sessionKey, java.lang.String message) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    return cMv4Soap.sendMIMEMessageEncoded(sessionKey, message);
  }
  
  public java.lang.String getMailboxXML(java.lang.String sessionKey, int mailboxType, int pageNum, java.lang.String orderBy, int folderID, java.lang.String filter, int pageSize, boolean orderDesc, boolean getRetractedMsgs, boolean getInboxUnReadOnly) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    return cMv4Soap.getMailboxXML(sessionKey, mailboxType, pageNum, orderBy, folderID, filter, pageSize, orderDesc, getRetractedMsgs, getInboxUnReadOnly);
  }
  
  public java.lang.String getMIMEMessage(java.lang.String sessionKey, int MID, boolean withCMHeaderXML, boolean withTrackingXML, boolean withSecurityEnvelope) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    return cMv4Soap.getMIMEMessage(sessionKey, MID, withCMHeaderXML, withTrackingXML, withSecurityEnvelope);
  }
  
  public java.lang.String getFolderList(java.lang.String sessionKey) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    return cMv4Soap.getFolderList(sessionKey);
  }
  
  public java.lang.String createFolder(java.lang.String sessionKey, int folderType, java.lang.String folderName) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    return cMv4Soap.createFolder(sessionKey, folderType, folderName);
  }
  
  public java.lang.String deleteMessage(java.lang.String sessionKey, int folderType, int MID, boolean permanentlyDelete) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    return cMv4Soap.deleteMessage(sessionKey, folderType, MID, permanentlyDelete);
  }
  
  public java.lang.String getInTransit(java.lang.String sessionKey, int folderType, int MID) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    return cMv4Soap.getInTransit(sessionKey, folderType, MID);
  }
  
  public java.lang.String getAccountDetails(java.lang.String sessionKey) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    return cMv4Soap.getAccountDetails(sessionKey);
  }
  
  public void logout(java.lang.String sessionKey) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    cMv4Soap.logout(sessionKey);
  }
  
  public void deleteInboxMessage(java.lang.String sessionKey, int MID) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    cMv4Soap.deleteInboxMessage(sessionKey, MID);
  }
  
  public void deleteFolder(java.lang.String sessionKey, int FID) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    cMv4Soap.deleteFolder(sessionKey, FID);
  }
  
  public void moveMessage(java.lang.String sessionKey, int MID, int destFID) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    cMv4Soap.moveMessage(sessionKey, MID, destFID);
  }
  
  public void retractMessage(java.lang.String sessionKey, int MID) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    cMv4Soap.retractMessage(sessionKey, MID);
  }
  
  public void setInTransit(java.lang.String sessionKey, int folderType, int MID, boolean inTransitValue) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    cMv4Soap.setInTransit(sessionKey, folderType, MID, inTransitValue);
  }
  
  public void changePassword(java.lang.String emailAddr, java.lang.String newPassword, java.lang.String oldPassword) throws java.rmi.RemoteException{
    if (cMv4Soap == null)
      _initCMv4SoapProxy();
    cMv4Soap.changePassword(emailAddr, newPassword, oldPassword);
  }
  
  
}