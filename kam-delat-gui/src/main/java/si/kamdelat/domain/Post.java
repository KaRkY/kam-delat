package si.kamdelat.domain;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class Post {
  private final Integer postalNumber;
  private final Name    name;

  public Post(final Integer postalNumber, final Name name) {
    this.postalNumber = Preconditions.checkNotNull(postalNumber, "Postal number cannot be null.");
    Preconditions.checkArgument(postalNumber > 999 && postalNumber < 10000, "Postal number must bi 4 digits");
    this.name = Preconditions.checkNotNull(name, "Name cannot be null.");
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof Post) {
      final Post other = (Post) obj;
      return Objects.equal(postalNumber, other.postalNumber);
    }
    else
      return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(postalNumber);
  }

  public Name name() {
    return name;
  }

  public Integer postalNumber() {
    return postalNumber;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).add("postalNumber", postalNumber).add("name", name).toString();
  }
}
