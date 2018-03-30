package com.runoob.rest;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author DENGBIN
 * @since 2018-3-28
 */
@Repository
public class UserRepository {

    private Collection<User> users = new CopyOnWriteArrayList<User>() {{
        add(new User(1L, "db", "123456", getDate(1991, 11, 23)));
        add(new User(2L, "dq", "123456", getDate(1992, 6, 10)));
        add(new User(3L, "xff", "123456", getDate(193, 3, 29)));
    }};

    private Date getDate(int year, int month, int date) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.of(year, month, date);
        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }

    public Collection<User> findAll() {
        return users;
    }

    public Optional<User> findOne(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public void update(User user) {
        users.removeIf(u -> u.getId().equals(user.getId()));
        users.add(user);
    }
}
