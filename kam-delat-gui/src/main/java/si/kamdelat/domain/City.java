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

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (obj == this) return true;
    if (obj == null) return false;
    if (!getClass().equals(obj.getClass())) return false;

    final City other = (City) obj;

    return Objects.equal(name, other.name);
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
