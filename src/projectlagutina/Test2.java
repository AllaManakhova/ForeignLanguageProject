package projectlagutina;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Test2 {

    public static void Test(Model ml) throws UnsupportedEncodingException, IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.NONE);
        
        File asw2 = new File(ml.getAdress() + "asw2.txt");
        FileWriter writer = new FileWriter(asw2); 

        ScrollPane preRoot = new ScrollPane();
        preRoot.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        preRoot.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        HBox root = new HBox();
        preRoot.setContent(root);
        GridPane gpAsw = new GridPane();

        HBox vbAsw = new HBox(10);

        root.setPadding(new Insets(10, 10, 10, 10));
        gpAsw.setPadding(new Insets(10, 10, 10, 10));
        gpAsw.setHgap(10);
        gpAsw.setVgap(10);
        vbAsw.setPadding(new Insets(10, 10, 10, 10));

        TextArea text = new TextArea();
        root.getChildren().add(text);
        text.setWrapText(true);//текст разбивается на строки (убираем ScrollBar)
        text.setEditable(false);
        
        List<String> quest;
        quest = ml.readTask2();
        
//        Button btnClose = new Button("Close");
//        btnClose.setOnAction(ae -> stage.close());
//       
       int asw = Integer.parseInt(quest.get(0));//количество вопросов для Unicode
       
        LinkedList <TextField> answers = new LinkedList<>();  
        String tmp = null;
        int i = 0;
        for (i = 1; i < quest.size(); i++) {//считываем текст в TextArea 
            tmp = quest.get(i);
            text.appendText(tmp);//добавляет новую строку в конец предыдущего текста
            text.appendText("\n");
        }

        for (i = 1; i <= asw; i++) { //добавляем текстовые поля + TextField для ответа
            Label lab = new Label(i + ") ");
            TextField ntf = new TextField(Integer.toString(i));
            TextField tf = new TextField();
            TextField etf = new TextField("\n");
            answers.add(ntf);
            answers.add(tf);
            answers.add(etf);
            
            gpAsw.add(lab, 0, i);
            gpAsw.add(tf, 1, i);
        }
        
        
            Button btnClose = new Button("Close");
            btnClose.setOnAction(ae -> {
                for(TextField n: answers) {
                    try {
                        writer.write (n.getText() + "\r\n");
                        System.out.println(n.getText());
                    } catch (IOException ex) {
                        Logger.getLogger(Test2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                       
                   } 
               stage.close();
                   });
        
        gpAsw.add(btnClose, 0, i + 1);

        vbAsw.getChildren().add(gpAsw);
        root.getChildren().add(gpAsw);

        Scene scene = new Scene(preRoot);

        scene.getStylesheets().add(Main.class.getResource("newCascadeStyleSheetTest1.css").toExternalForm());

        stage.setScene(scene);
        stage.setHeight(600);
        stage.setTitle("Test 2");
        stage.showAndWait();
        
        writer.close();

    }

}
