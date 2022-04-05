package ru.job4j.concurrent.jcip.storagelist;


public interface Transformer {

    void transfer(int fromId, int told, int amount);

}
