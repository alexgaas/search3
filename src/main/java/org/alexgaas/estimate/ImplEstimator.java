package org.alexgaas.estimate;

import com.github.kilianB.pcg.fast.PcgRSUFast;

import java.util.ArrayList;
import java.util.List;

class ImplEstimator {

    public static class Element {
        int completed_count = 0;
        int running_count = 0;
        double sum = 0;

        int adjustedCount(){
            /*
                Cold invocations may provide incorrect stat, JVM have to be warmed up before accounting invocations
                Don't take first invocations into account. However, you can increase invocation based on yours results.
             */
            int NUM_INVOCATIONS_TO_THROW_OFF = 2;

            return completed_count - NUM_INVOCATIONS_TO_THROW_OFF;
        }

        double mean() {
            return sum / adjustedCount();
        }

        /*
        For better convergence, we don't use proper estimate of Apache common math functions.
            Must to eventually separate between two algorithms even in case,
            when there is no statistical significant difference between them.
         */
        double sigma()
        {
            return mean() / Math.sqrt(adjustedCount());
        }

        void run()
        {
            ++running_count;
        }

        void complete(double nanos)
        {
            --running_count;
            ++completed_count;
            if (adjustedCount() > 0)
                sum += nanos;
        }

        double sample () {
            /*
                If there is a variant with not enough statistics, always choose it.
                And in that case prefer variant with lesser number of invocations.
             */
            if (adjustedCount() < 2)
                return adjustedCount() - 1 + running_count;

            /*
                Pcg have been used for fastest random generation - see results on this Github page:
                https://github.com/KilianB/pcg-java
                (or just use Random() from standard lib if performance does not matter to you)
             */
            return mean() + PcgRSUFast.nextGaussian() * sigma();
        }
    }

    private final List<Element> data;

    public ImplEstimator(){
        this.data = new ArrayList<>();
    }

    public int Select(){
        // To not start evaluation process if there is only one variant to measure
        if (size() == 1)
            return 0;

        // you may protect this section by mutex for parallel execution
        int best = 0;
        double best_sample = data.get(0).sample();
        for (int i = 1; i < data.size(); ++i) {
            double sample = data.get(i).sample();
            if (sample < best_sample) {
                best_sample = sample;
                best = i;
            }
        }
        data.get(best).run();
        return best;
    }

    public void Complete(int id, double nanos){
        if (size() == 1)
            return;

        // you may protect [complete] by mutex for parallel execution
        data.get(id).complete(nanos);
    }

    public void emplaceStatData(){
        data.add(new Element());
    }

    int size() {
        return data.size();
    }
}
