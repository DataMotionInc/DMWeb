using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DMWebLib
{
    public class AccountDetails
    {
        public int MsgUnread;
        public int MsgSent;
        public int TotalMsgSent;
        public int MsgReceived;
        public int TotalMsgReceived;
        public int TotalDiskSpace;
        public int DiskSpaceUsed;
        public int TotalVisits;
        public string LastLoginDateTime;
        public string DateCreated;
        public string DateLastNotice;
        public string DatePasswordeExpires;
        public int TotalFilesSent;
        public int TotalFilesInOutbox;
    }
}
