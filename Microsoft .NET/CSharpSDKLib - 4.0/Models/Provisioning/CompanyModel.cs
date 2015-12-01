using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DMWebLib.Models.Provisioning
{
    public class CompanyModel
    {
        public string ReferenceId { get; set; }
        public string CompanyName { get; set; }
        public UserModel Admin { get; set; }
        public BillingIntervalModel BillingInterval { get; set; }
        public string ExternanlIp { get; set; }
        public Status Status { get; set; }
        public List<UserTypeModel> UserTypes { get; set; }
        public CobrandModel Cobrand { get; set; }
        public List<string> AutoSenderDomains { get; set; }
        public string ExpirationDate { get; set; }
    }

    public enum Status
    {
        Active=0,
        Trial=1,
        Cancel=2,
        NoUpdate=3
    }
}
