package org.alexgaas.search.impl.wu_manber;

import org.alexgaas.search.impl.Search;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Wu-Manber algorithm explanation:
    https://pld.cs.luc.edu/courses/447/sum08/classA/groepl,klau,reinert.2002.fast_exact_string_matching.pdf

Wu-Manber pseudocode:

WuManber(pattern, text):
    // Preprocessing
    Compute a suitable value of B (e.g. B = log|Σ|(2 · lmin · r));
    // We have to choose B. Let's choose B as 2

    Construct Hash tables SHIFT and HASH;

    // Searching
    pos = lmin;
    while pos ≤ n do {
        i = h1(tpos−B+1 ... tpos);
        if SHIFT[i] = 0 {
            list = HASH[h2(tpos−B+1 ... tpos)];
            Verify all patterns in list against the text;
            pos++;
        } else {
            pos = pos + SHIFT[i];
        }
    }

 */
public class WuManber implements Search {
    private final String[] patterns;
    private final Map<String, Integer> shiftMap;
    private final Map<String, List<Integer>> hash;
    private int pos;
    private int lMin;
    private int B;

    public WuManber(String[] patterns) {
        this.patterns = patterns;
        shiftMap = new HashMap<>();
        hash = new HashMap<>();
        lMin = Integer.MAX_VALUE;

        for (String p: patterns) {
            if (p.length() < lMin){
                lMin = p.length();
            }
        }

        pos = lMin;

        B = (int) (Math.ceil(Math.log(2.0 * lMin * patterns.length)) / Math.log(255.0));
        B = (B == 0) ? 2 : B;

        for (int i=0; i < patterns.length; i++){
            String block = patterns[i].substring(patterns[i].length() - B);
            List<Integer> updatedList = hash.get(block);
            if (updatedList == null) {
                updatedList = new ArrayList<>();
            }
            updatedList.add(i);
            hash.put(block, updatedList);

            for(int j = 0; j < patterns[i].length() - B + 1; j++) {
                String bgram = patterns[i].substring(j, j + B);
                int pos = patterns[i].length() - j - B;
                shiftMap.merge(bgram, pos, Math::min);
            }
        }
    }

    public Integer searchFirst(String text) {
        int text_length = text.length();
        while (pos <= text_length){
            String bgram = text.substring(pos - B, pos);
            var shift = shiftMap.get(bgram);
            if (shift != null){
                if (shift == 0){
                    pos += 1;
                    for(int index: hash.get(bgram)){
                        String str = patterns[index];
                        int foundPos = pos - str.length() - 1;

                        int k = 0;
                        while(k < str.length()){
                            if (str.charAt(k) != text.charAt(foundPos + k)) {
                                break;
                            }
                            k++;
                        }
                        if (k == str.length()){
                            return foundPos;
                        }
                    }
                }
                else {
                    pos += shift;
                }
            }
            else {
                pos += lMin - B + 1;
            }
        }

        return -1;
    }

    @Override
    public List<Integer> search(String text) {
        List<Integer> matches = new ArrayList<>();

        int text_length = text.length();
        while (pos <= text_length){
            String bgram = text.substring(pos - B, pos);
            var shift = shiftMap.get(bgram);
            if (shift != null){
                if (shift == 0){
                    pos += 1;
                    for(int index: hash.get(bgram)){
                        String str = patterns[index];
                        int foundPos = pos - str.length() - 1;

                        int k = 0;
                        while(k < str.length()){
                            if (str.charAt(k) != text.charAt(foundPos + k)) {
                                break;
                            }
                            k++;
                        }
                        if (k == str.length()){
                            matches.add(foundPos);
                        }
                    }
                }
                else {
                    pos += shift;
                }
            }
            else {
                pos += lMin - B + 1;
            }
        }
        
        return matches;
    }

    @Override
    public Pair<String, Integer> searchAllPatternsFirst(String text) {
        int text_length = text.length();
        while (pos <= text_length){
            String bgram = text.substring(pos - B, pos);
            var shift = shiftMap.get(bgram);
            if (shift != null){
                if (shift == 0){
                    pos += 1;
                    for(int index: hash.get(bgram)){
                        String str = patterns[index];
                        int foundPos = pos - str.length() - 1;

                        int k = 0;
                        while(k < str.length()){
                            if (str.charAt(k) != text.charAt(foundPos + k)) {
                                break;
                            }
                            k++;
                        }
                        if (k == str.length()){
                            return new Pair<>(str, foundPos);
                        }
                    }
                }
                else {
                    pos += shift;
                }
            }
            else {
                pos += lMin - B + 1;
            }
        }

        return null;
    }

    @Override
    public List<Pair<String, Integer>> searchAllPatterns(String text) {
        List<Pair<String, Integer>> matches = new ArrayList<>();

        int text_length = text.length();
        while (pos <= text_length){
            String bgram = text.substring(pos - B, pos);
            var shift = shiftMap.get(bgram);
            if (shift != null){
                if (shift == 0){
                    pos += 1;
                    for(int index: hash.get(bgram)){
                        String str = patterns[index];
                        int foundPos = pos - str.length() - 1;

                        int k = 0;
                        while(k < str.length()){
                            if (str.charAt(k) != text.charAt(foundPos + k)) {
                                break;
                            }
                            k++;
                        }
                        if (k == str.length()){
                            matches.add(new Pair<>(str,foundPos));
                        }
                    }
                }
                else {
                    pos += shift;
                }
            }
            else {
                pos += lMin - B + 1;
            }
        }

        return matches;
    }
}
