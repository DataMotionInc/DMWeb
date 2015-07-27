using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DMWebLib.Models.Admin
{
    public class SMTPCredentialsModel
    {
        public string UserId { get; set; } //<CompanyId>______<CompanyUsername> OR <CompanyId>______<CompanyNameLowerCaseWithSpaceRemoved>
        public string Password { get; set; }
        public List<SMTPEndpoint2Model> Endpoints { get; set; }
    }
}
