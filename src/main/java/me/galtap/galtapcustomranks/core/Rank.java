package me.galtap.galtapcustomranks.core;

import java.util.Objects;

public class Rank {
    private final String id;
    private final String prefix;
    private final int position;

    public Rank(String id, String prefix, int position){

        this.id = id;
        this.prefix = prefix;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        var rank = (Rank) o;
        return position == rank.position || Objects.equals(id, rank.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, position);
    }
}
