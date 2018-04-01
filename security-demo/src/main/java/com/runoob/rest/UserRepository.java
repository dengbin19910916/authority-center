package com.runoob.rest;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/**
 * @author DENGBIN
 * @since 2018-3-28
 */
@Repository
public class UserRepository {

    private List<User> users = new CopyOnWriteArrayList<User>() {{
        add(new User(1L, "db", "123456", getDate(1991, 11, 23)));
        add(new User(2L, "dq", "123456", getDate(1992, 6, 10)));
        add(new User(3L, "xff", "123456", getDate(193, 3, 29)));
    }};

    private Date getDate(int year, int month, int date) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.of(year, month, date);
        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findOne(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public Long insert(User user) {
        if (!users.isEmpty()) {
            Long maxId = users.stream().max(Comparator.comparing(User::getId)).get().getId() + 1;
            user.setId(maxId);
            users.add(user);
            return maxId;
        } else {
            users.add(user);
            return (long) users.size();
        }
    }

    public void update(User user) {
        IntStream.range(0, users.size()).filter(i -> users.get(i).getId().equals(user.getId())).forEachOrdered(i -> {
            users.remove(i);
            users.add(i, user);
        });
    }

    public void delete(Long id) {
        users.iterator().forEachRemaining(user -> {
            if (user.getId().equals(id)) {
                users.remove(user);
            }
        });
    }
}
