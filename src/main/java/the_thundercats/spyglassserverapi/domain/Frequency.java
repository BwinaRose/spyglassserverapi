package the_thundercats.spyglassserverapi.domain;

public enum Frequency {

    WEEKLY("Weekly"), BIWEEKLY("Bi-weekly"), MONTHLY("Monthly");
    private final String name;
    Frequency(String name) {
        this.name = name;
    }
}
