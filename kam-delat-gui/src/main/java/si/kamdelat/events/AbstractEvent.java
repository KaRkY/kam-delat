package si.kamdelat.events;

public abstract class AbstractEvent<T> implements Event<T> {

  private final T source;

  public AbstractEvent(final T source) {
    this.source = source;
  }

  @Override
  public T getSource() {
    return source;
  }
}
