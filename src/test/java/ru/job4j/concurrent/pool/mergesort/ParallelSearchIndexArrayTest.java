package ru.job4j.concurrent.pool.mergesort;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class ParallelSearchIndexArrayTest {

    @Test
    public void whenHaveDec10NumThenUsuallySearch() {
        Integer[] arr = new Integer[] {1, 2, 3};
        assertThat(ParallelSearchIndexArray.findIndex(arr, 2), is(1));
    }

    @Test
    public void whenHaveInc10NumThenSearchEl() {
        Integer[] arr = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        assertThat(ParallelSearchIndexArray.findIndex(arr, 4), is(3));
    }

}