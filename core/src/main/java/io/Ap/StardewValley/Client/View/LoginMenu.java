package io.Ap.StardewValley.Client.View;

import io.Ap.StardewValley.Client.Controller.LoginMenuController;
import io.Ap.StardewValley.Common.Model.Command.LoginMenuCommand;
import io.Ap.StardewValley.Common.Model.Command.SecurityQuestion;
import io.Ap.StardewValley.Common.Model.Result;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) throws IOException {
        String input = scanner.nextLine();
        Matcher matcher;

        if ((matcher = LoginMenuCommand.Register.getMatcher(input)) != null) {
            Result result = LoginMenuController.register(matcher.group("username"), matcher.group("password"),
                    matcher.group("rePassword"), matcher.group("nickname"), matcher.group("email"),
                    matcher.group("gender"));
            System.out.println(result);
            // TODO: Badtarin rah
            if (result.message().contains("Do you accept")) {
                String newPassword = result.message().substring(20, result.message().indexOf("\n")).trim();
                input = scanner.nextLine();
                if (input.contains("yes")) {
                    result = LoginMenuController.register(matcher.group("username"), newPassword,
                            newPassword, matcher.group("nickname"), matcher.group("email"),
                            matcher.group("gender"));
                    System.out.println(result);
                } else {
                  System.out.println("Bashe Pas");
                }
            }
            if (result.isSuccessful()) {
                String username = matcher.group("username");
                input = scanner.nextLine();
                while (true) {
                    if ((matcher = LoginMenuCommand.SecurityQuestion.getMatcher(input)) != null) {
                        result = LoginMenuController.securityQuestion(username,  Integer.parseInt(matcher.group("questionNumber")), matcher.group("answer"), matcher.group("reAnswer"));
                        System.out.println(result);
                        if (result.isSuccessful()) break;
                    } else {
                        System.out.println("Please pick a security question!\n" + SecurityQuestion.getQuestions());
                    }
                    input = scanner.nextLine();
                }
            }
        } else if ((matcher = LoginMenuCommand.Login.getMatcher(input)) != null) {
            System.out.println(LoginMenuController.login(matcher.group("username"), matcher.group("password"), matcher.group("loggedIn")));
        } else if ((matcher = LoginMenuCommand.ForgetPassword.getMatcher(input)) != null) {
            String username = matcher.group("username");
            Result result = LoginMenuController.forgetPassword(username);
            System.out.println(result);
            if (result.isSuccessful()) {
                input = scanner.nextLine();
                if ((matcher = LoginMenuCommand.EnterAnswer.getMatcher(input)) != null) {
                    result = LoginMenuController.securityAnswer(username, matcher.group("answer").trim());
                    System.out.println(result);
                    if (result.isSuccessful()) {
                        input = scanner.nextLine();
                        if ((matcher = LoginMenuCommand.EnterPassword.getMatcher(input)) != null) {
                            result = LoginMenuController.forgetPassword(username, matcher.group("password").trim());
                            System.out.println(result);
                            if (result.message().contains("Do you accept")) {
                                String newPassword = result.message().substring(20, result.message().indexOf("\n")).trim();
                                input = scanner.nextLine();
                                if (input.contains("yes")) {
                                    result = LoginMenuController.securityAnswer(username, newPassword);
                                    System.out.println(result);
                                } else {
                                    System.out.println("Bashe Pas");
                                }
                            }
                        } else {
                            System.out.println("Please try again. format: (password -p <password>)");
                        }
                    }
                } else {
                    System.out.println("Please try again. format: (answer -a <answer>)");
                }
            }
        } else if (LoginMenuCommand.CurrentMenu.getMatcher(input) != null) {
            System.out.println(LoginMenuController.currentMenu());
        } else if (LoginMenuCommand.Exit.getMatcher(input) != null) {
            System.out.println(LoginMenuController.exitMenu());
        } else {
            System.out.println("invalid command");
        }
    }
}
