package org.d3ifcool.cubeacon;

import android.content.Context;
import java.util.ArrayList;

public class Route {

    private ArrayList<String> unvisited = new ArrayList<>();
    private ArrayList<ArrayList<String>> neightboars = new ArrayList<>();
    private ArrayList<String> beacon01 = new ArrayList<>();
    private ArrayList<String> beacon02 = new ArrayList<>();
    private ArrayList<String> beacon03 = new ArrayList<>();
    private ArrayList<ArrayList<String>> beacon = new ArrayList<>();
    ArrayList<ArrayList<String>> routes = new ArrayList<>();
    private ArrayList<String> rutes = new ArrayList<>();

    private Context context;
    public Route(Context context) {
        this.context = context;
    }

    private void initDataAllBeacon() {
        beacon01.add("beacon01");
        beacon01.add("beacon02");
        beacon01.add("kantin");
        beacon01.add("kitchen");
        beacon01.add("g1");
        beacon01.add("g5");
        beacon01.add("g6");
        beacon01.add("g7");

        beacon02.add("beacon02");
        beacon02.add("mpmart");
        beacon02.add("gate");
        beacon02.add("beacon01");
        beacon02.add("laboran");
        beacon02.add("beacon03");

        beacon03.add("beacon03");
        beacon03.add("beacon02");
        beacon03.add("g9");
        beacon03.add("beacon04");

        beacon.add(beacon01);beacon.add(beacon02);beacon.add(beacon03);
        unvisited.add("beacon01");
        unvisited.add("beacon02");
        unvisited.add("beacon03");
    }

    public ArrayList<ArrayList<String>> findWay(String starts, String end){
        initDataAllBeacon();

        ArrayList<String> startPoint = new ArrayList<>();

        for (int i = 0; i < beacon.size() ; i++) {
            if (starts.equalsIgnoreCase(beacon.get(i).get(0))){
                startPoint = beacon.get(i);

                wayTracking(startPoint,end);
                ArrayList<ArrayList<String>> nav = new ArrayList<>();
                nav = findRoute(starts,end);

                if (end.equalsIgnoreCase("g9") && startPoint.get(0).equalsIgnoreCase("beacon01")){
                    rutes.remove(2);
                }
                if (end.equalsIgnoreCase("g9") && startPoint.get(0).equalsIgnoreCase("beacon02")){
                    rutes.remove(1);
                }

                routes.add(rutes);
                routes.add(nav.get(1));

                return routes;
            }
        }

        return routes;
    }

    public void wayTracking(ArrayList<String> starts, String end){
        boolean meet = false;
        boolean beaconExist = false;

        while (!meet) {
            // Periksa dulu start udah divisit belum
            for (int u = 0; u < unvisited.size(); u++) {
                // Kalo belum
                if (starts.get(0).equalsIgnoreCase(unvisited.get(u))) {
                    unvisited.remove(u);
                    // Periksa tetangga start
                    for (int i = 0; i < starts.size(); i++) {
                        // Kalo ketemu end
                        if (starts.get(i).equalsIgnoreCase(end)) {
                            // Ketemu! keluar dari while
                            rutes.add(starts.get(0));
                            rutes.add(end);
                            meet = true;
                        }
                        // Kalo ketemu tetangga yang id:beacon
                        else if (starts.get(i).equalsIgnoreCase(starts.get(starts.size()-1))){
                            for (String start1 : starts) {
                                for (ArrayList<String> aBeacon : beacon) {
                                    if (start1.equalsIgnoreCase(aBeacon.get(0)) &&
                                            !starts.get(0).equalsIgnoreCase(aBeacon.get(0))) {
                                        // Masukin tetangga ke neighboar, buat diperiksa nanti
                                        neightboars.add(aBeacon);
                                        // Ada tetangga dengan id:beacon
                                        beaconExist = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (beaconExist) {
                // Masukin start ke rute
                if (!meet){
                    rutes.add(starts.get(0));
                }
                // Pindahin posisi start ke tetangga pertama
                starts = neightboars.get(0);
                // Hapus tetangga yg jadi target pindah
                neightboars.remove(0);
            }
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
                rute.add("kitchen");

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
