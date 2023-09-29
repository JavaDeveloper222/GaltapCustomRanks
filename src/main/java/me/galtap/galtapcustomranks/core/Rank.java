package me.galtap.galtapcustomranks.core;

import java.util.Objects;

public class Rank {
    private final String id;
    private final String prefix;
    private final int position;
    private final int price;
    private final int blockCount;

    public Rank(String id, String prefix, int position, int price, int blockCount){

        this.id = id;
        this.prefix = prefix;
        this.position = position;
        this.price = price;
        this.blockCount = blockCount;
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

    public int getPrice() {
        return price;
    }

    public int getBlockCount() {
        return blockCount;
    }
}
