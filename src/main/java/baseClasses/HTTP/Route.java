package baseClasses.HTTP;

import baseClasses.Services.Service;

public record Route(Service service, Boolean hasPath) {
}
