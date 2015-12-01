using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DMWebLib.Models.Admin
{
    public class AccountModel
    {
        public int UniqueId { get; set; }
        public string Email { get; set; }
        public string UserId { get; set; }
        public string SingleSignOnId { get; set; }
        public string Password { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Phone { get; set; }
        public int CompanyId { get; set; }
        public string EmployeeId { get; set; }
        public string Miscellaneous { get; set; }
        public bool Disabled { get; set; }
        public int UserTypeId { get; set; }
        public bool ButtonUser { get; set; }
        public bool ReceiveOffers { get; set; }
        public Dictionary<string, List<string>> Errors { get; set; }
    }
}
