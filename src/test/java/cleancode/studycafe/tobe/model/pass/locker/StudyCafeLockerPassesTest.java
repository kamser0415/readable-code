package cleancode.studycafe.tobe.model.pass.locker;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;

class StudyCafeLockerPassesTest {

	@Test
	@DisplayName("좌석 이용권의 이용 기간과 종류가 같은 사물함 이용권을 하나 조회할 수 있다.")
	void findLockerPassBy() {
		// given
		StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 100, 0.1);
		StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 100);
		StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of(lockerPass));

		// when
		Optional<StudyCafeLockerPass> foundLockerPass = lockerPasses.findLockerPassBy(studyCafeSeatPass);

		// then
		Assertions.assertThat(foundLockerPass).isPresent();
		Assertions.assertThat(foundLockerPass.get()).isEqualTo(lockerPass);
	}

	@Test
	@DisplayName("사물함 이용권 목록이 비어있으면 사물함 이용권을 조회할 수 없다.")
	void findLockerPassBy0() {
		// given
		StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 100, 0.1);
		StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of());

		// when
		Optional<StudyCafeLockerPass> foundLockerPass = lockerPasses.findLockerPassBy(studyCafeSeatPass);

		// then
		Assertions.assertThat(foundLockerPass).isEmpty();
	}

	@Test
	@DisplayName("좌석 이용권의 이용 기간과 종류가 같은 사물함 이용권이 여러개라도 하나만 조회할 수 있다.")
	void findLockerPassBy4() {
		// given
		StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 100, 0.1);
		StudyCafeLockerPass lockerPass1 = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 100);
		StudyCafeLockerPass lockerPass2 = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 100);
		StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of(lockerPass1, lockerPass2));

		// when
		Optional<StudyCafeLockerPass> foundLockerPass = lockerPasses.findLockerPassBy(studyCafeSeatPass);

		// then
		Assertions.assertThat(foundLockerPass).isPresent();
	}

	@Test
	@DisplayName("좌석 이용권의 이용 기간과 사물함의 이용기간이 다르면 사물함 이용권을 조회할 수 없다.")
	void findLockerPassBy2() {
		// given
		StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 100, 0.1);
		StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 20, 100);
		StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of(lockerPass));

		// when
		Optional<StudyCafeLockerPass> foundLockerPass = lockerPasses.findLockerPassBy(studyCafeSeatPass);

		// then
		Assertions.assertThat(foundLockerPass).isEmpty();
	}

	@Test
	@DisplayName("이용기간은 같아도 이용권의 종류가 다르면 사물함 이용권을 조회할 수 없다.")
	void findLockerPassBy3() {
		// given
		StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 100, 0.1);
		StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.HOURLY, 30, 100);
		StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of(lockerPass));

		// when
		Optional<StudyCafeLockerPass> foundLockerPass = lockerPasses.findLockerPassBy(studyCafeSeatPass);

		// then
		Assertions.assertThat(foundLockerPass).isEmpty();
	}
}
