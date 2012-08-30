package si.kamdelat.domain;

import org.joda.time.LocalDate;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class Visit {
  private final Identifier id;
  private final LocalDate  date;

  public Visit(final Identifier id, final LocalDate date) {
    this.id = Preconditions.checkNotNull(id, "Id cannot be null.");
    this.date = Preconditions.checkNotNull(date, "Date cannot be null.");
  }

  public LocalDate date() {
    return date;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof Visit) {
      final Visit other = (Visit) obj;
      return Objects.equal(date, other.date);
    }
    else
      return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(date);
  }

  public Identifier id() {
    return id;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).add("id", id).add("date", date).toString();
  }
}
