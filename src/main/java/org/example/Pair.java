package org.example;

public class Pair<F, S> {
    public F first;
    public S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        Pair p = (Pair) obj;
        return first.equals(p.first) && second.equals(p.second);
    }
}
