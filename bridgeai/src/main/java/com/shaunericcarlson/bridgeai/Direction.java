package com.shaunericcarlson.bridgeai;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;
    
    public Direction getPrev() {
        Direction prev;
        if (this.ordinal() > 0) {
            prev = Direction.values()[this.ordinal() - 1];
        } else {
            Direction[] v = Direction.values();
            prev = v[v.length - 1];
        }
        
        return prev;
    }
    
    public Direction getNext() {
        Direction next;
        Direction[] v = Direction.values();
        if (this.ordinal() < v.length - 1) {
            next = Direction.values()[this.ordinal() + 1];
        } else {
            next = Direction.values()[0];
        }
        
        return next;
    }
}