package baseClasses.HTTP;

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

        for(int i = 0; i < routingComponents.size(); i++) {

            String path = String.join("/", routingComponents.subList(0, i+1));
            Route existingRoute = tempMap.get(path);

            int finalI = i;

            Route routeComponent = new Route(i == routingComponents.size() - 1 ? service : existingRoute != null ? existingRoute.service() : null,
                    IntStream.of(pathsVariables).anyMatch(x -> x == (finalI + 1)) || (existingRoute != null && existingRoute.hasPath()));

            tempMap.put(path, routeComponent);
        }

    }

    public Service resolveRoute(final HTTPMethod method, final String route) {
        System.out.println("Resolving route: " + route);
        String[] routeComponents = route.split("/");

        String path = null;

        Route component = this.routeMap.get(method).get("/" + routeComponents[1]);

        for (int i = 1; component != null && (component.service() == null || i < routeComponents.length - 1); i++) {
            String currentPathComponent = routeComponents[i];
            String currentPath = "/" + currentPathComponent;
            component = this.routeMap.get(method).get(currentPath);

            if (component != null) {
                if (component.hasPath() && i < routeComponents.length - 1) {
                    path = routeComponents[++i];
                    currentPath += "/{var}";
                }
            }
        }
        if(component == null)
            return null;

        return component.service();
    }
}
