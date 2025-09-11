package io.Ap.StardewValley.Common.Model.Command;

public enum ProfileMenuCommand implements Command {
    ChangeUsername("change username -u (?<username>\\S+)"),
    ChangeNickname("change nickname -u (?<nickname>\\S+)"),
    ChangeEmail("change email -e (?<email>\\S+)"),
    ChangePassword("change password -p (?<newPassword>\\S+) -o (?<oldPassword>\\S+)"),

    ShowInfo("user info"),

    GoMenu ("go to (?<menu>main|login|game|profile) menu"),
    CurrentMenu ("show current menu"),
    Exit ("menu exit");

    private final String pattern;

    ProfileMenuCommand(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }
}
