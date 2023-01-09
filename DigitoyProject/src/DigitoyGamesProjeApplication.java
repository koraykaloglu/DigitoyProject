import service.OkeyService;

import java.util.Collections;
import java.util.List;

public class DigitoyGamesProjeApplication
{
    public static void main(String[] args)
    {
        OkeyService okeyService = new OkeyService();

        List<Integer> taslar = okeyService.getTaslar();

        Collections.shuffle(taslar);

        int gostergeIndex = okeyService.getGostergeIndex();
        int gosterge = taslar.get(gostergeIndex);

        taslar.remove(gostergeIndex);

        okeyService.getOkey(gosterge);

        okeyService.getOyuncuElleri();

        okeyService.getKazanmayaEnYakinOyuncu();
    }
}