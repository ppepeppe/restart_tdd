package io.hhplus.tdd.databse;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.entity.PointHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@SpringBootTest
public class PointHistoryTableTest {
    @Autowired
    private PointHistoryTable pointHistoryTable;

    @DisplayName("충전 포인트 내역을 등록한다.")
    @Test
    void insertWithChargePoint() {
        // given
        long userId = 1L;
        long amount = 1000L;
        // when
        PointHistory pointHistory = pointHistoryTable.insert(userId, amount, TransactionType.CHARGE, System.currentTimeMillis());
        // then
        assertThat(pointHistory.id()).isEqualTo(userId);
        assertThat(pointHistory.amount()).isEqualTo(amount);
        assertThat(pointHistory.type()).isEqualTo(TransactionType.CHARGE);

    }

    @DisplayName("사용 포인트 내역을 등록한다.")
    @Test
    void insertWithoutUsePoint() {
        // given
        long userId = 1L;
        long amount = 1000L;
        // when
        PointHistory pointHistory = pointHistoryTable.insert(userId, amount, TransactionType.USE, System.currentTimeMillis());
        // then
        assertThat(pointHistory.id()).isEqualTo(userId);
        assertThat(pointHistory.amount()).isEqualTo(amount);
        assertThat(pointHistory.type()).isEqualTo(TransactionType.USE);
    }

    @DisplayName("사용자 ID로 포인트 내역을 조회한다.")
    @Test
    void selectAllByUserId() {
        // given
        long userId = 1L;
        long anotherUserId = 2L;

        pointHistoryTable.insert(userId, 2000L, TransactionType.CHARGE, System.currentTimeMillis());
        pointHistoryTable.insert(userId, 1000L, TransactionType.USE, System.currentTimeMillis());
        pointHistoryTable.insert(anotherUserId, 1000L, TransactionType.USE, System.currentTimeMillis());

        // when
        List<PointHistory> pointHistoryList = pointHistoryTable.selectAllByUserId(userId);

        // then
        assertThat(pointHistoryList).hasSize(2)
                .extracting("userId", "amount", "type")
                .containsExactly(
                        tuple(userId, 2000L, TransactionType.CHARGE),
                        tuple(userId, 1000L, TransactionType.USE)
                );
    }
}
