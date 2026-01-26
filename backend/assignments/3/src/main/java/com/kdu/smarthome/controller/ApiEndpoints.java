package com.kdu.smarthome.controller;

public final class ApiEndpoints {

    private ApiEndpoints(){

    }
    public static final String API_V1 = "/api/v1";

    public static final class Auth {
        public static final String BASE = API_V1 + "/auth";
        public static final String LOGIN = BASE + "/login";

        private Auth() {
        }
    }

    public static final class House{
        public static final String BASE = API_V1 + "/houses";
        public static final String ADD_DEVICE_HOUSE = BASE + "/{houseId}/devices";
        public static final String ADD_DEVICE_ROOM = BASE + "/rooms/{roomId}/devices/{deviceId}";
        public static final String GET_DEVICE_ALL_ROOMS = BASE + "/{houseId}/devices";
        public static final String MOVE_DEVICE = BASE + "/devices/{deviceId}/rooms/{newRoomId}";
        public static final String REMOVE_DEVICE_FROM_ROOM = BASE + "/rooms/{roomId}/devices/{deviceId}";
        public static final String UNREGISTER_DEVICE = BASE + "/{houseId}/devices/{deviceId}";
        public static final String GET_MY_HOUSES = BASE + "/myHouses";
        public static final String UPDATE_ADDRESS = BASE + "/{houseId}";
        public static final String TRANSFER_OWNER = BASE + "/{houseId}/admin";
        public static final String ADD_USER_TO_HOUSE = BASE + "/{houseId}/users";
        public static final String DELETE_USER_FROM_HOUSE = BASE + "/{houseId}/users/{userId}";
        public static final String DELETE_HOUSE = BASE + "/{houseId}";
        public static final String CREATE_ROOM = BASE + "/{houseId}/rooms";
        public static final String GET_ALL_ROOMS = BASE + "/{houseId}/rooms";


        private House() {

        }
    }

    public static final class User {
        public static final String BASE = API_V1 + "/users";
        
        private User(){}


      
    }


}
