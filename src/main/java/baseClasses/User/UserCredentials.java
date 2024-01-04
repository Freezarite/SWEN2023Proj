package baseClasses.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.junit.Ignore;


@JsonIgnoreProperties(ignoreUnknown = true)
public record UserCredentials(String username, String password) {

}
