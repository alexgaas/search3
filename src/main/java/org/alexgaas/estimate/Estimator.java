package org.alexgaas.estimate;

import java.util.List;

public class Estimator {
    private final List<Element> data;

    public Estimator(List<Element> data) {
        this.data = data;
    }

    public int Select(Boolean considerable){
        if (size() == 1)
            return 0;

        int best = 0;
        double best_sample = data.get(0).sample();
        for (int i = 1; i < data.size(); ++i)
        {
            double sample = data.get(i).sample();
            if (sample < best_sample)
            {
                best_sample = sample;
                best = i;
            }
        }
        if (considerable)
            data.get(best).run();
        return best;
    }

    public void Complete(int id, double seconds, double bytes){
        if (size() == 1)
            return;
        data.get(id).complete(seconds, bytes);
    }

    int size() {
        return data.size();
    }
    Boolean empty() {
        return size() == 0;
    }
}
