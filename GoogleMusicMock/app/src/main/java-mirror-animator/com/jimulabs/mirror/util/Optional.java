package com.jimulabs.mirror.util;

import java.util.Collections;
import java.util.Set;

/**
 * Created by matt on 2014-03-05.
 */
public class Optional<T> {

    private final T mReference;
    private final boolean mPresent;

    // Private constructor for factory methods
    private Optional(T reference, boolean present) {
        mReference = reference;
        mPresent = present;
    }

    /**
     * Create an absent Optional with no value.
     *
     * @param <T> The type of the non-value
     * @return An Optional representing this non-value
     */
    public static <T> Optional<T> absent() {
        return new Optional<T>(null, false);
    }

    /**
     * Create an Optional value with the given reference
     *
     * @param reference The non-null value to wrap in the Optional instance
     * @param <T>       The type of the value
     * @return An Optional holding the passed value
     */
    public static <T> Optional<T> of(T reference) {
        if (reference == null) {
            throw new IllegalStateException("Optional.of() must be passed a non-null reference");
        } else {
            return new Optional<T>(reference, true);
        }
    }

    /**
     * Create an Optional from a reference that may be null
     *
     * @param reference The value (possibly null) to create the optional from
     * @param <T>       The type of the value
     * @return An pOtional wrapping the value if it's non-null, or an absent Optional otherwise
     */
    public static <T> Optional<T> fromNullable(T reference) {
        if (reference == null) {
            return Optional.absent();
        } else {
            return Optional.of(reference);
        }
    }

    /**
     * @return true if the wrapped reference is present, false otherwise
     */
    public boolean isPresent() {
        return mPresent;
    }

    /**
     * @return The non-null value wrapped by this Optional
     * @throws IllegalStateException if the Optional is absent
     */
    public T get() {
        if (mPresent) {
            return mReference;
        } else {
            throw new IllegalStateException("Can't get() an absent reference from Optional");
        }
    }

    /**
     * @return An immutable singleton set containing the reference if it is present, or an
     * immutable empty set if it is absent
     */
    public Set<T> asSet() {
        if (mPresent) {
            return Collections.singleton(mReference);
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * @return The wrapped reference if it's present, or null if it's not
     */
    public T orNull() {
        return mPresent ? mReference : null;
    }

    /**
     * @param defaultValue
     * @return The wrapped reference if it's present, or <code>defaultValue</code> if it's not
     */
    public T or(T defaultValue) {
        return mPresent ? mReference : defaultValue;
    }

    /**
     * @param other
     * @return This instance if it has a value present, other <code>otherwise</code>
     */
    public Optional<T> or(Optional<T> other) {
        return mPresent ? this : other;
    }

    /**
     * Equality comparison for Optional. Note that all absent Optionals are equal regardless
     * of type.
     *
     * @param other
     * @return true if both Optionals are absent, or both have the same value present
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!other.getClass().equals(Optional.class)) return false;

        Optional<?> o = (Optional<?>) other;
        if (mPresent) {
            return o.isPresent() && mReference.equals(o.get());
        } else {
            return !o.isPresent();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Optional:");
        if (mPresent) {
            sb.append("present[")
                    .append(mReference)
                    .append(']');
        } else {
            sb.append("absent");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = mReference != null ? mReference.hashCode() : 0;
        result = 31 * result + (mPresent ? 1 : 0);
        return result;
    }
}
