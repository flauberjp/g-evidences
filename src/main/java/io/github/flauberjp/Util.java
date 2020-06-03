package io.github.flauberjp;

public class Util {
  public static boolean isRunningFromJar() {
    return Util
        .class
        .getResource("Util.class")
        .toString()
        .startsWith("jar:");
  }
}
