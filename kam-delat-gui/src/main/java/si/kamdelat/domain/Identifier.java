/**
 * Copyright 2012 Rene Svetina
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package si.kamdelat.domain;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.util.concurrent.ExecutionError;

/**
 * Class for DB Identity.
 * 
 * @author Rene Svetina
 */
public final class Identifier implements Serializable {
  private static final long                      serialVersionUID  = 6346749479769179997L;
  private static final int                       CACHE_EXPIRE_TIME = 10;
  private static final long                      CACHE_SIZE        = 10000L;
  private static final HashFunction              HF                = Hashing.sha1();
  private static final Cache<String, Identifier> CACHE;
  private static final Pattern                   HASH_PATTERN;
  private final String                           hash;

  static {
    CACHE = CacheBuilder.newBuilder().maximumSize(Identifier.CACHE_SIZE).expireAfterAccess(Identifier.CACHE_EXPIRE_TIME, TimeUnit.MINUTES).build();
    HASH_PATTERN = Pattern.compile("[A-Fa-f0-9]{40}");
  }

  private Identifier() {
    final UUID uuid = UUID.randomUUID();
    final Hasher hasher = Identifier.HF.newHasher();
    hasher.putString(uuid.toString());
    hash = hasher.hash().toString();
  }

  private Identifier(final String hash) {
    super();
    this.hash = hash;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof Identifier) {
      final Identifier other = (Identifier) obj;
      return Objects.equal(hash, other.hash);
    }
    else
      return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(hash);
  }

  @Override
  public String toString() {
    return hash;
  }

  public static Identifier newId() {
    final Identifier result = new Identifier();
    Identifier.CACHE.put(result.toString(), result);
    return result;
  }

  public static Identifier valueOf(final String hash) {
    final Matcher hashMatcher = Identifier.HASH_PATTERN.matcher(Preconditions.checkNotNull(hash, "Hash cannot be null."));
    Preconditions.checkArgument(hashMatcher.matches(), String.format("%s: does not matches predefined pattern [A-Fa-f0-9]{64}", hash));

    try {
      return Identifier.CACHE.get(hash, new Callable<Identifier>() {

        @Override
        public Identifier call() throws Exception {
          return new Identifier(hash);
        }

      });
    } catch (final ExecutionException e) {
      throw new ExecutionError(new Error("Could not create Id", e));
    }
  }
}
