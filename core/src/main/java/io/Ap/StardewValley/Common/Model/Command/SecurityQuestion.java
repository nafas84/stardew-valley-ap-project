package io.Ap.StardewValley.Common.Model.Command;

public enum SecurityQuestion {
    S1 ("Esme saget/siblinget chie?"),
    S2 ("Aya bad az ghahr o cat aks az pahat midi?"),
    S3 ("Where is Abam?"),
    S4 ("Chand ta bache dari?"),
    S5 ("Mamaneto bishtar dost dari ya aghato?"),
    S6 ("Positione morede alaghat?"),
    S7 ("Chikar karD ba delam? (vayyy)"),
    S8 ("Nezhade morede alaghat?"),
    S9 ("Esme pedaret?"),
    S10 ("Avalin partnert?");

    private final String question;

    SecurityQuestion(String question) {
        this.question = question;
    }

    public static String getQuestions() {
        StringBuilder allQuestions = new StringBuilder();
        allQuestions.append("Questions:\n").append("_________________________________________________\n");
        int i = 1;
        for (SecurityQuestion sq : SecurityQuestion.values()) {
            allQuestions.append(i++).append(".").append(sq.getQuestion()).append("\n");
        }

        return allQuestions.toString();
    }

    public String getQuestion() {
        return question;
    }
}
