package org.alexgaas.estimate;

import com.github.kilianB.pcg.fast.PcgRSUFast;

import java.util.ArrayList;
import java.util.List;

public class ImplEstimator {

    public static class Element {
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

        void complete(double seconds, double items)
        {
            --running_count;
            ++completed_count;
            if (adjustedCount() > 0)
                sum += seconds / items;
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

    private final List<Element> data;

    public ImplEstimator(){
        this.data = new ArrayList<>();
    }

    public int Select(){
        if (size() < 2)
            return 0;

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

    public void Complete(int id, double seconds, double items){
        if (size() < 2)
            return;
        data.get(id).complete(seconds, items);
    }

    public void emplaceStatData(){
        data.add(new Element());
    }

    int size() {
        return data.size();
    }
    Boolean empty() {
        return size() == 0;
    }
}
