package si.kamdelat.domain;

import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class Restaurant {
  private final Identifier id;
  private final Name       name;
  private final City       city;
  private final Set<Visit> visits;

  public Restaurant(final Identifier id, final Name name, final City city) {
    this.city = Preconditions.checkNotNull(city, "City cannot be null");
    this.id = Preconditions.checkNotNull(id, "Id canot be null.");
    this.name = Preconditions.checkNotNull(name, "Name canot be null.");
    visits = Sets.newHashSet();
  }

  public void addVisit(final Visit visit) {
    Preconditions.checkArgument(!visits.contains(visit), "Cannot have 2 equal visits.");
    visits.add(visit);
  }

  public City city() {
    return city;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof Restaurant) {
      final Restaurant other = (Restaurant) obj;
      return Objects.equal(city, other.city) && Objects.equal(name, other.name);
    }
    else
      return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(city, name);
  }

  public Identifier id() {
    return id;
  }

  public Set<Visit> listVisits() {
    return ImmutableSet.copyOf(visits);
  }

  public Name name() {
    return name;
  }

  public void removeVisit(final Visit visit) {
    visits.remove(visit);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).add("id", id).add("name", name).add("city", city).add("visits", visits).toString();
  }
}
