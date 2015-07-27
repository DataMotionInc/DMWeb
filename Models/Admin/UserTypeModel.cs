using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DMWebLib.Models.Admin
{
    public class UserTypeModel
    {
        public int TypeId { get; set; }
        public string Description { get; set; }
        public string MinimumPasswordLength { get; set; }
        public string RequiredPasswordCategories { get; set; } //0-4 (see below)
        public string[] Categories { get; set; }
    }
}
