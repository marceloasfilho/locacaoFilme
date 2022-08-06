import br.ce.wcaquino.entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;

public class AssertTests {
    @Test
    public void test(){
        Assert.assertTrue(1 == 1);
        Assert.assertEquals(1, 1, 0.0);
        Assert.assertFalse(false);

        Assert.assertEquals(2, 2);
        Assert.assertEquals(0.51234, 0.512, 0.001);

        Assert.assertEquals(Math.PI, 3.14, 0.01);

        int i1 = 5;
        Integer i2 = 5;

        Assert.assertEquals(Integer.valueOf(i1), i2);
        Assert.assertEquals(i1, i2.intValue());

        Assert.assertEquals("bola", "bola");
        Assert.assertNotEquals("bola", "casa");
        Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
        Assert.assertTrue("bola".startsWith("bo"));

        Usuario usuario1 = new Usuario("Usuario 1");
        Usuario usuario2 = new Usuario("Usuario 1");
        Usuario usuario3 = null;

        Assert.assertEquals(usuario1, usuario2);
        Assert.assertSame(usuario2, usuario2);
        Assert.assertNull(usuario3);
        Assert.assertNotNull(usuario1);

    }
}
