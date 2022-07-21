package tn.esprit.softib.enums;

public enum TypeCredit {
	CAR("car"),
    HOME("home"),
    CONSUMPTION("consumption");

    private final String typeName;

    TypeCredit(final String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }

}
