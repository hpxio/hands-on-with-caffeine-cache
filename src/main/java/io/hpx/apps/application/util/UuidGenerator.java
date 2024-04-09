package io.hpx.apps.application.util;

import java.util.UUID;

public class UuidGenerator {

  public static String generate() {
    return UUID.randomUUID().toString();
  }
}
