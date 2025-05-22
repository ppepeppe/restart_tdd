package io.hhplus.tdd.databse;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.entity.UserPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class UserPointTableTest {
    @Autowired
    private UserPointTable userPointTable;

    @DisplayName("포인트 잔고가 부족하다.")
    @Test
    void insertOrUpdateWithInsufficientPoint() {
        // given
        long userId = 1L;
        long amount = -1L;
        // when & then
        assertThatThrownBy(() -> userPointTable.insertOrUpdate(userId, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잔고가 부족합니다.");
    }

    @DisplayName("포인트 최대 잔고를 넘기면 안된다.")
    @Test
    void insertOrUpdateWithExceedMaxPoint() {
        // given
        long userId = 1L;
        long amount = 1000001L;

        // when & then
        assertThatThrownBy(() -> userPointTable.insertOrUpdate(userId, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최대 잔고는 1,000,000 포인트 입니다.");
    }

    @DisplayName("포인트 잔고를 등록 및 수정 한다.")
    @Test
    void insertOrUpdateForChargePoint() {
        // given
        long userId = 1L;
        long amount = 1000L;

        // when
        UserPoint userPoint = userPointTable.insertOrUpdate(userId, amount);
        // then
        assertThat(userPoint.id()).isEqualTo(userId);
        assertThat(userPoint.point()).isEqualTo(amount);
    }

    @DisplayName("포인트 잔고를 사용자 ID로 조회한다.")
    @Test
    void selectById() {
        // given
        long userId = 1L;
        long amount = 1000L;
        userPointTable.insertOrUpdate(userId, amount);
        // when
        UserPoint userPoint = userPointTable.selectById(userId);
        // then
        assertThat(userPoint.id()).isEqualTo(userId);
        assertThat(userPoint.point()).isEqualTo(amount);
    }
}
