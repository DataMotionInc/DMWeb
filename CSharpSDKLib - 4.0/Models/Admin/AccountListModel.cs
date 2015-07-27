using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DMWebLib.Models.Admin
{
    public class AccountListModel
    {
        public int Count { get; set; }
        public int CurrentPage { get; set; }
        public int TotalPages { get; set; }
        public string TotalUsers { get; set; }
        public string PageSize { get; set; }
        public List<AccountListItemModel> Accounts { get; set; }
    }
}
