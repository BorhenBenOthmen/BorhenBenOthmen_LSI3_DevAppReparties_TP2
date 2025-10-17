package objetPackage;

import java.io.Serializable;

public class Operation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String operande1;
    private String operateur;
    private String operande2;

    public Operation(String operande1, String operateur, String operande2) {
        this.operande1 = operande1;
        this.operateur = operateur;
        this.operande2 = operande2;
    }

    public String getOperande1() {
        return operande1;
    }

    public String getOperande2() {
        return operande2;
    }

    public String getOperateur() {
        return operateur;
    }

    @Override
    public String toString() {
        return operande1 + " " + operateur + " " + operande2;
    }
}
