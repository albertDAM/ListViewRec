package com.dam.eva.listviewrec;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Parsejador extends AppCompatActivity{

    public List<Bloc> parseja(String xml) throws XmlPullParserException {

        String time=null;
        String temperature=null;
        String calorFred=null;
        Bloc bloc;

        List<Bloc> llista = new ArrayList<Bloc>();

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
