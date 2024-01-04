package baseClasses.HTTP;

import baseClasses.Services.Service;

import java.util.*;
import java.util.stream.IntStream;

public class Router {
    private final Map<HTTPMethod, Map<String, Route>> routeMap = new HashMap<>();

    public void addRoute(final HTTPMethod method, final String route, final Service service, final int[] pathsVariables) {

        Map<String, Route> tempMap = this.routeMap.get(method);

        if(method != null && this.routeMap.get(method) == null)
            this.routeMap.put(method, (tempMap = new HashMap<>()));

        List<String> routingComponents = new ArrayList<>(Arrays.asList(route.split("/")));

        for(Integer pathVarPos : pathsVariables)
            routingComponents.set(pathVarPos, "{var}");

        /*
        for(int i = 0; i < routingComponents.size(); i++) {

            String path = String.join("/", routingComponents.subList(0, i+1));
            Route existingRoute = tempMap.get(path);

            int finalI = i;

            Route routeComponent = new Route(i == routingComponents.size() - 1 ? service : existingRoute != null ? existingRoute.service() : null,
                    IntStream.of(pathsVariables).anyMatch(x -> x == (finalI + 1)) || (existingRoute != null && existingRoute.hasPath()));

            tempMap.put(path, routeComponent);
        }
         */

        for (int i = 0; i < routingComponents.size(); i++) {
            String path = String.join("/", routingComponents.subList(0, i+1));
            Route existingRoute = tempMap.get(path);
            int finalI = i;
            Route routeComponent = new Route(i == routingComponents.size() - 1 ? service : existingRoute != null ? existingRoute.service() : null, IntStream.of(pathsVariables).anyMatch(x -> x == (finalI + 1)) || (existingRoute != null && existingRoute.hasPath()));
            tempMap.put(path, routeComponent);
        }

    }

    public Service resolveRoute(final HTTPMethod method, final String route) {
        System.out.println("Resolving route: " + route);
        String[] routeComponents = route.split("/");

        int i = 1;

        Route component = this.routeMap.get(method).get("/" + routeComponents[1]);

        for (String search = "/" + routeComponents[i]; component != null && (component.service() == null || routeComponents.length - 1 > i) && routeComponents.length - 1 >= i; component = this.routeMap.get(method).get(search = routeComponents.length - 1 > i++ ? String.join("/", search, routeComponents[i]) : search)) {
            if (component.hasPath() && routeComponents.length - 1 > i) {
                ++i;
                search = String.join("/", search, "{var}");
            }
        }

        if (component == null || component.service() == null)
            return null;

        return component.service();
    }
}
