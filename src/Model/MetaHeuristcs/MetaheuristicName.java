package Model.MetaHeuristcs;

public enum MetaheuristicName {
    TABOU("Tabou"),
    RECUIT("Recuit Simulé"),
    NONE("None")
    ;

    private final String text;

    /**
     * @param text
     */
    MetaheuristicName(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
