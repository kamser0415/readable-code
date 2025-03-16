package cleancode.studycafe.tobe.model.pass;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyCafeSeatPassTest {

	@Test
	@DisplayName("할인율이 1이면 할인되는 금액은 이용권 금액과 같다")
	void calculatePrice() {
		// given
		final StudyCafePassType userActionPassType = StudyCafePassType.FIXED;
		final double discountRate = 1;
		int passPrice = 100;
		StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(userActionPassType, 30, passPrice, discountRate);

		// when
		int discountPrice = studyCafeSeatPass.getDiscountPrice();

		// then
		Assertions.assertThat(discountPrice).isEqualTo(passPrice);
	}

	@Test
	@DisplayName("할인율이 0.1이면 할인되는 금액은 10원이다.")
	void calculatePrice2() {
		// given
		final StudyCafePassType userActionPassType = StudyCafePassType.FIXED;
		final double discountRate = 0.1;
		StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(userActionPassType, 30, 100, discountRate);

		// when
		int discountPrice = studyCafeSeatPass.getDiscountPrice();

		// then
		Assertions.assertThat(discountPrice).isEqualTo(10);
	}

	@Test
	@DisplayName("할인 금액은 소수점은 버린다.")
	void calculatePrice3() {
		// given
		final StudyCafePassType userActionPassType = StudyCafePassType.FIXED;
		final double discountRate = 0.1;
		StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(userActionPassType, 30, 101, discountRate);

		// when
		int discountPrice = studyCafeSeatPass.getDiscountPrice();

		// then
		Assertions.assertThat(discountPrice).isEqualTo(10);
		Assertions.assertThat(discountPrice).isNotEqualTo(10.1);
	}

	@Test
	@DisplayName("할인률이 1 초과하면 할인금액은 이용권 금액보다 크다.")
	void calculatePrice4() {
		// given
		final StudyCafePassType userActionPassType = StudyCafePassType.FIXED;
		final double discountRate = 1.01;
		final int passPrice = 100;
		StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(userActionPassType, 30, passPrice, discountRate);

		// when
		int discountPrice = studyCafeSeatPass.getDiscountPrice();

		// then
		Assertions.assertThat(discountPrice).isGreaterThan(passPrice);
	}

	@Test
	@DisplayName("이용권 금액이 음수이면 할인금액도 음수이다.")
	void calculatePrice5() {
		// given
		final StudyCafePassType userActionPassType = StudyCafePassType.FIXED;
		final double discountRate = 0.1;
		final int passPrice = -100;
		StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(userActionPassType, 30, passPrice, discountRate);

		// when
		int discountPrice = studyCafeSeatPass.getDiscountPrice();

		// then
		Assertions.assertThat(discountPrice).isLessThan(0);
	}


}
