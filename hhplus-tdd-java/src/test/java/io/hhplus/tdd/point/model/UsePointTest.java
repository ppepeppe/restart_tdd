package io.hhplus.tdd.point.model;

import io.hhplus.tdd.point.TransactionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UsePointTest {
    @DisplayName("사용 포인트를 생성한다.")
    @Test
    void createUsePoint() {
        // given
        long value = 1000L;
        Amount amount = new Amount(value);
        // when
        UsePoint usePoint = UsePoint.of(amount);
        // then
        assertThat(usePoint.getAmount()).isEqualTo(-value);
        assertThat(usePoint.getTransactionType()).isEqualTo(TransactionType.USE);
    }
}
