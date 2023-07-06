package ru.yandex.practicum.service.user;

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
    private final InMemoryUserStorage userStorage;
    private final UserValidation userValidation;


    @Autowired
    public UserService(InMemoryUserStorage storage, UserValidation userValidation) {
        this.userStorage = storage;
        this.userValidation = userValidation;

    }

    public void addFriend(int userId, int friendId) {
        if (userId > 0 && friendId > 0) {
            if (userValidation.isValid(getUser(userId)) && userValidation.isValid(getUser(friendId))) {
                getUserFriendsById(userId).add((long) friendId);
                updateUser(getUser(userId));
            } else {
                throw new ValidationException("invalid userId friendId");
            }
        } else {
            throw new NoSuchUserException("no such user" + friendId);
        }

    }

    public void deleteFriend(int userId, int friendId) {
        getUserFriendsById(userId).remove((long) friendId);
        updateUser(getUser(userId));
    }

    public List<User> getFriendlist(int userId) {
        return makeFriendlist(userStorage.users.get(userId).getFriends());
    }

    public List<User> getCommonFriendlistWithOther(int userId, int otherUserId) {
        List<User> commonFriends = new ArrayList<>();
        if (userId > 0 && otherUserId > 0) {
            if (userValidation.isValid(getUser(userId)) && userValidation.isValid(getUser(otherUserId))) {
                for (long id : getUserFriendsById(userId)) {
                    if (getUserFriendsById(otherUserId).contains(id)) {
                        commonFriends.add(getUser((int) id));
                    }
                }
            } else {
                throw new ValidationException("validation error");
            }
        } else {
            throw new NoSuchUserException("no such user");
        }
        return commonFriends;
    }


    private HashSet<Long> getUserFriendsById(int userId) {  // получить список друзей по id
        return getUser(userId).getFriends();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userStorage.users.values());
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
        for (long id: idSet) {
            userFriendList.add(getUser((int)id));
        }
        return userFriendList;
    }

    public boolean containsUser(int userId) {
        return userStorage.users.containsKey(userId);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User saveUser(User user) {
        return userStorage.saveUser(user);
    }
}
