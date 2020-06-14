package org.d3ifcool.cubeacon;

import java.util.ArrayList;

public class Route {

    private String[] beacon01 = {"beacon01", "beacon02", "kantin", "dapur", "g1", "g5", "g6", "g7","lol"};
    private String[] beacon02 = {"beacon02", "beacon01", "beacon03", "mpmart", "laboran", "lol"};
    private String[] beacon03 = {"beacon03", "beacon02", "g9", "beacon04"};
    private String[][] beacon = {beacon01, beacon02, beacon03};

    private ArrayList<String> rute;
    private ArrayList<String> visited;
    private ArrayList<String[]> neightboar;
    private ArrayList<String> rutes = new ArrayList<>();

    public Route() {
    }

    public ArrayList<String> findWay(String start, String end){

        String pinstart[] = new String[0];
        for (int i = 0; i < beacon.length; i++) {
            if (start.equalsIgnoreCase(beacon[i][0])){
                pinstart = beacon[i];
            }
        }

        wayTracking(pinstart, end);

        return rutes;
    }

    public void wayTracking(String[] starts, String end){

        String pos[] = starts;
        boolean meet = false;
        boolean way = false;
        boolean beaconExist = false;

        if (visited.isEmpty()){
            visited.add(pos[0]);
        }else {
            for (int i = 0; i < visited.size(); i++) {
                if (!pos[0].matches(visited.get(i))){
                    visited.add(pos[0]);
                }
            }
        }

        while (!meet) {
            if (neightboar.isEmpty()) {
                // Perulangan pencarian
                for (int i = 0; i < pos.length; i++) {
                    // Jika tetangga sama dengan tujuan akhir
                    if (pos[i].equalsIgnoreCase(end)) {
                        rute.add(pos[0]);
                        rute.add(end);
                        way = true;
                        meet = true;
                    }
                    // Periksa adakah tetangga beacon
                    else if (pos[i].equalsIgnoreCase(pos[pos.length - 1])) {
                        // Perulangan mencari tetangga beacon
                        for (int j = 0; j < pos.length; j++) {
                            for (int k = 0; k < beacon.length; k++) {
                                // Jika ada tetangga beacon
                                if (pos[j].equalsIgnoreCase(beacon[k][0])) {
                                    for (int l = 0; l < visited.size(); l++) {
                                        if (!pos[j].matches(visited.get(l))){
                                            neightboar.add(beacon[k]);
                                            beaconExist = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (beaconExist) {
                    rute.add(pos[0]);
                    pos = neightboar.get(0);
                    neightboar.remove(0);
                }

//                meet = true;
            }
            else {
                // Jika tetangga sama dengan tujuan akhir
                for (String po : pos) {
                    if (po.equalsIgnoreCase(end)) {
                        rute.add(pos[0]);
                        rute.add(end);
                        way = true;
                        meet = true;
                    }
                }
                // Jika tetangga tidak sama dengan tujuan akhir
                pos = neightboar.get(0);
                neightboar.remove(0);
//                meet = true;
            }
        }

        if (way) {
//            showPath(start, end);
            rutes = rute;
        } else {
            wayTracking(pos, end);
        }

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

                sign.add("Move Forward");
                sign.add("Turn Right");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("kitchen")){
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("dapur");

                sign.add("Move Forward");
                sign.add("Slight right");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("g1")){
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("g1");

                sign.add("Move Forward");
                sign.add("Move Forward");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("g5")){
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("g5");

                sign.add("Move Forward");
                sign.add("Slight left");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("g6")){
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("g6");

                sign.add("Move Forward");
                sign.add("Slight left");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("g7")){
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("g7");

                sign.add("Move Forward");
                sign.add("Turn Left");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("mp mart")){
                rute.add("beacon02");
                rute.add("mpmart");

                sign.add("Slight right");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("laboran")){
                rute.add("beacon02");
                rute.add("laboran");

                sign.add("on the right");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("g9")){
                rute.add("beacon02");
                rute.add("beacon03");
                rute.add("g9");

                sign.add("Move Forward");
                sign.add("Turn Left");
                sign.add("Arrive at destination");

            }
        }

        else if (start.equalsIgnoreCase("beacon01")){

            if(end.equalsIgnoreCase("kantin")){
                rute.add("beacon01");
                rute.add("kantin");

                sign.add("on the right");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("kitchen")){
                rute.add("beacon01");
                rute.add("dapur");

                sign.add("Slight right");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("g1")){
                rute.add("beacon01");
                rute.add("g1");

                sign.add("Move Forward");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("g5")){
                rute.add("beacon01");
                rute.add("g5");

                sign.add("Turn Left");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("g6")){
                rute.add("beacon01");
                rute.add("g6");

                sign.add("Turn Left");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("g7")){
                rute.add("beacon01");
                rute.add("g7");

                sign.add("Turn Left");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("mpmart")){
                rute.add("beacon01");
                rute.add("beacon02");
                rute.add("mpmart");

                sign.add("Move Forward");
                sign.add("Slight left");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("laboran")){
                rute.add("beacon01");
                rute.add("beacon02");
                rute.add("laboran");

                sign.add("Move Forward");
                sign.add("Move Forward");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("g9")){
                rute.add("beacon01");
                rute.add("beacon02");
                rute.add("beacon03");
                rute.add("g9");

                sign.add("Move Forward");
                sign.add("Turn Right");
                sign.add("Turn Left");
                sign.add("Arrive at destination");

            }
        }

        else if (start.equalsIgnoreCase("beacon03")){

            if(end.equalsIgnoreCase("kantin")){
                rute.add("beacon03");
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("kantin");

                sign.add("Move Forward");
                sign.add("Turn Left");
                sign.add("Turn Right");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("kitchen")){
                rute.add("beacon03");
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("dapur");

                sign.add("Move Forward");
                sign.add("Turn Left");
                sign.add("Slight right");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("g1")){
                rute.add("beacon03");
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("g1");

                sign.add("Move Forward");
                sign.add("Turn Left");
                sign.add("Move Forward");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("g5")){
                rute.add("beacon03");
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("g5");

                sign.add("Move Forward");
                sign.add("Turn Left");
                sign.add("Turn Left");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("g6")){
                rute.add("beacon03");
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("g6");

                sign.add("Move Forward");
                sign.add("Turn Left");
                sign.add("Turn Left");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("g7")){
                rute.add("beacon03");
                rute.add("beacon02");
                rute.add("beacon01");
                rute.add("g7");

                sign.add("Move Forward");
                sign.add("Turn Left");
                sign.add("Turn Left");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("mp mart")){
                rute.add("beacon03");
                rute.add("beacon02");
                rute.add("mpmart");

                sign.add("Move Forward");
                sign.add("Slight right");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("laboran")){
                rute.add("beacon03");
                rute.add("beacon02");
                rute.add("mpmart");

                sign.add("Move Forward");
                sign.add("Turn right");
                sign.add("Arrive at destination");

            }else if (end.equalsIgnoreCase("g9")){
                rute.add("beacon03");
                rute.add("g9");

                sign.add("on the right");
                sign.add("Arrive at destination");
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

            }
        }

        nav.add(rute);
        nav.add(sign);
        return nav;

    }

}
