package com.example.tadziu.forrunners1;

/**
 * Created by TADZIU on 2017-01-14.
 */

public class Kalorie {

   public double spaloneKalorieBieganie(double odl, int waga)
    {
        return odl * waga * 1.5;

    }

    public double spaloneKalorieSpacer(double odl)
    {
        return 55*odl;
    }

    public double spaloneKalorieRower(double odl)
    {
        return 25*odl; //
    }

    //ze stronki http://www.poradnikzdrowie.pl/diety/cwiczenia/ile-kalorii-spalamy-podczas-roznych-czynnosci-tabela-spalania-kalorii_41582.html
}
