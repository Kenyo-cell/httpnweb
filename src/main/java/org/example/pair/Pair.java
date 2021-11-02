package org.example.pair;

import static java.util.Objects.hash;

public class Pair<F, S> {
    protected F first;
    protected S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        Pair p = (Pair) obj;
        return first.equals(p.first) && second.equals(p.second);
    }

    @Override
    public int hashCode() {
        return hash(first, second);
    }

    @Override
    public String toString() {
        return "Pair{" +
               "first=" + first +
               ", second=" + second +
               "}\n";
    }
}
