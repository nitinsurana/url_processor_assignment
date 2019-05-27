package com.nitinsurana;

public class Result {
    private int total, success, failure;

    public int getTotal() {
        return total;
    }

    public int getSuccess() {
        return success;
    }

    public int getFailure() {
        return failure;
    }

    public void incSuccess() {
        success++;
        total++;
    }

    public void incFailure() {
        failure++;
        total++;
    }
}
