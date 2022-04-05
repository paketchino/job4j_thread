package ru.job4j.concurrent.jcip;

import org.junit.jupiter.api.Test;
import ru.job4j.concurrent.jcip.storagelist.User;
import ru.job4j.concurrent.jcip.storagelist.UserStorage;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.List;

class UserStorageTest {

    private class ThreadUser extends Thread {

        private final UserStorage userStorage;
        private User user;

        public ThreadUser(final UserStorage userStorage, final User user) {
            this.userStorage = userStorage;
            this.user = user;
        }

        @Override
        public void run() {
            this.userStorage.add(user);
            this.userStorage.transfer(user.getId(), user.getId(), user.getAmount());
        }
    }

    @Test
    public void whenTransferCash2UserThenExecuteMethodTransfer() throws InterruptedException {
        User userFirst = new User(1, 100);
        User userSecond = new User(2, 200);
        List<User> listUser = new ArrayList<>();
        listUser.add(userFirst);
        listUser.add(userSecond);
        final UserStorage userStorage = new UserStorage(listUser);
        userStorage.transfer(1, 2, 50);
        Thread threadUserFirst = new ThreadUser(userStorage, userFirst);
        Thread threadUserSecond = new ThreadUser(userStorage, userSecond);

        threadUserFirst.start();
        threadUserSecond.start();

        threadUserFirst.join();
        threadUserSecond.join();
        assertThat(userFirst.getAmount(), is(50));
        assertThat(userSecond.getAmount(), is(250));
    }

}