package cleancode.studycafe.kms1st;

import java.util.List;

import cleancode.studycafe.kms1st.exception.AppException;
import cleancode.studycafe.kms1st.io.InputHandler;
import cleancode.studycafe.kms1st.io.OutputHandler;
import cleancode.studycafe.kms1st.io.StudyCafeFileHandler;
import cleancode.studycafe.kms1st.model.StudyCafeLockerPass;
import cleancode.studycafe.kms1st.model.StudyCafeLockerPasses;
import cleancode.studycafe.kms1st.model.StudyCafePass;
import cleancode.studycafe.kms1st.model.StudyCafePassType;
import cleancode.studycafe.kms1st.model.StudyCafePasses;

/*
리팩토링 1일차 3월 9일
 */
public class StudyCafePassMachine {

	private final InputHandler inputHandler = new InputHandler();
	private final OutputHandler outputHandler = new OutputHandler();
	private final StudyCafeFileHandler fileHandler = new StudyCafeFileHandler();

	public void run() {
		try {
			showInitializedComment();

			StudyCafePassType userActionType = getStudyCafePassTypeUserAction();

			List<StudyCafePass> userActionCafePasses = getUserActionCafePassesAt(userActionType);

			StudyCafePass selectedPass = getUserActionPassAt(userActionCafePasses);

			StudyCafeLockerPass lockerPass = getStudyCafeLockerPassAt(selectedPass);

			if (doesNotHaveLocker(lockerPass)) {
				outputHandler.showPassOrderSummary(selectedPass, null);
				return;
			}
			outputHandler.showPassOrderSummary(selectedPass, lockerPass);
		} catch (AppException e) {
			outputHandler.showSimpleMessage(e.getMessage());
		} catch (Exception e) {
			outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
		}
	}

	private StudyCafePass getUserActionPassAt(List<StudyCafePass> userActionCafePasses) {
		// 이용권 목록 출력
		outputHandler.showPassListForSelection(userActionCafePasses);
		// 선택한 이용권
		return inputHandler.getSelectPass(userActionCafePasses);

}
	private List<StudyCafePass> getUserActionCafePassesAt(StudyCafePassType userActionType) {
		StudyCafePasses studyCafePasses = fileHandler.readStudyCafePassesV2();
		return studyCafePasses.extractPasses(userActionType);
	}

	private StudyCafePassType getStudyCafePassTypeUserAction() {
		// 이용권 선택을 물어본다.
		outputHandler.askPassTypeSelection();
		// 선택한 이용권 타입
		return inputHandler.getPassTypeSelectingUserAction();
	}

	private void showInitializedComment() {
		// 환영인사
		outputHandler.showWelcomeMessage();
		// 공지사항
		outputHandler.showAnnouncement();
	}

	private static boolean doesNotHaveLocker(StudyCafeLockerPass lockerPass) {
		return lockerPass == null;
	}

	private StudyCafeLockerPass getStudyCafeLockerPassAt(StudyCafePass selectedPass) {
		StudyCafeLockerPasses lockerPasses = fileHandler.readLockerPasses();
		StudyCafeLockerPass usedLockerPass = lockerPasses.findUsedLockerPass(selectedPass);
		outputHandler.askLockerPass(usedLockerPass);
		boolean lockerSelection = inputHandler.getLockerSelection();
		if (lockerSelection) {
			return usedLockerPass;
		}
		return null;
	}

}
