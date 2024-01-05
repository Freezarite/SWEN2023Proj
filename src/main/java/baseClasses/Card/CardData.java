package baseClasses.Card;

import java.util.UUID;

public record CardData(UUID id, String name, int damage) {
}
