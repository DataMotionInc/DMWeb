using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DMWebLib
{
    class HPDSearchModels
    {
    }
    public class Entry
    {
        public string Address1 { get; set; }
        public string Address2 { get; set; }
        public string City { get; set; }
        public string DirectAddress { get; set; }
        public string Fax { get; set; }
        public string FirstName { get; set; }
        public string HealthcareServiceClass { get; set; }
        public string HispOperator { get; set; }
        public string LastName { get; set; }
        public string MiddleName { get; set; }
        public string Name { get; set; }
        public string Npi { get; set; }
        public string OrgHealthcareServiceClass { get; set; }
        public string OrgNpi { get; set; }
        public string OrgSpecialty { get; set; }
        public string OrgUniqueID { get; set; }
        public string Phone { get; set; }
        public string Provider { get; set; }
        public string Role { get; set; }
        public string ServiceDescription { get; set; }
        public int SourceID { get; set; }
        public string Specialty { get; set; }
        public string State { get; set; }
        public string Suffix { get; set; }
        public string UniqueID { get; set; }
        public string Zip { get; set; }
    }

    public class SearchParameters
    {
        public object City { get; set; }
        public object Fax { get; set; }
        public object FirstName { get; set; }
        public string LastName { get; set; }
        public object Npi { get; set; }
        public object OrganizationName { get; set; }
        public object OrganizationNpi { get; set; }
        public object OrganizationSpecialty { get; set; }
        public object Phone { get; set; }
        public object Role { get; set; }
        public object Specialty { get; set; }
        public object State { get; set; }
        public object Street { get; set; }
        public object Zip { get; set; }
    }

    public class SearchResponse
    {
        public IList<Entry> Entries { get; set; }
        public SearchParameters SearchParameters { get; set; }
        public int TotalMatches { get; set; }
    }
}
