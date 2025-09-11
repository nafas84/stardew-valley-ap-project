package io.Ap.StardewValley.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.Ap.StardewValley.Client.App.ClientApp;
import io.Ap.StardewValley.StardewValley;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.

        if(args.length > 0) {
            try {
                ClientApp.initFromArgs(args);
                ClientApp.connectServer();
            } catch (Exception e) {
                return;
            }
        }

        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new StardewValley(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(144);
        config.useVsync(true);
        config.setWindowIcon("assets/etc/icon.png");
        config.setTitle("Stardew Valley");
        config.setWindowedMode(1920, 1080);
        config.setResizable(false);
        return config;
    }
}
