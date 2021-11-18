import lombok.Getter;
import lombok.ToString;

import java.util.Random;
import java.util.stream.IntStream;

@ToString
@Getter
public class DndCharacter {
    @ToString.Exclude
    private Random r = new Random();

    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private int hitPoint;

    public DndCharacter(){
        strength = generateAbilityValue();
        dexterity = generateAbilityValue();
        constitution = generateAbilityValue();
        intelligence = generateAbilityValue();
        wisdom = generateAbilityValue();
        charisma = generateAbilityValue();

        hitPoint = 10 + modifier(constitution);
    }

    private int modifier (int constitution) {
        return (int)Math.floor(((double) constitution - 10) / 2);
    }

    private int generateAbilityValue() {
        return IntStream.generate(() -> r.nextInt(7)).limit(4).sorted().skip(1).sum();
    }
}
