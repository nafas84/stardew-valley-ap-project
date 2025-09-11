package io.Ap.StardewValley.Common.Model.Command;

public enum LoginMenuCommand implements Command {
    Register("register -u (?<username>\\S+) -p (?<password>\\S+)(?: (?<rePassword>\\S+))? -n (?<nickname>\\S+) -e (?<email>\\S+) -g (?<gender>\\S+)"),
        Name ("^[a-zA-Z0-9_]+$"),
        Password("^[a-zA-Z0-9!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/|\\\\]+$"),
        Email("^[a-zA-Z0-9](?!.*\\.\\.)[a-zA-Z0-9._-]*[a-zA-Z0-9]@[a-zA-Z0-9][a-zA-Z0-9_]*[a-zA-Z0-9]\\.[A-Za-z]{2,}$"),
        Gender("^male|female$"),
    SecurityQuestion("pick question -q (?<questionNumber>-?[0-9]+) -a (?<answer>.+?) -c (?<reAnswer>.+)"),

    Login("login -u (?<username>\\S+) -p (?<password>\\S+)(?: (?<loggedIn>-stay-logged-in))?"),
    EnterAnswer("answer -a (?<answer>.+)"),
    EnterPassword("password -p (?<password>.+)"),
    ForgetPassword("forget password -u (?<username>\\S+)"),

    GoMenu("go to (?<menu>main|login|game|profile) menu"),
    CurrentMenu("show current menu"),
    Exit("menu exit");

    private final String pattern;

    LoginMenuCommand(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }
}
