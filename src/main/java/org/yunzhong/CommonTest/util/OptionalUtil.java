package org.yunzhong.CommonTest.util;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author yunzhong
 *
 * @param <T>
 */
public final class OptionalUtil<T> {

    private static final OptionalUtil<?> EMPTY = new OptionalUtil<>();

    private final T value;

    private OptionalUtil() {
        this.value = null;
    }

    public OptionalUtil(T value) {
        this.value = value;
    }

    public static <T> OptionalUtil<T> of(T value) {
        return new OptionalUtil<>(Objects.requireNonNull(value));
    }

    /**
     * @param <T>
     * @param value
     * @return
     */
    public static <T> OptionalUtil<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    /**
     * 
     * 取出具体的值
     * 
     * @param fn
     * @param <R>
     * @return
     */

    public T get() {
        return value;
    }

    /**
     * 
     * 取出一个可能为空的对象
     * 
     * @param fn
     * @param <R> * @return
     */

    public <R> OptionalUtil<R> getProperty(Function<? super T, ? extends R> fn) {
        return Objects.isNull(value) ? OptionalUtil.empty() : OptionalUtil.ofNullable(fn.apply(value));
    }

    /**
     * 如果目标值不为空，返回value。<br>
     * 如果目标值为空,返回传入的默认值
     * 
     * @param defaultValue
     * @return
     */

    public T orElse(T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * 如果目标值不为空，返回value。<br>
     * 如果目标值为空 通过lambda表达式获取一个值
     * 
     * @param defaultSupplier
     * @return
     */

    public T orElseGet(Supplier<? extends T> defaultSupplier) {
        return value != null ? value : defaultSupplier.get();
    }

    /**
     * 
     * 如果目标值为空 抛出一个异常
     * 
     * @param exceptionSupplier
     * @param <X>
     * @return
     * @throws X
     */

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    public boolean isPresent() {
        return value != null;
    }

    /**
     * @param consumer
     */
    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null)
            consumer.accept(value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    public OptionalUtil<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return this;
        } else {
            return predicate.test(value) ? this : empty();
        }
    }

    /**
     * 空值常量
     * 
     * @param <T> * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> OptionalUtil<T> empty() {
        OptionalUtil<T> none = (OptionalUtil<T>) EMPTY;
        return none;
    }
}
