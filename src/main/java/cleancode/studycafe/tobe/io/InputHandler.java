package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;

import java.util.List;
import java.util.Scanner;

public class InputHandler {

    private final Scanner scanner;

	public InputHandler(Scanner scanner) {
		this.scanner = scanner;
	}
	/*
	TODO-10 테스트가 필요하다고 생각하는 메서드
	사용자의 입력한 대로 원하는 좌석 이용권으로 변환되는지 확인한다.
	그런데 사용자가 잘못된 입력을 하는 경우에는 어떻게 처리할 것인지도 확인한다.

	저 부분을 테스트 편하게 하기 위해서 분리를 한다면 public으로 선언해야한다.
	public으로 선언하면 테스트하기 편하다.
	public으로 선언하면 다른 곳에서도 사용한다.
	 */
    public StudyCafePassType getPassTypeSelectingUserAction() {
        String userInput = scanner.nextLine();

        if ("1".equals(userInput)) {
            return StudyCafePassType.HOURLY;
        }
        if ("2".equals(userInput)) {
            return StudyCafePassType.WEEKLY;
        }
        if ("3".equals(userInput)) {
            return StudyCafePassType.FIXED;
        }
        throw new AppException("잘못된 입력입니다.");
    }

	/*
	TODO-10 테스트가 필요하다고 생각하는 메서드
	사용자의 입력한 대로 원하는 좌석 이용권으로 변환되는지 확인한다.
	사용자가 잘못된 입력을 하는 경우에는 어떻게 처리할 것인지도 확인한다.
	 */
    public StudyCafeSeatPass getSelectPass(List<StudyCafeSeatPass> passes) {
        String userInput = scanner.nextLine();
        int selectedIndex = Integer.parseInt(userInput) - 1;
        return passes.get(selectedIndex);
    }

    public boolean getLockerSelection() {
        String userInput = scanner.nextLine();
        return "1".equals(userInput);
    }

}
