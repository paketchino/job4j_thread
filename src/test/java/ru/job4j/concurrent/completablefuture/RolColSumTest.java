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
    public void whenSumEqualsAsyncSum() {
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        RolColSum.Sums[] sum = RolColSum.sum(array);
        RolColSum.Sums[] asyncSum = RolColSum.sum(array);
        assertArrayEquals(sum, asyncSum);
    }
    @Test
    public void whenSumExecuteSingleThread() {
        String excepted = "По ряду {6}, по столбцу {12} По ряду {15}, по столбцу {15} По ряду {24}, по столбцу {18} ";
        StringBuilder sb = new StringBuilder();
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        RolColSum.Sums[] rolColSums = RolColSum.sum(array);
        for (RolColSum.Sums s : rolColSums) {
            sb.append(
                    String.format("По ряду {%d}, по столбцу {%d}", s.getRowSum(), s.getColSum()))
                    .append(" ");
        }
        assertThat(excepted, is(sb.toString()));
    }

    @Test
    public void whenAsyncThenExecuteAsynchronously() throws ExecutionException, InterruptedException {
        String excepted = "По ряду {6}, по столбцу {12} По ряду {15}, по столбцу {15} По ряду {24}, по столбцу {18} ";
        StringBuilder sb = new StringBuilder();
        int[][] array = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        RolColSum.Sums[] rolColSums = RolColSum.asyncSum(array);
        for (RolColSum.Sums s : rolColSums) {
            sb.append(
                    String.format("По ряду {%d}, по столбцу {%d}", s.getRowSum(), s.getColSum()))
                    .append(" ");
        }
        assertThat(excepted, is(sb.toString()));
    }
}