package commmadondo.github.everyonevotes;

/**
 * Created by mmadondo on 4/10/2017.
 */

public class Vote {
    String answerText;
    Integer selection;

    /**
     * Empty constructor
     */
    public Vote() {
    }

    public Vote(String answerText, Integer selection) {
        this.answerText = answerText;
        this.selection = selection;
    }

    public String getAnswerText() {
        return answerText;
    }

    public Integer getSelection() {
        return selection;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public void setSelection(Integer selection) {
        this.selection = selection;
    }

    @Override
    public String toString() {
      return "A vote for " + answerText + " or " + selection;
    }
}
