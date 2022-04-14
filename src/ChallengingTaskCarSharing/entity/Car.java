package ChallengingTaskCarSharing.entity;

public class Car {
    private final Integer id;
    private final String name;
    private final Integer companyId;

    public Car(Integer id, String name, Integer companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCompanyId() {
        return companyId;
    }
}