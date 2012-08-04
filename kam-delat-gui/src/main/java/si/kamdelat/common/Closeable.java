package si.kamdelat.common;

public interface Closeable extends AutoCloseable {
  @Override
  public void close() throws RuntimeException;
}
