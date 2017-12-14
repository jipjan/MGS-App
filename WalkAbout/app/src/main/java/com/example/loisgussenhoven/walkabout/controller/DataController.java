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
        String qryInsert = "INSERT INTO "  + DBNAME +  ".RoutePoint VALUES (`1`, `51,35647`, `4,46765`, `VVV`, `Beginpunt`);\n" +
                "INSERT INTO Pinpoint VALUES (`2`, `51,35597`, `4,467633`, `Liefdeszuster`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`3`, `51,3555`, `4,467817`, `Nassau Baronie Monument`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`4`, `51,3555`, `4,467633`, ``, `Pad ten westen van monument`);\n" +
                "INSERT INTO Pinpoint VALUES (`5`, `51,3557`, `4,467083`, `The Light House`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`6`, `51,3556`, `4,46675`, ``, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`7`, `51,35437`, `4,4662`, ``, `Einde park`);\n" +
                "INSERT INTO Pinpoint VALUES (`8`, `51,35437`, `4,4657`, `Kasteel van Breda`, `Kasteelplein`);\n" +
                "INSERT INTO Pinpoint VALUES (`9`, `51,35382`, `4,465683`, `Stadhouderspoort`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`10`, `51,3542`, `4,4656`, ``, `Kruising Kasteelplein/Cingelstraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`11`, `51,35423`, `4,465`, ``, `Bocht Cingelstraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`12`, `51,35402`, `4,464617`, `Huis van Brecht (rechter zijde)`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`13`, `51,35412`, `4,464067`, `Spanjaardsgat (rechter zijde)`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`14`, `51,3539`, `4,464`, `Begin Vismarkt`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`15`, `51,35362`, `4,464667`, `Begin Havermarkt`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`16`, `51,35327`, `4,464933`, ``, `Kruising Torenstraat/Kerkplein`);\n" +
                "INSERT INTO Pinpoint VALUES (`17`, `51,3533`, `4,465167`, `Grote Kerk`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`18`, `51,35327`, `4,464933`, ``, `Kruising Torenstraat/Kerkplein`);\n" +
                "INSERT INTO Pinpoint VALUES (`19`, `51,35292`, `4,465083`, `Het Poortje`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`20`, `51,35225`, `4,46545`, `Ridderstraat`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`21`, `51,35245`, `4,465933`, `Grote Markt`, `Zuidpunt Grote Markt`);\n" +
                "INSERT INTO Pinpoint VALUES (`22`, `51,35282`, `4,4658`, `Bevrijdingsmonument`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`23`, `51,35325`, `4,465667`, `Stadhuis`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`24`, `51,35278`, `4,465817`, ``, `Terug naar begin Grote Markt`);\n" +
                "INSERT INTO Pinpoint VALUES (`25`, `51,3525`, `4,465933`, ``, `Zuidpunt Grote Markt`);\n" +
                "INSERT INTO Pinpoint VALUES (`26`, `51,35258`, `4,46635`, `Antonius van Paduakerk`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`27`, `51,35297`, `4,4671`, ``, `Kruising St.Janstraat/Molenstraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`28`, `51,3528`, `4,467367`, `Bibliotheek`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`29`, `51,35242`, `4,468133`, ``, `Kruising Molenstraat/Kloosterplein`);\n" +
                "INSERT INTO Pinpoint VALUES (`30`, `51,35263`, `4,468617`, `Kloosterkazerne`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`31`, `51,35265`, `4,4692`, `Chasse theater`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`32`, `51,35265`, `4,46875`, ``, `Kruising Kloosterplein/Vlaszak`);\n" +
                "INSERT INTO Pinpoint VALUES (`33`, `51,35317`, `4,468533`, `Binding van Isaac`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`34`, `51,3537`, `4,468267`, ``, `Kruising Vlaszak/Boschstraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`35`, `51,3538`, `4,4686`, `Beyerd`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`36`, `51,3537`, `4,468267`, ``, `Kruising Vlaszak/Boschstraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`37`, `51,35373`, `4,468`, `Gasthuispoort`, ``);\n" +
                "INSERT INTO Pinpoint VALUES (`38`, `51,35365`, `4,467917`, ``, `Ingang Veemarktstraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`39`, `51,35342`, `4,467817`, ``, `1e bocht Veemarktstraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`40`, `51,35313`, `4,467`, ``, `Kruising Veemarktstraat/St.Annastraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`41`, `51,35347`, `4,466767`, `Willem Merkxtuin`, `Ingang Willem Merkxtuin`);\n" +
                "INSERT INTO Pinpoint VALUES (`42`, `51,3538`, `4,466683`, ``, `Kruising St.Annastraat/Catharinastraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`43`, `51,35382`, `4,467017`, `Begijnenhof`, `Ingang Begijnenhof`);\n" +
                "INSERT INTO Pinpoint VALUES (`44`, `51,3538`, `4,466683`, ``, `Kruising St.Annastraat/Catharinastraat`);\n" +
                "INSERT INTO Pinpoint VALUES (`45`, `51,3537`, `4,46575`, `Einde stadswandeling`, `Eindpunt`);\n";
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
