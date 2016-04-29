package com.mobiledev.topimpamatricks.MatrixCalculation;

/**
 * Created by maiaphoebedylansamerjan on 4/14/16.
 */
public class Detail {
    private String latex;
    private String description;
    private String definition;

    public Detail(String description, String latex, String definition) {
        this.description = description;
        this.latex = latex;
        this.definition = definition;
    }

    public Detail(String description, String latex) {
        this.description = description;
        this.latex = latex;
    }

    public String getLatex() {
        return latex;
    }

    public String getDescription() {
        return description;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setLatex(String latex) {
        this.latex = latex;
    }
}
