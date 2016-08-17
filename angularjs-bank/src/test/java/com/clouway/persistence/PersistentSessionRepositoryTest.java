package com.clouway.persistence;

import com.clouway.adapter.persistence.PersistentSessionRepository;
import com.clouway.core.ConnectionProvider;
import com.clouway.core.Session;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 06.06.16.
 */
public class PersistentSessionRepositoryTest {
  private ConnectionProvider connectionProvider = new FakeJdbcConnectionProvider();
  private PersistentSessionRepository sessionRepository = new PersistentSessionRepository(connectionProvider);

  @Before
  public void cleanUp() {
    sessionRepository.deleteAll();
  }

  @Test
  public void create() throws Exception {
    Session session = new Session("admin@abv.bg", "24-15-2135-135-316-1515");
    sessionRepository.create(session);

    Session expected = sessionRepository.get("24-15-2135-135-316-1515");

    assertThat(expected, is(session));
  }

  @Test
  public void deleteById() throws Exception {
    String sessionID = "24-15-2135-135-316-1515";
    sessionRepository.delete(sessionID);
    Session actual = sessionRepository.get("24-15-2135-135-316-1515");
    assertThat(null, is(actual));
  }

  @Test
  public void cleanExpiredSessionId() throws Exception {
    Session session = new Session("admin@abv.bg", "24-15-2135-135-316-1515");
    insertExpiredSession(session.email, session.sessionId);
    sessionRepository.cleanExpired();
    assertThat(null, is(sessionRepository.get("24-15-2135-135-316-1515")));
  }

  @Test
  public void getCurrentUserEmail() throws Exception {
    Session session = new Session("admin@abv.bg", "24-15-2135-135-316-1515");
    sessionRepository.create(session);
    String actual = session.email;
    String expected = sessionRepository.getCurrentUserEmail(session.sessionId);
    assertThat(actual, is(expected));
  }

  @Test
  public void refreshSessionTime() throws Exception {
    Session session = new Session("admin@abv.bg", "24-15-2135-135-316-1515");
    insertExpiredSession(session.email, session.sessionId);
    sessionRepository.refreshSessionTime(session);
    sessionRepository.cleanExpired();
    Session expected = sessionRepository.get(session.sessionId);
    assertThat(session, is(expected));
  }

  private void insertExpiredSession(String email, String sessionId) {
    Connection connection = connectionProvider.get();
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement("INSERT INTO session (email,sessionID,expirationTime) VALUES (?,?,?)");
      statement.setString(1, email);
      statement.setString(2, sessionId);
      statement.setLong(3, System.currentTimeMillis() - 5 * 60 * 1000);
      statement.execute();
      statement.close();
    } catch (SQLException sqlExc) {
      sqlExc.printStackTrace();
    }
  }
}
