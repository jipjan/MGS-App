package com.example.loisgussenhoven.walkabout.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.loisgussenhoven.walkabout.model.Pinpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jaap-Jan on 12-12-2017.
 */

public class DataController extends SQLiteOpenHelper {

    private static final String DBNAME = "MGS";
    private static final int VERSION = 1;

    private List<Pinpoint> session;
    private HashMap<String, String> routes;


    public DataController(Context c) {
        super(c, DBNAME, null, VERSION);
    }

    public String getRouteInformation(String name) {
        return routes.get(name);
    }

    public void setNewSession() {
        session = new ArrayList<Pinpoint>();
    }

    public void addPinpointToSession(Pinpoint p) {
        session.add(p);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qryCreate = "CREATE TABLE " + DBNAME + ".RoutePoint(Id INT PRIMARY KEY, Noorderbreedte FLOAT, Oosterlengte FLOAT, Naam TEXT, Opmerking TEXT)";
        String qryInsert =
                "INSERT INTO Pinpoint VALUES (`1`, `51,59411`, `4,779417`, `VVV`, `Beginpunt`);\n" +
                "INSERT INTO Pinpoint VALUES (`2`, `51,59328`, `4,779388`, `Liefdeszuster`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`3`, `51,5925`, `4,779695`, `Nassau Baronie Monument`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`4`, `51,5925`, `4,779388`, ``, `Pad ten westen van monument`);\n" +
                "INSERT INTO Pinpoint VALUES (`5`, `51,59283`, `4,778471`, `The Light House`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`6`, `51,59267`, `4,777917`, ``, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`7`, `51,59061`, `4,777`, ``, `Einde park`);\n" +
                "INSERT INTO Pinpoint VALUES (`8`, `51,59061`, `4,776167`, `Kasteel van Breda`, `Kasteelplein`);\n" +
                "INSERT INTO Pinpoint VALUES (`9`, `51,58969`, `4,776138`, `Stadhouderspoort`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`10`, `51,59033`, `4,776`, ``, `Kruising Kasteelplein/Cingelstraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`11`, `51,59039`, `4,775`, ``, `Bocht Cingelstraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`12`, `51,59003`, `4,774362`, `Huis van Brecht (rechter zijde)`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`13`, `51,59019`, `4,773445`, `Spanjaardsgat (rechter zijde)`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`14`, `51,58983`, `4,773333`, `Begin Vismarkt`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`15`, `51,58936`, `4,774445`, `Begin Havermarkt`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`16`, `51,58878`, `4,774889`, ``, `Kruising Torenstraat/Kerkplein`);\n" +
                "INSERT INTO Pinpoint VALUES (`17`, `51,58883`, `4,775279`, `Grote Kerk`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`18`, `51,58878`, `4,774889`, ``, `Kruising Torenstraat/Kerkplein`);\n" +
                "INSERT INTO Pinpoint VALUES (`19`, `51,5882`, `4,775138`, `Het Poortje`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`20`, `51,58708`, `4,77575`, `Ridderstraat`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`21`, `51,58742`, `4,776555`, `Grote Markt`, `Zuidpunt Grote Markt`);\n" +
                "INSERT INTO Pinpoint VALUES (`22`, `51,58803`, `4,776333`, `Bevrijdingsmonument`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`23`, `51,58875`, `4,776112`, `Stadhuis`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`24`, `51,58797`, `4,776361`, ``, `Terug naar begin Grote Markt`);\n" +
                "INSERT INTO Pinpoint VALUES (`25`, `51,5875`, `4,776555`, ``, `Zuidpunt Grote Markt`);\n" +
                "INSERT INTO Pinpoint VALUES (`26`, `51,58764`, `4,77725`, `Antonius van Paduakerk`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`27`, `51,58828`, `4,7785`, ``, `Kruising St.Janstraat/Molenstraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`28`, `51,588`, `4,778945`, `Bibliotheek`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`29`, `51,58736`, `4,780221`, ``, `Kruising Molenstraat/Kloosterplein`);\n" +
                "INSERT INTO Pinpoint VALUES (`30`, `51,58772`, `4,781028`, `Kloosterkazerne`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`31`, `51,58775`, `4,782`, `Chasse theater`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`32`, `51,58775`, `4,78125`, ``, `Kruising Kloosterplein/Vlaszak`);\n" +
                "INSERT INTO Pinpoint VALUES (`33`, `51,58861`, `4,780889`, `Binding van Isaac`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`34`, `51,5895`, `4,780445`, ``, `Kruising Vlaszak/Boschstraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`35`, `51,58967`, `4,781`, `Beyerd`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`36`, `51,5895`, `4,780445`, ``, `Kruising Vlaszak/Boschstraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`37`, `51,58955`, `4,78`, `Gasthuispoort`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`38`, `51,58942`, `4,779862`, ``, `Ingang Veemarktstraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`39`, `51,58903`, `4,779695`, ``, `1e bocht Veemarktstraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`40`, `51,58855`, `4,778333`, ``, `Kruising Veemarktstraat/St.Annastraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`41`, `51,58911`, `4,777945`, `Willem Merkxtuin`, `Ingang Willem Merkxtuin`);\n" +
                "INSERT INTO Pinpoint VALUES (`42`, `51,58967`, `4,777805`, ``, `Kruising St.Annastraat/Catharinastraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`43`, `51,58969`, `4,778362`, `Begijnenhof`, `Ingang Begijnenhof`);\n" +
                "INSERT INTO Pinpoint VALUES (`44`, `51,58967`, `4,777805`, ``, `Kruising St.Annastraat/Catharinastraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`45`, `51,5895`, `4,77625`, `Einde stadswandeling`, `Eindpunt`);\n";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(qryCreate);
        db.execSQL(qryInsert);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Niet relevant
    }
}
