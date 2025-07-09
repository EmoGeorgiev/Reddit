package com.reddit.vote;

public enum VoteType {
    UP_VOTE(1),
    NO_VOTE(0),
    DOWN_VOTE(-1);

    private final int score;

    VoteType(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
