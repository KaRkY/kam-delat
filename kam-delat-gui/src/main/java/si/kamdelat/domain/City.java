package si.kamdelat.domain;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class City {
  private final Identifier id;
  private final Name       name;
  private final Post       post;

  public City(final Identifier id, final Name name, final Post post) {
    this.post = Preconditions.checkNotNull(post, "Post cannot be null.");
    this.id = Preconditions.checkNotNull(id, "Id canot be null.");
    this.name = Preconditions.checkNotNull(name, "Name canot be null.");
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof City) {
      final City other = (City) obj;
      return Objects.equal(name, other.name);
    }
    else
      return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }

  public Identifier id() {
    return id;
  }

  public Name name() {
    return name;
  }

  public Post post() {
    return post;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).add("id", id).add("name", name).add("post", post).toString();
  }
}
