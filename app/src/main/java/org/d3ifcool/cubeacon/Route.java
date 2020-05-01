package org.d3ifcool.cubeacon;

import java.util.ArrayList;

public class Route {

    public Route() {
    }

    public ArrayList<ArrayList<String>> findRoute(String start, String end){

        ArrayList<ArrayList<String>> nav = new ArrayList<>();

        ArrayList<String> rute = new ArrayList<>();
        ArrayList<String> sign = new ArrayList<>();

        if (start.equalsIgnoreCase("beacon02")){

            if(end.equalsIgnoreCase("kantin")){
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("kantin");

                sign.add("lurus");
                sign.add("belok kanan");
                sign.add("finish");

            }else if (end.equalsIgnoreCase("dapur")){
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("dapur");

                sign.add("lurus");
                sign.add("belok serong kanan");
                sign.add("finish");

            }else if (end.equalsIgnoreCase("g1")){
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("g1");

                sign.add("lurus");
                sign.add("lurus");
                sign.add("finish");

            }else if (end.equalsIgnoreCase("g5")){
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("g5");

                sign.add("lurus");
                sign.add("belok serong kiri");
                sign.add("finish");

            }else if (end.equalsIgnoreCase("g6")){
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("g6");

                sign.add("lurus");
                sign.add("belok serong kiri");
                sign.add("finish");

            }else if (end.equalsIgnoreCase("g7")){
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("g7");

                sign.add("lurus");
                sign.add("belok kiri");
                sign.add("finish");

            }else if (end.equalsIgnoreCase("mpmart")){
                rute.add("beacon02");
                rute.add("mpmart");

                sign.add("belok serong kanan");
                sign.add("finish");

            }else if (end.equalsIgnoreCase("laboran")){
                rute.add("beacon02");
                rute.add("laboran");

                sign.add("belok kanan");
                sign.add("finish");

            }else if (end.equalsIgnoreCase("g9")){
                rute.add("beacon02");
                rute.add("beacon03");
                rute.add("g9");

                sign.add("lurus");
                sign.add("belok kiri");
                sign.add("finish");

            }else if (end.equalsIgnoreCase("g10")){
                rute.add("beacon02");
                rute.add("beacon03");
                rute.add("beacon04");
                rute.add("g10");

                sign.add("lurus");
                sign.add("lurus");
                sign.add("belok kiri");
                sign.add("finish");

            }else if (end.equalsIgnoreCase("lobby")){
                rute.add("beacon02");
                rute.add("beacon03");
                rute.add("beacon04");
                rute.add("lobby");

                sign.add("lurus");
                sign.add("lurus");
                sign.add("belok kanan");
                sign.add("finish");

            }
        }

        else if (start.equalsIgnoreCase("beacon01")){

            if(end.equalsIgnoreCase("kantin")){

            }else if (end.equalsIgnoreCase("dapur")){

            }else if (end.equalsIgnoreCase("g1")){

            }else if (end.equalsIgnoreCase("g5")){

            }else if (end.equalsIgnoreCase("g6")){

            }else if (end.equalsIgnoreCase("g7")){

            }else if (end.equalsIgnoreCase("mpmart")){

            }else if (end.equalsIgnoreCase("laboran")){

            }else if (end.equalsIgnoreCase("g9")){

            }else if (end.equalsIgnoreCase("g10")){

            }else if (end.equalsIgnoreCase("lobby")){

            }
        }

        else if (start.equalsIgnoreCase("beacon03")){

            if(end.equalsIgnoreCase("kantin")){

            }else if (end.equalsIgnoreCase("dapur")){

            }else if (end.equalsIgnoreCase("g1")){

            }else if (end.equalsIgnoreCase("g5")){

            }else if (end.equalsIgnoreCase("g6")){

            }else if (end.equalsIgnoreCase("g7")){

            }else if (end.equalsIgnoreCase("mpmart")){

            }else if (end.equalsIgnoreCase("laboran")){

            }else if (end.equalsIgnoreCase("g9")){

            }else if (end.equalsIgnoreCase("g10")){

            }else if (end.equalsIgnoreCase("lobby")){

            }
        }

        else if (start.equalsIgnoreCase("beacon04")){

            if(end.equalsIgnoreCase("kantin")){

            }else if (end.equalsIgnoreCase("dapur")){

            }else if (end.equalsIgnoreCase("g1")){

            }else if (end.equalsIgnoreCase("g5")){

            }else if (end.equalsIgnoreCase("g6")){

            }else if (end.equalsIgnoreCase("g7")){

            }else if (end.equalsIgnoreCase("mpmart")){

            }else if (end.equalsIgnoreCase("laboran")){

            }else if (end.equalsIgnoreCase("g9")){

            }else if (end.equalsIgnoreCase("g10")){

            }else if (end.equalsIgnoreCase("lobby")){

            }
        }

        nav.add(rute);
        nav.add(sign);
        return nav;

    }

}
