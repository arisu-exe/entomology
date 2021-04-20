package io.github.fallOut015.entomology.common.capabilities;

import java.util.concurrent.Callable;

public class InsectTrackerCallable implements Callable<IInsectTracker> {
    @Override
    public IInsectTracker call() throws Exception {
        return new InsectTracker();
    }
}