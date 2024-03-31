package org.arhdeez.test;

import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
public class TestSuite extends Suite {
    public TestSuite(final Class<?> klass, final RunnerBuilder runnerBuilder) throws InitializationError {
        super(klass, runnerBuilder);
    }
}
