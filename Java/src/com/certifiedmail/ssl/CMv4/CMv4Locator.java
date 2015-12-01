/**
 * CMv4Locator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.certifiedmail.ssl.CMv4;

public class CMv4Locator extends org.apache.axis.client.Service implements com.certifiedmail.ssl.CMv4.CMv4 {

    public CMv4Locator() {
    }


    public CMv4Locator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CMv4Locator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CMv4Soap
    private java.lang.String CMv4Soap_address = "https://ssl.datamotion.com/cmv4/cmv4.asmx";

    public java.lang.String getCMv4SoapAddress() {
        return CMv4Soap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CMv4SoapWSDDServiceName = "CMv4Soap";

    public java.lang.String getCMv4SoapWSDDServiceName() {
        return CMv4SoapWSDDServiceName;
    }

    public void setCMv4SoapWSDDServiceName(java.lang.String name) {
        CMv4SoapWSDDServiceName = name;
    }

    public com.certifiedmail.ssl.CMv4.CMv4Soap getCMv4Soap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CMv4Soap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCMv4Soap(endpoint);
    }

    public com.certifiedmail.ssl.CMv4.CMv4Soap getCMv4Soap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.certifiedmail.ssl.CMv4.CMv4SoapStub _stub = new com.certifiedmail.ssl.CMv4.CMv4SoapStub(portAddress, this);
            _stub.setPortName(getCMv4SoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCMv4SoapEndpointAddress(java.lang.String address) {
        CMv4Soap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.certifiedmail.ssl.CMv4.CMv4Soap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.certifiedmail.ssl.CMv4.CMv4SoapStub _stub = new com.certifiedmail.ssl.CMv4.CMv4SoapStub(new java.net.URL(CMv4Soap_address), this);
                _stub.setPortName(getCMv4SoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CMv4Soap".equals(inputPortName)) {
            return getCMv4Soap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://ssl.certifiedmail.com/CMv4", "CMv4");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://ssl.certifiedmail.com/CMv4", "CMv4Soap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CMv4Soap".equals(portName)) {
            setCMv4SoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
