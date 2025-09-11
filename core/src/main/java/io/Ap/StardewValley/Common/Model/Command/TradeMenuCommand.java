package io.Ap.StardewValley.Common.Model.Command;

public enum TradeMenuCommand implements Command {

    TradeItem("trade\\s+-u\\s+(?<username>.+?)\\s+-t\\s+(?<type>.+?)\\s+-i\\s+(?<item>.+?)\\s+-a\\s+(?<amount>\\d+)-ti\\s+(?<targetItem>.+?)\\s+-ta\\s+(?<targetAmount>.+))"),
    TradePrice("trade\\s+-u\\s+(?<username>.+?)\\s+-t\\s+(?<type>.+?)\\s+-i\\s+(?<item>.+?)\\s+-a\\s+(?<amount>\\d+)\\s+-p\\s+(?<price>.+)"),
    TradeLIst("trade\\s+list"),
    TradeRespond("trade\\s+response\\s+(?<response>.+)\\s+-i\\s+(?<id>\\d+)"),
    TradeHistory("trade\\s+history"),
    BackToGameMenu("back\\s+game\\s+menu");

    private final String pattern;

    TradeMenuCommand(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }


}
