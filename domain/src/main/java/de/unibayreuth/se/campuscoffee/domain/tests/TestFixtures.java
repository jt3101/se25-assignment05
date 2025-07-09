package de.unibayreuth.se.campuscoffee.domain.tests;

import de.unibayreuth.se.campuscoffee.domain.model.CampusType;
import de.unibayreuth.se.campuscoffee.domain.model.Pos;
import de.unibayreuth.se.campuscoffee.domain.model.PosType;
import de.unibayreuth.se.campuscoffee.domain.model.User;
import de.unibayreuth.se.campuscoffee.domain.ports.PosService;
import de.unibayreuth.se.campuscoffee.domain.ports.UserService;
import org.apache.commons.lang3.SerializationUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TestFixtures {
    private static final List<Pos> POS_LIST = List.of(
            new Pos("CrazySheep (RW-I)", "Description 1", PosType.CAFE, CampusType.MAIN, "Andreas-Maisel-Weg", "2",
                    95445, "Bayreuth"),
            new Pos("Cafeteria (Mensa)", "Description 1", PosType.CAFE, CampusType.MAIN, "Universitätsstraße", "30",
                    95447, "Bayreuth"),
            new Pos("Lidl (Nürnberger Str.)", "Description 1", PosType.VENDING_MACHINE, CampusType.ZAPF,
                    "Nürnberger Str.", "3a", 95448, "Bayreuth"));

    private static final List<User> USER_LIST = List.of(
            new User("mrbanan31", "mrbanan@ukr.net", "Anthony", "Black"),
            new User("bolide31_ukr", "bolide@gmail.com", "Вася", "Пупкін"),
            new User("jt31_ukr", "hodzhatimur@gmail.com", "Tymur", "Khodzha"));

    public static List<Pos> getPosList() {
        return POS_LIST.stream()
                .map(SerializationUtils::clone) // prevent issues when tests modify the fixture objects
                .toList();
    }

    public static List<User> getUserList() {
        return USER_LIST.stream()
                .map(SerializationUtils::clone)
                .toList();
    }

    public static List<Pos> createPos(PosService posService) {
        return getPosList().stream()
                .map(posService::upsert)
                .collect(Collectors.toList());
    }

    public static List<User> createUsers(UserService userService) {
        return getUserList().stream()
                .map(userService::upsert)
                .collect(Collectors.toList());
    }
}
