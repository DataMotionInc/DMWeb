using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DMWebLib.Models.Provisioning
{
    public class ResponseModel
    {
        public string TransactionId { get; set; }
        public List<ErrorCodes> Errors { get; set; }
        public string IV { get; set; }
        public string Data { get; set; }
        public string Hash { get; set; }
    }

    public enum ErrorCodes
    {
        NoError = 0,
        CouldNotCreateCompany,
        CompanyNotFound,
        CompanyTypeNotFound,
        CompanyNameAlreadyExists,
        DomainAlreadyRegistered,
        UnableToRegisterDomain,
        ReferenceIdAlreadyUsed,
        UnknownCompanyStatus,
        BillingPeriodInvalid,
        PastExpirationDate,
        CouldNotCreateAdmin,
        CouldNotLoadUser,
        UserNotInCompany,
        CouldNotAddUserToUserType,
        CouldNotUpdateUser,
        CouldNotCreateUser,
        InvalideUserId,
        ErrorDeletingUser,
        InvalidEmailAddress,
        TemplateNotFound,
        UserTypeNotFound,
        LicenseQuanityLessThanNumberOfUsers,
        CouldNotCreateUserType,
        ErrorDeletingUserType,
        CouldNotCreateCobrand,
        IncompleteCobrandData,
        CobrandExists,
        UnableToProcessCobrandLogo,
        MoreSecureContactsThanLicenses,
        SecureContactProvisioningNotAllowed,
        SecureContactRequiresCobrand,
        NotAuthenticated,
        BadCrypto,
        InvalidPlatformName,
        UnknownCommand,
        ErrorConnectingToRemotePlatform,
        InvalidDate,
        MalformedJsonString,
        UnknownError
    }
}
