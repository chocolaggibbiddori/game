package chocola.interfaces;

@FunctionalInterface
public interface Validator<T> {

    boolean isValid(T t);
}
