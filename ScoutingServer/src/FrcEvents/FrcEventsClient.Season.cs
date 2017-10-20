//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System.Collections.Generic;
using System.Globalization;
using System.Threading.Tasks;
using Org.USFirst.Frc.Team4911.FrcEvents.Models;

namespace Org.USFirst.Frc.Team4911.FrcEvents
{
    /// <summary>
    /// The season summary API returns a high level glance of a particular FRC season.
    /// </summary>
    public partial class FrcEventsClient
    {
        /// <summary>
        /// Gets the all events for teams in a particular season.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        public async Task<IEnumerable<Event>> GetEvents(int season)
        {
            season = Validation.EnsureSeason(season);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/events", season);
            return await GetEvents(path);
        }

        /// <summary>
        /// Gets the all events for teams in a particular season at a particular event.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        public async Task<IEnumerable<Event>> GetEvents(int season, string eventCode)
        {
            season = Validation.EnsureSeason(season);
            eventCode = Validation.EnsureEventCode(eventCode);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/events?eventCode={1}", season, eventCode);
            return await GetEvents(path);
        }

        /// <summary>
        /// Gets the all events for teams in a particular season for a particluar team.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="teamNumber">Team number of the team whose ranking is requested.</param>
        public async Task<IEnumerable<Event>> GetEvents(int season, int teamNumber)
        {
            season = Validation.EnsureSeason(season);
            teamNumber = Validation.EnsureTeamNumber(teamNumber);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/events?teamNumber={1}", season, teamNumber);
            return await GetEvents(path);
        }

        /// <summary>
        /// Gets the all events for teams in a particular season at particular event.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="teamNumber">Team number of the team whose ranking is requested.</param>
        /// <param name="districtCode"></param>
        public async Task<IEnumerable<Event>> GetEvents(int season, int teamNumber, string districtCode)
        {
            season = Validation.EnsureSeason(season);
            teamNumber = Validation.EnsureTeamNumber(teamNumber);
            districtCode = Validation.EnsureDictrictCode(districtCode);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/events?teamNumber={1}&districtCode={2}", season, teamNumber, districtCode);
            return await GetEvents(path);
        }

        /// <summary>
        /// Gets the all ditricts for teams in a particular season.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        public async Task<IEnumerable<District>> GetDistricts(int season)
        {
            season = Validation.EnsureSeason(season);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/districts", season);
            return await GetDistricts(path);
        }

        /// <summary>
        /// Gets all the all FRC official teams in a particular season.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        public async Task<IEnumerable<Team>> GetTeams(int season)
        {
            season = Validation.EnsureSeason(season);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/teams", season);
            return await GetTeams(path);
        }

        /// <summary>
        /// Gets all the all FRC official teams in a particular season for a particluar event.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        /// <param name="districtCode">Case insensitive districtCode code of the district from which team listings are requested</param>
        public async Task<IEnumerable<Team>> GetTeams(int season, string eventCode, string districtCode = null)
        {
            season = Validation.EnsureSeason(season);
            eventCode = Validation.EnsureEventCode(eventCode);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/teams?eventCode={1}", season, eventCode);
            if (!string.IsNullOrWhiteSpace(districtCode))
            {
                path = string.Format(CultureInfo.InvariantCulture, "{0}&districtCode={1}", path, districtCode);
            }

            return await GetTeams(path);
        }

        /// <summary>
        /// Gets all the the FRC official team in a particular season for a particular team.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="teamNumber">Team number of the team whose ranking is requested.</param>
        public async Task<IEnumerable<Team>> GetTeams(int season, int teamNumber)
        {
            season = Validation.EnsureSeason(season);
            teamNumber = Validation.EnsureTeamNumber(teamNumber);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/teams?teamNumber={1}", season, teamNumber);
            return await GetTeams(path);
        }

        /// <summary>
        /// Gets all registrations of teams in a particular season.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        public async Task<IEnumerable<RegistrationRecord>> GetRegistrations(int season)
        {
            season = Validation.EnsureSeason(season);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/registrations", season);
            return await GetRegistrations(path);
        }

        /// <summary>
        /// Gets all registrations for a particular team in a particular season.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="teamNumber">Team number of the team whose ranking is requested.</param>
        public async Task<IEnumerable<RegistrationRecord>> GetRegistrations(int season, int teamNumber)
        {
            season = Validation.EnsureSeason(season);
            teamNumber = Validation.EnsureTeamNumber(teamNumber);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/registrations?teamNumber={1}", season, teamNumber);
            return await GetRegistrations(path);
        }

        /// <summary>
        /// Gets all registrations for a particular team and a particular event.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        public async Task<IEnumerable<RegistrationRecord>> GetRegistrations(int season, string eventCode)
        {
            season = Validation.EnsureSeason(season);
            eventCode = Validation.EnsureEventCode(eventCode);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/registrations?eventCode={1}", season, eventCode);
            return await GetRegistrations(path);
        }
    }
}