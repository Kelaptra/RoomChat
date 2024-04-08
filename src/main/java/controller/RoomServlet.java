package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import requests.CreateRoomRequest;
import requests.DeleteRoomRequest;
import responses.CreateRoomResponse;
import responses.GetRoomsResponse;
import service.DBManager;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/room")
public class RoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GetRoomsResponse getRoomsResponse = DBManager.getDBManager().getRooms();
        resp.getWriter().write(getRoomsResponse.toJson());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        CreateRoomRequest createRoomRequest = objectMapper.readValue(body, CreateRoomRequest.class);
        DBManager.getDBManager().addChatRoom(createRoomRequest);
        CreateRoomResponse createRoomResponse = createRoomRequest.generateResponse();
        resp.getWriter().write(createRoomResponse.toJson());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        DeleteRoomRequest deleteRoomRequest = objectMapper.readValue(body, DeleteRoomRequest.class);
        DBManager.getDBManager().DeleteChatRoom(deleteRoomRequest);
        GetRoomsResponse getRoomsResponse = DBManager.getDBManager().getRooms();
        resp.getWriter().write(getRoomsResponse.toJson());
    }
}
