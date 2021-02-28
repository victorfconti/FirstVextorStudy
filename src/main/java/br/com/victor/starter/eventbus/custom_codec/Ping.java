package br.com.victor.starter.eventbus.custom_codec;

import java.util.Objects;

public class Ping {
  private String message;
  private Boolean enabled;

  public Ping() {
  }

  public Ping(String message, Boolean enabled) {
    this.message = message;
    this.enabled = enabled;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Ping ping = (Ping) o;
    return Objects.equals(message, ping.message) && Objects.equals(enabled, ping.enabled);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, enabled);
  }

  @Override
  public String toString() {
    return "Pong{" +
      "message='" + message + '\'' +
      ", enabled=" + enabled +
      '}';
  }
}
