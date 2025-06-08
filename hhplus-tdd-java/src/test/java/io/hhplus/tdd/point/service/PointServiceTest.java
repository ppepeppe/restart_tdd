package io.hhplus.tdd.point.service;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.entity.PointHistory;
import io.hhplus.tdd.point.entity.UserPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class PointServiceTest {
    @Autowired
    private PointService pointService;
    @Autowired
    private UserPointTable userPointTable;
    @Autowired
    private PointHistoryTable pointHistoryTable;

    @DisplayName("사용자 ID로 포인트를 조회한다.")
    @Test
    void readPoint() {
        // given
        long userId = 1L;
        long amount = 1000L;
        userPointTable.insertOrUpdate(userId, amount);
        // when
        UserPoint userPoint = pointService.readPoint(userId);
        // then
        assertThat(userPoint.id()).isEqualTo(userId);
        assertThat(userPoint.point()).isEqualTo(amount);
    }

    @DisplayName("사용자 ID로 포인트를 충전합니다.")
    @Test
    void changePoint() {

        // given
        long userId = 1L;
        long amount = 1500L;
        userPointTable.insertOrUpdate(userId, 1000L);

        // when
        UserPoint userPoint = pointService.chargePoint(userId, amount);

        // then
        assertThat(userPoint.id()).isEqualTo(userId);
        assertThat(userPoint.point()).isEqualTo( 1000L + amount );

    }
    @DisplayName("사용자 ID로 포인트를 사용합니다.")
    @Test
    void usePoint() {

        // given
        long userId = 1L;
        long amount = 1500L;
        userPointTable.insertOrUpdate(userId, 2000L);

        // when
        UserPoint userPoint = pointService.usePoint(userId, amount);

        // then
        assertThat(userPoint.id()).isEqualTo(userId);
        assertThat(userPoint.point()).isEqualTo(2000 - amount);

    }
    @DisplayName("사용자 ID로 포인트 내역을 조회한다.")
    @Test
    void readPointHistories() {
        // given
        long userId = 1L;
        pointHistoryTable.insert(userId, 100_000L, TransactionType.CHARGE, System.currentTimeMillis());
        pointHistoryTable.insert(userId, -50_000L, TransactionType.USE, System.currentTimeMillis());
        //when
        List<PointHistory> histories = pointService.readPointHistories(userId);
        //then
        PointHistory usePoint = histories.get(0);
        assertThat(usePoint.userId()).isEqualTo(userId);
        assertThat(usePoint.amount()).isEqualTo(-50_000L);
        assertThat(usePoint.type()).isEqualTo(TransactionType.USE);

        PointHistory chargedPoint = histories.get(1);
        assertThat(chargedPoint.userId()).isEqualTo(userId);
        assertThat(chargedPoint.amount()).isEqualTo(100_000L);
        assertThat(chargedPoint.type()).isEqualTo(TransactionType.CHARGE);

    }

    @DisplayName("포인트 충전 시, 포인트 내역을 저장한다.")
    @Test
    void chargePointWithSavePointHistory() {
        // given
        long userId = 1L;
        long amount = 1000L;
        pointService.chargePoint(userId, amount);
        // when
        List<PointHistory> histories = pointService.readPointHistories(userId);
        // then
        PointHistory usePoint = histories.get(0);
        assertThat(usePoint.userId()).isEqualTo(userId);
        assertThat(usePoint.amount()).isEqualTo(amount);
        assertThat(usePoint.type()).isEqualTo(TransactionType.CHARGE);


    }

    @DisplayName("포인트 사용 시, 포인트 내역을 저장한다.")
    @Test
    void usePointWithSavePointHistory() {
        // given
        long userId = 1L;
        long amount = 1000L;
        userPointTable.insertOrUpdate(userId, 10_000L);
        pointService.usePoint(userId, amount);
        // when
        List<PointHistory> histories = pointService.readPointHistories(userId);
        // then
        PointHistory usePoint = histories.get(0);
        assertThat(usePoint.userId()).isEqualTo(userId);
        assertThat(usePoint.amount()).isEqualTo(amount);
        assertThat(usePoint.type()).isEqualTo(TransactionType.USE);


    }

    @Test
    void getGreeting 메서드는 Hello로 시작하는 인사 메세지를 반환한다
}
