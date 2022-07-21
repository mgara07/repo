package tn.esprit.softib.enums;

public enum CreditStatus {
	CREATED("created"),
    VALIDATED("validated"),
    CONFIRMED("confirmed"),
    REJECTED("rejected"),
    ACCEPTED("accepted"),
    WAITINGFORCLIENTACCEPTANCE("waiting for client acceptance"),
    PAYED("payed"),
    CLOSED("closed");
    private final String typeName;

    CreditStatus(final String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }

}
