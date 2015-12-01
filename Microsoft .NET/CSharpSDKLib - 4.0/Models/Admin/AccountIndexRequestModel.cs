using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DMWebLib.Models.Admin
{
    public class AccountIndexRequestModel
    {
        public int UserTypeId { get; set; }
        public int PageNumber { get; set; }
        public int CompanyId { get; set; }
        public string OrderBy { get; set; }
        public string Filter { get; set; }
    }
}
