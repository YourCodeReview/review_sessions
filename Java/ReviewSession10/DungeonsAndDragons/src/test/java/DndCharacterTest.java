import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DndCharacterTest {
    @Test
    @DisplayName("modifier of 0 = -5")
    void modifierTest0(){
        Assertions.assertEquals(-5,DndCharacter.modifier(0));
    }
    @Test
    @DisplayName("modifier of 1 = -5")
    void modifierTest1(){
        Assertions.assertEquals(-5,DndCharacter.modifier(1));
    }
    @Test
    @DisplayName("modifier of 2 = -4")
    void modifierTest2(){
        Assertions.assertEquals(-4,DndCharacter.modifier(2));
    }
    @Test
    @DisplayName("modifier of 3 = -4")
    void modifierTest3(){
        Assertions.assertEquals(-4,DndCharacter.modifier(3));
    }
    @Test
    @DisplayName("modifier of 4 = -3")
    void modifierTest4(){
        Assertions.assertEquals(-3,DndCharacter.modifier(4));
    }
    @Test
    @DisplayName("modifier of 5 = -3")
    void modifierTest5(){
        Assertions.assertEquals(-3,DndCharacter.modifier(5));
    }
    @Test
    @DisplayName("modifier of 6 = -2")
    void modifierTest6(){
        Assertions.assertEquals(-2,DndCharacter.modifier(6));
    }
    @Test
    @DisplayName("modifier of 7 = -2")
    void modifierTest7(){
        Assertions.assertEquals(-2,DndCharacter.modifier(7));
    }
    @Test
    @DisplayName("modifier of 8 = -1")
    void modifierTest8(){
        Assertions.assertEquals(-1,DndCharacter.modifier(8));
    }
    @Test
    @DisplayName("modifier of 9 = -1")
    void modifierTest9(){
        Assertions.assertEquals(-1,DndCharacter.modifier(9));
    }
    @Test
    @DisplayName("modifier of 10 = 0")
    void modifierTest10(){
        Assertions.assertEquals(0,DndCharacter.modifier(10));
    }
    @Test
    @DisplayName("modifier of 11 = 0")
    void modifierTest11(){
        Assertions.assertEquals(0,DndCharacter.modifier(11));
    }
    @Test
    @DisplayName("modifier of 12 = 1")
    void modifierTest12(){
        Assertions.assertEquals(1,DndCharacter.modifier(12));
    }
    @Test
    @DisplayName("modifier of 13 = 1")
    void modifierTest13(){
        Assertions.assertEquals(1,DndCharacter.modifier(13));
    }
    @Test
    @DisplayName("modifier of 14 = 2")
    void modifierTest14(){
        Assertions.assertEquals(2,DndCharacter.modifier(14));
    }
    @Test
    @DisplayName("modifier of 15 = 2")
    void modifierTest15(){
        Assertions.assertEquals(2,DndCharacter.modifier(15));
    }
    @Test
    @DisplayName("modifier of 16 = 3")
    void modifierTest16(){
        Assertions.assertEquals(3,DndCharacter.modifier(16));
    }
}
