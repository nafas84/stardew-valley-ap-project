package io.Ap.StardewValley.Client.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Command.LoginMenuCommand;
import io.Ap.StardewValley.Common.Model.Command.Menu;
import io.Ap.StardewValley.Common.Model.Result;
import com.google.gson.Gson;
import io.Ap.StardewValley.Common.Model.User;
import io.Ap.StardewValley.Client.Screen.MenuScreen.ChangePasswordScreen;
import io.Ap.StardewValley.Client.Screen.MenuScreen.ProfileMenuScreen;

import java.io.*;

public class ProfileMenuController {
    public static Result changeUsername (String username) throws IOException {
        String oldUsername = App.getCurrentUser().getUsername();
        if (!LoginMenuCommand.Name.isMatch(username)) {
            return new Result(false, "Username format is invalid!\nUsernames can only contain lowercase and uppercase letters, numbers, and the _ symbol.");
        } else if (oldUsername.equals(username)) {
            return new Result(false, "Old and new usernames are the same!");
        }  else if (App.getUserByUsername(username) != null) {
            return new Result(false, "Username already exist!");
        }

        App.getCurrentUser().setUsername(username);

        Gson gson = new Gson();
        File oldFile = new File("data/users/" + oldUsername + ".json");
        File newFile = new File("data/users/" + username + ".json");
        FileWriter writer = new FileWriter(oldFile);

        gson.toJson(App.getCurrentUser(), writer);
        writer.close();
        oldFile.renameTo(newFile);

        return new Result(true, "Username successfully changed.");
    }

    public static Result changeNickname (String nickname) throws IOException {
        if (!LoginMenuCommand.Name.isMatch(nickname)) {
            return new Result(false, "Nickname format is invalid!\nNickname can only contain lowercase and uppercase letters, numbers, and the _ symbol.");
        } else if (App.getCurrentUser().getNickname().equals(nickname)) {
            return new Result(false, "Old and new nickname are the same!");
        }

        App.getCurrentUser().setNickname(nickname);
        updateCurrentUser();
        return new Result(true, "Nickname successfully changed.");
    }

    public static Result changeEmail (String email) throws IOException {
        if (!LoginMenuCommand.Email.isMatch(email)) {
            return new Result(false, "Email format is invalid!");
        } else if (App.getCurrentUser().getEmail().equals(email)) {
            return new Result(false, "Old and new email are the same!");
        }

        App.getCurrentUser().setEmail(email);
        updateCurrentUser();
        return new Result(true, "Email successfully changed.");
    }

    public static Result changePassword (String newPassword, String oldPassword) throws IOException {
        if (!LoginMenuCommand.Password.isMatch(newPassword)) {
            return new Result(false, "Password format is invalid!");
        } else if (newPassword.length() < 8) {
            return new Result(false, "Your password is not strong enough!\nIt must be at least 8 characters long.");
        } else if (!newPassword.matches(".*[a-z].*")) {
            return new Result(false, "Your password is not strong enough!\nIt must contain lowercase letters.");
        } else if (!newPassword.matches(".*[A-Z].*")) {
            return new Result(false, "Your password is not strong enough!\nIt must contain uppercase letters.");
        } else if (!newPassword.matches(".*[0-9].*")) {
            return new Result(false, "Your password is not strong enough!\nIt must contain numbers.");
        } else if (!newPassword.matches(".*[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/|\\\\].*")) {
            return new Result(false, "Your password is not strong enough!\nIt must contain special symbols.");
        } else if (!App.getCurrentUser().getPassword().equals(LoginMenuController.getHashPassword(oldPassword))) {
            return new Result(false, "Old password entered is incorrect!");
        } else if (App.getCurrentUser().getPassword().equals(LoginMenuController.getHashPassword(newPassword))) {
            return new Result(false, "Old and new password are the same!");
        }

        App.getCurrentUser().setPassword(LoginMenuController.getHashPassword(newPassword));
        updateCurrentUser();
        return new Result(true, "Password successfully changed.");
    }

    public static Result showInfo() {
        return new Result(true, App.getUserByUsername(App.getCurrentUser().getUsername()).toString());
    }

    public static Result goMenu (String menu) {
        if (!menu.equals("main")) {
            return new Result(false, "You can't go to " + menu + " menu in profile menu");
        }

        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "Now you are in " + menu + " menu");
    }

    public static Result currentMenu () {
        return new Result(true, "You are in profile menu");
    }

    public static Result exitMenu () {
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "Now you are in main menu");
    }

    private static void updateCurrentUser () throws IOException {
        Gson gson = new Gson();
        FileWriter writer = new FileWriter("data/users/" + App.getCurrentUser().getUsername() + ".json");
        gson.toJson(App.getCurrentUser(), writer);
        writer.close();
    }

    private static void updateLoggedIn() throws IOException {
        // TODO: aynaz daste khodeto mibose
        File file = new File("data/users/loggedIn.json");

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            User user = gson.fromJson(reader, User.class);

            if (user != null) {
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(App.getCurrentUser(), writer);
                }
            }
        }
    }


    //added by aynaz:
    public static void changeAvatar(int index, ProfileMenuScreen profileMenuScreen) throws IOException {
        String avatarPath = "etc/avatar/" + index + ".png";
        App.getCurrentUser().setAvatarPath(avatarPath);
        profileMenuScreen.setAvatarPath(avatarPath);
        profileMenuScreen.getAvatarImage().setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(avatarPath)))));
        updateCurrentUser();
        updateLoggedIn();
    }

    public static Result changeNicknameThroughScreen (String nickname) throws IOException {
        if (!LoginMenuCommand.Name.isMatch(nickname)) {
            return new Result(false, "Nickname format is invalid!");
        } else if (App.getCurrentUser().getNickname().equals(nickname)) {
            return new Result(false, "Old and new nickname are the same!");
//            return new Result(false, "Nickname is the same as old one!");
        }

        App.getCurrentUser().setNickname(nickname);
        updateCurrentUser();
        updateLoggedIn();
        return new Result(true, "Nickname successfully changed.");
    }


    public static Result changeUsernameThroughScreen (String username) throws IOException {
        String oldUsername = App.getCurrentUser().getUsername();
        if (!LoginMenuCommand.Name.isMatch(username)) {
            return new Result(false, "Username format is invalid!");
        } else if (oldUsername.equals(username)) {
            return new Result(false, "Old and new usernames are the same!");
        }  else if (App.getUserByUsername(username) != null) {
            return new Result(false, "Username already exist!");
        }

        App.getCurrentUser().setUsername(username);

        Gson gson = new Gson();
        File oldFile = new File("data/users/" + oldUsername + ".json");
        File newFile = new File("data/users/" + username + ".json");
        FileWriter writer = new FileWriter(oldFile);

        gson.toJson(App.getCurrentUser(), writer);
        writer.close();
        oldFile.renameTo(newFile);
        updateLoggedIn();

        return new Result(true, "Username successfully changed.");
    }

    public static Result changePasswordThroughScreen (String newPassword, String oldPassword) throws IOException {
        if (!LoginMenuCommand.Password.isMatch(newPassword)) {
            return new Result(false, "Password format is invalid!");
        } else if (newPassword.length() < 8) {
            return new Result(false, "password is not long enough!");
        } else if (!newPassword.matches(".*[a-z].*")) {
            return new Result(false, "pass must contain lowercase letters!");
        } else if (!newPassword.matches(".*[A-Z].*")) {
            return new Result(false, "pass must contain uppercase letters!");
        } else if (!newPassword.matches(".*[0-9].*")) {
            return new Result(false, "password must contain numbers!");
        } else if (!newPassword.matches(".*[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/|\\\\].*")) {
            return new Result(false, "pass must contain special symbols!");
        } else if (!App.getCurrentUser().getPassword().equals(LoginMenuController.getHashPassword(oldPassword))) {
            return new Result(false, "Old password is incorrect!");
        } else if (App.getCurrentUser().getPassword().equals(LoginMenuController.getHashPassword(newPassword))) {
            return new Result(false, "Old and new password are the same!");
        }

        App.getCurrentUser().setPassword(LoginMenuController.getHashPassword(newPassword));
        updateCurrentUser();
        updateLoggedIn();
        return new Result(true, "Password successfully changed.");
    }

    public static void setRandomPass (ChangePasswordScreen changePasswordScreen) {
        TextField newPass = changePasswordScreen.getNewPassField();
        String randomPass = LoginMenuController.generatePassword();

        newPass.setText(randomPass);
    }
}
