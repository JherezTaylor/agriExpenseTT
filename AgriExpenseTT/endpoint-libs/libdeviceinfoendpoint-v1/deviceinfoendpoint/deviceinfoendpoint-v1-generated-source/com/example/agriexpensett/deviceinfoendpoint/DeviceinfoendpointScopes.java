/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-07-22 21:53:01 UTC)
 * on 2014-08-05 at 21:13:34 UTC 
 * Modify at your own risk.
 */

package com.example.agriexpensett.deviceinfoendpoint;

/**
 * Available OAuth 2.0 scopes for use with the deviceinfoendpoint.
 *
 * @since 1.4
 */
public class DeviceinfoendpointScopes {

  /** View your email address. */
  public static final String USERINFO_EMAIL = "https://www.googleapis.com/auth/userinfo.email";

  /**
   * Returns an unmodifiable set that contains all scopes declared by this class.
   *
   * @since 1.16
   */
  public static java.util.Set<String> all() {
    java.util.Set<String> set = new java.util.HashSet<String>();
    set.add(USERINFO_EMAIL);
    return java.util.Collections.unmodifiableSet(set);
  }

  private DeviceinfoendpointScopes() {
  }
}
