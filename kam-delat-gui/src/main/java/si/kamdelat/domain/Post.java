package si.kamdelat.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;

public class Post {
  // =================================================================================================================
  // Fields
  // =================================================================================================================
  private final Integer postalNumber;
  private final Name    name;

  // =================================================================================================================
  // Constructors
  // =================================================================================================================
  public Post(final Integer postalNumber, final Name name) {
    this.postalNumber = checkNotNull(postalNumber, "Postal number cannot be null.");
    checkArgument(postalNumber > 999 && postalNumber < 10000, "Postal number must bi 4 digits");
    this.name = checkNotNull(name, "Name cannot be null.");
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof Post) {
      final Post other = (Post) obj;
      return Objects.equal(postalNumber, other.postalNumber);
    } else
      return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(postalNumber);
  }

  public Name name() {
    return name;
  }

  // =================================================================================================================
  // Methods
  // =================================================================================================================
  public Integer postalNumber() {
    return postalNumber;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).add("postalNumber", postalNumber).add("name", name).toString();
  }
}
