package ru.job4j.concurrent.completablefuture;

import org.junit.Ignore;
import org.junit.Test;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class RolColSumTest {

    @Ignore
    @Test
    public void whenSumEqualsAsyncSum() throws ExecutionException, InterruptedException {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        RolColSum.Sums[] sum = RolColSum.sum(array);
        RolColSum.Sums[] asyncSum = RolColSum.asyncSum(array);
        assertArrayEquals(sum, asyncSum);
    }
    @Test
    public void whenSumExecuteSingleThread() {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        RolColSum.Sums[] sum = RolColSum.sum(array);
        RolColSum.Sums[] expected = {
                new RolColSum.Sums(6, 12),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(24, 18)
        };
        assertArrayEquals(sum, expected);
    }

    @Test
    public void whenAsyncThenExecuteAsynchronously() throws ExecutionException, InterruptedException {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        RolColSum.Sums[] rolColSums = RolColSum.asyncSum(array);
        RolColSum.Sums[] excepted =  {
                new RolColSum.Sums(6, 12),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(24, 18)
        };
        assertArrayEquals(rolColSums, excepted);

    }
}