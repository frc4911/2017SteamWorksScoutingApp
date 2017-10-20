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
        /// Gets the 2016 match results for all matches of a particular event.
        /// </summary>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        /// <param name="tournamentLevel">Tournament Level of desired match results.</param>
        public async Task<IEnumerable<MatchScores2016>> GetScores2016(string eventCode, TournamentLevel tournamentLevel)
        {
            const int season = 2016;
            eventCode = Validation.EnsureEventCode(eventCode);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/scores/{1}/{2}", season, eventCode, tournamentLevel.ToString());
            return await GetScores2016(path);
        }

        /// <summary>
        /// Gets the 2016 match results for all matches of a particular event and particular team.
        /// </summary>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        /// <param name="tournamentLevel">Tournament Level of desired match results.</param>
        /// <param name="teamNumber">Team Number to search for within the results. Only returns match results in which the requested team was a participant. Example: 101.</param>
        public async Task<IEnumerable<MatchScores2016>> GetScores2016(string eventCode, TournamentLevel tournamentLevel, int teamNumber)
        {
            const int season = 2016;
            eventCode = Validation.EnsureEventCode(eventCode);
            teamNumber = Validation.EnsureTeamNumber(teamNumber);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/scores/{1}/{2}?teamNumber={3}", season, eventCode, tournamentLevel.ToString(), teamNumber);
            return await GetScores2016(path);
        }

        /// <summary>
        /// Gets the 2016 match results for a particular match of a particular event.
        /// </summary>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        /// <param name="matchNumber">single matchNumber of result. Example</param>
        /// <param name="tournamentLevel">Tournament Level of desired match results.</param>
        public async Task<IEnumerable<MatchScores2016>> GetScores2016(string eventCode, int matchNumber, TournamentLevel tournamentLevel)
        {
            const int season = 2016;
            eventCode = Validation.EnsureEventCode(eventCode);
            if (matchNumber < 1)
            {
                throw new ArgumentNullException(nameof(matchNumber), "Match number must be greater than or equal to 1.");
            }

            var path = string.Format(CultureInfo.InvariantCulture, "0:D4}/scores/{1}/{2}?matchNumber{3}", season, eventCode, tournamentLevel.ToString(), matchNumber);
            return await GetScores2016(path);
        }

        /// <summary>
        /// Gets a range of 2016 match results for a particular event .
        /// </summary>
        /// <param name="eventCode">Case insensitive alphanumeric eventCode of the event from which the alliance selection results are requested. Must be at least 3 characters.</param>
        /// <param name="startMatchNumber">Start match number for subset of results to return.</param>
        /// <param name="endMatchNumber">End match number for subset of results to return (inclusive.)</param>
        /// <param name="tournamentLevel">Tournament Level of desired match results.</param>
        public async Task<IEnumerable<MatchScores2016>> GetScores2016(string eventCode, int startMatchNumber, int endMatchNumber, TournamentLevel tournamentLevel)
        {
            const int season = 2016;
            eventCode = Validation.EnsureEventCode(eventCode);
            var path = string.Format(CultureInfo.InvariantCulture, "{0:D4}/scores/{1}/{2}?start={3}&end={4}", season, eventCode, tournamentLevel.ToString(), startMatchNumber, endMatchNumber);
            return await GetScores2016(path);
        }
    }
}
