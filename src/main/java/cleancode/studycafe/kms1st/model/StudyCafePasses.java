package cleancode.studycafe.kms1st.model;

import java.util.ArrayList;
import java.util.List;

public class StudyCafePasses {
	private final List<StudyCafePass> passes;

	private StudyCafePasses(List<StudyCafePass> passes) {
		this.passes = new ArrayList<>(passes);
	}

	public static StudyCafePasses of(List<StudyCafePass> passes) {
		return new StudyCafePasses(passes);
	}

	public List<StudyCafePass> extractPasses(StudyCafePassType userActionType) {
		return passes.stream()
			.filter(pass -> pass.getPassType().equalsAt(userActionType))
			.toList();
	}
}
