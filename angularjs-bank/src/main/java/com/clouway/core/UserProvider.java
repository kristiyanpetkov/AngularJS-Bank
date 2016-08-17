package com.clouway.core;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 14.09.16.
 */
public interface UserProvider {

  CurrentUser getCurrentUser(HttpServletRequest request);
}
