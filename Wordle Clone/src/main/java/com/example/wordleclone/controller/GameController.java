package com.example.wordleclone.controller;

import com.example.wordleclone.service.GameService;
import com.example.wordleclone.service.GameService.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        if (session.getAttribute("gameService") == null) {
            gameService.startNewGame();
            session.setAttribute("gameService", gameService);
        }
        return "index";
    }
    /*
    @PostMapping("/guess")
    @ResponseBody
    public Feedback guess(@RequestParam String playerGuess, HttpSession session) {
        gameService = (GameService) session.getAttribute("gameService");
        return gameService.processGuess(playerGuess.toLowerCase());
    }
    */

    @PostMapping("/guess")
    @ResponseBody
    public Feedback guess(@RequestParam String playerGuess, HttpSession session) {
        // Retrieve or create a new gameService instance for the session
        GameService gameService = (GameService) session.getAttribute("gameService");
    
        if (gameService == null) {
            gameService = new GameService();
            gameService.startNewGame(); // Start a new game for the session
            session.setAttribute("gameService", gameService);
        }
    
        // Process the player's guess
        return gameService.processGuess(playerGuess.toLowerCase());
    }


    /*
    @PostMapping("/new-game")
    @ResponseBody
    public String newGame(HttpSession session) {
        gameService.startNewGame();
        session.setAttribute("gameService", gameService);
        return "New game started";
    }
    */

    @PostMapping("/new-game")
    @ResponseBody
    public String newGame(HttpSession session) {
        // Create and start a new game
        GameService gameService = new GameService();
        gameService.startNewGame();
        session.setAttribute("gameService", gameService);
        return "New game started";
    }

}
