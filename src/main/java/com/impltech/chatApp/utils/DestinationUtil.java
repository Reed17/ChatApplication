package com.impltech.chatApp.utils;

public class DestinationUtil {

    public static String chatRoomMessages(final String chatRoomId) {
        return "/queue/" + chatRoomId + ".messages";
    }

    public static String chatRoomConnectedUsers(final String chatRoomId) {
        return "/topic/" + chatRoomId + ".connected.users";
    }

}
