package com.kdu.smarthome.controller;

public final class ApiEndpoints {

    private ApiEndpoints(){

    }
    public static final String API_V1 = "/api/v1";

    public static final class Auth {
        public static final String BASE = API_V1 + "/auth";
        public static final String LOGIN = "/login";

        private Auth() {
        }
    }

    public static final class House{
        public static final String BASE = API_V1 + "/houses";
        public static final String ADD_DEVICE_HOUSE = "/{houseId}/devices";
        public static final String ADD_DEVICE_ROOM = "/rooms/{roomId}/devices/{deviceId}";
        public static final String GET_DEVICE_ALL_ROOMS = "/{houseId}/devices";
        public static final String MOVE_DEVICE = "/devices/{deviceId}/rooms/{newRoomId}";
        public static final String REMOVE_DEVICE_FROM_ROOM = "/rooms/{roomId}/devices/{deviceId}";
        public static final String UNREGISTER_DEVICE = "/{houseId}/devices/{deviceId}";
        public static final String GET_MY_HOUSES = "/myHouses";
        public static final String UPDATE_ADDRESS = "/{houseId}";
        public static final String TRANSFER_OWNER = "/{houseId}/admin";
        public static final String ADD_USER_TO_HOUSE = "/{houseId}/users";
        public static final String DELETE_USER_FROM_HOUSE = "/{houseId}/users/{userId}";
        public static final String DELETE_HOUSE = "/{houseId}";
        public static final String CREATE_ROOM = "/{houseId}/rooms";
        public static final String GET_ALL_ROOMS = "/{houseId}/rooms";


        private House() {

        }
    }

    public static final class User {
        public static final String BASE = API_V1 + "/users";
        
        private User(){}


      
    }


}
