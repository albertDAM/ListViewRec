package com.dam.eva.listviewrec;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Parsejador extends AppCompatActivity{

    public List<Temp> parsejaJSon(String json) throws JSONException {

        String time = null;
        String temperature = null;
        String calorFred = null;
        Temp temp;
        String humid=null;

        List<Temp> llista = new ArrayList<Temp>();
        String direccion="";

        //https://www.jetbrains.com/help/idea/2017.3/set-up-a-git-repository.html#clone-repo

        //Creem un objecte JSONObject para poder acceder als atributs o camps
        JSONObject respuestaJSON = null;   //Creo un JSONObject a partir del StringBuilder passat
        try {
            respuestaJSON = new JSONObject(json);

            JSONArray resultJSON = respuestaJSON.getJSONArray("list");
            if (resultJSON.length()>0) {
                Integer i = 0;
                while (i<resultJSON.length()){
                    temperature=resultJSON.getJSONObject(i).getString("main");
                    //accedir al nivell seguent interior

                    Log.d("test", temperature);

                    if (temperature != null) {
                        if (Double.parseDouble(temperature)>=20) {
                            calorFred = "hot";

                        }else calorFred = "cold";
                    }

                    time = resultJSON.getJSONObject(i).getString("dt_txt");
                    temp = new Temp(time, temperature, calorFred, humid);
                    llista.add(temp);
                }
            }
            //accedim al vector de resultats

            //Log.d("test", "dades: "   + direccion);



        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("test", "parsejaJSon: " + e.getMessage());
        } catch (Exception e){
            Log.d("test", "exc: " + e.getMessage());

        }
        return llista;
    }
    public List<Bloc> parseja(String xml) throws XmlPullParserException {



        String time=null;
        String temperature=null;
        String calorFred=null;
        Bloc bloc;

        List<Bloc> llista = new ArrayList<Bloc>();
        String direccio="";

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        //new JSONObject() new JSONArray()

        xpp.setInput(new StringReader(xml));
        int eventType=xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if(xpp.getName().equals("time")) {
                    time = xpp.getAttributeValue(null, "from");
                }
                if (xpp.getName().equals("temperature")) {
                    temperature = xpp.getAttributeValue(null, "value");
                }
                if (temperature != null) {
                    if (Double.parseDouble(temperature)>=20) {
                        calorFred = "hot";

                    }else calorFred = "cold";
                }

                bloc = new Bloc(time, temperature, calorFred);
                llista.add(bloc);
                Log.d("test", "parseja" + time + temperature + calorFred);
            }
            try {
                eventType = xpp.next();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return llista;

    }


}
