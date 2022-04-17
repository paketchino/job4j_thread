package ru.job4j.concurrent.pool.mergesort;

public class MergeSort {

    /**
     * @param array массив для возврата
     * @return Возвращает полностью отсортированный скопированный массив
     */
    public static int[] sort(int[] array) {
        return sort(array, 0, array.length - 1);
    }

    /**
     * при следующем условии, массив из одного элемента
     * делить нечего, возвращаем элемент
     * попали сюда, значит в массиве более одного элемента
     * находим середину
     * int mid = (from + to) / 2;
     * объединяем отсортированные части массива
     * @param array - принимает массив для сортировки
     * @param from - начальный эл
     * @param to - конечный эл
     * @return отсортированный массив
     */
    private static int[] sort(int[] array, int from, int to) {
        if (from == to) {
            return new int[] {array[from]};
        }
        int mid = (from + to) / 2;
        return merge(sort(array, from, to), sort(array, from, to));
    }

    /**
     * Метод объединения двух отсортированных массивов
     * @param left - первый массив
     * @param right - второй массив
     * @return result общий массив
     */
    public static int[] merge(int[] left, int[] right) {
        int li = 0;
        int ri = 0;
        int resI = 0;
        int[] result = new int[left.length + right.length];
        while (resI != result.length) {
            if (li == left.length) {
                result[resI++] = right[ri++];
            } else if (ri == right.length) {
                result[resI++] = left[li++];
            } else if (left[li] <= right[ri]) {
                result[resI++] = left[li++];
            } else {
                result[resI++] = right[ri++];
            }
        }
        return result;
    }
 }
