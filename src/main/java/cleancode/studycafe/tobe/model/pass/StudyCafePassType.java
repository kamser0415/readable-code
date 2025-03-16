package cleancode.studycafe.tobe.model.pass;

import java.util.Set;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권"),
    WEEKLY("주 단위 이용권"),
    FIXED("1인 고정석");

    private static final Set<StudyCafePassType> LOCKER_TYPES = Set.of(StudyCafePassType.FIXED);

    private final String description;

    StudyCafePassType(String description) {
        this.description = description;
    }
	/*
	TODO-5 테스트가 필요하다고 생각하는 메서드
	사물함 이용권을 사용할 수 있는지 확인되어야 사물함 결제시스템으로 넘어갈 수 있으므로 확인해야한다.
	변경사항이 생길 경우에도 추가로 관리해야하는 대상이다.
	 */
    public boolean isLockerType() {
        return LOCKER_TYPES.contains(this);
    }
	/*
	TODO-6 테스트가 필요하다고 생각하는 메서드
	사물함 이용권을 사용할 수 있는지 확인되어야 사물함 결제시스템으로 넘어갈 수 있으므로 확인해야한다.
	변경사항이 생길 경우에도 추가로 관리해야하는 대상이다.
	 */
    public boolean isNotLockerType() {
        return !isLockerType();
    }

}
