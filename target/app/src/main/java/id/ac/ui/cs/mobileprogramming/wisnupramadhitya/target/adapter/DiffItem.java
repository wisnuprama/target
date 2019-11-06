package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.adapter;

/**
 * Interface for item class that used in BaseRecyclerView.
 * @param <T>
 */
public interface DiffItem<T> {
    boolean isItemTheSame(T other);
}
