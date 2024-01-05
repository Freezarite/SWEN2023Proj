package baseClasses;

import baseClasses.Card.Card;
import baseClasses.Card.MonsterCard;
import baseClasses.Card.SpellCard;
import baseClasses.DB.CardDBHandler;
import baseClasses.DB.UserDBHandler;
import baseClasses.HTTP.HTTPMethod;
import baseClasses.HTTP.Router;
import baseClasses.Server.Server;
import baseClasses.Services.BattleService;
import baseClasses.Services.CardService;
import baseClasses.Services.UserService;
import baseClasses.User.User;

import java.io.IOError;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        Router router = new Router();

        //battles routes
        router.addRoute(HTTPMethod.POST, "/battles", new BattleService(), new int[] {});

        // user routes
        router.addRoute(HTTPMethod.POST, "/users", new UserService(), new int[]{});
        router.addRoute(HTTPMethod.GET, "/users/{username}", new UserService(), new int[]{2});
        router.addRoute(HTTPMethod.PUT, "/users/{username}", new UserService(), new int[]{2});
        router.addRoute(HTTPMethod.POST, "/sessions", new UserService(), new int[]{});

        router.addRoute(HTTPMethod.GET, "/cards", new CardService(), new int[]{});
        router.addRoute(HTTPMethod.PUT, "/deck", new CardService(), new int[]{});
        router.addRoute(HTTPMethod.GET, "/deck", new CardService(), new int[]{});

        Server server = new Server(10001, 5, router);
        server.start();
    }
}
