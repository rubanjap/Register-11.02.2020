package com.register.me.model.data.model;

/**
 * Created by Jennifer - AIT on 14-02-2020AM 11:06.
 */
public class QandA {
    String question;
    String answer;
    /*
    1-EditText
    * 2-RadioButton
    *
    */
    int type;

    public QandA(String question, String answer, int type) {
        this.question = question;
        this.answer = answer;
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
