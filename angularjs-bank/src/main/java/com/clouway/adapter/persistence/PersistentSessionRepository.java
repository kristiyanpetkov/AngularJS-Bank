package com.clouway.adapter.persistence;

import com.clouway.core.ConnectionProvider;
import com.clouway.core.Session;
import com.clouway.core.SessionRepository;
import com.google.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 02.06.16.
 */
public class PersistentSessionRepository implements SessionRepository {
  private ConnectionProvider connectionProvider;

  @Inject
  public PersistentSessionRepository(ConnectionProvider connectionProvider) {
    this.connectionProvider = connectionProvider;
  }

  public void create(Session session) {
    Connection connection = connectionProvider.get();
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement("INSERT INTO session (email,sessionID,expirationTime) VALUES (?,?,?)");
      statement.setString(1, session.email);
      statement.setString(2, session.sessionId);
      statement.setLong(3, System.currentTimeMillis()+5*60*1000);
      statement.execute();
    } catch (SQLException sqlExc) {
      sqlExc.printStackTrace();
    } finally {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public Session get(String sessionID) {
    Session session = null;
    Connection connection = connectionProvider.get();
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement("SELECT * FROM session WHERE sessionID=?");
      statement.setString(1, sessionID);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        session = new Session(resultSet.getString("email"), resultSet.getString("sessionID"));
      }
      resultSet.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return session;
  }

  public void delete(String sessionID) {
    Connection connection = connectionProvider.get();
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement("DELETE FROM session WHERE sessionID=?");
      statement.setString(1, sessionID);
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void deleteAll() {
    Connection connection = connectionProvider.get();
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement("DELETE FROM session");
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void cleanExpired() {
    Connection connection = connectionProvider.get();
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement("DELETE FROM session WHERE expirationTime < " + System.currentTimeMillis());
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void refreshSessionTime(Session session) {
    Connection connection = connectionProvider.get();
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement("UPDATE session SET expirationTime=? WHERE email=? AND sessionID=?");
      statement.setLong(1, System.currentTimeMillis() + 5 * 60 * 1000 );
      statement.setString(2, session.email);
      statement.setString(3, session.sessionId);
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public Integer getActiveSessions() {
    Connection connection = connectionProvider.get();
    ResultSet resultSet = null;
    PreparedStatement statement;
    Integer counter = null;
    try {
      statement = connection.prepareStatement("SELECT COUNT(*) FROM session");
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        counter = resultSet.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return counter;
  }

  public String getCurrentUserEmail(String sessionId) {
    Connection connection = connectionProvider.get();
    PreparedStatement statement = null;
    String email = null;
    try {
      statement = connection.prepareStatement("SELECT email FROM session WHERE sessionID=?");
      statement.setString(1, sessionId);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        email = resultSet.getString("email");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return email;
  }
}
