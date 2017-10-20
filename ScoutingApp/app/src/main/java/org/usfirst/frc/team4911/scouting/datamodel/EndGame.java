package org.usfirst.frc.team4911.scouting.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * Data model for the end-game phase.
 */

public class EndGame implements DataValidator {
    // Did robot attempt to climb?
    @SerializedName("Attempted") private boolean attempted = false;

    // Did robot succeed in climbing?
    @SerializedName("Succeeded") private boolean succeeded = false;

    @SerializedName("ClimbOutcome") private ClimbOutcome climbOutcome = ClimbOutcome.NoAttempt;

    @SerializedName("TimeInSeconds") private int timeInSeconds = 0;

    // Indicates whether or not the team committed any non-technical fouls
    @SerializedName("NonTechFoul") private boolean nonTechFoul = false;

    // Indicates whether or not the team committed any technical fouls
    @SerializedName("TechFoul") private boolean techFoul = false;

    // Indicates if the team earned any cards during the match
    @SerializedName("Card") private Card card = Card.None;

    public Card getCard() {
        return this.card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean isNonTechFoul() {
        return this.nonTechFoul;
    }

    public void setNonTechFoul(boolean nonTechFoul) {
        this.nonTechFoul = nonTechFoul;
    }

    public boolean isTechFoul() {
        return this.techFoul;
    }

    public void setTechFoul(boolean techFoul) {
        this.techFoul = techFoul;
    }

    public ClimbOutcome getClimbOutcome() {
        return this.climbOutcome;
    }

    public void setClimbOutcome(ClimbOutcome climbOutcome) {
        this.attempted = climbOutcome != ClimbOutcome.NoAttempt;

        this.succeeded = (climbOutcome == ClimbOutcome.Success
                || climbOutcome == ClimbOutcome.SucceededButPoorClimber);

        this.climbOutcome = climbOutcome;
    }

    public int getTimeInSeconds() {
        return this.timeInSeconds;
    }

    public void setTimeInSeconds(int timeInSeconds) { this.timeInSeconds = timeInSeconds; }

    @Override
    public boolean isDataBad() {
        return (!this.attempted && (this.succeeded ||  this.timeInSeconds != 0))
                || (this.succeeded && this.timeInSeconds <= 0)
                || (this.succeeded
                    && !(this.climbOutcome == ClimbOutcome.SucceededButPoorClimber || this.climbOutcome == ClimbOutcome.Success))
                || (!this.succeeded
                    && (this.climbOutcome == ClimbOutcome.Success || this.climbOutcome == ClimbOutcome.SucceededButPoorClimber));
    }
}
