package com.example.demo;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/game")
public class GameController{
    List<Board>  boards= new ArrayList<>();


    @GetMapping("/new")
    public ResponseEntity<String> getNewGame(){
        Integer newId = 0;
        if(boards.size() == 0)
        {
            newId =0;
        }
        else{
            newId = boards.get(boards.size() - 1).getId() + 1;
        }

        Board board  = new Board(newId);
        boards.add(board);
        int x = 0, y = 0;
        Board.STATUS status;
        do{

            x = getRandomNumberInRange(0,29);
            y= getRandomNumberInRange(0, 29);

            status = board.applyMove(2, x, y);

        }while(status == Board.STATUS.INVALID);

        JsonObject response = new JsonObject();
        response.addProperty("id", newId);
        response.addProperty("x", x);
        response.addProperty("y", y);
        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }

    @PostMapping("/move/{gameid}/{x}/{y}")
    ResponseEntity<String> move(@PathVariable("gameid") int gameID, @PathVariable("x") int x, @PathVariable("y") int y){
        Board board = null;

        for(Board thisBoard : boards){
            if(thisBoard.getId() == gameID){
                board = thisBoard;
                break;
            }
        }
        if(board == null)
        {
            throw new MyException();
        }

        Board.STATUS status = board.applyMove(1, x, y);

        switch (status){
            case INVALID:
                throw new MyException();
            case WIN_PLAYER1:
                boards.remove(board);
                return new ResponseEntity<String>("{\"result\": \"WIN_PLAYER1\"}", HttpStatus.BAD_REQUEST);
        }

        do{

            x = getRandomNumberInRange(0,29);
            y= getRandomNumberInRange(0, 29);

            status = board.applyMove(2, x, y);

        }while(status == Board.STATUS.INVALID);

        if (status == Board.STATUS.WIN_PLAYER2) {
            boards.remove(board);
            return new ResponseEntity<String>("{\"result\": \"WIN_PLAYER1\"}", HttpStatus.OK);
        }
        JsonObject response = new JsonObject();
        response.addProperty("x", x);
        response.addProperty("y", y);
        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
