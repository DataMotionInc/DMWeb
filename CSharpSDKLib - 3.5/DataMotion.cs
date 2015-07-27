using DMWebLib;
using DMWebLib.DMService;
using DMWebLib.Models.Admin;
using DMWebLib.Models.Provisioning;
using MailBee.Mime;
using Newtonsoft.Json;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Net;
using System.Security.Cryptography;
using System.Text;
using System.Web;
using System.Web.Services.Protocols;
using System.Xml;

namespace DMWebLib
{
    public class Messaging
    {
        CMv4 client = new CMv4();

        public struct folder
        {
            public int FID;
            public string Name;
            public int Type;
            public string TypeDesc;
            public int TotalMessages;
        }

        public Messaging(string endpoint)
        {
            client.Url = endpoint;
        }
        public void ChangePassword(string emailAddr, string newPassword, string oldPassword)
        {
            try
            {
                client.ChangePassword(emailAddr, newPassword, oldPassword);
            }
            catch (Exception ex)
            {
                throw new Exception("", ex);
            }
        }
        public string CreateFolder(string sessionKey, int folderType, string folder)
        {
            try
            {
                string result = client.CreateFolder(sessionKey, folderType, folder);
                string returnString = "test";
                using (XmlReader reader = XmlReader.Create(new StringReader(result)))
                {
                    while (reader.Read())
                    {
                        if (reader.NodeType == XmlNodeType.Element)
                        {
                            if (reader.Name == "NewFID")
                            {
                                while (reader.Value == "")
                                    reader.Read();
                                returnString = reader.Value;
                            }
                        }
                    }
                }
                return returnString;
            }
            catch (Exception ex)
            {
                throw new Exception("", ex);
            }
        }
        public void Logout(string sessionKey)
        {
            client.Logout(sessionKey);
        }
        public string Logon(string userId, string password)
        {
            try
            {
                return client.Logon(userId, password);
            }
            catch (Exception ex)
            {
                throw new Exception("", ex);
            }
        }
        public void SetEndpoint(string endpoint)
        {
            client.Url = endpoint;
        }
        public string GetEndpoint()
        {
            return client.Url;
        }
        public DataSet GetMailboxXML(string sessionKey, int mailboxType, int pageNum, string orderBy, int folderID, string filter, int pageSize, bool orderDesc, bool getRetractedMessages, bool getInboxUnreadOnly)
        {
            try
            {
                string result = client.GetMailboxXML(sessionKey, mailboxType, pageNum, orderBy, folderID, filter, pageSize, orderDesc, getRetractedMessages, getInboxUnreadOnly);
                DataSet dataSet = new DataSet();
                StringReader stringReader = new StringReader(result);
                dataSet.ReadXml(stringReader);
                return dataSet;
            }
            catch (Exception ex)
            {
                throw new Exception("", ex);
            }
        }
        public string DeleteMessage(string sessionKey, int folderType, int MID, bool permanentlyDelete)
        {
            try
            {
                string result = client.DeleteMessage(sessionKey, folderType, MID, permanentlyDelete);
                using (XmlReader reader = XmlReader.Create(new StringReader(result)))
                {
                    while (reader.Read())
                    {
                        if (reader.Name == "Description")
                        {
                            while (reader.Value == "")
                                reader.Read();
                            return reader.Value;
                        }
                    }
                    return "";
                }
            }
            catch (Exception ex)
            {
                throw new Exception("", ex);
            }
        }
        //public void deleteInboxMessage(string sessionKey, int MID)
        //{
        //    try
        //    {
        //        client.DeleteInboxMessage(sessionKey, MID);
        //    }
        //    catch (Exception ex)
        //    {
        //        throw new Exception("", ex);
        //    }
        //}
        public void RetractMessage(string sessionKey, int MID)
        {
            try
            {
                client.RetractMessage(sessionKey, MID);
            }
            catch (Exception ex)
            {
                throw new Exception("", ex);
            }
        }
        public string GetInTransit(string sessionKey, int folderType, int MID)
        {
            try
            {
                string result = client.GetInTransit(sessionKey, folderType, MID);
                using (XmlReader reader = XmlReader.Create(new StringReader(result)))
                {
                    while (reader.Read())
                    {
                        if (reader.Name == "Value")
                        {
                            while (reader.Value == "")
                                reader.Read();
                            return reader.Value;
                        }
                    }
                    return "";
                }
            }
            catch (Exception ex)
            {
                throw new Exception("", ex);
            }
        }
        public void SetInTransit(string sessionKey, int folderType, int MID, bool inTransitValue)
        {
            try
            {
                client.SetInTransit(sessionKey, folderType, MID, inTransitValue);
            }
            catch (Exception ex)
            {
                throw new Exception("", ex);
            }
        }
        public string GetMIMEMessage(string sessionKey, int MID, bool withCMHeader, bool withTrackingXML, bool withSecurityEnvelope)
        {
            try
            {
                return client.GetMIMEMessage(sessionKey, MID, withCMHeader, withTrackingXML, withSecurityEnvelope);

            }
            catch (Exception ex)
            {
                throw new Exception("", ex);
            }
        }
        //public string getInboxMessage(string sessionKey, int MID)
        //{
        //    try
        //    {
        //        return client.GetInboxMIMEMessage(sessionKey, MID);
        //    }
        //    catch (Exception ex)
        //    {
        //        throw new Exception("", ex);
        //    }
        //}
        //public string getInboxMessageEncoded(string sessionKey, int MID)
        //{
        //    try
        //    {
        //        return client.GetInboxMIMEMessageEncoded(sessionKey, MID);
        //    }
        //    catch (Exception ex)
        //    {
        //        throw new Exception("", ex);
        //    }
        //}
        public List<folder> GetFolderList(string sessionKey)
        {
            try
            {
                string result = client.GetFolderList(sessionKey);
                List<folder> folders = new List<folder>();
                using (XmlReader reader = XmlReader.Create(new StringReader(result)))
                {
                    folder folderInfo = new folder();
                    while (!reader.EOF)
                    {
                        reader.ReadToFollowing("FID");
                        reader.Read();
                        folderInfo = new folder();
                        if (reader.HasValue)
                        {
                            int FID = int.Parse(reader.Value);
                            folderInfo.FID = FID;
                        }
                        else
                        {
                            reader.Close();
                            break;
                        }
                        reader.ReadToFollowing("Name");
                        reader.Read();
                        folderInfo.Name = reader.Value;
                        reader.ReadToFollowing("Type");
                        reader.Read();
                        folderInfo.Type = int.Parse(reader.Value);
                        reader.ReadToFollowing("TypeDesc");
                        reader.Read();
                        folderInfo.TypeDesc = reader.Value;
                        reader.ReadToFollowing("TotalMessages");
                        reader.Read();
                        folderInfo.TotalMessages = int.Parse(reader.Value);
                        reader.Read();
                        folders.Add(folderInfo);
                    }
                    return folders;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("", ex);
            }
        }
        public AccountDetails GetAccountDetails(string sessionKey)
        {
            try
            {
                string result = client.GetAccountDetails(sessionKey);
                AccountDetails accountDetails = new AccountDetails();
                using (XmlReader reader = XmlReader.Create(new StringReader(result)))
                {
                    while (reader.Read())
                    {
                        if (reader.NodeType == XmlNodeType.Element)
                        {
                            if (reader.Name == "MsgUnread")
                            {
                                reader.Read();
                                accountDetails.MsgUnread = int.Parse(reader.Value);
                            }
                            else if (reader.Name == "MsgSent")
                            {
                                reader.Read();
                                accountDetails.MsgSent = int.Parse(reader.Value);
                            }
                            else if (reader.Name == "TotalMsgSent")
                            {
                                reader.Read();
                                accountDetails.TotalMsgSent = int.Parse(reader.Value);
                            }
                            else if (reader.Name == "MsgReceived")
                            {
                                reader.Read();
                                accountDetails.MsgReceived = int.Parse(reader.Value);
                            }
                            else if (reader.Name == "TotalMsgReceived")
                            {
                                reader.Read();
                                accountDetails.TotalMsgReceived = int.Parse(reader.Value);
                            }
                            else if (reader.Name == "TotalDiskSpace")
                            {
                                reader.Read();
                                accountDetails.TotalDiskSpace = int.Parse(reader.Value);
                            }
                            else if (reader.Name == "DiskSpaceUsed")
                            {
                                reader.Read();
                                accountDetails.DiskSpaceUsed = int.Parse(reader.Value);
                            }
                            else if (reader.Name == "TotalVisits")
                            {
                                reader.Read();
                                accountDetails.TotalVisits = int.Parse(reader.Value);
                            }
                            else if (reader.Name == "LastLoginDateTime")
                            {
                                reader.Read();
                                accountDetails.LastLoginDateTime = reader.Value;
                            }
                            else if (reader.Name == "DateCreated")
                            {
                                reader.Read();
                                accountDetails.DateCreated = reader.Value;
                            }
                            else if (reader.Name == "DateLastNotice")
                            {
                                reader.Read();
                                accountDetails.DateLastNotice = reader.Value;
                            }
                            else if (reader.Name == "DatePasswordExpires")
                            {
                                reader.Read();
                                accountDetails.DatePasswordeExpires = reader.Value;
                            }
                            else if (reader.Name == "TotalFilesSent")
                            {
                                reader.Read();
                                accountDetails.TotalFilesSent = int.Parse(reader.Value);
                            }
                            else if (reader.Name == "TotalFilesInOutbox")
                            {
                                reader.Read();
                                accountDetails.TotalFilesInOutbox = int.Parse(reader.Value);
                            }
                        }
                    }
                }
                return accountDetails;
            }
            catch (Exception ex)
            {
                throw new Exception("", ex);
            }
        }
        public void DeleteFolder(string sessionKey, int FID)
        {
            try
            {
                client.DeleteFolder(sessionKey, FID);
            }
            catch (Exception ex)
            {
                throw new Exception("", ex);
            }
        }
        public void MoveMessage(string sessionKey, int MID, int destFID)
        {
            try
            {
                client.MoveMessage(sessionKey, MID, destFID);
            }
            catch (Exception ex)
            {
                throw new Exception("", ex);
            }
        }
        public void UndeleteMessage(string sessionKey, int MID)
        {
            MoveMessage(sessionKey, MID, 1);
        }
        private static string StreamToString(Stream stream)
        {
            stream.Position = 0;
            using (StreamReader reader = new StreamReader(stream, Encoding.UTF8))
                return reader.ReadToEnd();
        }
        public string SendMIMEMessage(string sessionKey, string[] To, string[] Cc, string[] Bcc, List<byte[]> attachments, string body, string subject)
        {
            try
            {
                MailBee.Mime.MailMessage message = new MailBee.Mime.MailMessage();
                message.Subject = subject;
                foreach (string to in To)
                    message.To.Add(to);
                foreach (string cc in Cc)
                    message.Cc.Add(cc);
                foreach (string bcc in Bcc)
                    message.Bcc.Add(bcc);
                foreach (byte[] file in attachments)
                {
                    if (file.ToString().Contains(".xml"))
                        message.Attachments.Add(file, null, null, "text/xml", null, NewAttachmentOptions.None, MailTransferEncoding.Base64);
                    else
                        message.Attachments.Add(file.ToString());
                }
                message.BodyHtmlText = "<pre>" + body + "</pre>";
                Stream stream = new MemoryStream();
                message.SaveMessage(stream);
                string finalMessage = StreamToString(stream);
                string result = client.SendMIMEMessage(sessionKey, finalMessage);
                string returnString = "test";
                using (XmlReader reader = XmlReader.Create(new StringReader(result)))
                {
                    while (reader.Read())
                    {
                        if (reader.NodeType == XmlNodeType.Element)
                        {
                            if (reader.Name == "NewMID")
                            {
                                while (reader.Value == "")
                                    reader.Read();
                                returnString = reader.Value;
                            }
                        }
                    }
                }
                return returnString;
            }
            catch (Exception ex)
            {
                throw new Exception("", ex);
            }
        }
        //public string sendMessageEncoded(string sessionKey, string[] To, string[] Cc, string[] Bcc, ArrayList attachments, string body, string subject)
        //{
        //    try
        //    {
        //        MailBee.Mime.MailMessage message = new MailBee.Mime.MailMessage();
        //        message.Subject = subject;
        //        foreach (string to in To)
        //            message.To.Add(to);
        //        foreach (string cc in Cc)
        //            message.Cc.Add(cc);
        //        foreach (string bcc in Bcc)
        //            message.Bcc.Add(bcc);
        //        foreach (var file in attachments)
        //            message.Attachments.Add(file.ToString());
        //        message.BodyHtmlText = "<pre>" + body + "</pre>";
        //        Stream stream = new MemoryStream();
        //        message.SaveMessage(stream);
        //        string finalMessage = StreamToString(stream);
        //        string result = client.SendMIMEMessageEncoded(sessionKey, finalMessage);
        //        string returnString = "test";
        //        using (XmlReader reader = XmlReader.Create(new StringReader(result)))
        //        {
        //            while (reader.Read())
        //            {
        //                if (reader.NodeType == XmlNodeType.Element)
        //                {
        //                    if (reader.Name == "NewMID")
        //                    {
        //                        while (reader.Value == "")
        //                            reader.Read();
        //                        returnString = reader.Value;
        //                    }
        //                }
        //            }
        //        }
        //        return returnString;
        //    }
        //    catch (Exception ex)
        //    {
        //        throw new Exception("", ex);
        //    }
        //}
    }

    public struct Headers
    {
        public const string CompanyAutomation = "X-Company-Automation-Id";
        public const string Email = "X-Email";
        public const string Hash = "X-Hash";
        public const string Iv = "X-Iv";
        public const string Proxy = "X-Proxy";
        public const string Role = "X-Role";
        public const string SessionCookie = "Session-Key";
        public const string SessionKey = "X-Session-Key";
        public const string SingleSignOn = "X-Single-Sign-On-Id";
        public const string Test = "X-Is-Test";
        public const string User = "X-User-Id";

        public static string AuthorizationPayload;
    }


    public class Admin
    {
        string endpoint, automationId, encryptionKey;
        public Admin(string _endpoint, string _automationId, string _encryptionKey)
        {
            endpoint = _endpoint;
            automationId = _automationId;
            encryptionKey = _encryptionKey;
        }

#region Admin
        public string GetSessionKey(LoginModel loginModel)
        {
            //Generate IV/key/hash
            AesCryptoServiceProvider cryptoProvider = new AesCryptoServiceProvider();
            cryptoProvider.KeySize = 256;
            cryptoProvider.GenerateIV();
            string IV = Convert.ToBase64String(cryptoProvider.IV);
            byte[] data = Encoding.UTF8.GetBytes(Newtonsoft.Json.JsonConvert.SerializeObject(loginModel));
            byte[] key = Convert.FromBase64String(encryptionKey);
            string hash = Convert.ToBase64String(new HMACSHA512(key).ComputeHash(data));
            string encryptedData = Encrypt(key, Convert.FromBase64String(IV), Newtonsoft.Json.JsonConvert.SerializeObject(loginModel));

            //Send payload
            var req = new WebClient();
            req.BaseAddress = endpoint + "/Remote/Account/GetSessionKey";
            req.Headers.Clear();
            req.Headers.Add("Accept", "application/json");
            req.Headers.Add(Headers.Email, loginModel.Identity.Email);
            req.Headers.Add(Headers.User, loginModel.Identity.UserId);
            req.Headers.Add(Headers.SingleSignOn, loginModel.Identity.SingleSignOnId);
            req.Headers.Add(Headers.Iv, IV);
            req.Headers.Add(Headers.CompanyAutomation, automationId);
            req.Headers.Add(Headers.Hash, hash);
            try
            {
                var request = req.UploadData(endpoint + "/Remote/Account/GetSessionKey", "POST", Encoding.UTF8.GetBytes(encryptedData));
                return Encoding.UTF8.GetString(request);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public List<DMWebLib.Models.Admin.UserTypeModel> GetUserTypes(string sessionKey) 
        {
            //Send payload
            var req = new WebClient();
            req.BaseAddress = endpoint + "/Remote/Account/GetUserTypes";
            req.Headers.Clear();
            req.Headers.Add("Accept", "application/json");
            req.Headers.Add(Headers.SessionKey, sessionKey);
            try
            {
                var request = req.DownloadString(endpoint + "/Remote/Account/GetUserTypes");
                return JsonConvert.DeserializeObject<List<DMWebLib.Models.Admin.UserTypeModel>>(request);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public AccountModel Create(string sessionKey, AccountModel accountModel)
        {
            //Send Payload
            var req = new WebClient();
            req.BaseAddress = endpoint + "/Remote/Account/Create";
            req.Headers.Clear();
            req.Headers.Add("Accept", "application/json");
            req.Headers.Add(Headers.SessionKey, sessionKey);
            try
            {
                var request = req.UploadData(endpoint + "/Remote/Account/Create", Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(accountModel)));
                return JsonConvert.DeserializeObject<AccountModel>(Encoding.UTF8.GetString(request));
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public string GetEncryptionKey(string sessionKey, IdentityModel identityModel)
        {
            //Send Payload
            var req = new WebClient();
            req.BaseAddress = endpoint + "/Remote/Account/GetEncryptionKey";
            req.Headers.Clear();
            req.Headers.Add("Accept", "application/json");
            req.Headers.Add(Headers.SessionKey, sessionKey);
            try
            {
                var request = req.UploadData(endpoint + "/Remote/Account/GetEncryptionKey", Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(identityModel)));
                return Encoding.UTF8.GetString(request);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public string NewEncryptionKey(string sessionKey, IdentityModel identityModel)
        {
            //Send Payload
            var req = new WebClient();
            req.BaseAddress = endpoint + "/Remote/Account/NewEncryptionKey";
            req.Headers.Clear();
            req.Headers.Add("Accept", "application/json");
            req.Headers.Add(Headers.SessionKey, sessionKey);
            try
            {
                var request = req.UploadData(endpoint + "/Remote/Account/NewEncryptionKey", Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(identityModel)));
                return Encoding.UTF8.GetString(request);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public AccountListModel List(string sessionKey, AccountIndexRequestModel accountIndexRequest)
        {
            //Send Payload
            var req = new WebClient();
            req.BaseAddress = endpoint + "/Remote/Account/List";
            req.Headers.Clear();
            req.Headers.Add("Accept", "application/json");
            req.Headers.Add(Headers.SessionKey, sessionKey);
            try
            {
                var request = req.UploadData(endpoint + "/Remote/Account/List", Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(accountIndexRequest)));
                return JsonConvert.DeserializeObject<AccountListModel>(Encoding.UTF8.GetString(request));
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public AccountModel Read(string sessionKey, IdentityModel identityModel)
        {
            //Send Payload
            var req = new WebClient();
            req.BaseAddress = endpoint + "/Remote/Account/Read";
            req.Headers.Clear();
            req.Headers.Add("Accept", "application/json");
            req.Headers.Add(Headers.SessionKey, sessionKey);
            try
            {
                var request = req.UploadData(endpoint + "/Remote/Account/Read", Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(identityModel)));
                return JsonConvert.DeserializeObject<AccountModel>(Encoding.UTF8.GetString(request));
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public AccountModel Update(string sessionKey, AccountModel accountModel)
        {
            //Send Payload
            var req = new WebClient();
            req.BaseAddress = endpoint + "/Remote/Account/Update";
            req.Headers.Clear();
            req.Headers.Add("Accept", ("application/json"));
            req.Headers.Add(Headers.SessionKey, sessionKey);
            try
            {
                var request = req.UploadData(endpoint + "/Remote/Account/Update", Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(accountModel)));
                return JsonConvert.DeserializeObject<AccountModel>(Encoding.UTF8.GetString(request));
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public void Delete(string sessionKey, IdentityModel identityModel)
        {
            //Send Payload
            var req = new WebClient();
            req.BaseAddress = endpoint + "/Remote/Account/Delete";
            req.Headers.Clear();
            req.Headers.Add("Accept", "application/json");
            req.Headers.Add(Headers.SessionKey, sessionKey);
            try
            {
                var request = req.UploadData(endpoint + "/Remote/Account/Delete", Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(identityModel)));
                //Do nothing
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }
#endregion

#region Admin SMTP
        public SMTPCredentialsModel GetCredentials(string sessionKey)
        {
            //Send Payload
            var req = new WebClient();
            req.BaseAddress = endpoint + "/Remote/Smtp/GetCredentials";
            req.Headers.Clear();
            req.Headers.Add("Accept", "application/json");
            req.Headers.Add(Headers.SessionKey, sessionKey);
            try
            {
                var request = req.DownloadString(endpoint + "/Remote/Smtp/GetCredentials");
                return JsonConvert.DeserializeObject<SMTPCredentialsModel>(request);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public SMTPCredentialsModel PutSMTPEndpoints(string sessionKey, SMTPEndpoint2Model[] smtpEndpoints)
        {
            //Send Payload
            var req = new WebClient();
            req.BaseAddress = endpoint + "/Remote/Smtp/PutSmtpEndpoints";
            req.Headers.Clear();
            req.Headers.Add("Accept", "application/json");
            req.Headers.Add(Headers.SessionKey, sessionKey);
            try
            {
                var request = req.UploadData(endpoint + "/Remote/Smtp/PutSmtpEndpoints", Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(smtpEndpoints)));
                return JsonConvert.DeserializeObject<SMTPCredentialsModel>(Encoding.UTF8.GetString(request));
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public SMTPCredentialsModel ResetPassword(string sessionKey)
        {
            //Send Payload
            var req = new WebClient();
            req.BaseAddress = endpoint + "/Remote/Smtp/ResetPassword";
            req.Headers.Clear();
            req.Headers.Add("Accept", "application/json");
            req.Headers.Add(Headers.SessionKey, sessionKey);
            try
            {
                var request = req.UploadData(endpoint + "/Remote/Smtp/ResetPassword", Encoding.UTF8.GetBytes("-1"));
                return JsonConvert.DeserializeObject<SMTPCredentialsModel>(Encoding.UTF8.GetString(request));
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }
#endregion
        private string Encrypt(byte[] key, byte[] IV, string model)
        {
            string encryptedMessage;
            using (var cryptoProvider = new AesCryptoServiceProvider() { KeySize = 256 })
            {
                using (var encryptor = cryptoProvider.CreateEncryptor(key, IV))
                {
                    using (var memory = new MemoryStream())
                    {
                        using (var crypto = new CryptoStream(memory, encryptor, CryptoStreamMode.Write))
                        {
                            using (var writer = new StreamWriter(crypto))
                            {
                                writer.Write(model);
                            }
                        }
                        encryptedMessage = Convert.ToBase64String(memory.ToArray());
                    }
                }
            }
            return encryptedMessage;
        }

    }

    public class Provisioning
    {
        string provisionerKey;
        public Provisioning(string _provisionerKey)
        {
            provisionerKey = _provisionerKey;
        }

        #region Provisioning
        public ResponseModel Provision(CompanyModel companyModel, string UserName, string PlatformCode, Command Command, string Email)
        {
            //Generate IV
            AesCryptoServiceProvider cryptoProvider = new AesCryptoServiceProvider();
            cryptoProvider.KeySize = 256;
            cryptoProvider.GenerateIV();
            string IV = Convert.ToBase64String(cryptoProvider.IV);
            //Encrypt Model
            byte[] data = Encoding.UTF8.GetBytes(Newtonsoft.Json.JsonConvert.SerializeObject(companyModel));
            byte[] key = Convert.FromBase64String(provisionerKey);
            string hash = Convert.ToBase64String(new HMACSHA512(key).ComputeHash(data));
            string data2 = Encrypt(key, Convert.FromBase64String(IV), Newtonsoft.Json.JsonConvert.SerializeObject(companyModel));
            //Construct the request model
            RequestModel requestModel = new RequestModel
            {
                UserName = UserName,
                PlatformCode = PlatformCode,
                Command = Command,
                IV = IV,
                Data = data2,
                Hash = hash
            };
            var req = new WebClient();
            req.BaseAddress = "https://provisioning.datamotion.com:8888";
            req.Headers.Clear();
            req.Headers.Add("Accept", "application/json");
            req.Headers.Add(Headers.Email, Email);
            req.Headers.Add(Headers.Iv, IV);
            try
            {
                var request = req.UploadData("https://provisioning.datamotion.com:8888", Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(requestModel)));
                ResponseModel response = JsonConvert.DeserializeObject<ResponseModel>(Encoding.UTF8.GetString(request));
                response.Data = Decrypt(key, Convert.FromBase64String(response.IV), response.Data);
                return response;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public ResponseModel GetEncryptionKey(AutomationKeyModel automationModel, string Email, string UserName, string PlatformCode)
        {
            //Generate IV
            AesCryptoServiceProvider cryptoProvider = new AesCryptoServiceProvider();
            cryptoProvider.KeySize = 256;
            cryptoProvider.GenerateIV();
            string IV = Convert.ToBase64String(cryptoProvider.IV);
            //Encrypt Model
            byte[] data = Encoding.UTF8.GetBytes(Newtonsoft.Json.JsonConvert.SerializeObject(automationModel));
            byte[] key = Convert.FromBase64String(provisionerKey);
            string hash = Convert.ToBase64String(new HMACSHA512(key).ComputeHash(data));
            string data2 = Encrypt(key, Convert.FromBase64String(IV), Newtonsoft.Json.JsonConvert.SerializeObject(automationModel));
            //Construct the request model
            RequestModel requestModel = new RequestModel
            {
                UserName = UserName,
                PlatformCode = PlatformCode,
                IV = IV,
                Data = data2,
                Hash = hash
            };
            var req = new WebClient();
            req.BaseAddress = "https://provisioning.datamotion.com:8888/company/GetEncryptionKey";
            req.Headers.Clear();
            req.Headers.Add("Accept", "application/json");
            req.Headers.Add(Headers.Email, Email);
            req.Headers.Add(Headers.Iv, IV);
            try
            {
                var request = req.UploadData("https://provisioning.datamotion.com:8888/company/GetEncryptionKey", Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(requestModel)));
                ResponseModel response = JsonConvert.DeserializeObject<ResponseModel>(Encoding.UTF8.GetString(request));
                response.Data = Decrypt(key, Convert.FromBase64String(response.IV), response.Data);
                return response;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public ResponseModel NewEncryptionKey(AutomationKeyModel automationModel, string Email, string UserName, string PlatformCode)
        {
            //Generate IV
            AesCryptoServiceProvider cryptoProvider = new AesCryptoServiceProvider();
            cryptoProvider.KeySize = 256;
            cryptoProvider.GenerateIV();
            string IV = Convert.ToBase64String(cryptoProvider.IV);
            //Encrypt Model
            byte[] data = Encoding.UTF8.GetBytes(Newtonsoft.Json.JsonConvert.SerializeObject(automationModel));
            byte[] key = Convert.FromBase64String(provisionerKey);
            string hash = Convert.ToBase64String(new HMACSHA512(key).ComputeHash(data));
            string data2 = Encrypt(key, Convert.FromBase64String(IV), Newtonsoft.Json.JsonConvert.SerializeObject(automationModel));
            //Construct the request model
            RequestModel requestModel = new RequestModel
            {
                UserName = UserName,
                PlatformCode = PlatformCode,
                IV = IV,
                Data = data2,
                Hash = hash
            };
            var req = new WebClient();
            req.BaseAddress = "https://provisioning.datamotion.com:8888/company/NewEncryptionKey";
            req.Headers.Clear();
            req.Headers.Add("Accept", "application/json");
            req.Headers.Add(Headers.Email, Email);
            req.Headers.Add(Headers.Iv, IV);
            try
            {
                var request = req.UploadData("https://provisioning.datamotion.com:8888/company/NewEncryptionKey", Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(requestModel)));
                ResponseModel response = JsonConvert.DeserializeObject<ResponseModel>(Encoding.UTF8.GetString(request));
                response.Data = Decrypt(key, Convert.FromBase64String(response.IV), response.Data);
                return response;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        private string Encrypt(byte[] key, byte[] IV, string data)
        {
            string encryptedMessage;
            using (var cryptoProvider = new AesCryptoServiceProvider() { KeySize = 256 })
            {
                using (var encryptor = cryptoProvider.CreateEncryptor(key, IV))
                {
                    using (var memory = new MemoryStream())
                    {
                        using (var crypto = new CryptoStream(memory, encryptor, CryptoStreamMode.Write))
                        {
                            using (var writer = new StreamWriter(crypto))
                            {
                                writer.Write(data);
                            }
                        }
                        encryptedMessage = Convert.ToBase64String(memory.ToArray());
                    }
                }
            }
            return encryptedMessage;
        }

        private string Decrypt(byte[] key, byte[] IV, string data)
        {
            string encryptedMessage;
            using (var cryptoProvider = new AesCryptoServiceProvider() { KeySize = 256, Key = key, IV = IV })
            {
                using (var encryptor = cryptoProvider.CreateDecryptor(key, IV))
                {
                    using (var memory = new MemoryStream(Convert.FromBase64String(data)))
                    {
                        using (var crypto = new CryptoStream(memory, encryptor, CryptoStreamMode.Read))
                        {
                            using (var writer = new StreamReader(crypto))
                            {
                                encryptedMessage = writer.ReadToEnd();
                            }
                        }
                    }
                }
            }
            return encryptedMessage;
        }
        #endregion
    }

    public class HPD
    {
        public IList<Entry> SearchHPD(string sessionKey, string endpoint, string city, string fax, string FirstName, string LastName, string npi, string OrganizationName, string OrganizationNpi, string OrganizationSpeciality, string phone, string role, string specialty, string state, string street, string zip)
        {
            SearchResponse searchResults;
            var req = new WebClient();
            string requestUrl = "";
            string baseAddress = "";
            switch (endpoint.ToLower())
            {
                case "https://ssl.dmhisp.com/cmv4/cmv4.asmx":
                    requestUrl = "https://ssl.dmhisp.com/HpdProxyApi/Search?City=";
                    baseAddress = "https://ssl.dmhisp.com";
                    break;
                case "https://stage.dmhisp.com/cmv4/cmv4.asmx":
                    requestUrl = "https://stage.dmhisp.com/HpdProxyApi/Search?City=";
                    baseAddress = "https://stage.dmhisp.com";
                    break;
                case "https://direct.datamotioncorp.com/cmv4/cmv4.asmx":
                    requestUrl = "https://direct.datamotioncorp.com/HpdProxyApi/Search?City=";
                    baseAddress = "https://direct.datamotioncorp.com";
                    break;
                default:
                    requestUrl = "https://ssl.dmhisp.com/HpdProxyApi/Search?City=";
                    baseAddress = "https://ssl.dmhisp.com";
                    break;
            }
            requestUrl += HttpUtility.UrlEncode(city) + "&Fax=" + HttpUtility.UrlEncode(fax) + "&FirstName=" + HttpUtility.UrlEncode(FirstName) + "&LastName=" + HttpUtility.UrlEncode(LastName) + "&Npi=" + HttpUtility.UrlEncode(npi) + "&OrganizationName=" + HttpUtility.UrlEncode(OrganizationName) + "&OrganizationNpi=" + HttpUtility.UrlEncode(OrganizationNpi) + "&OrganizationSpecialty=" + HttpUtility.UrlEncode(OrganizationSpeciality) + "&Phone=" + HttpUtility.UrlEncode(phone) + "&Role=" + HttpUtility.UrlEncode(role) + "&Specialty=" + HttpUtility.UrlEncode(specialty) + "&State=" + HttpUtility.UrlEncode(state) + "&Street=" + HttpUtility.UrlEncode(street) + "&Zip=" + HttpUtility.UrlEncode(zip);
            req.BaseAddress = baseAddress;
            req.Headers.Clear();
            req.Headers.Add("Accept", "application/json");
            req.Headers.Add("X-Session-Key", sessionKey);
            try
            {
                var request = req.DownloadString(requestUrl);
                searchResults = JsonConvert.DeserializeObject<SearchResponse>(request);
                return searchResults.Entries;
            }
            catch
            {
                return null;
            }
        }
    }
}
