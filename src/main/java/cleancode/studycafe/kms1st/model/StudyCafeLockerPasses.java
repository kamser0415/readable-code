package cleancode.studycafe.kms1st.model;

import java.util.ArrayList;
import java.util.List;

public class StudyCafeLockerPasses {

	private final List<StudyCafeLockerPass> lockerPasses;

	private StudyCafeLockerPasses(List<StudyCafeLockerPass> lockerPasses) {
		this.lockerPasses = lockerPasses;
	}

	public static StudyCafeLockerPasses of(List<StudyCafeLockerPass> lockerPasses) {
		return new StudyCafeLockerPasses(lockerPasses);
	}

	public StudyCafeLockerPass findUsedLockerPass(StudyCafePass passType) {
		List<StudyCafeLockerPass> passes = new ArrayList<>(lockerPasses);
		return passes.stream()
			.filter(
				option -> option.getPassType() == passType.getPassType()
												&& option.getDuration() == passType.getDuration())
			.findFirst()
			.orElse(null);
	}
}
