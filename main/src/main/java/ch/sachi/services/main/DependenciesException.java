package ch.sachi.services.main;

public class DependenciesException extends Exception {
    public DependenciesException(String description, Exception e) {
        super(description, e);
    }
}
