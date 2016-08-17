package com.clouway.persistence;

import com.clouway.adapter.http.PerRequestConnectionProvider;
import com.clouway.adapter.persistence.PersistentUserRepository;
import com.clouway.core.ConnectionProvider;
import com.clouway.core.User;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 03.06.16.
 */
public class PersistentUserRepositoryTest {
  private ConnectionProvider connectionProvider = new FakeJdbcConnectionProvider();
  private PersistentUserRepository userRepository = new PersistentUserRepository(connectionProvider);

  @Before
  public void cleanUp() {
    userRepository.deleteAll();
  }


  @Test
  public void registerNewUser() throws Exception {

    User user = new User("Georgi", "georgi", "gosho@abv.bg");
    userRepository.register(user);

    User expected = userRepository.findByEmail("gosho@abv.bg");

    assertThat(user, is(expected));
  }
}


