import org.junit.Assert;
import org.junit.Test;
import gamesPackage.Game;

public class gamesDeleteTest {
    @Test
    public void testDelete(){
        Game.load("Test/gamesTest.xml");
        Assert.assertEquals(4, Game.Games.size());
        Game.Games.remove(1);
        Assert.assertEquals(3, Game.Games.size());
        Game.Games.clear();
    }
}
