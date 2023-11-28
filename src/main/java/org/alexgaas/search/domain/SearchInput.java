package org.alexgaas.search.domain;

public class SearchInput {
    public String[] needle;
    public String haystack;

    public SearchInput(String[] needle, String haystack) {
        this.needle = needle;
        this.haystack = haystack;
    }
}
