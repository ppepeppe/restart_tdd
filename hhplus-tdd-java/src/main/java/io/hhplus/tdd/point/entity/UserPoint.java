package io.hhplus.tdd.point.entity;

import java.util.concurrent.atomic.AtomicLong;

public record UserPoint(
        long id,
        long point,
        long updateMillis
) {
    private static final long MIN_POINT = 0;
    private static final long MAX_POINT = 1000000L;

    public static UserPoint empty(long id) {
        return new UserPoint(id, 0, System.currentTimeMillis());
    }
//
//    public UserPoint () {
//        if (point < MAX_POINT) {
//
//        }
//    }
}
