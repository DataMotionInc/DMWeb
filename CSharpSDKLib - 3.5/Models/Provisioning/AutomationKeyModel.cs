using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DMWebLib.Models.Provisioning
{
    public class AutomationKeyModel
    {
        public string ReferenceId { get; set; }
        public string AutomationId { get; set; }
        public string ApiEncryptionKey { get; set; }
    }
}
