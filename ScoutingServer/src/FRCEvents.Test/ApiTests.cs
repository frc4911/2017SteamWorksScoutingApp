//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Org.USFirst.Frc.Team4911.FrcEvents;

namespace Org.USFirst.Frc.Team4911.FRCEvents.Test
{
    [TestClass]
    public class ApiTests
    {
        /// NOTE: Please add the registered user name, and key
        private string userName = @"";
        private string key = @"";
        private string baseAddress = @"https://frc-staging-api.firstinspires.org/v2.0/";

        private int year = 2016;
        private int teamNumber = 4911;
        private string eventCode = "WAMOU";


        [TestMethod]
        public async Task GetDistricts()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);
            var results = await client.GetDistricts(this.year);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetEventsBySeason()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);
            var results = await client.GetEvents(this.year);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetEventsBySeasonAndEventCode()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var eventCodes = new List<string>()
            {
                "WAMOU",
                "CMP",
                "NEWTON",
            };
            foreach (var eventCode in eventCodes)
            {
                var results = await client.GetEvents(this.year, this.eventCode);
                Assert.IsNotNull(results, eventCode + " results");
                Assert.AreNotEqual(0, results.Count(), this.eventCode);
            }
        }

        [TestMethod]
        public async Task GetEventsBySeasonAndTeamNumber()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetEvents(this.year, this.teamNumber);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetTeamsBySeason()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);
            var results = await client.GetTeams(this.year);

            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetRegistrationsBySeason()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetRegistrations(this.year);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetRegistrationsBySeasonAndTeamNumber()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetRegistrations(this.year, this.teamNumber);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetRankingsBySeasonAndEventCode()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetRankings(this.year, this.eventCode);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }


        [TestMethod]
        public async Task GetRankingsBySeasonAndEventCodeAndTeamNumber()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetRankingsByTeamNumber(this.year, this.eventCode, this.teamNumber);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetAlliancesBySeasonAndEventCode()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetAlliances(this.year, this.eventCode);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }


        [TestMethod]
        public async Task GetAwardsBySeasonAndEventCode()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetAwards(this.year, this.eventCode);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetAwardsBySeasonAndEventCodeAndTeamNumber()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetAwards(this.year, this.eventCode, this.teamNumber);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetAwardListings()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetAwardListings(this.year);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetEventScheduleForSeasonAndEventCodeAndQualifying()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetEventSchedule(this.year, this.eventCode, TournamentLevel.qual);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetEventScheduleForSeasonAndEventCodeAndPlayoff()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetEventSchedule(this.year, this.eventCode, TournamentLevel.playoff);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetMatchesForSeasonAndEventCode()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetMatches(this.year, this.eventCode);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetMatchesForSeasonAndEventCodeAndteamNumber()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetMatches(this.year, this.eventCode, this.teamNumber);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetMatchesForSeasonAndEventCodeAndMatchNumberPlayOff()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetMatches(this.year, this.eventCode, 1, TournamentLevel.playoff);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetMatchesForSeasonAndEventCodeAndRange()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetMatches(this.year, this.eventCode, 15, 17, TournamentLevel.playoff);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetScores2016ForSeasonAndEventCodeAndQual()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetScores2016(this.eventCode, TournamentLevel.qual);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetScores2016ForSeasonAndEventCodeAndPlayOff()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetScores2016(this.eventCode, TournamentLevel.playoff);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetScores2016ForSeasonAndEventCodeAndQualAndTeamNumber()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetScores2016(this.eventCode, TournamentLevel.qual, this.teamNumber);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetScores2016ForSeasonAndEventCodeAndRange()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetScores2016(this.eventCode, 6, 12, TournamentLevel.qual);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }

        [TestMethod]
        public async Task GetScores2017ForSeasonAndEventCodeAndQualAndTeamNumber()
        {
            //var client = new FrcEventsClient(userName, key, baseAddress);
            var client = new FrcEventsClient(this.userName, this.key);

            var results = await client.GetScores2017("WASPO", TournamentLevel.qual, this.teamNumber);
            Assert.IsNotNull(results, "results");
            Assert.AreNotEqual(0, results.Count());
        }


    }
}
