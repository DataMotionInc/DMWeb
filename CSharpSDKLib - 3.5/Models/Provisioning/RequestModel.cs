using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DMWebLib.Models.Provisioning
{
    public class RequestModel
    {
        public string UserName { get; set; }
        public string PlatformCode { get; set; }
        public Command Command { get; set; }
        public string IV { get; set; }
        public string Data { get; set; }
        public string Hash { get; set; }
    }

    public enum Command
    {
        Create=1,
        Update=2,
        Cancel=3
    }
}
