package com.clouway.adapter.persistence;

import com.clouway.core.User;
import com.clouway.core.ConnectionProvider;
import com.clouway.core.UserRepository;
import com.google.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 26.05.16.
 */
public class PersistentUserRepository implements UserRepository {
  private ConnectionProvider connectionProvider;

  @Inject
  public PersistentUserRepository(ConnectionProvider connectionProvider) {
    this.connectionProvider = connectionProvider;
  }

  public void register(User user) {
    Connection connection = connectionProvider.get();
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement("INSERT INTO users (username,password,email) VALUES (?,?,?)");
      statement.setString(1, user.getUsername());
      statement.setString(2, user.getPassword());
      statement.setString(3, user.getEmail());
      statement.execute();
      statement.close();
    } catch (SQLException sql) {
      sql.printStackTrace();
    }
  }

  public User findByEmail(String email) {
    Connection connection = connectionProvider.get();
    User user = null;
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement("SELECT * FROM users WHERE email=?");
      statement.setString(1, email);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        user = new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("email"));
      }
      resultSet.close();
      statement.close();
    } catch (SQLException sql) {
      sql.printStackTrace();
    }
    return user;
  }

  public void deleteAll() {
    Connection connection = connectionProvider.get();
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement("DELETE FROM users");
      statement.execute();
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}

