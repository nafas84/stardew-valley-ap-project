package io.Ap.StardewValley.Common.Model.Command;

public enum MainMenuCommand implements Command {
    Logout("logout"),

    GoMenu ("go to (?<menu>main|login|game|profile) menu"),
    CurrentMenu ("show current menu"),
    Exit ("menu exit");

    private final String pattern;

    MainMenuCommand(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }
}
