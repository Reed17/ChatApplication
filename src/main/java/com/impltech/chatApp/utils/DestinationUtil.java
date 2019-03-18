package com.impltech.chatApp.utils;

public class DestinationUtil {

    public static String chatRoomMessages(String chatRoomId) {
        return "/queue/" + chatRoomId + ".messages";
    }

    public static String chatRoomConnectedUsers(String chatRoomId) {
        return "/topic/" + chatRoomId + ".connected.users";
    }

}
