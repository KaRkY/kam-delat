package si.kamdelat.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;

public class City {
  // =================================================================================================================
  // Fields
  // =================================================================================================================
  private final Identifier id;
  private final Name       name;
  private final Post       post;

  // =================================================================================================================
  // Constructors
  // =================================================================================================================
  public City(final Identifier id, final Name name, final Post post) {
    this.post = checkNotNull(post, "Post cannot be null.");
    this.id = checkNotNull(id, "Id canot be null.");
    this.name = checkNotNull(name, "Name canot be null.");
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof City) {
      final City other = (City) obj;
      return Objects.equal(name, other.name);
    } else
      return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }

  // =================================================================================================================
  // Methods
  // =================================================================================================================
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
