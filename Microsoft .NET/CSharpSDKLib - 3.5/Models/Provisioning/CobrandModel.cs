using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DMWebLib.Models.Provisioning
{
    public class CobrandModel
    {
        public string Url { get; set; }
        public string Color { get; set; }
        public byte[] Logo { get; set; }
        public string LogoFileName { get; set; }
        public SecureContactModel SecureContacts { get; set; }
    }
}
