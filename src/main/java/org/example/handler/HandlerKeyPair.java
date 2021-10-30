package org.example.handler;

import static java.util.Objects.hash;

public class HandlerKeyPair extends Pair<String, String> {
    public HandlerKeyPair(String first, String second) {
        super(first, second);
    }

    @Override
    public boolean equals(Object obj) {
        HandlerKeyPair other = (HandlerKeyPair) obj;
        return this.first.equals(other.first)
               && this.second.equals(other.second);
    }

    @Override
    public int hashCode() {
        return hash(first, second);
    }
}
