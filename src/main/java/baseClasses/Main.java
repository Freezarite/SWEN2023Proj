package baseClasses;

import baseClasses.HTTP.HTTPMethod;
import baseClasses.HTTP.Router;
import baseClasses.Services.BattleService;

import java.io.IOError;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Router router = new Router();

        //      ./battles related calls
        router.addRoute(HTTPMethod.POST, "battles", new BattleService(), new int[] {});

    }
}
