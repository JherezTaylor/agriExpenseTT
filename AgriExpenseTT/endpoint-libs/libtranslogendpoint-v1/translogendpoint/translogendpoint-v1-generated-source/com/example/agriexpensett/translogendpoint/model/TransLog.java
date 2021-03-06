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
 * on 2014-08-17 at 00:58:49 UTC 
 * Modify at your own risk.
 */

package com.example.agriexpensett.translogendpoint.model;

/**
 * Model definition for TransLog.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the translogendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class TransLog extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String account;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key key;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String keyrep;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String operation;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer rowId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String tableKind;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long transTime;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAccount() {
    return account;
  }

  /**
   * @param account account or {@code null} for none
   */
  public TransLog setAccount(java.lang.String account) {
    this.account = account;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public TransLog setId(java.lang.Integer id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Key getKey() {
    return key;
  }

  /**
   * @param key key or {@code null} for none
   */
  public TransLog setKey(Key key) {
    this.key = key;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getKeyrep() {
    return keyrep;
  }

  /**
   * @param keyrep keyrep or {@code null} for none
   */
  public TransLog setKeyrep(java.lang.String keyrep) {
    this.keyrep = keyrep;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getOperation() {
    return operation;
  }

  /**
   * @param operation operation or {@code null} for none
   */
  public TransLog setOperation(java.lang.String operation) {
    this.operation = operation;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getRowId() {
    return rowId;
  }

  /**
   * @param rowId rowId or {@code null} for none
   */
  public TransLog setRowId(java.lang.Integer rowId) {
    this.rowId = rowId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTableKind() {
    return tableKind;
  }

  /**
   * @param tableKind tableKind or {@code null} for none
   */
  public TransLog setTableKind(java.lang.String tableKind) {
    this.tableKind = tableKind;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getTransTime() {
    return transTime;
  }

  /**
   * @param transTime transTime or {@code null} for none
   */
  public TransLog setTransTime(java.lang.Long transTime) {
    this.transTime = transTime;
    return this;
  }

  @Override
  public TransLog set(String fieldName, Object value) {
    return (TransLog) super.set(fieldName, value);
  }

  @Override
  public TransLog clone() {
    return (TransLog) super.clone();
  }

}
