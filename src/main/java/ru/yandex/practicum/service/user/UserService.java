package ru.yandex.practicum.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.NoSuchUserException;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.validation.UserValidation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final InMemoryUserStorage userStorage;
    private final UserValidation userValidation;
    private Integer generatedId = 1;


    @Autowired
    public UserService(InMemoryUserStorage storage, UserValidation userValidation) {
        this.userStorage = storage;
        this.userValidation = userValidation;

    }

    public List<User> addFriend(int userId, int friendId) {
        getUserFriendsById(userId).add((long) getUserById(friendId).getId());
        userStorage.saveUser(getUserById(userId));
        return new ArrayList<>(userStorage.users.values());
    }

    public List<User> deleteFriend(int userId, int friendId) {
        getUserFriendsById(userId).remove((long) friendId);
        userStorage.updateUser(getUserById(userId));
        return makeFriendlist(getUserFriendsById(userId));
    }

    public List<User> getFriendlist(int userId) {
        return makeFriendlist(userStorage.users.get(userId).getFriends());
    }

    public List<User> getCommonFriendlistWithOther(int userId, int otherUserId) {
        List<User> commonFriends = new ArrayList<>();
        for (Long id : getUserFriendsById(userId)) {
            if (getUserFriendsById(otherUserId).contains(id)) {
                commonFriends.add(getUserById(id));
            }
        }
        return commonFriends;
    }

    private User getUserById(long userId) {  // получить объект User по id
        return userStorage.users.get((int) userId);
    }

    private HashSet<Long> getUserFriendsById(int userId) {  // получить список друзей по id
        return getUserById(userId).getFriends();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userStorage.users.values());
    }

    public User addUser(User user) {
        try {
            if (!userValidation.isValid(user)) {
                log.warn("Ошибка валидации при добавлении пользователя " + user);
                throw new ValidationException("Ошибка валидации при добавлении пользователя");
            }
            user.setId(generatedId++);
            userStorage.saveUser(user);
            log.debug("Успешное добавление пользователя");
        } catch (NullPointerException e) {
            user.setName(user.getLogin());
            if (!userValidation.isValid(user)) {
                log.warn("Ошибка валидации при добавлении пользователя " + user);
                throw new ValidationException("Ошибка валидации при добавлении пользователя");
            }
            user.setId(generatedId++);
            userStorage.saveUser(user);
            log.debug("Успешное добавление пользователя");
        }
        return user;
    }

    public User updateUser(User user) {
        if (!userValidation.isValid(user)) {
            log.warn("Ошибка валидации при обновлении пользователя " + user);
            throw new ValidationException("Ошибка валидации при обновлении пользователя");
        } else {
            if (userStorage.users.containsKey(user.getId())) {
                userStorage.updateUser(user);
                log.debug("Успешное обновление пользователя");
            } else {
                log.warn("Невозможно обновить,  пользователя " + user + " не существует");
                throw new NoSuchUserException("Нет такого пользователя");
            }
        }
        return user;
    }

    public User getUser(int id) {
        if (userStorage.users.containsKey(id)) {
            return userStorage.users.get(id);
        } else {
            throw new NoSuchUserException("нет такого пользователя");
        }
    }

    private List<User> makeFriendlist(HashSet<Long> idSet) {
        List<User> userFriendList = new ArrayList<>();
        for (Long id: idSet) {
            userFriendList.add(getUserById(id));
        }
        return userFriendList;
    }
}
