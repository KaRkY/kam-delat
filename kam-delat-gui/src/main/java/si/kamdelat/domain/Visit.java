package si.kamdelat.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import org.joda.time.LocalDate;

import com.google.common.base.Objects;

public class Visit {
  // =================================================================================================================
  // Fields
  // =================================================================================================================
  private final Identifier id;
  private final LocalDate  date;

  // =================================================================================================================
  // Constructors
  // =================================================================================================================
  public Visit(final Identifier id, final LocalDate date) {
    this.id = checkNotNull(id, "Id cannot be null.");
    this.date = checkNotNull(date, "Date cannot be null.");
  }

  public LocalDate date() {
    return date;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof Visit) {
      final Visit other = (Visit) obj;
      return Objects.equal(date, other.date);
    } else
      return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(date);
  }

  // =================================================================================================================
  // Methods
  // =================================================================================================================
  public Identifier id() {
    return id;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).add("id", id).add("date", date).toString();
  }
}
