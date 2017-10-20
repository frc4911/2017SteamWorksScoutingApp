//------------------------------------------------------------------------------
// <copyright>
//     Copyright (c) Cyberknights 4911 FRC Robotics team. All Rights Reserved.
// </copyright>
//------------------------------------------------------------------------------

using System;
using System.Collections.Generic;
using System.Globalization;
using System.Threading.Tasks;
using Org.USFirst.Frc.Team4911.FrcEvents.Models;

namespace Org.USFirst.Frc.Team4911.FrcEvents
{
    public partial class FrcEventsClient
    {
        /// <summary>
        /// Gets the match results for all matches of a particular event in a particular season.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        public async Task<IEnumerable<Match>> GetMatches(int season, string eventCode)
        {
            season = Validation.EnsureSeason(season);
            eventCode = Validation.EnsureEventCode(eventCode);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/matches/{1}", season, eventCode);
            return await GetMatches(path);
        }

        /// <summary>
        /// Gets the match results for all matches of a particular event in a particular season and particular team.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        /// <param name="teamNumber">Team Number to search for within the results. Only returns match results in which the requested team was a participant. Example: 101.</param>
        public async Task<IEnumerable<Match>> GetMatches(int season, string eventCode, int teamNumber)
        {
            season = Validation.EnsureSeason(season);
            eventCode = Validation.EnsureEventCode(eventCode);
            teamNumber = Validation.EnsureTeamNumber(teamNumber);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/matches/{1}?teamNumber={2}", season, eventCode, teamNumber);
            return await GetMatches(path);
        }

        /// <summary>
        /// Gets the results for a particular match of a particular event in a particular season.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        /// <param name="matchNumber">single matchNumber of result. Example</param>
        /// <param name="tournamentLevel">Tournament Level of desired match results.</param>
        public async Task<IEnumerable<Match>> GetMatches(int season, string eventCode, int matchNumber, TournamentLevel tournamentLevel)
        {
            season = Validation.EnsureSeason(season);
            eventCode = Validation.EnsureEventCode(eventCode);
            if (matchNumber < 1)
            {
                throw new ArgumentNullException(nameof(matchNumber), "Match number must be greater than or equal to 1.");
            }

            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/matches/{1}?matchNumber{2}&tournamentLevel={3}", season, eventCode, matchNumber, tournamentLevel.ToString());
            return await GetMatches(path);
        }

        /// <summary>
        /// Gets a range of match results for a particular event in a particular season.
        /// </summary>
        /// <param name="season">Numeric year of the event from which the event alliances are requested. Must be 4 digits and greater than or equal to 2015, and less than or equal to the current year.</param>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        /// <param name="startMatchNumber">Start match number for subset of results to return.</param>
        /// <param name="endMatchNumber">End match number for subset of results to return (inclusive.)</param>
        /// <param name="tournamentLevel">Tournament Level of desired match results.</param>
        public async Task<IEnumerable<Match>> GetMatches(int season, string eventCode, int startMatchNumber, int endMatchNumber, TournamentLevel tournamentLevel)
        {
            season = Validation.EnsureSeason(season);
            eventCode = Validation.EnsureEventCode(eventCode);
			var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/matches/{1}?start={2}&end={3}&tournamentLevel={4}", season, eventCode, startMatchNumber, endMatchNumber, tournamentLevel.ToString());
            return await GetMatches(path);
        }
    }
}
