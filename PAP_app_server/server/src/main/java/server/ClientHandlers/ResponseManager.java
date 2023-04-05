package server.ClientHandlers;

import server.Chat.Chat;
import server.Chat.RequestTypes;

import org.json.JSONObject;

import server.Music.MusicStreamsManager;

public class ResponseManager {
    public static JSONObject processRequests(RequestTypes req_type, JSONObject request) {
        JSONObject response = new JSONObject();
        switch (req_type) {
            case GET_MESSAGES:                  //fall
            case GET_USERS_CONVERSATIONS:       //fall
            case SEND_MESSAGE:                  //fall
            case CREATE_CONVERSATION:           //fall 
            case ADD_USER_TO_CONVERSATION:      //fall
            case USER_INFO:
                return response = Chat.proces_requests(req_type, request);
            case START_STREAM:
            case STOP_STREAM:
                MusicStreamsManager streamManager = MusicStreamsManager.getInstance();
                return response = streamManager.handleRequest(req_type, request);
        }
    }
}
