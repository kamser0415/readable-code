package cleancode.studycafe.kms1st;

import java.util.List;

import cleancode.studycafe.kms1st.exception.AppException;
import cleancode.studycafe.kms1st.io.InputHandler;
import cleancode.studycafe.kms1st.io.OutputHandler;
import cleancode.studycafe.kms1st.io.StudyCafeFileHandler;
import cleancode.studycafe.kms1st.model.StudyCafeLockerPass;
import cleancode.studycafe.kms1st.model.StudyCafePass;
import cleancode.studycafe.kms1st.model.StudyCafePassType;

/*
리팩토링 1일차 3월 9일
 */
public class StudyCafePassMachine {

	private final InputHandler inputHandler = new InputHandler();
	private final OutputHandler outputHandler = new OutputHandler();
	// 이것도 추상화해야함
	private final StudyCafeFileHandler fileHandler = new StudyCafeFileHandler();

	public void run() {
		try {
			// 환영인사
			outputHandler.showWelcomeMessage();
			// 공지사항
			outputHandler.showAnnouncement();
			// 이용권 선택
			outputHandler.askPassTypeSelection();
			// 선택한 이용권 타입
			StudyCafePassType userActionType = inputHandler.getPassTypeSelectingUserAction();

			if (StudyCafePassType.HOURLY.equalsAt(userActionType)) {
				// 유저가 선택한 타입에 맞는 이용권 목록 가져오기
				List<StudyCafePass> hourlyPasses = getStudyCafePassAt(userActionType);
				// 이용권 목록 출력
				outputHandler.showPassListForSelection(hourlyPasses);
				// 선택한 이용권
				StudyCafePass selectedPass = inputHandler.getSelectPass(hourlyPasses);

				// 이용권 요약 출력
				outputHandler.showPassOrderSummary(selectedPass, null);
			} else if (StudyCafePassType.WEEKLY.equalsAt(userActionType)) {
				// 유저가 선택한 타입에 맞는 이용권 목록 가져오기
				List<StudyCafePass> weeklyPasses = getStudyCafePassAt(userActionType);

				// 이용권 목록 출력
				outputHandler.showPassListForSelection(weeklyPasses);

				// 선택한 이용권
				StudyCafePass selectedPass = inputHandler.getSelectPass(weeklyPasses);

				// 이용권 요약 출력
				outputHandler.showPassOrderSummary(selectedPass, null);
			} else if (StudyCafePassType.FIXED.equalsAt(userActionType)) {
				// 유저가 선택한 타입에 맞는 이용권 목록 가져오기
				List<StudyCafePass> fixedPasses = getStudyCafePassAt(userActionType);

				// 이용권 목록 출력
				outputHandler.showPassListForSelection(fixedPasses);

				// 선택한 이용권
				StudyCafePass selectedPass = inputHandler.getSelectPass(fixedPasses);

				// 사물함 선택 여부
				StudyCafeLockerPass lockerPass = getStudyCafeLockerPassAt(selectedPass);

				// 사물함이 없는 경우
				if (doesNotHaveLocker(lockerPass)) {
					outputHandler.showPassOrderSummary(selectedPass, null);
					return;
				}
				// 사물함을 사용할지 물어봄
				outputHandler.askLockerPass(lockerPass);

				// 사물함을 사용하지 않는 경우
				if (doesNotUsedLocker()) {
					outputHandler.showPassOrderSummary(selectedPass, null);
					return;
				}

				// 사물함을 사용하는 경우
				outputHandler.showPassOrderSummary(selectedPass, lockerPass);
			}
		} catch (AppException e) {
			outputHandler.showSimpleMessage(e.getMessage());
		} catch (Exception e) {
			outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
		}
	}

	private static boolean doesNotHaveLocker(StudyCafeLockerPass lockerPass) {
		return lockerPass == null;
	}

	private boolean doesNotUsedLocker() {
		return !inputHandler.getLockerSelection();
	}

	private StudyCafeLockerPass getStudyCafeLockerPassAt(StudyCafePass selectedPass) {
		List<StudyCafeLockerPass> lockerPasses = fileHandler.readLockerPasses();
		return lockerPasses.stream()
			.filter(option ->
				option.getPassType() == selectedPass.getPassType()
					&& option.getDuration() == selectedPass.getDuration()
			)
			.findFirst()
			.orElse(null);
	}

	private List<StudyCafePass> getStudyCafePassAt(StudyCafePassType hourly) {
		// 이용권 목록 읽어오기
		List<StudyCafePass> studyCafePasses = fileHandler.readStudyCafePasses();

		// 시간 단위 이용권만 필터링
		return studyCafePasses.stream()
			.filter(studyCafePass -> studyCafePass.getPassType() == hourly)
			.toList();
	}

}
