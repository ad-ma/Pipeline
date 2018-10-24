package net.alagris;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This class encapsulates and manages a {@link Group} that was specially
 * constructed for performing tests.
 */
public class GroupTest<Cargo, TestUnit> implements AutoCloseable {
	/** This is the callback used inside pipeline */
	private final TestProcessingCallback<Cargo, TestUnit, PipeTestVerifier<Cargo, TestUnit>> callback;
	private final Group<Cargo> pipeline;

	public GroupTest(Group<Cargo> pipeline,
			TestProcessingCallback<Cargo, TestUnit, PipeTestVerifier<Cargo, TestUnit>> callback) {
		this.pipeline = pipeline;
		this.callback = callback;
	}

	public void runWith(String testJson, Class<Cargo> cargo, Class<TestUnit> unit)
			throws JsonProcessingException, IOException {
		runWith(BlueprintTest.load(testJson, cargo, unit), new DefaultResultReceiver<Cargo>());
	}

	public void runWith(String testJson, Class<Cargo> cargo, Class<TestUnit> unit, ResultReceiver<Cargo> resultReceiver)
			throws JsonProcessingException, IOException {
		runWith(BlueprintTest.load(testJson, cargo, unit));
	}

	public void runWith(File testFile, Class<Cargo> cargo, Class<TestUnit> unit)
			throws JsonProcessingException, IOException {
		runWith(BlueprintTest.load(testFile, cargo, unit), new DefaultResultReceiver<Cargo>());
	}

	public void runWith(File testFile, Class<Cargo> cargo, Class<TestUnit> unit, ResultReceiver<Cargo> resultReceiver)
			throws JsonProcessingException, IOException {
		runWith(BlueprintTest.load(testFile, cargo, unit));
	}

	public void runWith(BlueprintTest<Cargo, TestUnit> tests) {
		runWith(tests, new DefaultResultReceiver<Cargo>());
	}

	public void runWith(BlueprintTest<Cargo, TestUnit> tests, ResultReceiver<Cargo> resultReceiver) {
		callback.setTests(tests);
		pipeline.process(tests.getInput(), resultReceiver);
	}

	@Override
	public void close() throws Exception {
		pipeline.close();
	}
}
