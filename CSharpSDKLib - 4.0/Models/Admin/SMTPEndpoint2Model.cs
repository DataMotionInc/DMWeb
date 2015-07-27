using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DMWebLib.Models.Admin
{
    public class SMTPEndpoint2Model
    {
        public string IpAddress { get; set; }
        public string Subnet { get; set; }
        public bool Incoming { get; set; }
        public bool Outgoing { get; set; }
        public int Port { get; set; }
        public string Domain { get; set; }
        public Dictionary<string, List<string>> Errors { get; set; }
    }
}
