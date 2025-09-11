package io.Ap.StardewValley.Common.Model.Tool;

public enum ToolLevel {
    Starter (1),
    Copper (2),
    Steel (3),
    Gold (4),
    Iridium (5);

    private final int level;

    ToolLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
