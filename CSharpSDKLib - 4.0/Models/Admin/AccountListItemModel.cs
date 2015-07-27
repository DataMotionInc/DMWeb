using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DMWebLib.Models.Admin
{
    public class AccountListItemModel
    {
        public int UniqueId { get; set; }
        public int Aid { get; set; }
        public int CompanyId { get; set; }
        public DateTime Created { get; set; }
        public DateTime LastNoteice { get; set; }
        public string DiskSpaceUsed { get; set; }
        public int EmailId { get; set; }
        public string Email { get; set; }
        public string EmployeeId { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public DateTime LastLogin { get; set; }
        public int MessagesReceived { get; set; }
        public int MessagesSent { get; set; }
        public string UserId { get; set; }
        public int UserTypeId { get; set; }
    }
}
