package br.com.receitaquedoimenos.ReceitaQueDoiMenos.utils;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.forbbidenWordException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class WordValidator {

    private ArrayList<String> forbiddenWords;

    public WordValidator(){
        this.forbiddenWords = new ArrayList<String>();
        this.forbiddenWords.add("pau");
        this.forbiddenWords.add("rola");
        this.forbiddenWords.add("pica");
        this.forbiddenWords.add("pika");

        this.forbiddenWords.add("puta");
        this.forbiddenWords.add("rapariga");
        this.forbiddenWords.add("quenga");

        this.forbiddenWords.add("siririca");
        this.forbiddenWords.add("pariu");
        this.forbiddenWords.add("transar");
        this.forbiddenWords.add("cu");
        this.forbiddenWords.add("buceta");
        this.forbiddenWords.add("bct");

        this.forbiddenWords.add("caralho");
        this.forbiddenWords.add("carai");
        this.forbiddenWords.add("porra");
        this.forbiddenWords.add("merda");
        this.forbiddenWords.add("imbecil");

        this.forbiddenWords.add("foda");
        this.forbiddenWords.add("foda-se");
        this.forbiddenWords.add("fodase");
        this.forbiddenWords.add("fodasse");
    }

    public void validateString(String text) {
        for (String word:
             this.forbiddenWords) {
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher match = pattern.matcher(word);

            if(match.find()) {
                throw new forbbidenWordException();
            }
        }
    }
}
