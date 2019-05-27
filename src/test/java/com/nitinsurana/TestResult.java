package com.nitinsurana;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestResult {

    @Test
    public void testCounts() {
        Result r = new Result();
        r.incFailure();
        r.incFailure();
        r.incFailure();
        assertEquals(3, r.getTotal());
        assertEquals(3, r.getFailure());
        assertEquals(0, r.getSuccess());
        r.incSuccess();
        r.incSuccess();
        assertEquals(2, r.getSuccess());
        assertEquals(5, r.getTotal());
        assertEquals(3, r.getFailure());
        r.incFailure();
        assertEquals(2, r.getSuccess());
        assertEquals(6, r.getTotal());
        assertEquals(4, r.getFailure());
        r.incSuccess();
        assertEquals(3, r.getSuccess());
        assertEquals(7, r.getTotal());
        assertEquals(4, r.getFailure());
    }
}
