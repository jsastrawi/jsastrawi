package jsastrawi.specification;

public interface Specification<T> {
    public boolean isSatisfiedBy(T t);
}
