package si.kamdelat.events;

public interface Event<T> {

  T getSource();
}
