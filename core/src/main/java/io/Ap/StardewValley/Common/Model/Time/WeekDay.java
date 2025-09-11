package io.Ap.StardewValley.Common.Model.Time;

public enum WeekDay {
    Saturday("Sat."),
    Sunday("Sun."),
    Monday("Mon."),
    Tuesday("Tue."),
    Wednesday("Wed."),
    Thursday("Thu."),
    Friday("Fri.");

    private final String abbreviation;

    WeekDay(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
