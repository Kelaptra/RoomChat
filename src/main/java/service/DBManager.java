package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import model.Room;
import model.User;
import requests.CreateRoomRequest;
import requests.DeleteRoomRequest;
import responses.GetRoomsResponse;

import java.util.List;

public class DBManager {
    private static DBManager DBManager;
    private EntityManagerFactory emf;
    private EntityManager em;
    private CriteriaBuilder cb;

    private DBManager() {
        this.emf = Persistence.createEntityManagerFactory("RoomChat-unit");
        this.em = emf.createEntityManager();
        this.cb = em.getCriteriaBuilder();
    }
    public static DBManager getDBManager() {
        if(DBManager == null){
            DBManager = new DBManager();
        }
        return DBManager;
    }
    private void closer(){
        em.close();
        emf.close();
    }

    public void addChatRoom(CreateRoomRequest createRoomRequest){

        Room room = new Room(createRoomRequest.getRoom_name(), createRoomRequest.getMax_members());
        try {
            em.getTransaction().begin();
            em.persist(room);
            em.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public boolean deletable(String room_name){
        CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
        Root<Room> root = cq.from(Room.class);
        cq.select(root.get("online_members")).where(cb.equal(root.get("room_name"), room_name));
        TypedQuery<Integer> typedQuery = em.createQuery(cq);
        Integer online_members = typedQuery.getResultList().get(0);
        return online_members == 0;
    }
    public void DeleteChatRoom(DeleteRoomRequest deleteRoomRequest){
        try {
            em.getTransaction().begin();
            CriteriaDelete<Room> cd = cb.createCriteriaDelete(Room.class);
            Root<Room> root = cd.from(Room.class);
            cd.where(cb.equal(root.get("room_name"), deleteRoomRequest.getRoom_name()));
            em.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }
    public void changeOnlineMembersCount(Room room, int change){
        try {
            em.getTransaction().begin();
            CriteriaUpdate<Room> cu = cb.createCriteriaUpdate(Room.class);
            Root<Room> root = cu.from(Room.class);
            cu.set(root.get("online_members"), room.getOnline_members() + change).where(cb.equal(root.get("room_name"), room.getRoom_name()));
            em.createQuery(cu).executeUpdate();
            em.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }
    public GetRoomsResponse getRooms(){
        CriteriaQuery<Room> cq = cb.createQuery(Room.class);
        Root<Room> root = cq.from(Room.class);
        cq.select(root);
        TypedQuery<Room> typedQuery = em.createQuery(cq);
        Room[] rooms = typedQuery.getResultList().toArray(new Room[0]);
        return new GetRoomsResponse(rooms);
    }

    public String[] getUsernames(String room_name){
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<User> root = cq.from(User.class);
        Predicate p = cb.equal(root.get("room_name"), room_name);
        cq.select(root.get("username")).where(cb.equal(root.get("room_name"), room_name));
        TypedQuery<String> typedQuery = em.createQuery(cq);
        List<String> list = typedQuery.getResultList();
        return list.toArray(new String[0]);
    }
    public void addUser(String username, String room_name){
        User user = new User(username, room_name);
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }
    public void removeUser(String username){
        try {
            em.getTransaction().begin();
            CriteriaDelete<User> cd = cb.createCriteriaDelete(User.class);
            Root<User> root = cd.from(User.class);
            cd.where(cb.equal(root.get("username"), username));
            em.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }






}
