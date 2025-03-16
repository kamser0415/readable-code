package cleancode.studycafe.tobe.model.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;

class StudyCafePassOrderTest {

	@Test
	@DisplayName("할인이 없고, 고정 이용권과 사물함 이용권을 선택하면 두 이용권의 가격을 합산한 가격이 나온다.")
	void calculatePrice() {
		// given
		final StudyCafePassType userActionPassType = StudyCafePassType.FIXED;
		final double discountRate = 0.0;
		StudyCafeSeatPass studyCafeSeatPass = createSeatPassFrom(userActionPassType, 200, discountRate);
		StudyCafeLockerPass lockerPass = createLockerPassFrom(userActionPassType, 100);
		StudyCafePassOrder passOrder = StudyCafePassOrder.of(studyCafeSeatPass, lockerPass);

		// when
		int price = passOrder.getTotalPrice();

		// then
		Assertions.assertThat(price).isEqualTo(300);
	}

	@Test
	@DisplayName("할인이 있고, 고정 이용권과 사물함 이용권을 선택하면 두 이용권의 가격을 합산한 가격에서 할인율을 적용한 가격이 나온다.")
	void calculatePriceWithDiscount() {
		// given
		final StudyCafePassType userActionPassType = StudyCafePassType.FIXED;
		final double discountRate = 0.1;
		StudyCafeSeatPass studyCafeSeatPass = createSeatPassFrom(userActionPassType, 200, discountRate);
		StudyCafeLockerPass lockerPass = createLockerPassFrom(userActionPassType, 100);
		StudyCafePassOrder passOrder = StudyCafePassOrder.of(studyCafeSeatPass, lockerPass);

		// when
		int price = passOrder.getTotalPrice();

		// then
		Assertions.assertThat(price).isEqualTo(280);
	}

	@Test
	@DisplayName("할인없이 시간제 이용권을 선택하면 할인이 적용안된 시간제 이용권의 가격이 나온다.")
	void calculatePriceWithDiscountForHourlyPass() {
		// given
		final StudyCafePassType userActionPassType = StudyCafePassType.HOURLY;
		final double discountRate = 0.0;
		StudyCafeSeatPass studyCafeSeatPass = createSeatPassFrom(userActionPassType, 200, discountRate);
		StudyCafePassOrder passOrder = StudyCafePassOrder.of(studyCafeSeatPass, null);

		// when
		int price = passOrder.getTotalPrice();

		// then
		Assertions.assertThat(price).isEqualTo(200);
	}


	private StudyCafeSeatPass createSeatPassFrom(StudyCafePassType studyCafePassType, int price, double discountRate) {
		return StudyCafeSeatPass.of(studyCafePassType, 1, price, discountRate);
	}

	private StudyCafeLockerPass createLockerPassFrom(StudyCafePassType studyCafePassType, int price) {
		return StudyCafeLockerPass.of(studyCafePassType, 1, price);
	}


}
