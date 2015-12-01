/**
 * CMv4Soap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.certifiedmail.ssl.CMv4;

public interface CMv4Soap extends java.rmi.Remote {
    public java.lang.String logon(java.lang.String userIDorEmail, java.lang.String password) throws java.rmi.RemoteException;
    public int[] getInboxMIDs(java.lang.String sessionKey, int folderID, int filter, boolean mustHaveAttachments, com.certifiedmail.ssl.CMv4.holders.ArrayOfIntHolder msIDs) throws java.rmi.RemoteException;
    public java.lang.String getInboxMIMEMessage(java.lang.String sessionKey, int MID) throws java.rmi.RemoteException;
    public java.lang.String getInboxMIMEMessageEncoded(java.lang.String sessionKey, int MID) throws java.rmi.RemoteException;
    public java.lang.String sendMIMEMessage(java.lang.String sessionKey, java.lang.String message) throws java.rmi.RemoteException;
    public java.lang.String sendMIMEMessageEncoded(java.lang.String sessionKey, java.lang.String message) throws java.rmi.RemoteException;
    public java.lang.String getMailboxXML(java.lang.String sessionKey, int mailboxType, int pageNum, java.lang.String orderBy, int folderID, java.lang.String filter, int pageSize, boolean orderDesc, boolean getRetractedMsgs, boolean getInboxUnReadOnly) throws java.rmi.RemoteException;
    public java.lang.String getMIMEMessage(java.lang.String sessionKey, int MID, boolean withCMHeaderXML, boolean withTrackingXML, boolean withSecurityEnvelope) throws java.rmi.RemoteException;
    public java.lang.String getFolderList(java.lang.String sessionKey) throws java.rmi.RemoteException;
    public java.lang.String createFolder(java.lang.String sessionKey, int folderType, java.lang.String folderName) throws java.rmi.RemoteException;
    public java.lang.String deleteMessage(java.lang.String sessionKey, int folderType, int MID, boolean permanentlyDelete) throws java.rmi.RemoteException;
    public java.lang.String getInTransit(java.lang.String sessionKey, int folderType, int MID) throws java.rmi.RemoteException;
    public java.lang.String getAccountDetails(java.lang.String sessionKey) throws java.rmi.RemoteException;
    public void logout(java.lang.String sessionKey) throws java.rmi.RemoteException;
    public void deleteInboxMessage(java.lang.String sessionKey, int MID) throws java.rmi.RemoteException;
    public void deleteFolder(java.lang.String sessionKey, int FID) throws java.rmi.RemoteException;
    public void moveMessage(java.lang.String sessionKey, int MID, int destFID) throws java.rmi.RemoteException;
    public void retractMessage(java.lang.String sessionKey, int MID) throws java.rmi.RemoteException;
    public void setInTransit(java.lang.String sessionKey, int folderType, int MID, boolean inTransitValue) throws java.rmi.RemoteException;
    public void changePassword(java.lang.String emailAddr, java.lang.String newPassword, java.lang.String oldPassword) throws java.rmi.RemoteException;
}
