package org.alexgaas.search.horspool;

import java.util.HashMap;

/*
Horspool algorithm explanation:
    https://pld.cs.luc.edu/courses/447/sum08/classA/groepl,klau,reinert.2002.fast_exact_string_matching.pdf

Input: text T of length n and pattern p of length m
Output: all occurrences of p in T

Preprocessing:
    for c ∈ Σ do d[c] = m;
    for j ∈ 1 ... m − 1 do d[pj] = m − j

Searching:
pos = 0;
while pos ≤ n − m do
    j = m;
    while j > 0 ∧ tpos+j = pj do j−−;
    if j = 0 then output “p occurs at position pos + 1”
    pos = pos + d[tpos+m];
 */
class Horspool {
    private final int pattenLength;

    private final String pattern;

    public Horspool(String pattern) {
        this.pattern = pattern;
        this.pattenLength = pattern.length();
    }

    public Integer searchFirst(String text){
        if(pattenLength == 0)
            return -1;

        // Preprocessing
        HashMap<Character, Integer> hashmap = hashOf(pattern, pattenLength);

        int count = 0;
        int lastIndex = pattenLength - 1;
        int patpoint = pattenLength - 1;
        int point = pattenLength - 1;
        while(count < pattenLength && point < text.length()) {
            if(pattern.charAt(patpoint) == text.charAt(point)) {
                count++;
                point--;
                patpoint--;
            }
            else {
                point = lastIndex + hashmap.get(text.charAt(lastIndex));
                lastIndex = point;
                patpoint = pattenLength - 1;
                count = 0;
            }
        }

        if(count == pattenLength)
            return point+1;
        else
            return -1;
    }

    private static HashMap<Character, Integer> hashOf(String needle, int pattenLength) {
        HashMap<Character,Integer> hashmap = new HashMap<>();

        for(int i = 0; i < 256; i++) {
            hashmap.put((char)i, pattenLength);
        }

        for(int i = 0; i < pattenLength -1; i++) {
            hashmap.put(needle.charAt(i), pattenLength - 1 - i);
        }

        return hashmap;
    }
}