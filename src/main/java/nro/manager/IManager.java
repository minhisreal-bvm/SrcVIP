package nro.manager;

/**
 * @MinhDepZai
 */

public interface IManager <E> {

    void add(E e);

    void remove(E e);

    E findByID(int id);
}
