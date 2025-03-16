package cleancode.studycafe.tobe.io;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;

class InputHandlerTest {

	@Test
	@DisplayName("사용자는 콘솔에 1을 입력하면 시간 이용권이 선택된다.")
	void selectByStringOne() {
		// given
		final String userInputAction = "1";
		InputHandler inputHandler = createInputHandlerFor(userInputAction);

		// when
		StudyCafePassType userActionCafePassType = inputHandler.getPassTypeSelectingUserAction();

		// then
		Assertions.assertThat(userActionCafePassType).isEqualTo(StudyCafePassType.HOURLY);
	}

	@Test
	@DisplayName("사용자는 콘솔에 2을 입력하면 주 단위 이용권이 선택된다.")
	void selectByStringTwo() {
		// given
		final String userInputAction = "2";
		InputHandler inputHandler = createInputHandlerFor(userInputAction);

		// when
		StudyCafePassType userActionCafePassType = inputHandler.getPassTypeSelectingUserAction();

		// then
		Assertions.assertThat(userActionCafePassType).isEqualTo(StudyCafePassType.WEEKLY);
	}

	@Test
	@DisplayName("사용자는 콘솔에 3을 입력하면 고정 이용석 이용권이 선택된다.")
	void selectByStringThree() {
		// given
		final String userInputAction = "3";
		InputHandler inputHandler = createInputHandlerFor(userInputAction);

		// when
		StudyCafePassType userActionCafePassType = inputHandler.getPassTypeSelectingUserAction();

		// then
		Assertions.assertThat(userActionCafePassType).isEqualTo(StudyCafePassType.FIXED);
	}

	@Test
	@DisplayName("사용자는 콘솔에 1,2,3을 제외하고 다른 값을 입력하면 오류가 발생한다.")
	void selectByStringWithOut123() {
		// given
		final String userInputAction = "4";
		InputHandler inputHandler = createInputHandlerFor(userInputAction);

		// when & then
		Assertions.assertThatThrownBy(inputHandler::getPassTypeSelectingUserAction)
			.isInstanceOf(AppException.class)
			.hasMessage("잘못된 입력입니다.");
	}

	@Test
	@DisplayName("사용자는 콘솔에 공백을 입력후 엔터를 누르면 오류가 발생한다.")
	void selectByStringWithWhitespace() {
		// given
		final String userInputAction = "";
		InputHandler inputHandler = createInputHandlerFor(userInputAction);

		// when & then
		Assertions.assertThatThrownBy(inputHandler::getPassTypeSelectingUserAction)
			.isInstanceOf(AppException.class)
			.hasMessage("잘못된 입력입니다.");
	}

	@Test
	@DisplayName("사용자는 좌석 이용권 목록 번호인 1을 입력하면 첫번째 좌석 이용권이 선택된다.")
	void selectPassByStringOne() {
		// given
		final String userInputAction = "1";
		InputHandler inputHandler = createInputHandlerFor(userInputAction);
		StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 1, 1000, 0);
		List< StudyCafeSeatPass> seatPasses = List.of(
			studyCafeSeatPass
		);

		// when
		StudyCafeSeatPass userActionCafePassType = inputHandler.getSelectPass(seatPasses);

		// then
		Assertions.assertThat(userActionCafePassType).isEqualTo(studyCafeSeatPass);
	}

	@Test
	@DisplayName("사용자는 콘솔에 없는 번호를 입력하면 오류가 발생한다.")
	void selectPassByStringTwo() {
		// given
		final String userInputAction = "ㅁ";
		InputHandler inputHandler = createInputHandlerFor(userInputAction);
		StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 1, 1000, 0);
		List< StudyCafeSeatPass> seatPasses = List.of(
			studyCafeSeatPass
		);

		// when & then
		Assertions.assertThatThrownBy(() -> inputHandler.getSelectPass(seatPasses))
			.isInstanceOf(RuntimeException.class);
	}

	private InputHandler createInputHandlerFor(String userInputAction) {
		String userInput = userInputAction + "\n";
		return new InputHandler(new Scanner(new ByteArrayInputStream(userInput.getBytes())));
	}

}
