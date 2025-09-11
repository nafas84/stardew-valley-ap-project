package io.Ap.StardewValley.Client.View;

import io.Ap.StardewValley.Client.Controller.ProfileMenuController;
import io.Ap.StardewValley.Common.Model.Command.ProfileMenuCommand;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu {
    //TODO: reload current user to apply changes
    @Override
    public void check(Scanner scanner) throws IOException {
        String input = scanner.nextLine();
        Matcher matcher;

        if ((matcher = ProfileMenuCommand.ChangeUsername.getMatcher(input)) != null) {
            System.out.println(ProfileMenuController.changeUsername(matcher.group("username")));
        } else if ((matcher = ProfileMenuCommand.ChangeNickname.getMatcher(input)) != null) {
            System.out.println(ProfileMenuController.changeNickname(matcher.group("nickname")));
        } else if ((matcher = ProfileMenuCommand.ChangeEmail.getMatcher(input)) != null) {
            System.out.println(ProfileMenuController.changeEmail(matcher.group("email")));
        } else if ((matcher = ProfileMenuCommand.ChangePassword.getMatcher(input)) != null) {
            System.out.println(ProfileMenuController.changePassword(matcher.group("newPassword"), matcher.group("oldPassword")));
        } else if (ProfileMenuCommand.ShowInfo.getMatcher(input) != null) {
            System.out.println(ProfileMenuController.showInfo());
        } else if ((matcher = ProfileMenuCommand.GoMenu.getMatcher(input)) != null) {
            System.out.println(ProfileMenuController.goMenu(matcher.group("menu")));
        } else if (ProfileMenuCommand.CurrentMenu.getMatcher(input) != null) {
            System.out.println(ProfileMenuController.currentMenu());
        } else if (ProfileMenuCommand.Exit.getMatcher(input) != null) {
            System.out.println(ProfileMenuController.exitMenu());
        } else {
            System.out.println("invalid command");
        }
    }
}
