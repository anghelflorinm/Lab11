package com.example.demo;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/players")
public class PlayerController {
    PlayerHandler playerHandler;

    public PlayerController() {
        playerHandler = new PlayerHandler();
    }

    @GetMapping
    public List<Player> getProducts() {
        return playerHandler.getPlayers();
    }

    @GetMapping("/count")
    public int countProducts() {
        return playerHandler.getCount();
    }

    @GetMapping("/{id}")
    public Player getProduct(@PathVariable("id") int id) {
        return playerHandler.findById(id);
    }

    @PostMapping(value = "/createByName", consumes = "application/json")
    public int createProduct(@RequestBody String json) {
        int id = 1 + playerHandler.getCount();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        playerHandler.create(jsonObject.get("name").getAsString());
        return playerHandler.findByName(jsonObject.get("name").getAsString()).getId();
    }

    @PostMapping(value = "/obj", consumes = "application/json")
    public ResponseEntity<String>
    createProduct(@RequestBody Player player) {
        playerHandler.create(player.getName());
        return new ResponseEntity<>(
                "Player created successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<String> updateProduct(
            @PathVariable int id, @RequestBody String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        Player player = playerHandler.findById(id);
        if (player == null) {
            return new ResponseEntity<>(
                    "Player not found", HttpStatus.NOT_FOUND); //or GONE
        }
        playerHandler.updateName(id, name);
        return new ResponseEntity<>(
                "Player updated successsfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        playerHandler.delete(id);
        return new ResponseEntity<>("Product removed", HttpStatus.OK);
    }
}
