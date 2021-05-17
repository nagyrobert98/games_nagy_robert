import org.junit.Assert;
import org.junit.Test;
import gamesPackage.Game;
import gamesPackage.Platform;


public class gamesAddTest {

    @Test
    public void testAdd(){
        int size = Game.Games.size();

        Game.add("Sims",2018, "EA","Városépítő", new Platform[]{Platform.PC,Platform.XBOX});
        Assert.assertEquals(size+1, Game.Games.size());
        Game.Games.clear();
    }

}

