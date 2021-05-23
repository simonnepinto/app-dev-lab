package com.simonne.counterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class xmlActivity extends AppCompatActivity {

    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);

        textView1 = (TextView)findViewById(R.id.textView1);
        parseXML();
    }

    public void parseXML(){
        try{
            InputStream is1 = getAssets().open("Recipe.xml");   //open the file
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(is1);

            NodeList nodeList = document.getElementsByTagName("recipe");

            for(int i=0; i<nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element)node;
                    textView1.setText(textView1.getText() + "\nDish Name: " + getValue("name", element));
                    textView1.setText(textView1.getText() + "\nQuantity: " + getValue("quantity", element));
                    textView1.setText(textView1.getText() + "\nServes: " + getValue("serves", element));
                    textView1.setText(textView1.getText() + "\nType: " + getValue("type", element));
                    textView1.setText(textView1.getText() + "\nCooking Time: " + getValue("cooking", element));
                    textView1.setText(textView1.getText() + "\n_____________");
                }
            }
            is1.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private String getValue(String tag, Element element1){
        NodeList nodeList1 = element1.getElementsByTagName(tag).item(0).getChildNodes();
        Node node1 = nodeList1.item(0);
        return node1.getNodeValue();
    }
    public void back(View view) {
        finish();
    }
}