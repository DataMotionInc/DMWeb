package com.datamotion;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jdesktop.dataset.DataSet;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.certifiedmail.ssl.CMv4.*;
import com.certifiedmail.ssl.CMv4.holders.ArrayOfIntHolder;
import com.datamotion.Models.Admin.AccountIndexRequestModel;
import com.datamotion.Models.Admin.AccountListModel;
import com.datamotion.Models.Admin.AccountModel;
import com.datamotion.Models.Admin.IdentityModel;
import com.datamotion.Models.Admin.LoginModel;
import com.datamotion.Models.Admin.UserTypeModel;
import com.datamotion.Models.Messaging.Folder;
import com.datamotion.Models.Provisioning.CompanyModel;
import com.datamotion.Models.Provisioning.RequestModel;
import com.datamotion.Models.Provisioning.RequestModel.Command;
import com.datamotion.Models.Provisioning.ResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Class provided by DataMotion to interface with our SecureMail and Direct messaging system 
 *
 * @author Kyle Corsi
 * @version 0.2, 6/1/2015
 */

class SearchParameters
{
	public Object City;
	public Object Fax;
	public Object FirstName;
	public Object LastName;
	public Object Npi;
	public Object OrganizationName;
	public Object OrganizationNpi;
	public Object OrganizationSpecialty;
	public Object Phone;
	public Object Role;
	public Object Specialty;
	public Object State;
	public Object Street;
	public Object Zip;
}
class SearchResponse
{
	public List<Entry> Entries;
	public SearchParameters SearchParameters;
	public int TotalMatches;
}
public class DMWeb {
	
	public static class Messaging {
		CMv4SoapProxy proxy = new CMv4SoapProxy();
	
	public Messaging(String endpoint) {
		proxy.setEndpoint(endpoint);
	}
	/**
	 * Function call that modifies the user's password by specifying 
	 * The email address, old password and new password. 
	 *
	 * @param  emailAddr  The user's email address
	 * @param  newPassword The user's new password
	 * @param  oldPassword The user's old password
	 * @throws RemoteException If the email address is invalid, old password is invalid or new password does not fit criteria
	 */
	public void ChangePassword(String emailAddr, String newPassword, String oldPassword) throws RemoteException {
		proxy.changePassword(emailAddr, newPassword, oldPassword);
	}
	
	/**
	 * Function call that creates a folder and returns 
	 * the Folder ID (FID) if successful, or an exception
	 * if it failed for some reason 
	 *
	 * @param  sessionKey  Valid string containing the user's sessionkey
	 * @param  folderType Integer for the folder type (0 for inbox, 1 for outbox)
	 * @param  folder String for the folder's name
	 * @return String The function returns a string containing the Folder ID (FID)
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws RemoteException
	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
	 */
	public String CreateFolder(String sessionKey, int folderType, String folder) throws RemoteException, ParserConfigurationException, SAXException, IOException {
		String result = proxy.createFolder(sessionKey, folderType, folder);
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(result));
		Document doc = db.parse(is);
		NodeList nodes = doc.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			NodeList name = element.getElementsByTagName("NewFID");
			Element line = (Element) name.item(0);
			result = line.getNodeValue().toString();
		}
		return result;
	}
	
	/**
	 * Function call that deletes a folder 
	 * and raises an exception if it failed for some reason 
	 *
	 * @param  sessionKey  Valid string containing the user's sessionkey
	 * @param  FID Integer containing the FID
	 * @throws RemoteException If the sessionKey or FID is invalid
	 * @see com.datamotion.direct.DataMotionDirect#getFolders(String) Get Folders
	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
	 */
	public void DeleteFolder(String sessionKey, int FID) throws RemoteException {
		proxy.deleteFolder(sessionKey, FID);
	}

	
	/**
	 * Function call that deletes a message and returns and empty string if successful;
	 * and raises an exception if it failed for some reason 
	 *
	 * @param  sessionKey  Valid string containing the user's sessionkey
	 * @param  folderType Integer containg the folderType (0 for inbox, ,1 for outbox)
	 * @param permanentlyDelete Boolean determining if the message should be permanently deleted or not
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @return The description of the resulting action
	 * @see com.datamotion.direct.DataMotionDirect#getFolders(String) Get Folders
	 * @see com.datamotion.direct.DataMotionDirect#getMailbox(String, int, int, String, int, String, int, boolean, boolean, boolean) Get Mailbox
	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
	 */
	public String DeleteMessage(String sessionKey, int folderType, int MID, boolean permanentlyDelete) throws ParserConfigurationException, SAXException, IOException {
		String result = proxy.deleteMessage(sessionKey, folderType, MID, permanentlyDelete);
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(result));
		Document doc = db.parse(is);
		NodeList nodes = doc.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			NodeList name = element.getElementsByTagName("Description");
			Element line = (Element) name.item(0);
			result = line.getNodeValue().toString();
		}
		return result;
	}
	
	/**
	 * Function call that returns all account information for a given user
	 * and raises an exception if it failed for some reason 
	 *
	 * @param  sessionKey  Valid string containing the user's sessionkey
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @return AccountDetails object
	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
	 */
	public AccountDetails GetAccountDetails(String sessionKey) throws ParserConfigurationException, SAXException, IOException, Exception {
		String result = proxy.getAccountDetails(sessionKey);
		AccountDetails accountDetails = new AccountDetails();
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(result));
		Document doc = db.parse(is);
		NodeList pnodes = doc.getElementsByTagName("AccountDetails");
		NodeList nodes = pnodes.item(0).getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			switch (nodes.item(i).getNodeName()) {
			case "MsgUnread":
				accountDetails.MsgUnread = Integer.parseInt(nodes.item(i).getTextContent());
				break;
			case "MsgSent":
				accountDetails.MsgSent = Integer.parseInt(nodes.item(i).getTextContent());
				break;
			case "TotalMsgSent":
				accountDetails.TotalMsgSent = Integer.parseInt(nodes.item(i).getTextContent());
				break;
			case "MsgReceived":
				accountDetails.MsgReceived = Integer.parseInt(nodes.item(i).getTextContent());
				break;
			case "TotalMsgReceived":
				accountDetails.TotalMsgReceived = Integer.parseInt(nodes.item(i).getTextContent());
				break;
			case "TotalDiskSpace":
				accountDetails.TotalDiskSpace = Integer.parseInt(nodes.item(i).getTextContent());
				break;
			case "DiskSpaceUsed":
				accountDetails.DiskSpaceUsed = Integer.parseInt(nodes.item(i).getTextContent());
				break;
			case "TotalVisits":
				accountDetails.TotalVisits = Integer.parseInt(nodes.item(i).getTextContent());
				break;
			case "LastLoginDateTime":
				accountDetails.LastLoginDateTime = nodes.item(i).getTextContent();
				break;
			case "DateCreated":
				accountDetails.DateCreated = nodes.item(i).getTextContent();
				break;
			case "DateLastNotice":
				accountDetails.DateLastNotice = nodes.item(i).getTextContent();
				break;
			case "DatePasswordExpires":
				accountDetails.DatePasswordeExpires = nodes.item(i).getTextContent();
				break;
			case "TotalFilesSent":
				accountDetails.TotalFilesSent = Integer.parseInt(nodes.item(i).getTextContent());
				break;
			case "TotalFilesInOutbox":
				accountDetails.TotalFilesInOutbox = Integer.parseInt(nodes.item(i).getTextContent());
			}
		}
		return accountDetails;
	}
	
	
	/**
	 * Function call that returns the current endpoint value
	 * @return String containing the current endpoint
	 */
	public String GetEndpoint() {
		return proxy.getEndpoint();
	}
	
	/**
	 * Function call that returns a folder object with all folders' information
	 * The function raises an exception if it failed for some reason 
	 *
	 * @param  sessionKey  Valid string containing the user's sessionkey
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @return List of type Folder
	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
	 */
	public List<Folder> GetFolderList(String sessionKey) throws ParserConfigurationException, SAXException, IOException {
		String result = proxy.getFolderList(sessionKey);
		List<Folder> folders = new ArrayList<Folder>();
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(result));
		Document doc = db.parse(is);
		Folder Folder = new Folder();
		NodeList pnodes = doc.getElementsByTagName("Folder");
		for (int j = 0; j < pnodes.getLength(); j++) {
			NodeList nodes = pnodes.item(j).getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				switch (nodes.item(i).getNodeName()) {
				case "FID":
					Folder.setFID(Integer.parseInt(nodes.item(i).getTextContent()));
					break;
				case "Name":
					Folder.setName(nodes.item(i).getTextContent());
					break;
				case "Type":
					Folder.setType(Integer.parseInt(nodes.item(i).getTextContent()));
					break;
				case "TypeDesc":
					Folder.setTypeDesc(nodes.item(i).getTextContent());
					break;
				case "TotalMessages":
					Folder.setTotalMessage(Integer.parseInt(nodes.item(i).getTextContent()));
					folders.add(Folder);
					Folder = new Folder();
				}
			}
		}
		return folders;
	}
	
	
	/**
	 * Function call that prints whether a message is in transit or not
	 *
	 * @param  sessionKey  Valid string containing the user's sessionkey
	 * @param folderType Integer representing the folder type
	 * @param MID Integer containing the Message ID (MID)
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
	 * @see com.datamotion.direct.DataMotionDirect#getMailbox(String, int, int, String, int, String, int, boolean, boolean, boolean) Get Mailbox
	 * @see com.datamotion.direct.DataMotionDirect#getFolders(String) Get Folders
	 */
	public String GetInTransit(String sessionKey, int folderType, int MID) throws ParserConfigurationException, SAXException, IOException {
		String result = proxy.getInTransit(sessionKey, folderType, MID);
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(result));
		Document doc = db.parse(is);
		NodeList nodes = doc.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			NodeList name = element.getElementsByTagName("Value");
			Element line = (Element) name.item(0);
			result = line.getNodeValue();
		}
		return result;
	}
	
	/**
	 * Function call that returns a XML string with various information about the mailbox folder you query
	 *
	 * @param  sessionKey  Valid string containing the user's sessionkey
	 * @param mailboxType Integer containing a (0 for inbox, 1 for outbox)
	 * @param pageNum Integer containing the page number
	 * @param orderBy String containing "DATE", "NAME", etc.
	 * @param folderID Integer containing the folder ID
	 * @param filter String to filter messages by
	 * @param pageSize Integer containing how many elements per page
	 * @param orderDesc Boolean for whether to order by description or not
	 * @param getRetractedMsgs Boolean for whether or not to get retracted messages
	 * @param getInboxUnReadOnly Boolean for whether or not to get unread only
	 * @return String Object containing the mailbox contents
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
	 * @see com.datamotion.direct.DataMotionDirect#getFolders(String) Get Folders
	 */
	public String[][] GetMailboxXML(String sessionKey, int mailboxType, int pageNum, String orderBy, int folderID, String filter, int pageSize, boolean orderDesc, boolean getRetractedMsgs, boolean getInboxUnReadOnly) throws ParserConfigurationException, SAXException, IOException {
		String result = proxy.getMailboxXML(sessionKey, mailboxType, pageNum, orderBy, folderID, filter, pageSize, orderDesc, getRetractedMsgs, getInboxUnReadOnly);
		ArrayList<String> row = new ArrayList<String>();
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>(); 
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(result));
		Document doc = db.parse(is);
		NodeList pnodes = doc.getElementsByTagName("MessageListItem");
		for (int j = 0; j < pnodes.getLength(); j++) {
			NodeList nodes = pnodes.item(j).getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				switch (nodes.item(i).getNodeName()) {
				case "MID":
					row.add(nodes.item(i).getTextContent());
					break;
				case "CreateTime":
					row.add(nodes.item(i).getTextContent());
					break;
				case "FromEMail":
					row.add(nodes.item(i).getTextContent());
					break;
				case "Subject":
					row.add(nodes.item(i).getTextContent());
					break;
				case "MsgSize":
					row.add(nodes.item(i).getTextContent());
					break;
				case "MsgStatus":
					row.add(nodes.item(i).getTextContent());
					break;
				case "ReadConfirmation":
					row.add(nodes.item(i).getTextContent());
					rows.add(row);
					row = new ArrayList<String>();
				}
			}
		}
		final int listSize = rows.size();
		String[][] data = new String[listSize][];
		for (int i = 0; i < listSize; i++) {
			ArrayList<String> sublist = rows.get(i);
			final int sublistsize = sublist.size();
			data[i] = new String[sublistsize];
			for (int j = 0; j < sublistsize; j++) {
				data[i][j] = sublist.get(j);
			}
		}
		return data;
	}
	
	/**
	 * Function call that returns a XML string with message information
	 *
	 * @param  sessionKey  Valid string containing the user's sessionkey
	 * @param MID Integer containing the Message ID (MID)
	 * @param withCMHeaderXML Boolean for whether or not to include CMHeader
	 * @param withTrackingXML Boolean for whether or not to include Tracking
	 * @param withSecurityEnvelope Boolean for whether or not to include Security Envelope
	 * @throws RemoteException If the sessionKey or MID is invalid
	 * @return String containing the MIME message (should be parsed with an appropriate library or javax.mail)
	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
	 * @see com.datamotion.direct.DataMotionDirect#getMailbox(String, int, int, String, int, String, int, boolean, boolean, boolean) Get Mailbox
	 * @see com.datamotion.direct.DataMotionDirect#getFolders(String) Get Folders
	 */
	public String GetMIMEMessage(String sessionKey, int MID, boolean withCMHeaderXML, boolean withTrackingXML, boolean withSecurityEnvelope) throws RemoteException {
		return proxy.getMIMEMessage(sessionKey, MID, withCMHeaderXML, withTrackingXML, withSecurityEnvelope);
	}
	
//	/**
//	 * Function call that returns a integer array of MID's
//	 *
//	 * @param  sessionKey  Valid string containing the user's sessionkey
//	 * @param folderID Integer containing the Folder ID (FID)
//	 * @param filter Integer containing the filter
//	 * @param mustHaveAttachments Boolean for whether or not to include only messages with attachments
//	 * @param msIDs ArrayOfIntHolder for holding MSID's
//	 * @throws RemoteException If the sessionKey, folderID or filter is invalid
//	 * @return String containing the XML for messages of the given folder
//	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
//	 * @see com.datamotion.direct.DataMotionDirect#getFolders(String) Get Folders
//	 */
//	public int[] getMessages(String sessionKey, int folderID, int filter, boolean mustHaveAttachments, ArrayOfIntHolder msIDs) throws RemoteException {
//		return proxy.getInboxMIDs(sessionKey, folderID, filter, mustHaveAttachments, msIDs);
//	}
	
	/**
	 * Function call that returns a sessionkey string for a valid user
	 *
	 * @param  username  String containing the user's username
	 * @param password String containing the user's password
	 * @throws RemoteException If the username or password is invalid
	 * @return String containing the sessionkey
	 */
	public String Logon(String userId, String password) throws RemoteException {
		return proxy.logon(userId, password);
	}
	
	/**
	 * Function call that invalidates the sessionkey
	 *
	 * @param  sessionkey String containing a valid sessionkey
	 * @throws RemoteException If the username or password is invalid
	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
	 */
	public void Logout(String sessionkey) throws RemoteException {
		proxy.logout(sessionkey);
	}
	
	/**
	 * Function call that moves a message to a folder
	 *
	 * @param  sessionKey String containing a valid sessionkey
	 * @param MID Integer containing the Message ID (MID)
	 * @param destFID Integer containing the destination Folder ID (FID)
	 * @throws RemoteException If the sessionkey, MID or destFID is invalid
	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
	 * @see com.datamotion.direct.DataMotionDirect#getMailbox(String, int, int, String, int, String, int, boolean, boolean, boolean) Get Mailbox
	 * @see com.datamotion.direct.DataMotionDirect#getFolders(String) Get Folders
	 */
	public void MoveMessage(String sessionKey, int MID, int destFID) throws RemoteException {
		proxy.moveMessage(sessionKey, MID, destFID);
	}
	
	/**
	 * Function call that retracts a message that was sent if it hasn't already been opened
	 *
	 * @param sessionKey String containing a valid sessionkey
	 * @param MID Integer containing the Message ID (MID)
	 * @throws RemoteException If the sessionkey or MID is invalid
	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
	 * @see com.datamotion.direct.DataMotionDirect#getMailbox(String, int, int, String, int, String, int, boolean, boolean, boolean) Get Mailbox
	 */
	public void RetractMessage(String sessionKey, int MID) throws RemoteException {
		proxy.retractMessage(sessionKey, MID);
	}
	
	/**
	 * Function call that sends a message
	 *
	 * @param sessionKey String containing a valid sessionkey
	 * @param To String array containing all the to addresses
	 * @param Cc String array containing all the cc addresses
	 * @param Bcc String array containing all the bcc addresses
	 * @param attachments File array list containing desired attachment files
	 * @param message String containing the desired message
	 * @param Subject String containing the Subject line
	 * @throws Exception If there were errors processing and sending the message
	 * @return String containing the MID of the message sent
	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
	 */
	public String SendMIMEMessage(String sessionKey, String[] To, String[] Cc, String[] Bcc, ArrayList<String> attachments, String body, String subject) throws Exception {
		Properties props = new Properties();
		props.put("mail.host", "mail.cloud9.net");
		Session mailConnection = Session.getInstance(props, null);
		Message msg = new MimeMessage(mailConnection);
		if (To.length > 0){
			for (int i=0;i < To.length;i++) {
				System.out.println("Recipient: "+To[i]);
				msg.addRecipient(RecipientType.TO, new InternetAddress(To[i]));
			}
		}
		if (Cc.length > 0 && !Cc[0].equalsIgnoreCase("")) {
			System.out.println(Cc.length);
			for (int i=0;i < Cc.length;i++) {
				msg.addRecipient(RecipientType.CC, new InternetAddress(Cc[i]));
			}
		}
		if (Bcc.length > 0 && !Bcc[0].equalsIgnoreCase("")) {
			for (int i=0;i < Bcc.length;i++) {
				msg.addRecipient(RecipientType.BCC, new InternetAddress(Bcc[i]));
			}
		}
		Multipart multipart = new MimeMultipart();

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(body,"text/plain");
		multipart.addBodyPart(messageBodyPart);

		if (attachments != null && attachments.size() > 0) {
			for (int i = 0;i<attachments.size();i++) {
				MimeBodyPart attachPart = new MimeBodyPart();
				String attachFile = attachments.get(i);
				attachPart.attachFile(attachFile);
				multipart.addBodyPart(attachPart);
			}
		}

		msg.setContent(multipart);
		msg.setSubject(subject);
		ByteArrayOutputStream baos = null;
		String result = "";
		baos = new ByteArrayOutputStream();
		msg.writeTo(baos);
		result = baos.toString();
		System.out.println(result);
		if (baos != null) 
		{
			baos.close();
		}
		String response = proxy.sendMIMEMessage(sessionKey, result);
		System.out.println(response);
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(response));
		Document doc = db.parse(is);
		NodeList pnodes = doc.getElementsByTagName("SendMessage");
		NodeList nodes = pnodes.item(0).getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			switch (nodes.item(i).getNodeName()) {
			case "NewMID":
				return nodes.item(i).getTextContent();
			}
		}
		return "";
	}
	
//	/**
//	 * Function call that sends a message encoded
//	 *
//	 * @param sessionKey String containing a valid sessionkey
//	 * @param To String array containing all the to addresses
//	 * @param Cc String array containing all the cc addresses
//	 * @param Bcc String array containing all the bcc addresses
//	 * @param attachments File array list containing desired attachment files
//	 * @param message String containing the desired message
//	 * @param Subject String containing the Subject line
//	 * @throws Exception If there were errors processing and sending the message
//	 * @return String containing the MID of the message sent
//	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
//	 */
//	public String sendMessageEncoded(String sessionKey, String[] To, String[] Cc, String[] Bcc, ArrayList<String> attachments, String body, String subject) throws Exception {
//		Properties props = new Properties();
//		props.put("mail.host", "mail.cloud9.net");
//		Session mailConnection = Session.getInstance(props, null);
//		Message msg = new MimeMessage(mailConnection);
//		if (To.length > 0){
//			for (int i=0;i < To.length;i++) {
//				System.out.println("Recipient: "+To[i]);
//				msg.addRecipient(RecipientType.TO, new InternetAddress(To[i]));
//			}
//		}
//		if (Cc.length > 0 && !Cc[0].equalsIgnoreCase("")) {
//			System.out.println(Cc.length);
//			for (int i=0;i < Cc.length;i++) {
//				msg.addRecipient(RecipientType.CC, new InternetAddress(Cc[i]));
//			}
//		}
//		if (Bcc.length > 0 && !Bcc[0].equalsIgnoreCase("")) {
//			for (int i=0;i < Bcc.length;i++) {
//				msg.addRecipient(RecipientType.BCC, new InternetAddress(Bcc[i]));
//			}
//		}
//		Multipart multipart = new MimeMultipart();
//
//		MimeBodyPart messageBodyPart = new MimeBodyPart();
//		messageBodyPart.setContent(body,"text/plain");
//		multipart.addBodyPart(messageBodyPart);
//
//		if (attachments != null && attachments.size() > 0) {
//			for (int i = 0;i<attachments.size();i++) {
//				MimeBodyPart attachPart = new MimeBodyPart();
//				String attachFile = attachments.get(i);
//				attachPart.attachFile(attachFile);
//				multipart.addBodyPart(attachPart);
//			}
//		}
//
//		msg.setContent(multipart);
//		msg.setSubject(subject);
//		ByteArrayOutputStream baos = null;
//		String result = "";
//		baos = new ByteArrayOutputStream();
//		msg.writeTo(baos);
//		result = baos.toString();
//		System.out.println(result);
//		if (baos != null) 
//		{
//			baos.close();
//		}
//		return proxy.sendMIMEMessage(sessionKey, result);
//	}
	
	/**
	 * Function call that sets the cmv4 endpoint. Useful for switching the application from SecureMail to Direct
	 *
	 * @param endpoint String containing the URL of the CMv4 endpoint
	 */
	public void SetEndpoint(String endpoint) {
		proxy.setEndpoint(endpoint);
	}
	
	/**
	 * Function call that sets a message's In Transit state
	 *
	 * @param  sessionKey String containing a valid sessionkey
	 * @param folderType Integer containing the folder type
	 * @param MID Integer containing the Message ID (MID)
	 * @param inTransitValue Boolean setting the In Transit Value
	 * @throws RemoteException If the sessionKey, folderType or MID is invalid
	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
	 * @see com.datamotion.direct.DataMotionDirect#getMailbox(String, int, int, String, int, String, int, boolean, boolean, boolean) Get Mailbox
	 * @see com.datamotion.direct.DataMotionDirect#getFolders(String) Get Folders
	 */
	public void SetInTransit(String sessionKey, int folderType, int MID, boolean inTransitValue) throws RemoteException {
		proxy.setInTransit(sessionKey, folderType, MID, inTransitValue);
	}
	
	/**
	 * Function call that set returns a deleted message to the Inbox
	 *
	 * @param  sessionKey String containing a valid sessionkey
	 * @param MID Integer containing the Message ID (MID)
	 * @throws RemoteException If the sessionKey or MID is invalid
	 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
	 * @see com.datamotion.direct.DataMotionDirect#getMailbox(String, int, int, String, int, String, int, boolean, boolean, boolean) Get Mailbox
	 */
	public void UndeleteMessage(String sessionKey, int MID) throws RemoteException {
		proxy.moveMessage(sessionKey, MID, 1);
	}
	}

	public static class Admin {
		private class Headers
		{
			public static final String CompanyAutomation = "X-Company-Automation-Id";
			public static final String Email = "X-Email";
			public static final String Hash = "X-Hash";
			public static final String Iv = "X-Iv";
			public static final String Proxy = "X-Proxy";
			public static final String Role = "X-Role";
			public static final String SessionCookie = "Session-Key";
			public static final String SessionKey = "X-Session-Key";
			public static final String SingleSignOn = "X-Single-Sign-On-Id";
			public static final String Test = "X-Is-Test";
			public static final String User = "X-User-Id";
			public String AuthorizationPayload;
		}
	
		String endpoint, automationId, encryptionKey;
		String sessionKeyDateTimeFormat = "MM/dd/yyyy hh:mm:ss a";
		String HMACSHA512_ENCRYPTION = "HmacSHA512";
		String AES_ENCRYPTION_KEY = "AES";
		String AES_KEY_GENERATION_ENCRYPTION = "AES";
		String AES_CIPHER_ENCRYPTION = "AES/CBC/PKCS5Padding";
		
		public Admin(String endpoint, String automationId, String encryptionKey) {
			this.endpoint = endpoint;
			this.automationId = automationId;
			this.encryptionKey = encryptionKey;
		}
		
		public String GetSessionKey(LoginModel loginModel) throws UnsupportedEncodingException, ClientProtocolException, IOException {
			//Make timestamp
			DateTime currentDateTime = new DateTime(DateTimeZone.UTC);
			DateTimeFormatter fmt = DateTimeFormat.forPattern(sessionKeyDateTimeFormat);
			loginModel.setCurrentTimestamp(currentDateTime.toString(fmt));
			byte[] decodedEncryptionKey = Base64.decodeBase64(this.encryptionKey);
			String jsonObject = buildJSONStringFromObject(loginModel);
			IvParameterSpec ivParam = buildInitVector();
			String ivSpecString = Base64.encodeBase64String(ivParam.getIV());
			String xHashValue = getXHash(jsonObject.getBytes(), decodedEncryptionKey);
			String encryptedJsonPayload = encryptLoginJSON(jsonObject, decodedEncryptionKey, ivParam);
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost(this.endpoint + "/Remote/Account/GetSessionKey");
			try {
				encryptedJsonPayload = "\"" + encryptedJsonPayload + "\"";
				StringEntity input = new StringEntity(encryptedJsonPayload);
				httpPost.addHeader("Content-Type", "application/json");
				httpPost.setEntity(input);
				httpPost.addHeader("Accept", "application/json");
				httpPost.addHeader(Headers.Email, loginModel.getIdentity().getEmail());
				httpPost.addHeader(Headers.User, loginModel.getIdentity().getUserId());
				httpPost.addHeader(Headers.SingleSignOn, loginModel.getIdentity().getSingleSignOnId());
				httpPost.addHeader(Headers.Iv, ivSpecString);
				httpPost.addHeader(Headers.CompanyAutomation, this.automationId);
				httpPost.addHeader(Headers.Hash, xHashValue);
				httpPost.removeHeaders("Accept-Encoding");
				HttpResponse response = client.execute(httpPost); //wf_client.execute(httpPost);
				HttpEntity entity = response.getEntity();
				String theString = IOUtils.toString(entity.getContent(), "UTF-8");
				return theString;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new UnsupportedEncodingException(e.getMessage());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				throw new ClientProtocolException(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException(e.getMessage());
			}
		}

		public List<UserTypeModel> GetUserTypes(String sessionKey) throws UnsupportedEncodingException, ClientProtocolException, IOException {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost(this.endpoint + "/Remote/Account/Create");
			try {
				httpPost.addHeader("Accept", "application/json");
				httpPost.addHeader(Headers.SessionKey, sessionKey);
				httpPost.removeHeaders("Accept-Encoding");
				HttpResponse response = client.execute(httpPost); //wf_client.execute(httpPost);
				HttpEntity entity = response.getEntity();
				String content = IOUtils.toString(entity.getContent(), "UTF-8");
				Type listType = new TypeToken<ArrayList<UserTypeModel>>() { }.getType();
                List<UserTypeModel> userTypes = new Gson().fromJson(content, listType);
				return userTypes;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new RemoteException(e.getMessage());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				throw new ClientProtocolException(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException(e.getMessage());
			}
		}
		
		public AccountModel Create(String sessionKey, AccountModel accountModel) throws UnsupportedEncodingException, ClientProtocolException, IOException {
			String jsonObject = buildJSONStringFromObject(accountModel);
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost(this.endpoint + "/Remote/Account/Create");
			try {
				jsonObject = "\"" + jsonObject + "\"";
				StringEntity input = new StringEntity(jsonObject);
				httpPost.addHeader("Content-Type", "application/json");
				httpPost.setEntity(input);
				httpPost.addHeader("Accept", "application/json");
				httpPost.addHeader(Headers.SessionKey, sessionKey);
				httpPost.removeHeaders("Accept-Encoding");
				HttpResponse response = client.execute(httpPost); //wf_client.execute(httpPost);
				HttpEntity entity = response.getEntity();
				String content = IOUtils.toString(entity.getContent(), "UTF-8");
				return new Gson().fromJson(content, AccountModel.class);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new RemoteException(e.getMessage());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				throw new ClientProtocolException(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException(e.getMessage());
			}
		}
		
		public String GetEncryptionKey(String sessionKey, IdentityModel identityModel) throws UnsupportedEncodingException, ClientProtocolException, IOException {
			String jsonObject = buildJSONStringFromObject(identityModel);
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost(this.endpoint + "/Remote/Account/GetEncryptionKey");
			try {
				jsonObject = "\"" + jsonObject + "\"";
				StringEntity input = new StringEntity(jsonObject);
				httpPost.addHeader("Content-Type", "application/json");
				httpPost.setEntity(input);
				httpPost.addHeader("Accept", "application/json");
				httpPost.addHeader(Headers.SessionKey, sessionKey);
				httpPost.removeHeaders("Accept-Encoding");
				HttpResponse response = client.execute(httpPost); //wf_client.execute(httpPost);
				HttpEntity entity = response.getEntity();
				return IOUtils.toString(entity.getContent(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new RemoteException(e.getMessage());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				throw new ClientProtocolException(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException(e.getMessage());
			}
		}

		public String NewEncryptionKey(String sessionKey, IdentityModel identityModel) throws UnsupportedEncodingException, ClientProtocolException, IOException {
			String jsonObject = buildJSONStringFromObject(identityModel);
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost(this.endpoint + "/Remote/Account/NewEncryptionKey");
			try {
				jsonObject = "\"" + jsonObject + "\"";
				StringEntity input = new StringEntity(jsonObject);
				httpPost.addHeader("Content-Type", "application/json");
				httpPost.setEntity(input);
				httpPost.addHeader("Accept", "application/json");
				httpPost.addHeader(Headers.SessionKey, sessionKey);
				httpPost.removeHeaders("Accept-Encoding");
				HttpResponse response = client.execute(httpPost); //wf_client.execute(httpPost);
				HttpEntity entity = response.getEntity();
				return IOUtils.toString(entity.getContent(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new RemoteException(e.getMessage());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				throw new ClientProtocolException(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException(e.getMessage());
			}
		}

		public AccountListModel List(String sessionKey, AccountIndexRequestModel accountIndexRequest) throws UnsupportedEncodingException, ClientProtocolException, IOException {
			String jsonObject = buildJSONStringFromObject(accountIndexRequest);
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost(this.endpoint + "/Remote/Account/List");
			try {
				jsonObject = "\"" + jsonObject + "\"";
				StringEntity input = new StringEntity(jsonObject);
				httpPost.addHeader("Content-Type", "application/json");
				httpPost.setEntity(input);
				httpPost.addHeader("Accept", "application/json");
				httpPost.addHeader(Headers.SessionKey, sessionKey);
				httpPost.removeHeaders("Accept-Encoding");
				HttpResponse response = client.execute(httpPost); //wf_client.execute(httpPost);
				HttpEntity entity = response.getEntity();
				String content = IOUtils.toString(entity.getContent(), "UTF-8");
				return new Gson().fromJson(content, AccountListModel.class);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new RemoteException(e.getMessage());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				throw new ClientProtocolException(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException(e.getMessage());
			}
		}

		public AccountModel Read(String sessionKey, IdentityModel identityModel) throws UnsupportedEncodingException, ClientProtocolException, IOException {
			String jsonObject = buildJSONStringFromObject(identityModel);
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost(this.endpoint + "/Remote/Account/Read");
			try {
				jsonObject = "\"" + jsonObject + "\"";
				StringEntity input = new StringEntity(jsonObject);
				httpPost.addHeader("Content-Type", "application/json");
				httpPost.setEntity(input);
				httpPost.addHeader("Accept", "application/json");
				httpPost.addHeader(Headers.SessionKey, sessionKey);
				httpPost.removeHeaders("Accept-Encoding");
				HttpResponse response = client.execute(httpPost); //wf_client.execute(httpPost);
				HttpEntity entity = response.getEntity();
				String content = IOUtils.toString(entity.getContent(), "UTF-8");
				return new Gson().fromJson(content, AccountModel.class);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new RemoteException(e.getMessage());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				throw new ClientProtocolException(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException(e.getMessage());
			}
		}

		public AccountModel Update(String sessionKey, AccountModel accountModel) throws UnsupportedEncodingException, ClientProtocolException, IOException {
			String jsonObject = buildJSONStringFromObject(accountModel);
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost(this.endpoint + "/Remote/Account/Update");
			try {
				jsonObject = "\"" + jsonObject + "\"";
				StringEntity input = new StringEntity(jsonObject);
				httpPost.addHeader("Content-Type", "application/json");
				httpPost.setEntity(input);
				httpPost.addHeader("Accept", "application/json");
				httpPost.addHeader(Headers.SessionKey, sessionKey);
				httpPost.removeHeaders("Accept-Encoding");
				HttpResponse response = client.execute(httpPost); //wf_client.execute(httpPost);
				HttpEntity entity = response.getEntity();
				String content = IOUtils.toString(entity.getContent(), "UTF-8");
				return new Gson().fromJson(content, AccountModel.class);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new RemoteException(e.getMessage());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				throw new ClientProtocolException(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException(e.getMessage());
			}
		}
		
		public void Delete(String sessionKey, IdentityModel identityModel) throws RemoteException, ClientProtocolException, IOException {
			String jsonObject = buildJSONStringFromObject(identityModel);
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost(this.endpoint + "/Remote/Account/Delete");
			try {
				jsonObject = "\"" + jsonObject + "\"";
				StringEntity input = new StringEntity(jsonObject);
				httpPost.addHeader("Content-Type", "application/json");
				httpPost.setEntity(input);
				httpPost.addHeader("Accept", "application/json");
				httpPost.addHeader(Headers.SessionKey, sessionKey);
				httpPost.removeHeaders("Accept-Encoding");
				HttpResponse response = client.execute(httpPost); //wf_client.execute(httpPost);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new RemoteException(e.getMessage());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				throw new ClientProtocolException(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException(e.getMessage());
			}
		}
		
		//Helper Methods
		private String buildJSONStringFromObject(Object object)
		{
			ObjectMapper mapper = new ObjectMapper();
			String json = null;
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			try
			{
				if (object != null)
					json = mapper.writeValueAsString(object);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			return json;
		}
	
		private IvParameterSpec buildInitVector()
		{
			BytesKeyGenerator salt = KeyGenerators.secureRandom(16);
			byte[] iv = salt.generateKey();
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			return ivspec;
		}
		
		private String encryptLoginJSON(String jsonString, byte[] decodedEncryptionKey, IvParameterSpec ivParam)
		{
			SecretKeySpec secretKeySpec = new SecretKeySpec(decodedEncryptionKey, AES_KEY_GENERATION_ENCRYPTION);
			String encryptedJson = null;
			try
			{
				Cipher cipher = Cipher.getInstance(AES_CIPHER_ENCRYPTION);
				cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParam);
				byte[] encrypted = cipher.doFinal(jsonString.getBytes());
				encryptedJson = Base64.encodeBase64String(encrypted);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			return encryptedJson;
		}
		
		private String getXHash(byte[] serializedModel, byte[] encryptionKey)
		{
			Mac sha512_HMAC;
			try
			{
				sha512_HMAC = Mac.getInstance(HMACSHA512_ENCRYPTION);
				SecretKeySpec secretkey = new SecretKeySpec(encryptionKey, HMACSHA512_ENCRYPTION);
				sha512_HMAC.init(secretkey);
				byte[] encryptedValue = sha512_HMAC.doFinal(serializedModel);
				return Base64.encodeBase64String(encryptedValue);
			}
			catch (Exception ex)
			{
				return ex.getMessage();
			}
		}

	}

	public static class Provisioning {
		private class Headers
		{
			public static final String CompanyAutomation = "X-Company-Automation-Id";
			public static final String Email = "X-Email";
			public static final String Hash = "X-Hash";
			public static final String Iv = "X-Iv";
			public static final String Proxy = "X-Proxy";
			public static final String Role = "X-Role";
			public static final String SessionCookie = "Session-Key";
			public static final String SessionKey = "X-Session-Key";
			public static final String SingleSignOn = "X-Single-Sign-On-Id";
			public static final String Test = "X-Is-Test";
			public static final String User = "X-User-Id";
			public String AuthorizationPayload;
		}
	
		String provisionerKey;
		String sessionKeyDateTimeFormat = "MM/dd/yyyy hh:mm:ss a";
		String HMACSHA512_ENCRYPTION = "HmacSHA512";
		String AES_ENCRYPTION_KEY = "AES";
		String AES_KEY_GENERATION_ENCRYPTION = "AES";
		String AES_CIPHER_ENCRYPTION = "AES/CBC/PKCS5Padding";
		
		public Provisioning(String _provisionerKey) {
			this.provisionerKey = _provisionerKey;
		}
		
		public ResponseModel Provision(CompanyModel companyModel, String userName, String platformCode, Command command, String email) throws UnsupportedEncodingException, ClientProtocolException, IOException {
			byte[] decodedProvisionerKey = Base64.decodeBase64(this.provisionerKey);
			String jsonObject = buildJSONStringFromObject(companyModel);
			IvParameterSpec ivParam = buildInitVector();
			String ivSpecString = Base64.encodeBase64String(ivParam.getIV());
			String xHashValue = getXHash(jsonObject.getBytes(), decodedProvisionerKey);
			String encryptedJsonPayload = encryptJSON(jsonObject, decodedProvisionerKey, ivParam);
			encryptedJsonPayload = "\"" + encryptedJsonPayload + "\"";
			//Generate Request Model
			RequestModel requestModel = new RequestModel();
			requestModel.setUserName(userName);
			requestModel.setPlatformCode(platformCode);
			requestModel.setCommand(command);
			requestModel.setIV(ivSpecString);
			requestModel.setData(encryptedJsonPayload);
			requestModel.setHash(xHashValue);
			
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost("https://provisioning.datamotion.com:8888");
			try {
				StringEntity input = new StringEntity(new Gson().toJson(requestModel));
				httpPost.addHeader("Content-Type", "application/json");
				httpPost.setEntity(input);
				httpPost.addHeader("Accept", "application/json");
				httpPost.addHeader(Headers.Email, email);
				httpPost.addHeader(Headers.Iv, ivSpecString);
				httpPost.removeHeaders("Accept-Encoding");
				HttpResponse response = client.execute(httpPost); //wf_client.execute(httpPost);
				HttpEntity entity = response.getEntity();
				String theString = IOUtils.toString(entity.getContent(), "UTF-8");
				ResponseModel responseModel = new Gson().fromJson(theString, ResponseModel.class);
				responseModel.setData(decryptJSON(responseModel.getData(),decodedProvisionerKey,ivParam));
				return responseModel;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new UnsupportedEncodingException(e.getMessage());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				throw new ClientProtocolException(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException(e.getMessage());
			}
		}
		
		public ResponseModel GetEncryptionKey(CompanyModel companyModel, String userName, String platformCode, Command command, String email) throws UnsupportedEncodingException, ClientProtocolException, IOException {
			byte[] decodedProvisionerKey = Base64.decodeBase64(this.provisionerKey);
			String jsonObject = buildJSONStringFromObject(companyModel);
			IvParameterSpec ivParam = buildInitVector();
			String ivSpecString = Base64.encodeBase64String(ivParam.getIV());
			String xHashValue = getXHash(jsonObject.getBytes(), decodedProvisionerKey);
			String encryptedJsonPayload = encryptJSON(jsonObject, decodedProvisionerKey, ivParam);
			encryptedJsonPayload = "\"" + encryptedJsonPayload + "\"";
			//Generate Request Model
			RequestModel requestModel = new RequestModel();
			requestModel.setUserName(userName);
			requestModel.setPlatformCode(platformCode);
			requestModel.setCommand(command);
			requestModel.setIV(ivSpecString);
			requestModel.setData(encryptedJsonPayload);
			requestModel.setHash(xHashValue);
			
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost("https://provisioning.datamotion.com:8888/company/GetEncryptionKey");
			try {
				StringEntity input = new StringEntity(new Gson().toJson(requestModel));
				httpPost.addHeader("Content-Type", "application/json");
				httpPost.setEntity(input);
				httpPost.addHeader("Accept", "application/json");
				httpPost.addHeader(Headers.Email, email);
				httpPost.addHeader(Headers.Iv, ivSpecString);
				httpPost.removeHeaders("Accept-Encoding");
				HttpResponse response = client.execute(httpPost); //wf_client.execute(httpPost);
				HttpEntity entity = response.getEntity();
				String theString = IOUtils.toString(entity.getContent(), "UTF-8");
				ResponseModel responseModel = new Gson().fromJson(theString, ResponseModel.class);
				responseModel.setData(decryptJSON(responseModel.getData(),decodedProvisionerKey,ivParam));
				return responseModel;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new UnsupportedEncodingException(e.getMessage());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				throw new ClientProtocolException(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException(e.getMessage());
			}
		}

		public ResponseModel NewEncryptionKey(CompanyModel companyModel, String userName, String platformCode, Command command, String email) throws UnsupportedEncodingException, ClientProtocolException, IOException {
			byte[] decodedProvisionerKey = Base64.decodeBase64(this.provisionerKey);
			String jsonObject = buildJSONStringFromObject(companyModel);
			IvParameterSpec ivParam = buildInitVector();
			String ivSpecString = Base64.encodeBase64String(ivParam.getIV());
			String xHashValue = getXHash(jsonObject.getBytes(), decodedProvisionerKey);
			String encryptedJsonPayload = encryptJSON(jsonObject, decodedProvisionerKey, ivParam);
			encryptedJsonPayload = "\"" + encryptedJsonPayload + "\"";
			//Generate Request Model
			RequestModel requestModel = new RequestModel();
			requestModel.setUserName(userName);
			requestModel.setPlatformCode(platformCode);
			requestModel.setCommand(command);
			requestModel.setIV(ivSpecString);
			requestModel.setData(encryptedJsonPayload);
			requestModel.setHash(xHashValue);
			
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost("https://provisioning.datamotion.com:8888/company/NewEncryptionKey");
			try {
				StringEntity input = new StringEntity(new Gson().toJson(requestModel));
				httpPost.addHeader("Content-Type", "application/json");
				httpPost.setEntity(input);
				httpPost.addHeader("Accept", "application/json");
				httpPost.addHeader(Headers.Email, email);
				httpPost.addHeader(Headers.Iv, ivSpecString);
				httpPost.removeHeaders("Accept-Encoding");
				HttpResponse response = client.execute(httpPost); //wf_client.execute(httpPost);
				HttpEntity entity = response.getEntity();
				String theString = IOUtils.toString(entity.getContent(), "UTF-8");
				ResponseModel responseModel = new Gson().fromJson(theString, ResponseModel.class);
				responseModel.setData(decryptJSON(responseModel.getData(),decodedProvisionerKey,ivParam));
				return responseModel;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new UnsupportedEncodingException(e.getMessage());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				throw new ClientProtocolException(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException(e.getMessage());
			}
		}
		
		//Helper functions
		private String buildJSONStringFromObject(Object object)
		{
			ObjectMapper mapper = new ObjectMapper();
			String json = null;
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			try
			{
				if (object != null)
					json = mapper.writeValueAsString(object);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			return json;
		}
	
		private IvParameterSpec buildInitVector()
		{
			BytesKeyGenerator salt = KeyGenerators.secureRandom(16);
			byte[] iv = salt.generateKey();
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			return ivspec;
		}
		
		private String encryptJSON(String jsonString, byte[] decodedProvisionerKey, IvParameterSpec ivParam)
		{
			SecretKeySpec secretKeySpec = new SecretKeySpec(decodedProvisionerKey, AES_KEY_GENERATION_ENCRYPTION);
			String encryptedJson = null;
			try
			{
				Cipher cipher = Cipher.getInstance(AES_CIPHER_ENCRYPTION);
				cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParam);
				byte[] encrypted = cipher.doFinal(jsonString.getBytes());
				encryptedJson = Base64.encodeBase64String(encrypted);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			return encryptedJson;
		}
		
		private String decryptJSON(String encryptedString, byte[] decodedProvisionerKey, IvParameterSpec ivParam)
		{
			SecretKeySpec secretKeySpec = new SecretKeySpec(decodedProvisionerKey, AES_KEY_GENERATION_ENCRYPTION);
			String encryptedJson = null;
			try
			{
				Cipher cipher = Cipher.getInstance(AES_CIPHER_ENCRYPTION);
				cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParam);
				byte[] decrypted = cipher.doFinal(encryptedString.getBytes());
				encryptedJson = new String(Base64.decodeBase64(decrypted));
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			return encryptedJson;
		}
		
		private String getXHash(byte[] serializedModel, byte[] encryptionKey)
		{
			Mac sha512_HMAC;
			try
			{
				sha512_HMAC = Mac.getInstance(HMACSHA512_ENCRYPTION);
				SecretKeySpec secretkey = new SecretKeySpec(encryptionKey, HMACSHA512_ENCRYPTION);
				sha512_HMAC.init(secretkey);
				byte[] encryptedValue = sha512_HMAC.doFinal(serializedModel);
				return Base64.encodeBase64String(encryptedValue);
			}
			catch (Exception ex)
			{
				return ex.getMessage();
			}
		}
	
	}
	
	public static class HPD {
		/**
		 * Function call that returns a list of HPD Entries based on a filter
		 *
		 * @param  sessionKey String containing a valid sessionkey
		 * @param City
		 * @param Fax
		 * @param FirstName
		 * @param LastName
		 * @param Npi
		 * @param OrganizationName
		 * @param OrganizationNpi
		 * @param OrganizationSpecialty
		 * @param Phone
		 * @param Role
		 * @param Specialty
		 * @param State
		 * @param Street
		 * @param Zip
		 * @throws IOException 
		 * @see <a href="DataMotionDirect.html#logon(java.lang.String, java.lang.String)">Java Spec Logging On (Obtain a sessionKey)</a>
		 */
		public List<Entry> SearchHPD(String sessionKey, String endpoint, String city, String fax,
				String FirstName, String LastName, String npi,
				String OrganizationName, String OrganizationNpi,
				String OrganizationSpecialty, String phone, String role,
				String specialty, String state, String street, String zip)
				throws IOException {
			switch (endpoint.toLowerCase())
			{
			case "https://ssl.dmhisp.com/cmv4/cmv4.asmx":
				endpoint = "https://ssl.dmhisp.com/HpdProxyApi/Search?City=";
				break;
			case "https://stage.dmhisp.com/cmv4/cmv4.asmx":
				endpoint = "https://stage.dmhisp.com/HpdProxyApi/Search?City=";
				break;
			case "https://direct.datamotioncorp.com/cmv4/cmv4.asmx":
				endpoint = "https://direct.datamotioncorp.com/HpdProxyApi/Search?City=";
				break;
			default:
				endpoint = "https://ssl.dmhisp.com/cmv4/cmv4.asmx";
				break;
			}
			URL url = new URL(endpoint
					+ city + "&Fax=" + fax + "&FirstName=" + FirstName
					+ "&LastName=" + LastName + "&Npi=" + npi
					+ "&OrganizationName=" + OrganizationName + "&OrganizationNpi="
					+ OrganizationNpi + "&OrganizationSpecialty="
					+ OrganizationSpecialty + "&Phone=" + phone + "&Role=" + role
					+ "&Specialty=" + specialty + "&State=" + state + "&Street="
					+ street + "&Zip" + zip);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accep", "application/json");
			conn.setRequestProperty("X-Session-Key", sessionKey);
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP Error Code: "
						+ conn.getResponseCode());
			}
			String line;
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			String response = sb.toString();
			Gson gson = new Gson();
			SearchResponse searchResponse = gson.fromJson(response,
					SearchResponse.class);
			return searchResponse.Entries;
		}
	}

}
