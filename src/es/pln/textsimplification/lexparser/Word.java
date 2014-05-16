package es.pln.textsimplification.lexparser;

import java.util.Arrays;

/**
 *
 * @author Raúl Moya Reyes <raulmoya.es>
 */
public class Word {

    private String relation;
    private String governorWord;
    private int idGovWord;
    private String dependentWord;
    private int idDepWord;

    public Word() {
    }

    public Word(String relation, String dependentWord, String governorWord) {
        this.relation = relation;

        String[] split = governorWord.split("-");
        this.governorWord = "";
        for (int i = 0; i < split.length - 1; i++) {
            this.governorWord += split[i];
        }
        this.idGovWord = Integer.parseInt(split[split.length - 1]);

        split = dependentWord.split("-");
        this.dependentWord = "";
        for (int i = 0; i < split.length - 1; i++) {
            this.dependentWord += split[i];
        }
        this.idDepWord = Integer.parseInt(split[split.length - 1]);
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getGovernorWord() {
        return governorWord;
    }

    public void setGovernorWord(String governorWord) {
        this.governorWord = governorWord;
    }

    public String getDependentWord() {
        return dependentWord;
    }

    public void setDependentWord(String dependentWord) {
        this.dependentWord = dependentWord;
    }

    public int getIdGovWord() {
        return idGovWord;
    }

    public int getIdDepWord() {
        return idDepWord;
    }

    @Override
    public String toString() {
        return this.dependentWord;
    }

}
