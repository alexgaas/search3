package org.alexgaas.estimate;

import com.github.kilianB.pcg.fast.PcgRSUFast;

public class Element {
    int completed_count = 0;
    int running_count = 0;
    double sum = 0;

    int adjustedCount(){
        int NUM_INVOCATIONS_TO_THROW_OFF = 2;
        return completed_count - NUM_INVOCATIONS_TO_THROW_OFF;
    }

    double mean() {
        return sum / adjustedCount();
    }

    double sigma()
    {
        return mean() / Math.sqrt(adjustedCount());
    }

    void run()
    {
        ++running_count;
    }
    void complete(double seconds, double bytes)
    {
        --running_count;
        ++completed_count;
        if (adjustedCount() > 0)
            sum += seconds / bytes;
    }

    double sample () {
        /// If there is a variant with not enough statistics, always choose it.
        /// And in that case prefer variant with lesser number of invocations.
        if (adjustedCount() < 2)
            return adjustedCount() - 1 + running_count;

        // Pcg have been used for fastest evaluation
        // (or just use Random() from standard lib if performance does not matter to you)
        return mean() + PcgRSUFast.nextGaussian() * sigma();
    }
}
