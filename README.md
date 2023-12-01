# search3
Implementation of evaluation with Bayesian Bandit strategy for dynamic identification 
of the best variant based on Gaussian randomization process for reasonable number of 
exploration attempts in order to search for the optimal score.

`Bayesian Bandit` strategy can be simply defined with three bullet points:
- choose arm
- get score
- memorize arm and score for next exploitation

For exploration have been used strategy to measure score (best time) 
of search by pattern based on following algorithms:
- **Aho-Carasic**
- **Horspool**
- **Wu-Manber**

Search algorithms have been implemented manually without any third-party library.

`Bayesian Bandit` strategy explanation and exploration results are presented in `Explanation` and `Results` 
sections accordingly.

## Explanation
**WIP**

## Results
**WIP**

## TODO
- Time score (elapsed time)

Calculated using **_Google Guava_** `Stopwatch`
```
Stopwatch watch = Stopwatch.createStarted();
...
watch.stop();
stat.Complete(id, watch.elapsed().getNano());
```
what is great (in fact idiomatic) approach for any Java program,
but that could be more effective using **_rsdtsc_** for x86/x64 (look this package here - https://github.com/dterei/gotsc as starting point) 
or _cntvct_el0_ for arm64 M1/M2 (discussion is here - https://stackoverflow.com/questions/40454157/is-there-an-equivalent-instruction-to-rdtsc-in-arm)
with **JVMCI** (see amazing **_nalim_** example - https://github.com/apangin/nalim).

- For randomization have been used JNI port of PCG C implementation - https://github.com/KilianB/pcg-java

This port provides much better performance (three times faster) than `Random` class from standard JDK library, but could even
faster using **JVMCI**.

## License
MIT - https://github.com/git/git-scm.com/blob/main/MIT-LICENSE.txt