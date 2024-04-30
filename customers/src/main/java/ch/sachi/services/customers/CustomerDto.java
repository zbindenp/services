package ch.sachi.services.customers;

public class CustomerDto {
    private final Long id;
    private final String name;

    public CustomerDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
