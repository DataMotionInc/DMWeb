using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DMWebLib.Models.Admin
{
    public class LoginModel
    {
        //public LoginModel()
        //{
        //    Identity = new IdentityModel();
        //}
        public IdentityModel Identity { get; set; }
        public string IpAddress { get; set; }
        public DateTime? TimeStamp { get; set; }
    }
}
