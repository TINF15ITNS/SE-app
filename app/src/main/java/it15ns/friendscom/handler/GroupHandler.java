package it15ns.friendscom.handler;

import java.util.ArrayList;
import java.util.List;

import it15ns.friendscom.model.Group;

/**
 * Created by danie on 31/05/2017.
 */

public class GroupHandler {
    private static GroupHandler instance = new GroupHandler();

    private List<Group> groups;

    private GroupHandler() {
        groups = new ArrayList<Group>();
    }

    // Methoden für die Gruppenverwaltung
    public static void addGroup(Group group) {
        instance.groups.add(group);
    }

    public static void removeGroup(Group group) {
        instance.groups.remove(group);
    }

    public static List<Group> getGroups() {
        return instance.groups;
    }

    public static void flashInstance() {
        instance = new GroupHandler();
    }
}
