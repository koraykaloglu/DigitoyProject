package service;

import dto.DiziliElDTO;

import java.util.*;

public class OkeyService
{
    private static final List<Integer> taslar = new ArrayList<>();

    private final Random random = new Random();

    private int okey;

    private int[] oyuncu1;
    private int[] oyuncu2;
    private int[] oyuncu3;
    private int[] oyuncu4;

    public OkeyService()
    {
        for (int i = 0; i < 53; i++)
        {
            taslar.add(i);
            taslar.add(i);
        }
    }

    public List<Integer> getTaslar()
    {
        return taslar;
    }

    public void printArray(List<Integer> arr)
    {
        System.out.println("Array:");

        for (Integer integer : arr) System.out.print(integer + " ");
    }

    public void printArrays(int[] arr)
    {
        for (Integer integer : arr) System.out.print(integer + " ");
        System.out.println();
    }

    public int getGostergeIndex()
    {
        int randomIndex;

        do
        {
            randomIndex = random.nextInt(taslar.size());
        }
        while (randomIndex == 52);

        return randomIndex;
    }

    public void getOkey(int gosterge)
    {
        if (gosterge == 12 || gosterge == 25 || gosterge == 38 || gosterge == 51)
            okey = gosterge - 12;
        else
            okey = gosterge + 1;
    }

    public void getOyuncuElleri()
    {
        int x = random.nextInt(4) + 1;

        switch (x)
        {
            case 1 ->
            {
                oyuncu1 = baslayanOyuncuEli();
                oyuncu2 = oyuncuEli();
                oyuncu3 = oyuncuEli();
                oyuncu4 = oyuncuEli();
            }
            case 2 ->
            {
                oyuncu1 = oyuncuEli();
                oyuncu2 = baslayanOyuncuEli();
                oyuncu3 = oyuncuEli();
                oyuncu4 = oyuncuEli();
            }
            case 3 ->
            {
                oyuncu1 = oyuncuEli();
                oyuncu2 = oyuncuEli();
                oyuncu3 = baslayanOyuncuEli();
                oyuncu4 = oyuncuEli();
            }
            case 4 ->
            {
                oyuncu1 = oyuncuEli();
                oyuncu2 = oyuncuEli();
                oyuncu3 = oyuncuEli();
                oyuncu4 = baslayanOyuncuEli();
            }
        }
    }

    private int[] baslayanOyuncuEli()
    {
        int[] result = new int[15];

        for (int i = 0; i < 15; i++)
        {
            int randomIndex = random.nextInt(taslar.size());
            result[i] = taslar.get(randomIndex);
            taslar.remove(randomIndex);
        }

        return result;
    }

    private int[] oyuncuEli()
    {
        int[] result = new int[14];

        for (int i = 0; i < 14; i++)
        {
            int randomIndex = random.nextInt(taslar.size());
            result[i] = taslar.get(randomIndex);
            taslar.remove(randomIndex);
        }

        return result;
    }

    public void getKazanmayaEnYakinOyuncu()
    {
        DiziliElDTO oyuncu_1 = oyuncuEliDiz(oyuncu1);
        DiziliElDTO oyuncu_2 = oyuncuEliDiz(oyuncu2);
        DiziliElDTO oyuncu_3 = oyuncuEliDiz(oyuncu3);
        DiziliElDTO oyuncu_4 = oyuncuEliDiz(oyuncu4);

        List<Integer> artanTasSayilari = new ArrayList<>();
        artanTasSayilari.add(oyuncu_1.getArtanTasSayisi());
        artanTasSayilari.add(oyuncu_2.getArtanTasSayisi());
        artanTasSayilari.add(oyuncu_3.getArtanTasSayisi());
        artanTasSayilari.add(oyuncu_4.getArtanTasSayisi());

        System.out.println("---------- Her oyuncunun dizili elleri ----------");
        System.out.print("Oyuncu 1: ");
        printArray(oyuncu_1.getEl());
        System.out.print("Oyuncu 2: ");
        printArray(oyuncu_2.getEl());
        System.out.print("Oyuncu 3: ");
        printArray(oyuncu_3.getEl());
        System.out.print("Oyuncu 4: ");
        printArray(oyuncu_4.getEl());
        System.out.println("\n---------- Artan taş sayısı en az olan oyuncu kazanmaya en yakın olandır ----------");
        System.out.print("Kazanmaya en yakın oyuncu: ");
        int index = artanTasSayilari.indexOf(Collections.min(artanTasSayilari));
        if (index == 0)
            System.out.print("Oyuncu 1");
        else if (index == 1)
            System.out.print("Oyuncu 2");
        else if (index == 2)
            System.out.print("Oyuncu 3");
        else if (index == 3)
            System.out.print("Oyuncu 4");
    }

    private DiziliElDTO oyuncuEliDiz(int[] oyuncuEli)
    {
        Arrays.sort(oyuncuEli);

        int[] temp = Arrays.copyOf(oyuncuEli, oyuncuEli.length);
        DiziliElDTO seriDTO = seriOncelikliDizilim(temp);
        temp = Arrays.copyOf(oyuncuEli, oyuncuEli.length);

        DiziliElDTO askerDTO = askerOncelikliDizilim(temp);
        temp = Arrays.copyOf(oyuncuEli, oyuncuEli.length);

        DiziliElDTO ciftDTO = ciftDizilim(temp);

        int min = Math.min(seriDTO.getArtanTasSayisi(), Math.min(askerDTO.getArtanTasSayisi(), ciftDTO.getArtanTasSayisi()));

        if (seriDTO.getArtanTasSayisi() == min)
            return seriDTO;
        else if (askerDTO.getArtanTasSayisi() == min)
            return askerDTO;
        else
            return ciftDTO;
    }

    private DiziliElDTO seriOncelikliDizilim(int[] oyuncuEli)
    {
        int[] diziliEl = new int[oyuncuEli.length];
        int diziliElIndex = 0;

        int artanTasSayisi = oyuncuEli.length;
        int count = 1;

        for (int i = 1; i < oyuncuEli.length; i++)
        {
            if ((oyuncuEli[i] + 1) % 13 == 0)
            {
                if (count > 2)
                {
                    for (int j = i - count; j < i; j++)
                    {
                        diziliEl[diziliElIndex] = oyuncuEli[j];
                        oyuncuEli[j] = -1;
                        diziliElIndex++;
                    }
                    artanTasSayisi -= count;
                    count = 1;
                }
                else
                {
                    count = 1;
                    break;
                }
            }
            else if (oyuncuEli[i] == oyuncuEli[i-1] + 1)
                count ++;
            else if (count > 2)
            {
                for (int j = i - count; j < i; j++)
                {
                    diziliEl[diziliElIndex] = oyuncuEli[j];
                    oyuncuEli[j] = -1;
                    diziliElIndex++;
                }
                artanTasSayisi -= count;
                count = 1;
            }
            else
                count = 1;
        }

        int oncekiTas = -1;
        List<Integer> askerDizilim = new ArrayList<>();
        for (int i = 0; i < oyuncuEli.length - 1; i++)
        {
            if (oncekiTas == -1 && oyuncuEli[i] != -1)
            {
                oncekiTas = oyuncuEli[i];
                askerDizilim.add(oncekiTas);
            }
            else
            {
                askerDizilim.clear();
                continue;
            }

            for (int j = i + 1; j < oyuncuEli.length; j++)
            {
                if (oyuncuEli[j] == oncekiTas + 13)
                    askerDizilim.add(oyuncuEli[j]);
                else if (askerDizilim.size() > 2)
                {
                    for (int k = diziliElIndex; k < askerDizilim.size(); k++)
                    {
                        diziliEl[k] = askerDizilim.get(k);
                        diziliElIndex++;
                    }
                    artanTasSayisi -= askerDizilim.size();
                    askerDizilim.clear();
                }
                else
                    askerDizilim.clear();
            }
        }

        for (int tas : oyuncuEli)
        {
            if (tas != -1)
            {
                diziliEl[diziliElIndex] = tas;
                diziliElIndex++;
            }
        }

        DiziliElDTO diziliElDTO = new DiziliElDTO();
        diziliElDTO.setEl(diziliEl);
        diziliElDTO.setArtanTasSayisi(artanTasSayisi);

        return diziliElDTO;
    }

    private DiziliElDTO askerOncelikliDizilim(int[] oyuncuEli)
    {
        int[] diziliEl = new int[oyuncuEli.length];
        int diziliElIndex = 0;
        int artanTasSayisi = oyuncuEli.length;

        int oncekiTas = -1;
        List<Integer> askerDizilim = new ArrayList<>();
        for (int i = 0; i < oyuncuEli.length - 1; i++)
        {
            if (oncekiTas == -1 && oyuncuEli[i] != -1)
            {
                oncekiTas = oyuncuEli[i];
                askerDizilim.add(oncekiTas);
            }
            else
            {
                askerDizilim.clear();
                continue;
            }

            for (int j = i + 1; j < oyuncuEli.length; j++)
            {
                if (oyuncuEli[j] == oncekiTas + 13)
                    askerDizilim.add(oyuncuEli[j]);
                else if (askerDizilim.size() > 2)
                {
                    for (int k = diziliElIndex; k < askerDizilim.size(); k++)
                    {
                        diziliEl[k] = askerDizilim.get(k);
                        diziliElIndex++;
                    }
                    artanTasSayisi -= askerDizilim.size();
                    askerDizilim.clear();
                }
                else
                    askerDizilim.clear();
            }
        }

        int count = 1;

        for (int i = 1; i < oyuncuEli.length; i++)
        {
            if ((oyuncuEli[i] + 1) % 13 == 0)
            {
                if (count > 2)
                {
                    for (int j = i - count; j < i; j++)
                    {
                        diziliEl[diziliElIndex] = oyuncuEli[j];
                        oyuncuEli[j] = -1;
                        diziliElIndex++;
                    }
                    artanTasSayisi -= count;
                    count = 1;
                }
                else
                {
                    count = 1;
                    break;
                }
            }
            else if (oyuncuEli[i] == oyuncuEli[i-1] + 1)
                count ++;
            else if (count > 2)
            {
                for (int j = i - count; j < i; j++)
                {
                    diziliEl[diziliElIndex] = oyuncuEli[j];
                    oyuncuEli[j] = -1;
                    diziliElIndex++;
                }
                artanTasSayisi -= count;
                count = 1;
            }
            else
                count = 1;
        }

        for (int tas : oyuncuEli)
        {
            if (tas != -1)
            {
                diziliEl[diziliElIndex] = tas;
                diziliElIndex++;
            }
        }

        DiziliElDTO diziliElDTO = new DiziliElDTO();
        diziliElDTO.setEl(diziliEl);
        diziliElDTO.setArtanTasSayisi(artanTasSayisi);

        return diziliElDTO;
    }

    private DiziliElDTO ciftDizilim(int[] oyuncuEli)
    {
        int[] diziliEl = new int[oyuncuEli.length];
        int diziliElIndex = 0;
        int artanTasSayisi = oyuncuEli.length;

        for (int i = 0; i < oyuncuEli.length - 1; i++)
        {
            for (int j = i+1; j < oyuncuEli.length; j++)
            {
                if (oyuncuEli[i] == oyuncuEli[j])
                {
                    diziliEl[diziliElIndex] = oyuncuEli[i];
                    diziliEl[diziliElIndex + 1] = oyuncuEli[i];
                    diziliElIndex += 2;
                    artanTasSayisi -= 2;
                    oyuncuEli[i] = -1;
                    oyuncuEli[j] = -1;
                    break;
                }
            }
        }

        for (int tas : oyuncuEli)
        {
            if (tas != -1 && diziliElIndex < diziliEl.length)
            {
                diziliEl[diziliElIndex] = tas;
                diziliElIndex++;
            }
        }

        DiziliElDTO diziliElDTO = new DiziliElDTO();
        diziliElDTO.setEl(diziliEl);
        diziliElDTO.setArtanTasSayisi(artanTasSayisi);

        return diziliElDTO;
    }

    private void printArray(int[] arr)
    {
        for (int element: arr)
        {
            System.out.print(element + " ");
        }

        System.out.println();
    }

    public int[] getOyuncu1() {return oyuncu1;}

    public int[] getOyuncu2() {return oyuncu2;}

    public int[] getOyuncu3() {return oyuncu3;}

    public int[] getOyuncu4() {return oyuncu4;}
}
