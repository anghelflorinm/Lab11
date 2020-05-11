package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerHandler {
    Database database;

    public PlayerHandler() {
        this.database = Database.getInstance();
    }

    public void create(String name) {
        String query = "INSERT INTO players (name) VALUES ('" + name + "')";
        database.execute(query);
    }

    public Player findByName(String name) {
        String query = "SELECT name, id FROM players where name='" + name + "'";
        ResultSet resultSet = database.executeQuery(query);
        Player player = null;
        if (resultSet == null) {
            return null;
        }
        try {
            while (resultSet.next()) {
                player = new Player(resultSet.getInt("id"), resultSet.getString("name"));
            }
            resultSet.close();
            resultSet.getStatement().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    public Player findById(int id) {
        String query = "SELECT name, id FROM players where id='" + id + "'";
        ResultSet resultSet = database.executeQuery(query);
        Player player = null;
        if (resultSet == null) {
            return null;
        }
        try {
            while (resultSet.next()) {
                player = new Player(resultSet.getInt("id"), resultSet.getString("name"));
            }
            resultSet.close();
            resultSet.getStatement().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    public int getCount(){
        String query = "SELECT COUNT(*) from players";
        ResultSet resultSet = database.executeQuery(query);
        int count = 0;
        try {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            resultSet.close();
            resultSet.getStatement().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void updateName(int id, String name){
        String query = "UPDATE players set name=" + name + " where id="+id;
        database.execute(query);
    }

    public void delete(int id){
        String query = "DELETE from players where id=" + id;
        database.execute(query);
    }

    public List<Player> getPlayers(){
        List<Player> players = new ArrayList<>();
        String query = "SELECT * from players";
        ResultSet resultSet = database.executeQuery(query);
        try {
            while (resultSet.next()) {
                players.add(new Player(resultSet.getInt("id"), resultSet.getString("name")));
            }
            resultSet.close();
            resultSet.getStatement().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }
}

