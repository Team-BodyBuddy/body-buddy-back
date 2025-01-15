package BodyBuddy.demo.global.common;

public enum InBodyStatus {
  UP, DOWN, SAME;

  public static InBodyStatus compare(Float current, Float previous) {
    if (current > previous) return UP;
    if (current < previous) return DOWN;
    return SAME;
  }
}
