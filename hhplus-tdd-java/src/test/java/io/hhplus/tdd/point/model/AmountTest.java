package io.hhplus.tdd.point.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AmountTest {
    // given  >> 주어진 값
    // when  >> 어떤 메소드에서 테스트가 일어나느지
    // then  >> 테스트결과
    @DisplayName("금액은 0 보다 커야 한다.")
    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    void ofIfNotGreaterThenZero(long value) {
        // when & then
        assertThatThrownBy(() -> Amount.of(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("금액은 0보다 커야합니다.");
    }

    @Test
    @DisplayName("금액을 생성한다.")
    void of() {
        // given
        long value = 1000L;
        // when
        Amount amount = Amount.of(value);

        // then
        long result = amount.getValue();
        assertThat(result).isEqualTo(value);
    }
}
