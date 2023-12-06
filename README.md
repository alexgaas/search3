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
Score evaluation implementation located in package `src/main/java/org/alexgaas/estimate`:

- internal class **ImplEstimator** which performs _select / complete / emplace_ operations:
_src/main/java/org/alexgaas/estimate/ImplEstimator.java_
- public class **EvalSelector** provides public _contract to call evaluation_ based on **ImplEstimator**:
_src/main/java/org/alexgaas/estimate/EvalSelector.java_

As been said, Bayesian Bandit stranger can be implemented over 3 steps: choose arm, get score, memorize arm and score for next exploitation

For this implementation I've used **Gaussian Random Number Generator** (also known as normal distribution) from PCG library (library - https://www.pcg-random.org/download.html, JVM wrapper - https://github.com/KilianB/pcg-java). 
The randomness comes from atmospheric noise, which for many purposes is better than the pseudo-random number algorithms typically used in computer programs.

Simplest plot for a normal distribution looks like:

<img src="./plots/Norm.png" width="300">

<details>
<summary>R snippet to plot simplest normal distribution</summary>

```R
#install (if not already installed) and load ggplot2
if(!(require(ggplot2))){install.packages('ggplot2')}
#generate a normal distribution plot
ggplot(data.frame(x = c(-4, 4)), aes(x = x)) +
stat_function(fun = dnorm)
```
</details>

Normal distribution for **Aho-Carasic** (100 repetitions) looks like:
<img src="./plots/Aho-Corasick-Norm.png">

Normal distribution **Horspool** (100 repetitions) looks like:
<img src="./plots/Horspool-Norm.png">

Normal distribution for **Wu-Manber** (100 repetitions) looks like:
<img src="./plots/Wu-Manber-Norm.png">

Process of evaluation as high level diagram is represented as further diagram: **WIP**

## Results
Testing been performed on Britannica data corpus - https://data.nls.uk/data/digitised-collections/encyclopaedia-britannica/

Results of evaluation can be found over running unit and integration tests:
- **_test/java/search_** folder includes **unit** and **integration** tests for 
_Aho-Carasic, Horspool, Wu-Manber_ search algorithms
- Evaluation tests using [EvalSelector] with registered implementations of search algorithms
for estimation are located in **_test/java/estimate_** folder

#### Evaluation test of _Aho-Carasic, Horspool, Wu-Manber_ search algorithms (20 repetitions):

_Basic test - search in one line by two patterns_:

_Patterns_: **announce, cpm**

_Text to search in_: **cpmxannualxconferencexannounce**
<details>
<summary>Results:</summary>

```text
[AhoCarasick]: 17.69 ms
[Harspool]: 132.8 ms
[WuManber]: 236.5 μs
[AhoCarasick]: 6.580 ms
[Harspool]: 76.71 ms
[WuManber]: 976.8 μs
[AhoCarasick]: 1.014 ms
[Harspool]: 97.96 ms
[WuManber]: 138.6 μs
[AhoCarasick]: 915.5 μs
[Harspool]: 86.38 ms
[WuManber]: 988.8 μs
[WuManber]: 930.8 μs
[WuManber]: 171.5 μs
[WuManber]: 2.282 ms
[WuManber]: 754.3 μs
[WuManber]: 1.378 ms
[AhoCarasick]: 651.7 μs
[WuManber]: 352.0 μs
[WuManber]: 145.3 μs
```
</details>

Plot result (in nanoseconds):
<img src="./plots/Plot_Result_1.png">

_Test - search in one file (4MB of UTF text) by three patterns_:

_Patterns_: **ENCYCLOPEDIA, BRITANNICA, ENCYCLOPEDIA BRITANNICA**

_Text to search in_: `src/test/resources/text/144133901.txt`
<details>
<summary>Results:</summary>

```text
[AhoCarasic]: 80.02 ms
[Harspool]: 181.1 ms
[WuManber]: 40.10 ms
[AhoCarasic]: 25.17 ms
[Harspool]: 133.1 ms
[WuManber]: 30.16 ms
[AhoCarasic]: 11.91 ms
[Harspool]: 129.5 ms
[WuManber]: 25.75 ms
[AhoCarasic]: 8.383 ms
[Harspool]: 124.3 ms
[WuManber]: 18.21 ms
[WuManber]: 15.62 ms
[Harspool]: 126.4 ms
[AhoCarasic]: 7.960 ms
[WuManber]: 12.16 ms
[WuManber]: 11.08 ms
[WuManber]: 12.14 ms
[WuManber]: 22.43 ms
[WuManber]: 10.96 ms
```
</details>

_Test - search in 20 files (5MB of UTF text each) by one pattern_:

_Patterns_: **it**

_Text to search in folder_: `src/test/resources/text/`
<details>
<summary>Results:</summary>

```text
[AhoCarasic]: 691.9 ms
[Harspool]: 53.64 ms
[WuManber]: 1.987 s
[AhoCarasic]: 353.8 ms
[Harspool]: 34.65 ms
[WuManber]: 1.537 s
[AhoCarasic]: 336.9 ms
[Harspool]: 32.41 ms
[WuManber]: 1.443 s
[AhoCarasic]: 355.4 ms
[Harspool]: 28.66 ms
[AhoCarasic]: 336.4 ms
[WuManber]: 1.423 s
[Harspool]: 42.18 ms
[Harspool]: 33.66 ms
[WuManber]: 1.484 s
[Harspool]: 31.76 ms
[Harspool]: 30.28 ms
[Harspool]: 29.74 ms
[Harspool]: 29.66 ms
```
</details>

_Test - search in 20 files (5MB of UTF text each) by ten patterns_:

_Patterns_: **it,different,determined,may,metallic,compreffed body,fubclavian,fire,aluminous,got**

_Text to search in folder_: `src/test/resources/text/`
<details>
<summary>Results:</summary>

```text
[AhoCarasic]: 6.069 s
[Harspool]: 375.3 ms
[WuManber]: 4.218 s
[AhoCarasic]: 3.491 s
[Harspool]: 312.8 ms
[WuManber]: 3.684 s
[AhoCarasic]: 3.466 s
[Harspool]: 315.6 ms
[WuManber]: 4.090 s
[AhoCarasic]: 3.539 s
[Harspool]: 309.3 ms
[WuManber]: 3.415 s
[WuManber]: 3.718 s
[AhoCarasic]: 3.628 s
[Harspool]: 311.9 ms
[Harspool]: 312.1 ms
[WuManber]: 3.604 s
[Harspool]: 310.9 ms
[AhoCarasic]: 3.494 s
[WuManber]: 3.546 s
```
</details>

## Summary of results

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

- For normal distribution of random numbers have been used JNI port of PCG C implementation - https://github.com/KilianB/pcg-java
```
/*
    Pcg have been used for fastest random generation - see results on this Github page:
    https://github.com/KilianB/pcg-java
    (or just use Random() from standard lib if performance does not matter to you)
 */
return mean() + PcgRSUFast.nextGaussian() * sigma();
```
This port provides much better performance (three times faster) than `Random` class from standard JDK library, but could even
faster using **JVMCI**.

## License
MIT - https://github.com/git/git-scm.com/blob/main/MIT-LICENSE.txt