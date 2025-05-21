package io.hhplus.tdd.point.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class UserPointTest {
    @DisplayName("포인트 잔고는 최소 잔고보다 작을 수 없다.")
    @Test
    void cannotLessThanMinPoint() {
        // given
        long userId = 1L;
        long point = -1L;
        long updateMillis = System.currentTimeMillis();
        // when & then
        assertThatThrownBy(() -> new UserPoint(userId, point, updateMillis))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잔고가 부족합니다.");
    }
    @DisplayName("포인트 잔고는 최대 잔고 보다 클 수 없다.")
    @Test
    void cannotGreaterThanMaxPoint() {
        long userId = 1L;
        long point = 1000001L;
        long updateMillis = System.currentTimeMillis();
        assertThatThrownBy(() -> new UserPoint(userId, point, updateMillis))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최대 잔고는 1,000,000 포인트 입니다.");
    }

}
