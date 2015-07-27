using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DMWebLib.Models.Admin
{
    public class IdentityModel
    {
        public int UID { get; set; }
        public string Email { get; set; }
        public string UserId { get; set; }
        public string SingleSignOnId { get; set; }
    }
}
