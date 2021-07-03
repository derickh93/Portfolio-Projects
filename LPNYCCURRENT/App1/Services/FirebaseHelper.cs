using LaundryPickupNYC.Object_Classes;
using Firebase.Database;
using Firebase.Database.Query;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LaundryPickupNYC
{
    public class FirebaseHelper
    {
        private readonly string ChildName = "Persons";

        readonly FirebaseClient firebase = new FirebaseClient("https://washandgo-294116.firebaseio.com/");

        public async Task<List<Person>> GetAllPersons()
        {
            return (await firebase
                .Child(ChildName)
                .OnceAsync<Person>()).Select(item => new Person
                {
                    firstName = item.Object.firstName,
                    lastName = item.Object.lastName,
                    PersonId = item.Object.PersonId,
                    email = item.Object.email,
                    phone = item.Object.phone,
                    userID = item.Object.userID,
                    customerID = item.Object.customerID,
                    jsonList = item.Object.jsonList
                }).ToList();
        }

        public async Task AddPerson(string fName,string lName,string email, string phone,string userID, string customerID)
        {
            await firebase
                .Child(ChildName)
                .PostAsync(new Person() { PersonId = Guid.NewGuid(), firstName = fName, lastName = lName, email = email,phone = phone, userID = userID,customerID = customerID});
        }

        public async Task<Person> GetPerson(Guid personId)
        {
            var allPersons = await GetAllPersons();
            await firebase
                .Child(ChildName)
                .OnceAsync<Person>();
            return allPersons.FirstOrDefault(a => a.PersonId == personId);
        }

        public async Task<Person> GetPerson(string userID)
        {
            var allPersons = await GetAllPersons();
            await firebase
                .Child(ChildName)
                .OnceAsync<Person>();
            return allPersons.FirstOrDefault(a => a.userID == userID);
        }

        public async Task<Person> GetPerson(string first,string last)
        {
            var allPersons = await GetAllPersons();
            await firebase
                .Child(ChildName)
                .OnceAsync<Person>();
            return allPersons.FirstOrDefault(a => a.firstName == first && a.lastName == last);
        }

        public async Task DeletePerson(Guid personId)
        {
            var toDeletePerson = (await firebase
                .Child(ChildName)
                .OnceAsync<Person>()).FirstOrDefault(a => a.Object.PersonId == personId);
            await firebase.Child(ChildName).Child(toDeletePerson.Key).DeleteAsync();
        }

        public async Task UpdatePerson(Person user)
        {
            var toUpdatePerson = (await firebase
                .Child(ChildName)
                .OnceAsync<Person>()).FirstOrDefault(a => a.Object.PersonId == user.PersonId);
            await firebase
                .Child(ChildName)
                .Child(toUpdatePerson.Key)
                .PutAsync(new Person() { PersonId = user.PersonId, firstName = user.firstName, lastName = user.lastName, email = user.email,phone = user.phone, userID = user.userID, customerID = user.customerID, jsonList = user.jsonList });
        }
    }
}