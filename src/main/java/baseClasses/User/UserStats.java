package baseClasses.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserStats(String name, int elo, int wins, int losses, double wnr) {
}
