//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System;
using System.Collections.Generic;
using System.Globalization;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using Org.USFirst.Frc.Team4911.FrcEvents.Models;
using Newtonsoft.Json;

namespace Org.USFirst.Frc.Team4911.FrcEvents
{
    public enum TournamentLevel
    {
        qual,
        playoff
    }

    /// <summary>
    /// Provide a .Net wrapper for the FRC Events API.
    /// <para> 
    /// FMS FRC Events API is a service to return relevant information about the FIRST Robotics Competition (FRC). 
    /// Information is made available by the Field Management System (FMS) server operating at each event site.
    /// </para>
    /// <para>
    /// See http://docs.frcevents2.apiary.io/ for more information.
    /// </para>
    /// </summary>
    public partial class FrcEventsClient
    {
        private const string BaseAddress = "https://frc-api.firstinspires.org/v2.0/";
        private readonly AuthenticationHeaderValue authenticationHeaderValue;

        private readonly Uri baseAddress;

        private FrcEventsClient()
        {
        }

        public FrcEventsClient(string userName, string authorizationKey)
            : this(userName, authorizationKey, BaseAddress)
        {
        }

        public FrcEventsClient(string userName, string authorizationKey, string baseAddress)
        {
            if (string.IsNullOrWhiteSpace(userName))
            {
                throw new ArgumentNullException(nameof(userName));
            }   

            if (string.IsNullOrWhiteSpace(authorizationKey))
            {
                throw new ArgumentNullException(nameof(authorizationKey));
            }

            if (string.IsNullOrWhiteSpace(baseAddress))
            {
                throw new ArgumentNullException(nameof(baseAddress));
            }

            this.baseAddress = new Uri(baseAddress);

            this.authenticationHeaderValue = new AuthenticationHeaderValue(
                @"Basic", 
                Convert.ToBase64String(Encoding.UTF8.GetBytes(string.Format(CultureInfo.InvariantCulture, "{0}:{1}", userName, authorizationKey))));
        }

        public HttpClient CreateHttpClient()
        {
            var client = new HttpClient();
            client.BaseAddress = baseAddress;
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue(@"application/json"));
            if (this.authenticationHeaderValue != null)
            {
                client.DefaultRequestHeaders.Authorization = this.authenticationHeaderValue;
            }

            return client;
        }

        private async Task<IEnumerable<Event>> GetEvents(string path)
        {
            IEnumerable<Event> result = new List<Event>();
            using (var httpClient = CreateHttpClient())
            {
                try
                {
                    using (var response = await httpClient.GetAsync(path))
                    {
                        var responseText = await response.Content.ReadAsStringAsync();
                        result = JsonConvert.DeserializeObject<EventListings>(responseText).Events;
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Exception Message: " + ex.Message);
                }
            }

            return result;
        }

        private async Task<IEnumerable<RegistrationRecord>> GetRegistrations(string path)
        {
            IEnumerable<RegistrationRecord> result = new List<RegistrationRecord>();
            using (var httpClient = CreateHttpClient())
            {
                try
                {
                    using (var response = await httpClient.GetAsync(path))
                    {
                        var responsetext = await response.Content.ReadAsStringAsync();
                        result = JsonConvert.DeserializeObject<RegistrationListing>(responsetext).Registrations;
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Exception Message: " + ex.Message);
                }
            }

            return result;
        }

        private async Task<IEnumerable<Team>> GetTeams(string path)
        {
            List<Team> result = new List<Team>();

            using (var httpClient = CreateHttpClient())
            {
                var paramChar = path.Contains("?") ? "&" : "?";
                try
                {
                    var nextPage = 1;
                    int pageTotal;
                    do
                    {
                        var teamPath = string.Format(CultureInfo.InvariantCulture, "{0}{1}page={2}", path, paramChar, nextPage);
                        using (var response = await httpClient.GetAsync(teamPath))
                        {
                            var responseText = await response.Content.ReadAsStringAsync();
                            var teamListings = JsonConvert.DeserializeObject<TeamListings>(responseText);
                            result.AddRange(teamListings.Teams);
                            pageTotal = teamListings.PageTotal;
                            nextPage = teamListings.PageCurrent + 1;
                        }
                    }
                    while (nextPage < pageTotal);
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Exception Message: " + ex.Message);
                }
            }

            return result;
        }

        private async Task<IEnumerable<District>> GetDistricts(string path)
        {
            IEnumerable<District> result = new List<District>();
            using (var httpClient = CreateHttpClient())
            {
                try
                {
                    using (var response = await httpClient.GetAsync(path))
                    {
                        var responseText = await response.Content.ReadAsStringAsync();
                        result = JsonConvert.DeserializeObject<DistrictListings>(responseText).Districts;
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Exception Message: " + ex.Message);
                }
            }

            return result;
        }

        private async Task<IEnumerable<TeamRanking>> GetRankings(string path)
        {
            IEnumerable<TeamRanking> result = new List<TeamRanking>();
            using (var httpClient = CreateHttpClient())
            {
                try
                {
                    using (var response = await httpClient.GetAsync(path))
                    {
                        var responseText = await response.Content.ReadAsStringAsync();
                        result = JsonConvert.DeserializeObject<RankingsListing>(responseText).Rankings;
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Exception Message: " + ex.Message);
                }
            }

            return result;
        }

        private async Task<IEnumerable<Alliance>> GetAlliances(string path)
        {
            IEnumerable<Alliance> result = new List<Alliance>();
            using (var httpClient = CreateHttpClient())
            {
                try
                {
                    using (var response = await httpClient.GetAsync(path))
                    {
                        var responseText = await response.Content.ReadAsStringAsync();
                        result = JsonConvert.DeserializeObject<AllianceListing>(responseText).Alliances;
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Exception Message: " + ex.Message);
                }
            }   

            return result;
        }

        private async Task<IEnumerable<Award>> GetAwards(string path)
        {
            IEnumerable<Award> result = new List<Award>();
            using (var httpClient = CreateHttpClient())
            {
                try
                {
                    using (var response = await httpClient.GetAsync(path))
                    {
                        var responseText = await response.Content.ReadAsStringAsync();
                        result = JsonConvert.DeserializeObject<AwardListings>(responseText).Awards;
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Exception Message: " + ex.Message);
                }
            }

            return result;
        }

        private async Task<IEnumerable<AwardListing>> GetAwardListings(string path)
        {
            IEnumerable<AwardListing> result = new List<AwardListing>();
            using (var httpClient = CreateHttpClient())
            {
                try
                {
                    using (var response = await httpClient.GetAsync(path))
                    {
                        var responseText = await response.Content.ReadAsStringAsync();
                        result = JsonConvert.DeserializeObject<AwardListingListings>(responseText).AwardListings;
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Exception Message: " + ex.Message);
                }
            }

            return result;
        }

        private async Task<IEnumerable<ScheduledMatch>> GetEventSchedule(string path)
        {
            IEnumerable<ScheduledMatch> result = new List<ScheduledMatch>();
            using (var httpClient = CreateHttpClient())
            {
                try
                {
                    using (var response = await httpClient.GetAsync(path))
                    {
                        var responseText = await response.Content.ReadAsStringAsync();
                        result = JsonConvert.DeserializeObject<ScheduleListing>(responseText).Schedule;
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Exception Message: " + ex.Message);
                }
            }

            return result;
        }

        private async Task<IEnumerable<Match>> GetMatches(string path)
        {
            IEnumerable<Match> result = new List<Match>();
            using (var httpClient = CreateHttpClient())
            {
                try
                {
                    using (var response = await httpClient.GetAsync(path))
                    {
                        var responseText = await response.Content.ReadAsStringAsync();
                        result = JsonConvert.DeserializeObject<MatchListings>(responseText).Matches;
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Exception Message: " + ex.Message);
                }
            }

            return result;
        }

        private async Task<IEnumerable<MatchScores2016>> GetScores2016(string path)
        {
            IEnumerable<MatchScores2016> result = new List<MatchScores2016>();
            using (var httpClient = CreateHttpClient())
            {
                try
                {
                    using (var response = await httpClient.GetAsync(path))
                    {
                        var responseText = await response.Content.ReadAsStringAsync();
                        result = JsonConvert.DeserializeObject<MatchScorce2016Listings>(responseText).MatchScores;
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Exception Message: " + ex.Message);
                }
            }

            return result;
        }
        private async Task<IEnumerable<MatchScores2017>> GetScores2017(string path)
        {
            IEnumerable<MatchScores2017> result = new List<MatchScores2017>();
            using (var httpClient = CreateHttpClient())
            {
                try
                {
                    using (var response = await httpClient.GetAsync(path))
                    {
                        var responseText = await response.Content.ReadAsStringAsync();
                        result = JsonConvert.DeserializeObject<MatchScorce2017Listings>(responseText).MatchScores;
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Exception Message: " + ex.Message);
                }
            }

            return result;
        }
    }
}
