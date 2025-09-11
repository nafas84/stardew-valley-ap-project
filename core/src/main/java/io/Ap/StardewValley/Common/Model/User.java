package io.Ap.StardewValley.Common.Model;

import io.Ap.StardewValley.Common.Model.Command.SecurityQuestion;
import io.Ap.StardewValley.Server.Model.Lobby;

public class User {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String gender;

    private SecurityQuestion question;
    private String answer;

    private int games = 0;
    private int mostCoinsEarned = 0;

    private String avatarPath;

    private Lobby currentLobby;

    public User() {} //needed for jason

    public User(String username, String password, String nickname, String email, String gender, String avatarPath) {
        this.id = App.getNumberOfUsers() + 1;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.avatarPath = avatarPath;
        this.currentLobby = null;
    }

    public SecurityQuestion getQuestion() {
        return question;
    }

    public void setQuestion(SecurityQuestion question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public void setMostCoinsEarned(int mostCoinsEarned) {
        this.mostCoinsEarned = mostCoinsEarned;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getGender() {
        return gender;
    }

    public String getAnswer() {
        return answer;
    }

    public String getSecurityQuestion () {
        return this.question.getQuestion();
    }

    public int getId() {
        return id;
    }

    public int getGames() {
        return games;
    }

    public int getMostCoinsEarned() {
        return mostCoinsEarned;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public Lobby getCurrentLobby() {
        return currentLobby;
    }

    public void setCurrentLobby(Lobby lobby) {
        this.currentLobby = lobby;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("User Information:\n").append("______________________________\n");
        result.append("Username: ").append(this.username).append("\n")
                .append("Nickname: ").append(this.nickname).append("\n")
                .append("Email: ").append(this.email).append("\n")
                .append("Gender: ").append(this.gender).append("\n")
                .append("Security Question: ").append(this.getSecurityQuestion()).append("\n")
                .append("Most coins earned: ").append(this.mostCoinsEarned).append("\n")
                .append("Number of games played: ").append(this.games).append("\n");
        return result.toString();
    }
}
