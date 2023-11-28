package org.alexgaas.search.impl.aho_carasic;

import org.alexgaas.search.impl.Search;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
Aho–Corasick algorithm explanation:
    https://en.wikipedia.org/wiki/Aho–Corasick_algorithm
 */
class AhoCorasick implements Search {

    private static class Node {
        private final Node[] children;
        private boolean isPattern;
        private String patternValue;

        public Node() {
            children = new Node[256];
            isPattern = false;
        }
    }

    private final Node root;

    public AhoCorasick(String[] patterns) {
        root = new Node();
        for (String pattern : patterns) {
            buildTrie(root, pattern);
        }
    }

    private void buildTrie(Node node, String pattern) {
        for (int i = 0; i < pattern.length(); i++) {
            int charIndex = pattern.charAt(i);
            if (node.children[charIndex] == null) {
                node.children[charIndex] = new Node();
            }
            node = node.children[charIndex];
        }
        node.isPattern = true;
        node.patternValue = pattern;
    }

    public Integer searchFirst(String text) {
        var result = search(text);
        return !result.isEmpty() ? result.get(0) : -1;
    }

    public List<Integer> search(String text) {
        return searchAllPatterns(text).stream().map(Pair::getValue1).collect(Collectors.toList());
    }

    public Pair<String,Integer> searchAllPatternsFirst(String text) {
        var result = searchAllPatterns(text);
        return !result.isEmpty() ? result.get(0) : null;
    }

    public List<Pair<String,Integer>> searchAllPatterns(String text) {
        List<Pair<String,Integer>> matches = new ArrayList<>();
        Node currentNode = root;
        for (int i = 0; i < text.length(); i++) {
            int charIndex = text.charAt(i);
            currentNode = currentNode.children[charIndex];
            if (currentNode == null) {
                currentNode = root;
            }
            if (currentNode.isPattern) {
                matches.add(new Pair<>(currentNode.patternValue, i));
            }
        }
        return matches;
    }
}

